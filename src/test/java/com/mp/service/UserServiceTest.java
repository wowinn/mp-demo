package com.mp.service;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mp.po.User;
import com.mp.po.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void testSaveUser() {
        User user = new User();
        //user.setId(5L);
        user.setUsername("Lucy11");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
        //user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setInfo(UserInfo.of(24, "英文老师", "female"));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userService.save(user);
    }

    @Test
    void testQuery() {
        List<User> users = userService.listByIds(List.of(1L, 2L, 4L));
        users.forEach(System.out::println);
    }

    @Test
    void testPageQuery() {
        int pageNo = 1, pageSize = 3;
        Page<User> page = Page.of(pageNo, pageSize);
        page.addOrder(OrderItem.asc("balance"));
        page.addOrder(OrderItem.asc("id"));
        Page<User> p = userService.page(page);
        long total = p.getTotal();
        long pages = p.getPages();
        System.out.println("total = " + total);
        System.out.println("pages = " + pages);
        List<User> users = p.getRecords();
        users.forEach(System.out::println);
    }
}
