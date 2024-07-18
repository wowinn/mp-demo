package com.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mp.dto.PageDTO;
import com.mp.po.User;
import com.mp.query.UserQuery;
import com.mp.vo.UserVO;

import java.util.List;

public interface UserService extends IService<User> {
    void deductBalanceById(Long id, Integer money);

    List<User> queryUsers(String name, Integer status, Integer minBalance, Integer maxBalance);

    UserVO queryUserAndAddressById(Long id);

    List<UserVO> queryUserAndAddressByIds(List<Long> ids);

    PageDTO<UserVO> queryUsersPage(UserQuery query);
}
