package com.lei.main.system.course.service.impl;

import com.lei.main.comm.dao.page.DataStore;
import com.lei.main.system.course.bean.Course;
import com.lei.main.system.course.dao.CourseDao;
import com.lei.main.system.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service("courseService")
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Override
    public Course getCourseInfoById(String id) {
        return courseDao.getCourseInfoById(id);
    }

    @Override
    public DataStore getCourseListByName(Map<String, String> map) {
        return courseDao.getCourseListByName(map);
    }

    @Override
    public List getTodayCourseList(String id) {
        return courseDao.getTodayCourseList(id);
    }

    @Override
    public Boolean saveCourseInfo(Course course) {
        return courseDao.saveCourseInfo(course);
    }

    @Override
    public List getCourseGroupList(String id) {
        return courseDao.getCourseGroupList(id);
    }
}
