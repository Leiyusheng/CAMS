package com.lei.main.system.attendance.bean;

import com.lei.main.comm.bean.Entity;
import com.lei.main.system.course.bean.Course;

import java.sql.Time;
import java.util.Map;

public class TempCourse extends Entity {

    private Integer id;
    private String name;
    private Double lng;
    private Double lat;
    private Double minLng;
    private Double minLat;
    private Double maxLng;
    private Double maxLat;
    private Long duration;
    private Time startTime;
    private Integer num;

    public TempCourse() {}

    public TempCourse(Course course, int radius) {
        this.id = course.getId();
        this.name = course.getName();
        this.lng = course.getLng();
        this.lat = course.getLat();
        this.duration = (course.getEndTime().getTime() - course.getStartTime().getTime())/(60 * 1000);
        this.startTime = course.getStartTime();
        this.num = course.getStuNum();

        Position p = new Position(lng, lat);
        Map<String, Double> m = p.getAround(radius);
        this.minLng = m.get("minLng");
        this.minLat = m.get("minLat");
        this.maxLng = m.get("maxLng");
        this.maxLat = m.get("maxLat");
    }

    public Boolean judgePosition(Double lng, Double lat) {
        if (lng <= maxLng && lng >= minLng && lat <= maxLat && lat >= minLat) {
            return true;
        }
        return false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Double getMinLng() {
        return minLng;
    }

    public void setMinLng(Double minLng) {
        this.minLng = minLng;
    }

    public Double getMinLat() {
        return minLat;
    }

    public void setMinLat(Double minLat) {
        this.minLat = minLat;
    }

    public Double getMaxLng() {
        return maxLng;
    }

    public void setMaxLng(Double maxLng) {
        this.maxLng = maxLng;
    }

    public Double getMaxLat() {
        return maxLat;
    }

    public void setMaxLat(Double maxLat) {
        this.maxLat = maxLat;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "TempCourse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lng=" + lng +
                ", lat=" + lat +
                ", minLng=" + minLng +
                ", minLat=" + minLat +
                ", maxLng=" + maxLng +
                ", maxLat=" + maxLat +
                ", duration=" + duration +
                ", startTime=" + startTime +
                ", num=" + num +
                '}';
    }
}
