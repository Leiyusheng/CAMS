package com.lei.main.comm.bean;

import com.lei.main.system.systemManager.bean.User;


public class UserSession extends Entity {
    private static final long serialVersionUID = 1L;
    private User user;
    private String sessionId;
    private String roleIds;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }
}
