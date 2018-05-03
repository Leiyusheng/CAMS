package com.lei.main.system.systemManager.bean;

import com.lei.main.comm.util.Common;
import com.lei.util.TableName;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@ApiModel("用户")
@Entity
@Table(name=TableName.User)
public class User {
    @ApiModelProperty("用户编号")
    private Integer userId;
    @ApiModelProperty("用户名称")
    private String userName;
    @ApiModelProperty("用户密码")
    private String userPassword;//(32位MD5加密)
    @ApiModelProperty("性别 0女，1男")
    private String sex;
    @ApiModelProperty("状态，0-无效，1-有效")
    private Integer status;
    @ApiModelProperty(value = "联系电话")
    private String telephone;
    @ApiModelProperty("学校")
    private Integer school;
    @ApiModelProperty("备注")
    private String remark;
    private Integer isDelete;//是否删除 0-未删 ， 1-删除
    @ApiModelProperty("迟到次数")
    private Integer lateTimes;
    @ApiModelProperty("早退次数")
    private Integer leaveTimes;
    @ApiModelProperty("旷课次数")
    private Integer cutTimes;

    public User() {}

    public User(String phone, String pwd) {
        this.userName = phone;
        this.telephone = phone;
        this.userPassword = pwd;
        this.status = 1;
        this.isDelete = 0;
        this.lateTimes = 0;
        this.leaveTimes = 0;
        this.cutTimes = 0;
    }

    public void cleanPrivateInfo() {
        this.userPassword = null;
    }

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    @Column(name="user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Column(name="user_password")
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    @Column(name="sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name="status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    @Column(name="telephone")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    @Column(name="school")
    public Integer getSchool() {
        return school;
    }

    public void setSchool(Integer school) {
        this.school = school;
    }
    @Column(name="remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Column(name="is_delete")
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
    @Column(name="late_times")
    public Integer getLateTimes() {
        return lateTimes;
    }

    public void setLateTimes(Integer lateTimes) {
        this.lateTimes = lateTimes;
    }
    @Column(name="leave_times")
    public Integer getLeaveTimes() {
        return leaveTimes;
    }

    public void setLeaveTimes(Integer leaveTimes) {
        this.leaveTimes = leaveTimes;
    }
    @Column(name="cut_times")
    public Integer getCutTimes() {
        return cutTimes;
    }

    public void setCutTimes(Integer cutTimes) {
        this.cutTimes = cutTimes;
    }
}