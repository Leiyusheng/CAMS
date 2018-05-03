package com.lei.main.system.attendance.action;

import com.lei.main.system.attendance.service.AttendanceService;
import com.lei.main.system.course.bean.Course;
import com.lei.util.DateUtils;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping(value="/task")
public class TaskController {

    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private AttendanceController attendanceController;

    @Scheduled(cron = "0 58 23 ? * SAT ") // [秒] [分] [时] [日] [月] [周] [年]
    public void weekTask() {
        String start = DateUtils.currentDate();
        try {
            attendanceService.updateCourseWeek();
        } catch (Exception e) {
            e.printStackTrace();
            weekTask();
            return;
        }
        String end = DateUtils.currentDate();
        if (!start.equals(end)) {
            dayTask();
            System.out.println("超过啦");
        }
        System.out.println("周定时任务");

    }

    @Scheduled(cron = "0 0 0 * * ? ") // [秒] [分] [时] [日] [月] [周] [年]
    public void dayTask() {
        try {
            attendanceService.updateCourseDay();
        } catch (Exception e) {
            e.printStackTrace();
            dayTask();
            return;
        }
        System.out.println("天定时任务");
    }
    /*@ApiOperation(value = "任务", notes ="无")
    @RequestMapping(value = "attendanceTask.do", method = RequestMethod.POST)
    @ResponseBody*/
    @Scheduled(cron = "0 0/5 5-23 * * ? ") // [秒] [分] [时] [日] [月] [周] [年]
    public void attendanceTask() {
        try {
            attendanceService.updateCourseStart();
            attendanceService.updateCourseEnd();
            List<Course> list = attendanceService.getStartCourse();
            for (int i = 0; i < list.size(); i++) {
                Course course = list.get(i);
                //todo attendance
                attendanceController.attendance(course.getId().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("使用SpringMVC框架配置定时任务");

    }

}
