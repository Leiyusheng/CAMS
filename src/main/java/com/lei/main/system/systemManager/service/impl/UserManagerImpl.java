package com.lei.main.system.systemManager.service.impl;

import com.lei.main.system.systemManager.bean.User;
import com.lei.main.system.systemManager.dao.UserDao;
import com.lei.main.system.systemManager.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service("userManager")
public class UserManagerImpl implements UserManager {

    @Autowired
    private UserDao userDao;

    @Override
    public Boolean saveUser(User user) {
        return userDao.saveUser(user);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }

    @Override
    public User getUserByUserName(String name) {
        return userDao.getUserByUserName(name);
    }

    @Override
    public int removeUser(String id) throws Exception {
        return 0;
    }

    @Override
    public Map<String, Object> getUserList(Map<?, ?> params) throws Exception {
        return null;
    }

    @Override
    public User getUserById(String id) {
        return userDao.getUserById(id);
    }

    @Override
    public void updateUser(int userId, List<String> userRoleIds) throws Exception {

    }
}
