package com.buba.service;

import com.buba.pojo.User;

/**
 * 登录、注销、修改密码的service接口
 */
public interface LoginService {

    /**
     * 登录
     * @param userCode
     * @param userPassword
     * @return
     */
    User login(String userCode, String userPassword);

    /**
     * 修改密码
     * @param id
     * @param pwd
     * @return true:修改成功；false：修改失败
     */
    boolean updatePassword(String id, String pwd);
}
