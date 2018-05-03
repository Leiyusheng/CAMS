package com.lei.main.system.systemManager.service;

import com.lei.main.system.systemManager.bean.Friend;

import java.util.List;

public interface FriendService {

    List getFriendsList(String id);

    List getFriendApplyList(String id);
    /**
     * 更新一个好友信息
     * @param friend
     * @return
     */
    Boolean updateFriendship(Friend friend);
    /**
     * 更新一组好友信息
     * @param ship1 关系1
     * @param ship2 关系2
     * @return
     */
    Boolean updateFriendship(Friend ship1, Friend ship2);

    Friend getFriendshipById(String id);

    Friend getFriendshipById(String id1, String id2);
}
