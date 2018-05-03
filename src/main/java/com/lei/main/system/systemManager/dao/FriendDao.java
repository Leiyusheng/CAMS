package com.lei.main.system.systemManager.dao;

import com.lei.main.comm.dao.BaseDao;
import com.lei.main.system.systemManager.bean.Friend;

import java.util.List;

public interface FriendDao extends BaseDao {

    List getFriendsList(String id);

    List getFriendApplyList(String id);

    void saveFriendship(Friend friend);

    Friend getFriendshipById(String id);

    Friend getFriendshipById(String id1, String id2);
}
