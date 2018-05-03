package com.lei.main.system.systemManager.dao.impl;

import com.lei.main.comm.dao.jdbc.BaseDaoImpl;
import com.lei.main.system.systemManager.bean.Friend;
import com.lei.main.system.systemManager.bean.User;
import com.lei.main.system.systemManager.dao.FriendDao;
import com.lei.util.TableName;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("friendDao")
public class FriendDaoImpl extends BaseDaoImpl implements FriendDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List getFriendsList(String id) {
        String sql = "select u.id,f.alias as name,u.remark from " + TableName.User + " u," + TableName.Friend + " f where f.user1 = '" + id +
                "' and f.user2 = u.id and f.status = 1 order by convert_to(alias,'GBK')";
        return getRecordData(sql, null, jdbcTemplate);
    }

    @Override
    public List getFriendApplyList(String id) {
        String sql = "select f.id,u.id as uid,u.user_name,f.apply as status,f.remark from " + TableName.User + " u," + TableName.Friend + " f where f.user1 = '" + id +
                "' and f.user2 = u.id and f.apply != -1 order by f.update_time desc";
        return getRecordData(sql, null, jdbcTemplate);
    }

    @Override
    public void saveFriendship(Friend friend) {
        sessionFactory.getCurrentSession().saveOrUpdate(friend);
    }

    @Override
    public Friend getFriendshipById(String id) {
        return (Friend)sessionFactory.getCurrentSession().get(Friend.class, Integer.parseInt(id));
    }

    @Override
    public Friend getFriendshipById(String id1, String id2) {
        String sql = "select * from "+ TableName.Friend + " s where s.user1 = '"+id1+"' and s.user2 = '"+id2+"'";
        List<Friend> list = sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Friend.class).list();
        if(list.size() > 0) {
            return list.get(0);
        } else  {
            return null;
        }
    }
}
