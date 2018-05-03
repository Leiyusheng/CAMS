package com.lei.main.system.attendance.action;

import com.lei.main.comm.bean.Message;
import com.lei.main.comm.util.Common;
import com.lei.main.system.attendance.bean.Member;
import com.lei.main.system.attendance.bean.TempCourse;
import com.lei.main.system.attendance.service.AttendanceService;
import com.lei.main.system.course.bean.Course;
import com.lei.main.system.course.service.CourseService;
import com.lei.main.system.group.bean.GroupUser;
import com.lei.main.system.group.service.GroupService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/attendance")
public class AttendanceController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 考察课堂迟到情况
     * @param cid 课程编号
     * @throws InterruptedException
     */
    @Async
    public void attendance(String cid) throws InterruptedException {
        TempCourse t = attendanceService.getTempCourse(cid);
        List<Map> list = courseService.getCourseGroupList(cid);
        if (t == null) {
            return;
        }
        for (Map o : list) {
            String gid = o.get("group_id").toString();
            Map<String, Member> map = attendanceService.getGroupMemberList(gid, t.getDuration());
            for (Member m : map.values()) {
                //todo
                if (!t.judgePosition(m.getLng(), m.getLat())) {
                    m.setStatus(1);//迟到
                    try {
                        attendanceService.saveGroupMember(gid, m);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    pushNotice(m.getId().toString());//通知提醒
                }
            }
        }
        System.out.println(Thread.currentThread().getName());
    }

    @ApiOperation(value = "记录用户位置", notes ="0失败，1成功，2课程不存在，3课程未开始，4不在群组中")
    @RequestMapping(value = "recordPosition.do", method = RequestMethod.POST)
    @ResponseBody
    public Message<String> recordPosition(HttpServletRequest request,
                                  @ApiParam("课程编号")@RequestParam String cid,
                                  @ApiParam("群组编号")@RequestParam String gid,
                                  @ApiParam("经度")@RequestParam Double lng,
                                  @ApiParam("纬度")@RequestParam Double lat) {
        Integer uid = Common.getCurrentUser(request).getUserId();
        Course course = courseService.getCourseInfoById(cid);
        if (course == null) {
            return Common.messageBox("2", "所选课程不存在");
        }
        if (course.getIsAttend() != 3) {
            return Common.messageBox("3", "所选课程还未开始");
        }
        GroupUser groupUser = groupService.getGroupUserById(gid, uid.toString());
        if (groupUser == null) {
            return Common.messageBox("4", "不在该群组中");
        }
        Member m = new Member(uid, lng, lat);
        try {
            attendanceService.saveGroupMember(gid, m);
        } catch (Exception e) {
            e.printStackTrace();
            return Common.messageBox(Common.failed);
        }
        return Common.messageBox(Common.success);
    }

    private void pushNotice(String id) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(id+"迟到啦？！！");
    }
}
