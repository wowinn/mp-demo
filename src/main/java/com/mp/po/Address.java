package com.mp.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Address {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    private String province;

    private String city;

    private String town;

    private String mobile;

    private String street;

    private String contact;

    @TableField("is_default")
    private boolean isDefault;

    private String notes;

    private boolean deleted;
}
