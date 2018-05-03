package com.lei.main.system.course.dao;

import com.lei.main.comm.dao.BaseDao;
import com.lei.main.comm.dao.page.DataStore;
import com.lei.main.system.course.bean.Course;

import java.util.List;
import java.util.Map;

public interface CourseDao extends BaseDao {

    Course getCourseInfoById(String id);

    List getTodayCourseList(String id);

    DataStore getCourseListByName(Map<String,String> map);

    Boolean saveCourseInfo(Course course);

    List getCourseGroupList(String id);
}
