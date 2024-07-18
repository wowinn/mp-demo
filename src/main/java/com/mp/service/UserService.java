package com.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mp.po.User;

import java.util.List;

public interface UserService extends IService<User> {
    void deductBalanceById(Long id, Integer money);

    List<User> queryUsers(String name, Integer status, Integer minBalance, Integer maxBalance);
}
