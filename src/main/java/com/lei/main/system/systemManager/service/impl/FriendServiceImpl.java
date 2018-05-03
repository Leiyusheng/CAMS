package com.lei.main.system.systemManager.service.impl;

import com.lei.main.system.systemManager.bean.Friend;
import com.lei.main.system.systemManager.dao.FriendDao;
import com.lei.main.system.systemManager.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("friendService")
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendDao friendDao;

    @Override
    public List getFriendsList(String id) {
        return friendDao.getFriendsList(id);
    }

    @Override
    public List getFriendApplyList(String id) {
        return friendDao.getFriendApplyList(id);
    }

    @Override
    public Boolean updateFriendship(Friend friend) {
        try {
            friendDao.saveFriendship(friend);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Boolean updateFriendship(Friend ship1, Friend ship2) {
        friendDao.saveFriendship(ship1);
        friendDao.saveFriendship(ship2);
        return true;
    }

    @Override
    public Friend getFriendshipById(String id) {
        return friendDao.getFriendshipById(id);
    }

    @Override
    public Friend getFriendshipById(String id1, String id2) {
        return friendDao.getFriendshipById(id1, id2);
    }
}
