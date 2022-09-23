package com.buba.dao;

import com.buba.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.sql.Connection;

/**
 * 登录、注销、修改密码dao的接口
 */
public interface LoginDao {

    /**
     * 登录
     * @param userCode
     * @param userPassword
     * @return
     */
    User login(@Param("userCode") String userCode, @Param("userPassword") String userPassword);

    /**
     * 修改密码
     * @param id
     * @param pwd
     * @return
     */
    int updatePassword(@Param("id") String id, @Param("pwd") String pwd);

}
