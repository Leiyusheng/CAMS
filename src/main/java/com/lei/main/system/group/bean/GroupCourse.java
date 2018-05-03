package com.lei.main.system.group.bean;

import com.lei.util.TableName;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@ApiModel("群组课程")
@Entity
@Table(name= TableName.GroupCourse)
public class GroupCourse {
    @ApiModelProperty("编号")
    private Integer id;
    @ApiModelProperty("群组编号")
    private Integer groupId;
    @ApiModelProperty("课程编号")
    private Integer courseId;

    public GroupCourse() {}

    public GroupCourse(Integer groupId, Integer courseId) {
        this.groupId = groupId;
        this.courseId = courseId;
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
    @Column(name="course_id")
    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}
