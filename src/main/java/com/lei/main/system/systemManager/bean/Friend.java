package com.lei.main.system.systemManager.bean;

import com.lei.main.comm.util.Common;
import com.lei.util.TableName;
import com.wordnik.swagger.annotations.ApiModel;

import javax.persistence.*;

@ApiModel("好友")
@Entity
@Table(name= TableName.Friend)
public class Friend {
    private Integer id;
    private Integer user1;
    private Integer user2;
    private Integer status;//-1已删除，0未处理，1已建立
    private Integer apply;//-1已删除，0未处理，1正在申请，2已添加
    private String remark;
    private String alias;
    private String updateTime;

    public Friend() {}

    public Friend(Integer user1, Integer user2, Integer apply, String remark, String alias) {
        this.user1 = user1;
        this.user2 = user2;
        this.status = 0;
        this.apply = apply;
        this.remark = remark;
        this.alias = alias;
        this.updateTime = Common.getNow();
    }

    public void updateApply(Integer apply) {
        this.apply = apply;
        this.updateTime = Common.getNow();
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
    @Column(name="user1")
    public Integer getUser1() {
        return user1;
    }

    public void setUser1(Integer user1) {
        this.user1 = user1;
    }
    @Column(name="user2")
    public Integer getUser2() {
        return user2;
    }

    public void setUser2(Integer user2) {
        this.user2 = user2;
    }
    @Column(name="status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    @Column(name="apply")
    public Integer getApply() {
        return apply;
    }

    public void setApply(Integer apply) {
        this.apply = apply;
    }
    @Column(name="remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Column(name="alias")
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Column(name="update_time")
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
