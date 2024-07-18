package com.mp.controller;

import cn.hutool.core.bean.BeanUtil;
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
        User user = userService.getById(id);
        return BeanUtil.copyProperties(user, UserVO.class);
    }

    @GetMapping
    public List<UserVO> queryUserByIds(@RequestParam("ids") List<Long> ids) {
        List<User> users = userService.listByIds(ids);
        return BeanUtil.copyToList(users, UserVO.class);
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
}
