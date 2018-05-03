package com.lei.main.system.attendance.bean;


import com.lei.main.comm.bean.Entity;


public class Member extends Entity {
    private Integer id;
    private Double lng;
    private Double lat;
    private Integer status;//0正常，1迟到

    public Member() {}

    public Member(Integer id, Double lng, Double lat) {
        this.id = id;
        this.lng = lng;
        this.lat = lat;
        this.status = 0;
    }

    public Member updatePosition(Double lng, Double lat) {
        this.lng = lng;
        this.lat = lat;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
