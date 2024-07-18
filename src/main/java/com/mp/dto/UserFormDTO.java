package com.mp.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

@Data
public class UserFormDTO {

    private Long id;

    private String username;

    private String password;

    private String phone;

    private String info;

    private Integer balance;
}
