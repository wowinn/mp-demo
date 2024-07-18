package com.mp.controller;

import cn.hutool.core.bean.BeanUtil;
import com.mp.dto.PageDTO;
import com.mp.dto.UserFormDTO;
import com.mp.po.User;
import com.mp.query.UserQuery;
import com.mp.service.UserService;
import com.mp.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    //使用final + @RequiredArgsConstructor实现service注入
    private final UserService userService;

    @PostMapping
    public void saveUser(@RequestBody UserFormDTO userFormDTO) {
        //DTO拷贝到PO
        User user = BeanUtil.copyProperties(userFormDTO, User.class);
        userService.save(user);
    }

    @DeleteMapping("{id}")
    public void deleteUserById(@PathVariable("id") Long id) {
        userService.removeById(id);
    }

    @GetMapping("{id}")
    public UserVO queryUserById(@PathVariable("id") Long id) {
        return userService.queryUserAndAddressById(id);
    }

    @GetMapping
    public List<UserVO> queryUserByIds(@RequestParam("ids") List<Long> ids) {
        return userService.queryUserAndAddressByIds(ids);
    }

    @PutMapping("/{id}/deduction/{money}")
    public void deductBalanceById(@PathVariable("id") Long id, @PathVariable("money") Integer money) {
        userService.deductBalanceById(id, money);
    }

    @GetMapping("/list")
    public List<UserVO> queryUsers(UserQuery query) {
        List<User> users = userService.queryUsers(query.getName(), query.getStatus(), query.getMinBalance(), query.getMaxBalance());
        return BeanUtil.copyToList(users, UserVO.class);
    }

    @GetMapping("/page")
    public PageDTO<UserVO> queryUsersPage(UserQuery query) {
        return userService.queryUsersPage(query);
    }
}
