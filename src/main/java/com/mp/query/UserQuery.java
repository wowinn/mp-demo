package com.mp.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserQuery extends PageQuery {

    private String name;

    private Integer status;

    private Integer minBalance;

    private Integer maxBalance;
}
