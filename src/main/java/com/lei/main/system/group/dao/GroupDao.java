package com.lei.main.system.group.dao;

import com.lei.main.comm.dao.BaseDao;
import com.lei.main.system.group.bean.Group;
import com.lei.main.system.group.bean.GroupCourse;
import com.lei.main.system.group.bean.GroupUser;

import java.util.List;

public interface GroupDao extends BaseDao {

    Group getGroupById(String id);

    List getUserGroupList(String id);

    Boolean saveGroupInfo(Group group);

    void deleteGroup(String id)throws Exception;

    GroupUser getGroupUserById(String id);

    GroupUser getGroupUserById(String gId, String uId);

    Boolean saveGroupUserInfo(GroupUser g);

    void deleteGroupUser(String id)throws Exception;

    List getGroupCourseList(String id);

    Boolean saveGroupCourse(GroupCourse g);
}
