package com.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mp.po.User;
import com.mp.mapper.UserMapper;
import com.mp.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    @Transactional
    public void deductBalanceById(Long id, Integer money) {
        //1.查询用户
        User user = this.getById(id);
        //2.校验用户状态
        if(user == null || user.getStatus() == 2) {
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
                .set(remainBalance == 0, User::getStatus, 2)
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
}
