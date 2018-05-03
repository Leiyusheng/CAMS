package com.lei.main.system.course.service;

import com.lei.main.comm.dao.page.DataStore;
import com.lei.main.system.course.bean.Course;

import java.util.List;
import java.util.Map;

public interface CourseService {
    Course getCourseInfoById(String id);

    DataStore getCourseListByName(Map<String,String> map);

    List getTodayCourseList(String id);

    Boolean saveCourseInfo(Course course);

    List getCourseGroupList(String id);
}
