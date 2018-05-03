package com.lei.main.system.group.service.impl;

import com.lei.main.system.group.bean.Group;
import com.lei.main.system.group.bean.GroupCourse;
import com.lei.main.system.group.bean.GroupUser;
import com.lei.main.system.group.dao.GroupDao;
import com.lei.main.system.group.service.GroupService;
import com.lei.main.system.systemManager.bean.User;
import com.lei.main.system.systemManager.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("groupService")
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Group getGroupById(String id) {
        return groupDao.getGroupById(id);
    }

    @Override
    public List getUserGroupList(String id) {
        return groupDao.getUserGroupList(id);
    }

    @Override
    public Boolean saveGroupInfo(Group group) {
        return groupDao.saveGroupInfo(group);
    }

    @Override
    public void deleteGroup(String id) throws Exception {
        groupDao.deleteGroup(id);
    }

    @Override
    public GroupUser getGroupUserById(String id) {
        return groupDao.getGroupUserById(id);
    }

    @Override
    public GroupUser getGroupUserById(String gId, String uId) {
        return groupDao.getGroupUserById(gId, uId);
    }

    @Override
    public Boolean saveGroupUserInfo(GroupUser g) {
        return groupDao.saveGroupUserInfo(g);
    }

    @Override
    public Boolean saveGroupUserList(Group g, String uids, String inviter) throws Exception {
        String[] id = uids.split(",");
        int num = 0;
        for (int i = 0; i < id.length; i++) {
            if ((!id[i].equals("")) && id[i] != null) {
                User user = userDao.getUserById(id[i]);//判断用户是否存在
                if (user != null) {
                    GroupUser groupUser = groupDao.getGroupUserById(g.getId().toString(),id[i]);//判断邀请是否存在
                    if (groupUser != null) {
                        if (groupUser.getStatus() == 1) {
                            continue;//已接受邀请则跳过
                        } else {
                            groupUser.setInvitation(Integer.parseInt(inviter));//未接受或拒绝则更新邀请人及状态
                        }
                    } else {
                        groupUser = new GroupUser(g.getId(), Integer.parseInt(id[i]), Integer.parseInt(inviter), 0);
                    }
                    groupDao.saveGroupUserInfo(groupUser);
                    num++;
                }
            }
        }
        g.addGroupNum(num);
        groupDao.saveGroupInfo(g);
        return true;
    }

    @Override
    public void deleteGroupUser(String id) throws Exception {
        groupDao.deleteGroupUser(id);
    }

    @Override
    public List getGroupCourseList(String id) {
        return groupDao.getGroupCourseList(id);
    }

    @Override
    public Boolean saveGroupCourse(GroupCourse g) {
        return groupDao.saveGroupCourse(g);
    }
}
