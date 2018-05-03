package com.lei.main.system.group.bean;

import com.lei.main.comm.util.Common;
import com.lei.util.TableName;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Timestamp;

@ApiModel("群组用户")
@Entity
@Table(name= TableName.GroupUser)
public class GroupUser {
    @ApiModelProperty("编号")
    private Integer id;
    @ApiModelProperty("群组编号")
    private Integer groupId;
    @ApiModelProperty("用户编号")
    private Integer userId;
    @ApiModelProperty("邀请人编号")
    private Integer inviter;
    @ApiModelProperty("状态")
    private Integer status;//0待定，1加入，2拒绝
    private Timestamp updateTime;

    public GroupUser() {}

    public GroupUser(Integer id) {
        this.id = id;
    }

    public GroupUser(Integer groupId, Integer userId, Integer inviter, Integer status) {
        this.groupId = groupId;
        this.userId = userId;
        this.inviter = inviter;
        this.status = status;
    }

    public void setInvitation(Integer inviter) {
        this.inviter = inviter;
        this.status = 0;
        this.updateTime = Timestamp.valueOf(Common.getNow());
    }

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Column(name="group_id")
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
    @Column(name="user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    @Column(name="inviter")
    public Integer getInviter() {
        return inviter;
    }

    public void setInviter(Integer inviter) {
        this.inviter = inviter;
    }
    @Column(name="status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    @Column(name="update_time")
    public Timestamp  getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
