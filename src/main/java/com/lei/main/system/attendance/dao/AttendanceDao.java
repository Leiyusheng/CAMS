package com.lei.main.system.attendance.dao;

import com.lei.main.comm.dao.BaseDao;
import com.lei.main.system.attendance.bean.Attendance;
import com.lei.main.system.course.bean.Course;

import java.util.List;

public interface AttendanceDao extends BaseDao {

    Attendance getAttendanceById(String id);

    Attendance getAttendanceById(String uid, String cid);

    Boolean saveAttendanceInfo(Attendance attendance);

    void cancelCurrentWeek() throws Exception;

    void addCourseWeek(int num) throws Exception;

    void updateCourseStatusWeek() throws Exception;

    void cancelCurrentDay() throws Exception;

    void updateCourseStatusDay(int day) throws Exception;

    List<Course> getWillStartCourse();

    List<Course> getStartCourse();

    List<Course> getHadEndCourse();
}
