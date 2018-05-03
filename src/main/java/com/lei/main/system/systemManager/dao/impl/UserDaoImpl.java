package com.lei.main.system.systemManager.dao.impl;

import com.lei.main.comm.dao.jdbc.BaseDaoImpl;
import com.lei.main.system.systemManager.bean.User;
import com.lei.main.system.systemManager.dao.UserDao;
import com.lei.util.TableName;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User getUserById(String id) {
        return (User) sessionFactory.getCurrentSession().get(User.class, Integer.parseInt(id));
    }

    @Override
    public Map<String, Object> getUserList(Map<?, ?> params) throws Exception {
        return null;
    }

    @Override
    public int removeUser(String id) throws Exception {
        return 0;
    }

    @Override
    public User getUserByPhone(String phone) {
        String sql = "select * from "+ TableName.User+" s where s.telephone = '"+phone+"'";
        List<User> list = sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(User.class).list();
        if(list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public User getUserByUserName(String name) {
        String sql = "select * from "+ TableName.User+" s where s.user_name = '"+name+"'";
        List<User> list = sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(User.class).list();
        if(list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Boolean saveUser(User user) {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(user);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
