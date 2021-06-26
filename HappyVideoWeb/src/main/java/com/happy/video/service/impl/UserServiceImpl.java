package com.happy.video.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.happy.video.dao.UserDao;
import com.happy.video.entity.User;
import com.happy.video.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    public boolean checkEmail(String email) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("email", email);

        List<User> list = userDao.findUserByCondition(map);

        if (list == null || list.size() == 0) {
            // 用户不存在
            return true;
        }

        // 用户已经存在
        return false;
    }

    public User regist(User user) {

        user.setCreateTime(new Date());
        user.setVipFlag(0);
        user.setPassword(DigestUtil.md5Hex(user.getPassword()));
        int code = userDao.insertUser(user);
        if (code != 1) {
            return null;
        }
        return user;
    }

    public User login(String email, String password) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("email", email);
        map.put("password", DigestUtil.md5Hex(password));

        List<User> list = userDao.findUserByCondition(map);

        if (list == null || list.size() == 0) {
            return null;
        }

        return list.get(0);

    }

    public boolean resetPwd(String email, String password) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("email", email);

        List<User> list = userDao.findUserByCondition(map);

        if (list == null || list.size() == 0) {
            return false;
        }

        User user = list.get(0);

        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("id", user.getId());
        map2.put("password", DigestUtil.md5Hex(password));

        int code = userDao.updateUser(map2);

        return code == 0 ? false : true;
    }
}
