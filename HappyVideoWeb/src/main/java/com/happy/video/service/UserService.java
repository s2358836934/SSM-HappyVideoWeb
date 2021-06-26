package com.happy.video.service;

import com.happy.video.entity.User;

public interface UserService {

    // 验证email
    boolean checkEmail(String email);

    // 注册用户
    User regist(User user);

    User login(String email, String password);

    boolean resetPwd(String email, String password);

}
