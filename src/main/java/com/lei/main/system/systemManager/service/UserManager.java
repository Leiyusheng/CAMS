package com.lei.main.system.systemManager.service;

import com.lei.main.system.systemManager.bean.User;

import java.util.List;
import java.util.Map;


public interface UserManager {
    
    /**保存用户信息
     * @param user
     * @throws Exception
     */
    Boolean saveUser(User user);

    /**
     * 用户修改
     */
    void updateUser(int userId, List<String> userRoleIds)throws Exception;

    /**删除用户信息
     * @param id
     * @return
     * @throws Exception
     */
    int removeUser(String id)throws Exception;

    /**获取用户列表
     * @param params
     * @return
     * @throws Exception
     */
    Map<String,Object> getUserList(Map<?, ?> params)throws Exception;

    /**通过id获取User
     * @param id
     * @return
     * @throws Exception
     */
    User getUserById(String id);

    User getUserByPhone(String phone);

    User getUserByUserName(String name);

}
