package com.lei.main.system.systemManager.dao;

import com.lei.main.comm.dao.BaseDao;
import com.lei.main.system.systemManager.bean.User;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;
import java.util.Map;

public interface UserDao extends BaseDao {

    int removeUser(String id)throws Exception;

    Map<String,Object> getUserList(Map<?, ?> params)throws Exception;

    User getUserById(String id);

    User getUserByPhone(String phone);

    User getUserByUserName(String name);

    Boolean saveUser(User user);
}
