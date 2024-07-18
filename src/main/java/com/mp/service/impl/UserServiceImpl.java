package com.mp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.mp.dto.PageDTO;
import com.mp.enums.UserStatus;
import com.mp.po.Address;
import com.mp.po.User;
import com.mp.mapper.UserMapper;
import com.mp.query.UserQuery;
import com.mp.service.UserService;
import com.mp.vo.AddressVO;
import com.mp.vo.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    @Transactional
    public void deductBalanceById(Long id, Integer money) {
        //1.查询用户
        User user = this.getById(id);
        //2.校验用户状态
        if(user == null || user.getStatus() == UserStatus.FROZEN) {
            throw new RuntimeException("用户状态异常!");
        }
        //3.校验余额是否充足
        if(user.getBalance() < money) {
            throw new RuntimeException("用户余额不足!");
        }
        //4.扣减余额
        int remainBalance = user.getBalance() - money;
        lambdaUpdate()
                .set(User::getBalance, remainBalance)
                .set(remainBalance == 0, User::getStatus, UserStatus.FROZEN)
                .eq(User::getId, id)
                .eq(User::getBalance, user.getBalance())    //乐观锁
                .update();
        //baseMapper.deductBalanceById(id, money);
    }

    @Override
    public List<User> queryUsers(String name, Integer status, Integer minBalance, Integer maxBalance) {
        return lambdaQuery()
                .like(name != null, User::getUsername, name)
                .eq(status != null, User::getStatus, status)
                .ge(minBalance != null, User::getBalance, minBalance)
                .le(maxBalance != null, User::getBalance, maxBalance)
                .list();
    }

    @Override
    public UserVO queryUserAndAddressById(Long id) {
        //查询用户
        User user = getById(id);
        if(user == null || user.getStatus() == UserStatus.FROZEN) {
            throw new RuntimeException("用户状态异常！");
        }
        //查询地址
        List<Address> addresses = Db.lambdaQuery(Address.class)
                .eq(Address::getUserId, id)
                .list();
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        if(CollUtil.isNotEmpty(addresses)) {
            userVO.setAddresses(BeanUtil.copyToList(addresses, AddressVO.class));
        }
        return userVO;
    }

    @Override
    public List<UserVO> queryUserAndAddressByIds(List<Long> ids) {
        //查询用户
        List<User> users = listByIds(ids);
        if(CollUtil.isEmpty(users))
            return Collections.emptyList();
        //查询地址
        List<Long> userIds = users.stream().map(User::getId).toList();
        List<Address> addresses = Db.lambdaQuery(Address.class).in(Address::getUserId, userIds).list();
        List<AddressVO> addressVOList = BeanUtil.copyToList(addresses, AddressVO.class);

        Map<Long, List<AddressVO>> addressMap = new HashMap<>(0);
        if(CollUtil.isNotEmpty(addressVOList)) {
            addressMap = addressVOList.stream().collect(Collectors.groupingBy(AddressVO::getUserId));
        }
        List<UserVO> userVOList = new ArrayList<>(users.size());
        for (User user: users) {
            UserVO vo = BeanUtil.copyProperties(user, UserVO.class);
            vo.setAddresses(addressMap.get(user.getId()));
            userVOList.add(vo);
        }
        return userVOList;
    }

    @Override
    public PageDTO<UserVO> queryUsersPage(UserQuery query) {
        //构造条件
        String name = query.getName();
        Integer status = query.getStatus();

//        Page<User> page = Page.of(query.getPageNo(), query.getPageSize());
//        if(StrUtil.isNotBlank(query.getSortBy())) {
//            page.addOrder(query.getIsAsc() ? OrderItem.asc(query.getSortBy()) : OrderItem.desc(query.getSortBy()));
//        } else {
//            page.addOrder(OrderItem.desc("update_time"));
//        }
        Page<User> page = query.toMpPageDefaultSortByUpdateTime();
        //分页查询
        Page<User> p = lambdaQuery()
                .like(name != null, User::getUsername, name)
                .eq(status != null, User::getStatus, status)
                .page(page);
//        //封装VO结构
//        PageDTO<UserVO> dto = new PageDTO<>();
//        dto.setTotal(p.getTotal());
//        dto.setPages(p.getPages());
//        List<User> records = p.getRecords();
//        if(CollUtil.isEmpty(records)) {
//            dto.setList(Collections.emptyList());
//            return dto;
//        }
//        dto.setList(BeanUtil.copyToList(records, UserVO.class));
//        return dto;
        return PageDTO.of(p, UserVO.class);
    }
}
