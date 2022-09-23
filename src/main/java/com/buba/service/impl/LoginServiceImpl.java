package com.buba.service.impl;

import com.buba.dao.LoginDao;
import com.buba.pojo.User;
import com.buba.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;

/**
 * @author chenrui
 * @version 1.0
 * @description: TODO
 * @date 2022/8/22 09:25
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginDao loginDao;

    /**
     * 登录
     * @param userCode
     * @param userPassword
     * @return
     */
    @Override
    public User login(String userCode, String userPassword) {
        return loginDao.login(userCode, userPassword);
    }

    /**
     * 修改密码
     * @param id
     * @param pwd
     * @return true:修改成功；false：修改失败
     */
    @Override
    public boolean updatePassword(String id, String pwd) {
        boolean flag = false;
        int num = 0;

        num = loginDao.updatePassword(id, pwd);

        if (num > 0){
            // 修改成功
            flag = true;
        }

        return flag;
    }
}
