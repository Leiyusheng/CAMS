package com.lei.main.system.systemManager.action;

import com.lei.main.comm.bean.Message;
import com.lei.main.comm.dao.redis.RedisUtil;
import com.lei.main.comm.service.FileService;
import com.lei.main.comm.util.Common;
import com.lei.main.system.attendance.bean.Member;
import com.lei.main.system.attendance.bean.Position;
import com.lei.main.system.systemManager.bean.Friend;
import com.lei.main.system.systemManager.bean.User;
import com.lei.main.system.systemManager.service.FriendService;
import com.lei.main.system.systemManager.service.UserManager;
import com.wordnik.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/userManager")
public class UserManagerController {

    @Autowired
    private UserManager userManager;
    @Autowired
    private FriendService friendService;
    @Autowired
    private FileService fileService;

    @ApiOperation(value = "获取好友列表", notes = "0失败，1成功")
    @RequestMapping(value = "getFriendList.do", method = RequestMethod.POST)
    @ResponseBody
    public Message<List> getFriendList(HttpServletRequest request) {
        User user = Common.getCurrentUser(request);
        List list = friendService.getFriendsList(user.getUserId().toString());
        return Common.messageBox(list);
    }

    @ApiOperation(value = "添加好友", notes = "0失败，1成功，2用户不存在，3已是好友")
    @RequestMapping(value = "addFriend.do", method = RequestMethod.POST)
    @ResponseBody
    public Message<String> addFriend(HttpServletRequest request,
                                     @ApiParam("好友编号")@RequestParam String fid,
                                     @ApiParam("备注")@RequestParam String remark) {
        Boolean rs = false;
        User user1 = Common.getCurrentUser(request);
        User user2 = userManager.getUserById(fid);
        if (user2 == null) {
            return Common.messageBox("2", "该用户不存在");
        }
        String id = user1.getUserId().toString();
        Friend ship1 = friendService.getFriendshipById(id, fid);
        if (ship1 == null) {
            ship1 = new Friend(Integer.parseInt(id), Integer.parseInt(fid), 1, "", user2.getUserName());
            Friend ship2 = new Friend(Integer.parseInt(fid), Integer.parseInt(id), 0, remark, user1.getUserName());
            rs = friendService.updateFriendship(ship1, ship2);
        } else {
            Friend ship2 = friendService.getFriendshipById(fid, id);
            if (ship2.getStatus() != 1) {//对方未添加
                ship1.updateApply(1);
                ship2.updateApply(0);
                ship2.setRemark(remark);
                rs = friendService.updateFriendship(ship1, ship2);
            } else if (ship1.getStatus() != 1) {//己方未添加
                ship1.setStatus(1);
                ship1.setApply(2);
                rs = friendService.updateFriendship(ship1);
            } else {
                return Common.messageBox("3", "已是好友");
            }
        }
        if (rs) {
            return Common.messageBox(Common.success);
        } else {
            return Common.messageBox(Common.failed);
        }
    }

    @ApiOperation(value = "删除好友", notes = "0失败，1成功，2用户不存在，3已不是好友")
    @RequestMapping(value = "deleteFriend.do", method = RequestMethod.POST)
    @ResponseBody
    public Message<String> deleteFriend(HttpServletRequest request, @ApiParam("好友编号")@RequestParam String id) {
        User user1 = Common.getCurrentUser(request);
        User user2 = userManager.getUserById(id);
        if (user2 == null) {
            return Common.messageBox("2", "该用户不存在");
        }
        Friend ship = friendService.getFriendshipById(user1.getUserId().toString(), id);
        if (ship == null || ship.getStatus() != 1) {
            return Common.messageBox("3", "已不是好友");
        }
        ship.setStatus(-1);
        Boolean rs = friendService.updateFriendship(ship);
        if (rs) {
            return Common.messageBox(Common.success);
        } else {
            return Common.messageBox(Common.failed);
        }
    }

    @ApiOperation(value = "获取好友申请列表", notes = "0失败，1成功")
    @RequestMapping(value = "getFriendApplyList.do", method = RequestMethod.POST)
    @ResponseBody
    public Message<List> getFriendApplyList(HttpServletRequest request) {
        User user = Common.getCurrentUser(request);
        List list = friendService.getFriendApplyList(user.getUserId().toString());
        return Common.messageBox(list);
    }

    @ApiOperation(value = "处理好友申请", notes = "0失败，1成功，2申请不存在，3不是被申请对象，4申请已处理")
    @RequestMapping(value = "handleFriendApply.do", method = RequestMethod.POST)
    @ResponseBody
    public Message<String> handleFriendApply(HttpServletRequest request, @ApiParam("编号")@RequestParam String id) {
        User user = Common.getCurrentUser(request);
        Friend ship1 = friendService.getFriendshipById(id);
        if (ship1 == null) {
            return Common.messageBox("2", "申请不存在");
        }
        if (ship1.getUser1() != user.getUserId()) {
            return Common.messageBox("3", "不是该申请对象");
        }
        if (ship1.getApply() == 0) {
            Friend ship2 = friendService.getFriendshipById(ship1.getUser2().toString(), ship1.getUser1().toString());
            ship1.setApply(2);
            ship1.setStatus(1);
            ship2.setApply(2);
            ship2.setStatus(1);
            Boolean rs = friendService.updateFriendship(ship1, ship2);
            if (rs) {
                return Common.messageBox(Common.success);
            } else {
                return Common.messageBox(Common.failed);
            }
        } else {
            return Common.messageBox("4", "申请已处理");
        }
    }

    @ApiOperation(value = "删除好友申请", notes = "0失败，1成功，2申请不存在，3不是被申请对象")
    @RequestMapping(value = "deleteFriendApply.do", method = RequestMethod.POST)
    @ResponseBody
    public Message<String> deleteFriendApply(HttpServletRequest request, @ApiParam("编号")@RequestParam String id) {
        User user = Common.getCurrentUser(request);
        Friend ship = friendService.getFriendshipById(id);
        if (ship == null) {
            return Common.messageBox("2", "申请不存在");
        }
        if (ship.getUser1() != user.getUserId()) {
            return Common.messageBox("3", "不是该申请对象");
        }
        ship.setApply(-1);
        Boolean rs = friendService.updateFriendship(ship);
        if (rs) {
            return Common.messageBox(Common.success);
        } else {
            return Common.messageBox(Common.failed);
        }
    }

    @ApiOperation(value = "修改好友备注名", notes = "0失败，1成功，2好友不存在")
    @RequestMapping(value = "modifyFriendName.do", method = RequestMethod.POST)
    @ResponseBody
    public Message<String> modifyFriendName(HttpServletRequest request,
                                            @ApiParam("好友编号")@RequestParam String id,
                                            @ApiParam("备注名")@RequestParam String name) {
        User user = Common.getCurrentUser(request);
        Friend friend = friendService.getFriendshipById(user.getUserId().toString(), id);
        if (friend == null) {
            return Common.messageBox("2", "好友不存在");
        }
        friend.setAlias(name);
        Boolean rs = friendService.updateFriendship(friend);
        if (rs) {
            return Common.messageBox(Common.success);
        } else {
            return Common.messageBox(Common.failed);
        }
    }

    @ApiOperation(value = "查看用户基本信息", notes = "0失败，1成功，2未添加好友")
    @RequestMapping(value = "getUserById.do", method = RequestMethod.POST)
    @ResponseBody
    public Message getUserById(HttpServletRequest request, @ApiParam("编号")@RequestParam String id) {
        User user = Common.getCurrentUser(request);
        if (id.equals(user.getUserId().toString())) {
            user = userManager.getUserById(id);
            Common.updateSessionUser(request, user);
        } else {
            Friend friend = friendService.getFriendshipById(id, user.getUserId().toString());
            if (friend == null || friend.getStatus() != 1) {
                return Common.messageBox("2", "不是好友无法查看");
            }
            user = userManager.getUserById(id);
        }
        if (user != null) {
            user.cleanPrivateInfo();//清除个人信息
        }
        return Common.messageBox(user);
    }

    @ApiOperation(value = "修改用户名", notes = "0失败，1成功，2用户名不能为空")
    @RequestMapping(value = "updateUserName.do", method = RequestMethod.POST)
    @ResponseBody
    public Message<String> updateUserName(HttpServletRequest request, @ApiParam("用户名")@RequestParam String name) {
        User user = Common.getCurrentUser(request);
        if (name == null || name.equals("")) {
            return Common.messageBox("2", "用户名不能为空");
        }
        user.setUserName(name);
        Boolean rs = userManager.saveUser(user);
        if (rs) {
            Common.updateSessionUser(request, user);
            return Common.messageBox(Common.success);
        } else {
            return Common.messageBox(Common.failed);
        }
    }

    @ApiOperation(value = "修改用户性别", notes = "0失败，1成功，2性别需为0或1")
    @RequestMapping(value = "updateUserSex.do", method = RequestMethod.POST)
    @ResponseBody
    public Message<String> updateUserSex(HttpServletRequest request, @ApiParam("性别")@RequestParam String sex) {
        User user = Common.getCurrentUser(request);
        if (sex.equals("0") || sex.equals("1")) {
            user.setSex(sex);
        } else {
            return Common.messageBox("2", "性别需为0或1");
        }
        Boolean rs = userManager.saveUser(user);
        if (rs) {
            Common.updateSessionUser(request, user);
            return Common.messageBox(Common.success);
        } else {
            return Common.messageBox(Common.failed);
        }
    }

    @ApiOperation(value = "修改用户学校", notes = "0失败，1成功")
    @RequestMapping(value = "updateUserSchool.do", method = RequestMethod.POST)
    @ResponseBody
    public Message<String> updateUserSchool(HttpServletRequest request, @ApiParam("学校")@RequestParam Integer school) {
        User user = Common.getCurrentUser(request);
        user.setSchool(school);
        Boolean rs = userManager.saveUser(user);
        if (rs) {
            Common.updateSessionUser(request, user);
            return Common.messageBox(Common.success);
        } else {
            return Common.messageBox(Common.failed);
        }
    }

    @ApiOperation(value = "修改用户备注", notes = "0失败，1成功")
    @RequestMapping(value = "updateUserRemark.do", method = RequestMethod.POST)
    @ResponseBody
    public Message<String> updateUserRemark(HttpServletRequest request, @ApiParam("备注")@RequestParam String remark) {
        User user = Common.getCurrentUser(request);
        user.setRemark(remark);
        Boolean rs = userManager.saveUser(user);
        if (rs) {
            Common.updateSessionUser(request, user);
            return Common.messageBox(Common.success);
        } else {
            return Common.messageBox(Common.failed);
        }
    }

    @ApiOperation(value = "修改用户头像", notes = "0失败，1成功")
    @RequestMapping(value = "updateUserHeadLogo.do", method = RequestMethod.POST)
    @ResponseBody
    public Message<String> updateUserHeadLogo(HttpServletRequest request,@ApiParam("头像")@RequestParam MultipartFile file) {
        User user = Common.getCurrentUser(request);
        Message<String> m;
        try {
            m = fileService.saveFile("head", file);//保存图片，获取图片相对路径
        } catch (Exception e) {
            m = Common.messageBox(Common.failed);
            e.printStackTrace();
        }
        if (m.getCode().equals(Common.success)) {
            user.setHeadLogo(m.getMessage());
            Boolean rs = userManager.saveUser(user);
            if (rs) {
                Common.updateSessionUser(request, user);
                return m;
            }
        }
        return Common.messageBox(Common.failed);
    }
}
