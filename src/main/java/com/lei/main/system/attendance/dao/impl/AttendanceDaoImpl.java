package com.lei.main.system.attendance.dao.impl;

import com.lei.main.comm.dao.jdbc.BaseDaoImpl;
import com.lei.main.system.attendance.bean.Attendance;
import com.lei.main.system.attendance.dao.AttendanceDao;
import com.lei.main.system.course.bean.Course;
import com.lei.util.DateUtils;
import com.lei.util.TableName;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("attendanceDao")
public class AttendanceDaoImpl extends BaseDaoImpl implements AttendanceDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Attendance getAttendanceById(String id) {
        return (Attendance)sessionFactory.getCurrentSession().get(Attendance.class, Integer.parseInt(id));
    }

    @Override
    public Attendance getAttendanceById(String uid, String cid) {
        String sql = "select * from "+ TableName.Attendance + " s where s.user_id = '"+uid+"' and s.course_id = '"+cid+"'";
        List<Attendance> list = sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Attendance.class).list();
        if(list.size() > 0) {
            return list.get(0);
        } else  {
            return null;
        }
    }

    @Override
    public Boolean saveAttendanceInfo(Attendance attendance) {
        try {
            sessionFactory.getCurrentSession().save(attendance);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void cancelCurrentWeek() throws Exception {
        String hql = "update Course set is_attend = 0 where is_delete = 0";
        sessionFactory.getCurrentSession().createQuery(hql).executeUpdate();
    }

    @Override
    public void addCourseWeek(int num) throws Exception {
        String hql = "update Course set current = current + :num where is_delete = 0";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("num", num);
        query.executeUpdate();
    }

    @Override
    public void updateCourseStatusWeek() throws Exception {
        String hql = "update Course set is_attend = 1 where is_delete = 0 and (week like ('%,'||current||',%'))";
        sessionFactory.getCurrentSession().createQuery(hql).executeUpdate();
    }

    @Override
    public void cancelCurrentDay() throws Exception {
        String hql = "update Course set is_attend = 1 where is_delete = 0 and is_attend = 2";
        sessionFactory.getCurrentSession().createQuery(hql).executeUpdate();
    }

    @Override
    public void updateCourseStatusDay(int day) throws Exception {
        String hql = "update Course set is_attend = 2 where is_delete = 0 and is_attend = 1 and (day like ('%,'||:day||',%'))";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("day", day);
        query.executeUpdate();
    }

    @Override
    public List<Course> getWillStartCourse() {
        String sql = "select * from " + TableName.Course + " where is_attend = 2 and start_time > '" + DateUtils.currentTime() +
                "' and start_time - '"+ DateUtils.currentTime() + "' <= interval'10m'";
        return sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Course.class).list();
    }

    @Override
    public List<Course> getStartCourse() {
        String sql = "select * from " + TableName.Course + " where is_attend = 3 and (start_time = '" + DateUtils.currentTime() +
                "' or (start_time < '" + DateUtils.currentTime()+"' and '"+ DateUtils.currentTime() + "' - start_time < interval'3m'))";
        return sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Course.class).list();
    }

    @Override
    public List<Course> getHadEndCourse() {
        String sql = "select * from " + TableName.Course + " where is_attend = 3 and end_time < '" + DateUtils.currentTime() +
                "' and '"+ DateUtils.currentTime() + "' - end_time <= interval'10m'";
        return sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Course.class).list();
    }
}
