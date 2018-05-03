package com.lei.main.system.group.dao.impl;

import com.lei.main.comm.dao.jdbc.BaseDaoImpl;
import com.lei.main.system.group.bean.Group;
import com.lei.main.system.group.bean.GroupCourse;
import com.lei.main.system.group.bean.GroupUser;
import com.lei.main.system.group.dao.GroupDao;
import com.lei.util.TableName;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("groupDao")
public class GroupDaoImpl extends BaseDaoImpl implements GroupDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Group getGroupById(String id) {
        return (Group) sessionFactory.getCurrentSession().get(Group.class, Integer.parseInt(id));
    }

    @Override
    public List getUserGroupList(String id) {
        String sql = "select g.id,g.name from " + TableName.Group + " g left join "+
                TableName.GroupUser+ " u on g.id = u.group_id where u.user_id = '" + id + "' and u.status = 1";
        return getRecordData(sql, null, jdbcTemplate);
    }

    @Override
    public Boolean saveGroupInfo(Group group) {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(group);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void deleteGroup(String id) throws Exception {
        Group group = new Group(Integer.parseInt(id));
        sessionFactory.getCurrentSession().delete(group);
    }

    @Override
    public GroupUser getGroupUserById(String id) {
        return (GroupUser) sessionFactory.getCurrentSession().get(GroupUser.class, Integer.parseInt(id));
    }

    @Override
    public GroupUser getGroupUserById(String gId, String uId) {
        String sql = "select * from "+ TableName.GroupUser + " s where s.group_id = '"+gId+"' and s.user_id = '"+uId+"'";
        List<GroupUser> list = sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(GroupUser.class).list();
        if(list.size() > 0) {
            return list.get(0);
        } else  {
            return null;
        }
    }

    @Override
    public Boolean saveGroupUserInfo(GroupUser g) {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(g);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void deleteGroupUser(String id) throws Exception {
        GroupUser groupUser = new GroupUser(Integer.parseInt(id));
        sessionFactory.getCurrentSession().delete(groupUser);
    }

    @Override
    public List getGroupCourseList(String id) {
        String sql = "select c.course_id from " + TableName.GroupCourse + " c where c.group_id = '" + id + "'";
        return getRecordData(sql, null, jdbcTemplate);
    }

    @Override
    public Boolean saveGroupCourse(GroupCourse g) {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(g);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
