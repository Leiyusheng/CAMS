package com.lei.main.system.group.action;

import com.lei.main.comm.bean.Message;
import com.lei.main.comm.service.FileService;
import com.lei.main.comm.util.Common;
import com.lei.main.system.course.bean.Course;
import com.lei.main.system.course.service.CourseService;
import com.lei.main.system.group.bean.Group;
import com.lei.main.system.group.bean.GroupCourse;
import com.lei.main.system.group.bean.GroupUser;
import com.lei.main.system.group.service.GroupService;
import com.lei.main.system.systemManager.bean.User;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value="/group")
public class GroupController {

    @Autowired
    private GroupService groupService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private FileService fileService;

    @ApiOperation(value = "新建或修改群组", notes ="填编号为修改，不填为新建；0失败，1成功，2群组不存在，3不在群中")
    @RequestMapping(value = "saveGroupInfo.do", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(name = "gid", value = "群组编号", paramType = "query", dataType = "String")})
    @ResponseBody
    public Message<String> saveGroupInfo(HttpServletRequest request, @ApiIgnore String gid,
                                         @ApiParam("群组名")@RequestParam String name) {
        Boolean rs;
        User user = Common.getCurrentUser(request);
        Integer uId = user.getUserId();
        Group g = new Group(name, uId);
        if (gid != null) {
            Group group = groupService.getGroupById(gid);
            if (group == null) {
                return Common.messageBox("2", "所选群组不存在");
            }
            GroupUser groupUser = groupService.getGroupUserById(gid, uId.toString());
            if (groupUser == null) {
                return Common.messageBox("3", "您不在该群组中，无法修改其信息");
            }
            group.setBasicInfo(g);
            rs = groupService.saveGroupInfo(group);
        } else {
            rs = groupService.saveGroupInfo(g);
            if (rs) {
                GroupUser groupUser = new GroupUser(g.getId(), uId, 1, uId);
                rs = groupService.saveGroupUserInfo(groupUser);
                if (!rs) {
                    try {
                        groupService.deleteGroup(g.getId().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (rs) {
            return Common.messageBox(Common.success);
        } else {
            return Common.messageBox(Common.failed);
        }
    }

    @ApiOperation(value = "修改群组头像", notes = "0失败，1成功，2群组不存在，3不在群中")
    @RequestMapping(value = "updateGroupLogo.do", method = RequestMethod.POST)
    @ResponseBody
    public Message<String> updateGroupLogo(HttpServletRequest request,
                                           @ApiParam("群编号")@RequestParam String gid,
                                           @ApiParam("头像")@RequestParam MultipartFile file) {
        User user = Common.getCurrentUser(request);
        Group group = groupService.getGroupById(gid);
        if (group == null) {
            return Common.messageBox("2", "所选群组不存在");
        }
        GroupUser groupUser = groupService.getGroupUserById(gid, user.getUserId().toString());
        if (groupUser == null) {
            return Common.messageBox("3", "您不在该群组中，无法修改其信息");
        }
        Message<String> m;
        try {
            m = fileService.saveFile("group", file);//保存图片，获取图片相对路径
        } catch (Exception e) {
            m = Common.messageBox(Common.failed);
            e.printStackTrace();
        }
        if (m.getCode().equals(Common.success)) {
            group.setGroupLogo(m.getMessage());
            Boolean rs = groupService.saveGroupInfo(group);
            if (rs) {
                return m;
            }
        }
        return Common.messageBox(Common.failed);
    }

    @ApiOperation(value = "获取用户群组列表", notes ="0失败，1成功")
    @RequestMapping(value = "getUserGroupList.do", method = RequestMethod.POST)
    @ResponseBody
    public Message<List> getUserGroupList(HttpServletRequest request) {
        User user = Common.getCurrentUser(request);
        List list = groupService.getUserGroupList(user.getUserId().toString());
        return Common.messageBox(list);
    }

    @ApiOperation(value = "邀请加入群组", notes ="0失败，1成功，2群组不存在，3不在群中")
    @RequestMapping(value = "inviteJoinGroup.do", method = RequestMethod.POST)
    @ResponseBody
    public Message<String> inviteJoinGroup(HttpServletRequest request,
                                   @ApiParam("群组编号")@RequestParam String gid,
                                   @ApiParam("被邀请人编号，','分割")@RequestParam String ids) {
        Boolean rs = false;
        User user = Common.getCurrentUser(request);
        String uid = user.getUserId().toString();
        if (gid != null) {
            Group group = groupService.getGroupById(gid);
            if (group == null) {
                return Common.messageBox("2", "所选群组不存在");
            }
            GroupUser groupUser = groupService.getGroupUserById(gid, uid);
            if (groupUser == null) {
                return Common.messageBox("3", "您不在该群组中，无法邀请");
            }
            try {
                rs = groupService.saveGroupUserList(group, ids, uid);
            } catch (Exception e) {
                rs = false;
                e.printStackTrace();
            }
        }
        if (rs) {
            return Common.messageBox(Common.success);
        } else {
            return Common.messageBox(Common.failed);
        }
    }

    @ApiOperation(value = "处理群组邀请", notes ="0失败，1成功，2邀请不存在，3不是该邀请对象，4邀请已处理，5method不为1或2")
    @RequestMapping(value = "handleGroupInvite.do", method = RequestMethod.POST)
    @ResponseBody
    public Message<String> handleGroupInvite(HttpServletRequest request,
                                             @ApiParam("方法，1同意，2拒绝")@RequestParam Integer method,
                                             @ApiParam("邀请编号")@RequestParam String id) {
        Boolean rs = false;
        if (id != null) {
            GroupUser groupUser = groupService.getGroupUserById(id);
            if (groupUser == null) {
                return Common.messageBox("2", "所选邀请不存在");
            }
            User user = Common.getCurrentUser(request);
            if (!user.getUserId().equals(groupUser.getUserId())) {
                return Common.messageBox("3", "你不是该邀请对象");
            }
            if (groupUser.getStatus() != 0) {
                return Common.messageBox("4", "该邀请已处理");
            }
            if ((method != 1)&&(method != 2)) {
                return Common.messageBox("5", "method必须为1或2");
            }
            groupUser.setStatus(method);
            rs = groupService.saveGroupUserInfo(groupUser);
        }
        if (rs) {
            return Common.messageBox(Common.success);
        } else {
            return Common.messageBox(Common.failed);
        }
    }

    @ApiOperation(value = "添加群组课程", notes ="0失败，1成功，2群组不存在，3不在群中，4课程不存在")
    @RequestMapping(value = "addGroupCourse.do", method = RequestMethod.POST)
    @ResponseBody
    public Message<String> addGroupCourse(HttpServletRequest request,
                                          @ApiParam("群组编号")@RequestParam String gid,
                                          @ApiParam("课程编号")@RequestParam String cid) {
        User user = Common.getCurrentUser(request);
        String uid = user.getUserId().toString();
        Group group = groupService.getGroupById(gid);
        if (group == null) {
            return Common.messageBox("2", "所选群组不存在");
        }
        GroupUser groupUser = groupService.getGroupUserById(gid, uid);
        if (groupUser == null) {
            return Common.messageBox("3", "您不在该群组中，无法添加");
        }
        Course course = courseService.getCourseInfoById(cid);
        if (course == null) {
            return Common.messageBox("4", "课程不存在");
        }
        GroupCourse g = new GroupCourse(Integer.parseInt(gid), Integer.parseInt(cid));
        Boolean rs = groupService.saveGroupCourse(g);
        if (rs) {
            return Common.messageBox(Common.success);
        } else {
            return Common.messageBox(Common.failed);
        }
    }
}
