package com.lei.main.system.attendance.bean;

import com.lei.util.TableName;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@ApiModel("考勤")
@Entity
@Table(name=TableName.Attendance)
public class Attendance {
    @ApiModelProperty("考勤编号")
    private Integer id;
    @ApiModelProperty("用户编号")
    private Integer userId;
    @ApiModelProperty("课程编号")
    private Integer courseId;
    @ApiModelProperty("迟到次数")
    private Integer lateTimes;
    @ApiModelProperty("早退次数")
    private Integer leaveTimes;
    @ApiModelProperty("旷课次数")
    private Integer cutTimes;
    @ApiModelProperty("是否退出")
    private Integer isQuit;

    public Attendance() { }

    public Attendance(Integer userId, Integer courseId) {
        this.userId = userId;
        this.courseId = courseId;
        this.lateTimes = 0;
        this.leaveTimes = 0;
        this.cutTimes = 0;
        this.isQuit = 0;
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

    @Column(name="user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    @Column(name="course_id")
    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
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
    @Column(name="is_quit")
    public Integer getIsQuit() {
        return isQuit;
    }

    public void setIsQuit(Integer isQuit) {
        this.isQuit = isQuit;
    }
}