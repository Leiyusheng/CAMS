package com.lei.main.system.group.bean;

import com.lei.util.TableName;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@ApiModel("群组")
@Entity
@Table(name= TableName.Group)
public class Group {
    @ApiModelProperty("群组编号")
    private Integer id;
    @ApiModelProperty("群组名")
    private String name;
    @ApiModelProperty("群组人数")
    private Integer num;
    @ApiModelProperty("修改人")
    private Integer modifier;

    public Group() {}

    public Group(Integer id) {
        this.id = id;
    }

    public Group(String name, Integer modifier) {
        this.name = name;
        this.num = 1;
        this.modifier = modifier;
    }

    public Group setBasicInfo(Group group) {
        this.name = group.getName();
        this.modifier = group.getModifier();
        return this;
    }

    public void addGroupNum(int n) {
        this.num += n;
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
    @Column(name="num")
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
    @Column(name="modifier")
    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }
}
