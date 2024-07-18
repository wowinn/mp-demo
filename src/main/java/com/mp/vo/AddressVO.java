package com.mp.vo;

import lombok.Data;

import java.util.List;


@Data
public class AddressVO{

    private Long id;

    private Long userId;

    private String province;

    private String city;

    private String town;

    private String mobile;

    private String street;

    private String contact;

    private Boolean isDefault;

    private String notes;
}
