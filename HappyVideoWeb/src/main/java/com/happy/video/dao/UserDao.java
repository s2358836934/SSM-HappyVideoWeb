package com.happy.video.dao;

import com.happy.video.entity.User;

import java.util.HashMap;
import java.util.List;

/**
 * 用户接口
 */
public interface UserDao {

    /**
     * 添加用户
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 根据条件查询用户
     * @param map
     * @return
     */
    List<User> findUserByCondition(HashMap<String, Object> map);

    /**
     * 修改用户
     * @param map
     * @return
     */
    int updateUser(HashMap<String, Object> map);


}
