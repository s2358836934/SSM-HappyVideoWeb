package com.happy.video.controller;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.happy.video.dto.ResponseResult;
import com.happy.video.dto.UserToken;
import com.happy.video.entity.User;
import com.happy.video.exception.UserException;
import com.happy.video.service.UserService;
import com.happy.video.service.impl.UserServiceImpl;
import com.happy.video.util.Constants;
import com.happy.video.util.VideoUtil;
import com.sun.xml.internal.ws.api.policy.PolicyResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;

@Controller
public class UserController {

    @Autowired
    UserService userService;


    // 验证用户账号密码正确
    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult checkLogin(String email, String password) {

        ResponseResult responseResult = new ResponseResult();

        User user = userService.login(email, password);
        if (user == null) {
            responseResult.setCode(0);
        } else {
            responseResult.setCode(1);
        }

        // 清除cookie，服务器的usertoken

        return responseResult;
    }


    // 用户登录
    @RequestMapping("/login")
    public String login(String email, String password, String autoLogin,
                        HttpSession session,
                        HttpServletRequest request,
                        HttpServletResponse response) throws UserException {

        // 数据验证
        // 判断账号和密码是否正确
        // 正确 登录 保存登录状态 重定向到首页
        // 错误 失败

        if (!ReUtil.isMatch("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", email)) {
            throw new UserException("邮箱格式不正确");
        }
        if (StrUtil.isEmpty(password) || password.length() < 6) {
            throw new UserException("密码格式不正确");
        }
        User user = userService.login(email, password);

        if (user == null) {
            throw new UserException("账号密码不正确");
        }

        // session保存用户信息，登录状态
        session.setAttribute(Constants.SESSION_LOGINUSER, user);


        // 生成自动登录token标识（根据是否勾选自动登录决定）
        if (!StrUtil.isEmpty(autoLogin) && "1".equalsIgnoreCase(autoLogin)) {
            // token 生成规则
            // 时间，用户信息，IP，User-Agent
            UserToken userToken = new UserToken(request, user);

            String token = userToken.getToken();

            // 写入cookie，返回客户端
            Cookie cookie = new Cookie(Constants.COOKIE_NAME_LOGIN_TOKEN, token);
            // 设置cookie的参数
            cookie.setPath("/");
            // 单位秒 24小时
            cookie.setMaxAge(60 * 60 * 24);

            // cookie放入相应，返回客户端
            response.addCookie(cookie);

            // 保存token--user信息到application（Redis最好）

            ServletContext app = request.getServletContext();

            // application --> N个用户的登录信息
            HashMap<String, UserToken> userTokenMap = new HashMap<String, UserToken>();

            // 保存所有用户登录的 key（token）--value(userToken)
            userTokenMap.put(token, userToken);

            // 把map 存入 application
            app.setAttribute(Constants.CONTEXT_TOKEN_USER, userTokenMap);
        }

        return "redirect:/";
    }

    //    点击email充值密码
    @RequestMapping("/resetPwd")
    @ResponseBody
    public String resetPwd(String password, String p) throws UserException {

        // 判断凭证
        // 1 是否合法
        // 2 是否超时
        String urlParam = Base64.decodeStr(p);
        // c67d65208241875cd98a298622361a70_1612273431680_abc@qq.com
        String[] values = urlParam.split("_");
        String token = values[0];
        String time = values[1];
        String email = values[2];


        StringBuilder builder = new StringBuilder();
        builder.append(email);
        builder.append(time);
        builder.append(Constants.SERVER_SEC_KEY);

        String resetPwdToken = DigestUtil.md5Hex(builder.toString());

        if (!resetPwdToken.equals(token)) {
            throw new UserException("连接不合法");
        }

        long now = System.currentTimeMillis();

        if ((now - Long.parseLong(time)) > (1000 * 30)) {
            throw new UserException("连接已经超时");
        }

        // 修改数据库密码
        boolean suc = userService.resetPwd(email, password);

        return "reset password success";

    }


    //    点击email重置密码頁面
    @RequestMapping("/resetPwdPage")
    // http://www.duyi.com/resetPwd?p=cnB0PTU5ZGJlMTYyMGVjNTRiYzYxOWE1MzlhNDcyNDVjNGI4JnQ9MjAyMS0wMi0wMiAyMTozNzozMA==
    public String resetPwdPage(String p, Model model) throws UserException {

        // 判断凭证
        // 1 是否合法
        // 2 是否超时
        String urlParam = Base64.decodeStr(p);
        // c67d65208241875cd98a298622361a70_1612273431680_abc@qq.com
        String[] values = urlParam.split("_");
        String token = values[0];
        String time = values[1];
        String email = values[2];


        StringBuilder builder = new StringBuilder();
        builder.append(email);
        builder.append(time);
        builder.append(Constants.SERVER_SEC_KEY);

        String resetPwdToken = DigestUtil.md5Hex(builder.toString());

        if (!resetPwdToken.equals(token)) {
            throw new UserException("连接不合法");
        }

        long now = System.currentTimeMillis();

        if ((now - Long.parseLong(time)) > (1000 * 30)) {
            throw new UserException("连接已经超时");
        }

        model.addAttribute("p", p);

        return "resetPwd";
    }

    //     找回密码（发送email）
    @RequestMapping("/findPwd")
    @ResponseBody
    public String findPwd(String email) {

        // 发送email
        // 拼接URL（重置密码的请求）
        String resetUrl = "http://localhost:8080/resetPwdPage";

        // 生成找回密码凭证（token）
        // 不能伪造，过期不能修改
        StringBuilder builder = new StringBuilder();
        builder.append(email);
        String nowStr = System.currentTimeMillis() + "";
        builder.append(nowStr);
        builder.append(Constants.SERVER_SEC_KEY);

        String resetPwdToken = DigestUtil.md5Hex(builder.toString());
        // 第一段 凭证 第二段 时间 第三段 email
        String param = resetPwdToken + "_" + nowStr + "_" + email;
        //  base64
        String urlParam = Base64.encode(param);

        //
        String url = resetUrl + "?p=" + urlParam;

        System.out.println(url);

        // 发送email功能

        return "email send";
    }


    //    退出登录
    @RequestMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response,
                         HttpServletRequest request) {

        ServletContext servletContext = request.getServletContext();

        // 删除用户登录数据
        session.removeAttribute(Constants.SESSION_LOGINUSER);

        // 清空自动登录内容
        Cookie cookie = new Cookie(Constants.COOKIE_NAME_LOGIN_TOKEN, "invalid");
        cookie.setPath("/");
        cookie.setMaxAge(1);
        response.addCookie(cookie);

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie ck : cookies) {
                String cname = ck.getName();
                if (Constants.COOKIE_NAME_LOGIN_TOKEN.equalsIgnoreCase(cname)) {
                    HashMap<String, UserToken> userTokenmMap = (HashMap<String, UserToken>) servletContext.getAttribute(Constants.CONTEXT_TOKEN_USER);
                    // 用户自动登录的token值
                    userTokenmMap.remove(ck.getValue());
                }
            }
        }


        return "redirect:/";
    }


    // 注册
    // 注册成功 返回首页
    // 注册失败 : 用户名重复，
    //
    @RequestMapping("/regist")
    public String regist(String email, String password, String vcode,
                         HttpSession session) throws UserException {

        // 注册失败，返回错误页面
        if (!ReUtil.isMatch("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", email)) {
            throw new UserException("邮箱格式不正确");
        }
        if (StrUtil.isEmpty(password) || password.length() < 6) {
            throw new UserException("密码格式不正确");
        }
        String serverVcode = (String) session.getAttribute(Constants.SESSION_VCODE);
        if (StrUtil.isEmpty(serverVcode)) {
            throw new UserException("请求错误");
        }
        if (!serverVcode.equalsIgnoreCase(vcode)) {
            throw new UserException("验证码不正确");
        }
        // 提交数据到数据注册
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        User dbUser = userService.regist(user);
        if (dbUser == null) {
            // 数据库保存数据错误，返回错误页面
            throw new UserException("数据库错误");
        }

        // session保存用户信息，登录状态
        session.setAttribute(Constants.SESSION_LOGINUSER, user);

        // 注册成功，变成登录状态，重定向首页
        return "redirect:/";
    }

    // ajax 验证email
    // 返回json（相应数据格式）
    @RequestMapping("/checkEmail")
    @ResponseBody
    public ResponseResult checkEmail(String email) {

        // 服务器端的参数验证
        ResponseResult result = new ResponseResult();

        // 1 验证email，合法性，是否存在
        boolean res = userService.checkEmail(email);
        if (res) {
            // 账号可以注册
            result.setCode(1);
            result.setMessage("ok");
            return result;
        }
        result.setCode(-1);
        result.setMessage("fail");
        // 账号不可用
        return result;


    }
}
