package com.lei.main.system.systemManager.bean;

import com.lei.util.TableName;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@ApiModel("城市")
@Entity
@Table(name= TableName.City)
public class City {

    @ApiModelProperty("城市编号")
    private Integer id;
    @ApiModelProperty("地名")
    private Integer name;
    @ApiModelProperty("父级编号")
    private Integer pid;

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
    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }
    @Column(name="pid")
    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}
