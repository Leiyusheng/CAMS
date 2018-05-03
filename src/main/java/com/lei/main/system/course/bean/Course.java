package com.lei.main.system.course.bean;

import com.lei.util.TableName;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Time;

@ApiModel("课程")
@Entity
@Table(name= TableName.Course)
public class Course {
    @ApiModelProperty("课程编号")
    private Integer id;
    @ApiModelProperty("课程名")
    private String name;
    @ApiModelProperty("经度")
    private Double lng;
    @ApiModelProperty("纬度")
    private Double lat;
    @ApiModelProperty("上课周数")
    private String week;
    @ApiModelProperty("当前周")
    private Integer current;
    @ApiModelProperty("上课星期")
    private String day;
    @ApiModelProperty("上课节数")
    private String period;
    @ApiModelProperty("开始时间")
    private Time startTime;
    @ApiModelProperty("结束时间")
    private Time endTime;
    @ApiModelProperty("剩余次数")
    private Integer remainderTimes;
    @ApiModelProperty("总次数")
    private Integer totalTimes;
    @ApiModelProperty("学生人数")
    private Integer stuNum;
    private Integer isAttend;//是否开始 0否，1本周，2当天，3开始
    private Integer isDelete;//是否删除 0否，1是
    private Integer isOverdue;//是否过期 0否，1是
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("课室")
    private String classroom;

    public void setCourseBaseInfo(Course course) {
        this.name = course.getName();
        this.lng = course.getLng();
        this.lat = course.getLat();
        this.week = course.getWeek();
        this.current = course.getCurrent();
        this.day = course.getDay();
        this.period = course.getPeriod();
        this.startTime = course.getStartTime();
        this.endTime = course.getEndTime();
        this.remainderTimes = course.getRemainderTimes();
        this.totalTimes = course.getTotalTimes();
        this.remark = course.getRemark() == null?remark:course.getRemark();
        this.classroom = course.getClassroom() == null?classroom:course.getClassroom();
    }

    public void setCourseDefaultValue() {
        this.stuNum = 0;
        this.isAttend = 0;
        this.isDelete = 0;
        this.isOverdue = 0;
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
    @Column(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name="lng")
    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
    @Column(name="lat")
    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
    @Column(name="week")
    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
    @Column(name="current")
    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }
    @Column(name="day")
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
    @Column(name="period")
    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
    @Column(name="start_time")
    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
    @Column(name="end_time")
    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
    @Column(name="remainder_times")
    public Integer getRemainderTimes() {
        return remainderTimes;
    }

    public void setRemainderTimes(Integer remainderTimes) {
        this.remainderTimes = remainderTimes;
    }
    @Column(name="total_times")
    public Integer getTotalTimes() {
        return totalTimes;
    }

    public void setTotalTimes(Integer totalTimes) {
        this.totalTimes = totalTimes;
    }
    @Column(name="stu_num")
    public Integer getStuNum() {
        return stuNum;
    }

    public void setStuNum(Integer stuNum) {
        this.stuNum = stuNum;
    }
    @Column(name="is_attend")
    public Integer getIsAttend() {
        return isAttend;
    }

    public void setIsAttend(Integer isAttend) {
        this.isAttend = isAttend;
    }
    @Column(name="is_delete")
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
    @Column(name="is_overdue")
    public Integer getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(Integer isOverdue) {
        this.isOverdue = isOverdue;
    }
    @Column(name="remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Column(name="classroom")
    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}
