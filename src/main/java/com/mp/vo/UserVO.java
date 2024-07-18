package com.mp.vo;


import com.mp.enums.UserStatus;
import com.mp.po.UserInfo;
import lombok.Data;

import java.util.List;

@Data
public class UserVO {

    private Long id;

    private String username;

    private UserInfo info;

    private UserStatus status;

    private Integer balance;

    private List<AddressVO> addresses;
}
