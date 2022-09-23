package com.buba.controller;

import com.alibaba.fastjson.JSON;
import com.buba.pojo.User;
import com.buba.service.LoginService;
import com.buba.service.impl.LoginServiceImpl;
import com.buba.tool.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 包含登录、注销、修改密码
 * @author chenrui
 * @version 1.0
 * @description: TODO
 * @date 2022/8/22 08:50
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    @Qualifier("loginService")
    private LoginService loginService;

    /**
     * 登录
     * @param model
     * @param userCode
     * @param userPassword
     * @return
     */
    @RequestMapping
    public String login(Model model, HttpSession session, String userCode, String userPassword) {

        User user = loginService.login(userCode, userPassword);
        String view = "";

        if (user != null){
            // 登录成功
            // 将user存入session
            session.setAttribute(Constants.USER_SESSION, user);

            view = "frame";
        } else {
            // 登录失败
            model.addAttribute("error", "用户名或密码错误");
            view = "../login";
        }

        return view;
    }

    /**
     * 注销
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logOut(HttpSession session) {
        session.removeAttribute(Constants.USER_SESSION);
        return "../login";
    }

    /**
     * 验证旧密码
     * @param session
     * @param pwd
     * @return
     */
    @RequestMapping("/checkpwd")
    @ResponseBody
    public String checkOldPassword(HttpSession session, @RequestParam("oldpassword") String pwd){
        String result = "";
        if (pwd == null || pwd.isEmpty()){
            // 旧密码为空
            result = "error";
        } else {
            User user = (User) session.getAttribute(Constants.USER_SESSION);
            if (user == null) {
                // session过期
                result = "sessionerror";
            } else {
                if (user.getUserPassword().equals(pwd)){
                    // 旧密码正确
                    result = "true";
                } else {
                    // 旧密码错误
                    result = "false";
                }
            }
        }

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return JSON.toJSONString(resultMap);
    }

    /**
     * 修改密码
     * @param model
     * @param session
     * @param pwd
     * @return
     */
    @RequestMapping("/updatePassword")
    public String updatePassword(Model model, HttpSession session, @RequestParam("newpassword") String pwd) {
        User user = (User) session.getAttribute(Constants.USER_SESSION);
        String id = user.getId().toString();

        // 修改密码   true:修改成功；false：修改失败
        boolean flag = loginService.updatePassword(id, pwd);
        if (flag) {
            // 修改成功
            session.removeAttribute(Constants.USER_SESSION);
            model.addAttribute("error", "密码修改成功，请重新登录");
            return "../login";
        } else {
            // 修改失败
            model.addAttribute("message", "密码修改失败");
            return "pwdmodify";
        }
    }
}
