package com.lei.main.system.course.dao.impl;

import com.lei.main.comm.dao.jdbc.BaseDaoImpl;
import com.lei.main.comm.dao.page.DataStore;
import com.lei.main.system.course.bean.Course;
import com.lei.main.system.course.dao.CourseDao;
import com.lei.util.TableName;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("courseDao")
public class CourseDaoImpl extends BaseDaoImpl implements CourseDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Course getCourseInfoById(String id) {
        return (Course)sessionFactory.getCurrentSession().get(Course.class, Integer.parseInt(id));
    }

    @Override
    public List getTodayCourseList(String id) {
        String sql = "select c.id,c.name,c.remainder_times,c.total_times,c.classroom,c.start_time from " + TableName.Course +
                " c left join "+ TableName.Attendance+ " a on c.id = a.course_id where a.user_id = '" + id + "' and c.is_attend > 1";
        return getRecordData(sql, null, jdbcTemplate);
    }

    @Override
    public DataStore getCourseListByName(Map<String,String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append("select * from " + TableName.Course + " where 1=1");

        if(StringUtils.isNotEmpty(map.get("name"))) {
            sql.append(" and name like '%" + map.get("name") + "%'");
        }
        sql.append(" order by convert_to(name,'GBK')");

        return findPageForMap(sql.toString(),
                Integer.parseInt(map.get("page")),
                Integer.parseInt(map.get("rows")));
    }

    @Override
    public Boolean saveCourseInfo(Course course) {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(course);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List getCourseGroupList(String id) {
        String sql = "select c.group_id from " + TableName.GroupCourse + " c where c.course_id = '" + id + "'";
        return getRecordData(sql, null, jdbcTemplate);
    }
}
