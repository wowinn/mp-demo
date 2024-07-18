package com.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mp.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    void updateBalanceByIds(@Param("ew") UpdateWrapper<User> wrapper, @Param("amount") int amount);
//    void updateBalanceByIds(@Param(Constants.WRAPPER) UpdateWrapper<User> wrapper, @Param("amount") int amount);

    @Update("update user set balance = balance - #{money} where id = #{id}")
    void deductBalanceById(Long id, Integer money);
}
