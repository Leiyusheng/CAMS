package com.lei.main.comm.action;

import com.lei.main.comm.bean.Message;
import com.lei.main.comm.bean.UserSession;
import com.lei.main.comm.service.VerifyCodeService;
import com.lei.main.comm.util.Common;
import com.lei.main.comm.util.MySessionContext;
import com.lei.main.system.systemManager.bean.User;
import com.lei.main.system.systemManager.service.UserManager;
import com.lei.util.DateUtils;
import com.lei.util.MD5Util;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.*;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;


@Controller
@RequestMapping(value="/login")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LoginController {

    @Autowired
    private UserManager userManager;

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Value("#{configProperties['appcode']}")
    private String appCode;

    private static org.apache.commons.logging.Log logger = LogFactory.getLog(LoginController.class);

    @ApiIgnore
    @RequestMapping(value = "forwardLogin.do", method = RequestMethod.GET)
    public ModelAndView forwardLogin(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        if (session.getAttribute("userSession") == null) {
            ModelAndView mv = new ModelAndView("../../login");
            return mv;
        } else {
            ModelAndView mv = new ModelAndView("../../swagger/index");
            return mv;
        }
    }

    @ApiOperation(value = "验证登录", notes = "1成功，2密码错误，3用户不存在，4没有权限，5系统错误")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "phone", value = "电话", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "mm", value = "密码", required = true)})
    @RequestMapping(value = "checkLogin.do", method = RequestMethod.POST)
    @ResponseBody
    public Message<String> checkLogin(HttpServletRequest request, HttpServletResponse response){
        String phone = request.getParameter("phone");
        String mm =request.getParameter("mm");
        String msg = "";
        String msgCode = "";
        try {
            User user = userManager.getUserByPhone(phone);
            if(user == null) {
                msg = "用户不存在！";
                msgCode = "3";
            } else {
                String pass = MD5Util.string2MD5(mm);
                if ( pass.equals(user.getUserPassword()) && user.getStatus() == 1 ) {
                    UserSession userSession = new UserSession();
                    userSession.setUser(user);
                    userSession.setSessionId(request.getSession().getId());
                    request.getSession().setAttribute("userSession", userSession);
                    //将登录的用户信息添加到application
                    setOnline(userSession, request, request.getSession().getServletContext());
                    msg = "验证通过，正在登录中...";
                    msgCode = "1";
                } else if(user.getStatus() != 1){
                    msg = "无权访问！";
                    msgCode = "4";
                } else {
                    msg = "密码错误！";
                    msgCode = "2";
                }
            }
        } catch (Exception e) {
            msg = "因程序错误登陆失败！";
            msgCode = "5";
            e.printStackTrace();
        }
        return Common.messageBox(msgCode, msg);
    }

    @ApiIgnore
    @RequestMapping(value = "login.do", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView mv = null;
        String loginUser = null;
        HttpSession session = request.getSession();
        if (session.getAttribute("userSession") != null) {
            System.out.println("-----------------------------------");
            System.out.println("用户【"+ ((UserSession)session.getAttribute("userSession")).getUser().getUserName() +"】在  "+ DateUtils.currentDateTime() +"  登陆了系统...");
            System.out.println("-----------------------------------");
            mv = new ModelAndView("../../swagger/index");
            return mv;
        } else {
            mv = new ModelAndView("../../login");
            return mv;
        }
    }

    @ApiOperation(value = "退出登录", notes = "退出登录状态")
    @RequestMapping(value="logout.do", method = RequestMethod.POST)
    @ResponseBody
    public Message<String> logout(HttpServletRequest request) {
        UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
        if(userSession != null) {
            //下线功能
            setOffline(userSession, request, request.getSession().getServletContext());
        }
        request.getSession().removeAttribute("userSession");
        return Common.messageBox(Common.success);
    }

    @ApiIgnore
    @RequestMapping(value="logout1.do", method = RequestMethod.GET)
    public ModelAndView logout1(HttpServletRequest request) {
        UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
        ModelAndView mv = new ModelAndView("../../login");
        if(userSession != null) {
            //下线功能
            setOffline(userSession, request, request.getSession().getServletContext());
        }
        request.getSession().removeAttribute("userSession");
        return mv;
    }

    @ApiOperation(value = "注册", notes = "0失败，1成功，2格式错误，3验证码错误，4用户存在")
    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    @ResponseBody
    public Message register(HttpServletRequest request,
                            @ApiParam("电话")@RequestParam String phone,
                            @ApiParam("密码")@RequestParam String pwd,
                            @ApiParam("图片验证码")@RequestParam String picCode) {
        Boolean rs;
        if (phone.length() == 11 && pwd.length() > 5 && pwd.length() < 17) {
            if (picCode.length() != 4 || !request.getSession().getAttribute("pic_code").equals(picCode)) {
                return Common.messageBox("3", "验证码错误");
            }
            User user = userManager.getUserByPhone(phone);
            if (user != null) {
                return Common.messageBox("4", "用户已存在");
            }
            user = new User(phone, MD5Util.string2MD5(pwd));
            rs = userManager.saveUser(user);
        } else {
            return Common.messageBox("2", "账号密码格式错误");
        }
        if (rs) {
            return Common.messageBox(Common.success);
        } else {
            return Common.messageBox(Common.failed);
        }
    }

    @ApiOperation(value = "获取图片验证码", notes = "无")
    @RequestMapping(value = "getVerifyCode.do", method = RequestMethod.GET)
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置浏览器不要对数据进行缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //设置返回文件类型
        response.setContentType("image/jpeg");
        //生成随机字串
        String verifyCode = verifyCodeService.generateVerifyCode(4);
        //将验证码文本存入会话session
        HttpSession session = request.getSession(true);
        session.setAttribute("pic_code", verifyCode.toLowerCase());
        //生成图片
        int w = 220, h = 60;
        verifyCodeService.outputImage(w, h, response.getOutputStream(), verifyCode);
    }

    /**将登录系统的用户信息、登录的用户数 放到application中
     * @param userSession
     * @param request
     * @param application
     * @throws Exception
     */
    private void setOnline(UserSession userSession, HttpServletRequest request, ServletContext application) {
        User user = userSession.getUser();
        HashMap<String, UserSession> map = (HashMap<String, UserSession>)request.getSession().getServletContext().getAttribute("onlines");

        if (map == null) { //application中未开始存储用户信息
            map = new HashMap<String, UserSession>();
            map.put(user.getUserName(), userSession);
            //map.put("sessionId", request.getSession().getId());
            application.setAttribute("onlines", map);
            application.setAttribute("online", String.valueOf(1));
        } else if (map.containsKey(user.getUserName())) { //用户信息已放入过application
            String sessionId = userSession.getSessionId();

            System.out.println("------------------======now sessionId"+sessionId);
            UserSession u = (UserSession)map.get(user.getUserName());
            String old = u.getSessionId();
            System.out.println("------------------======old UserSession"+(UserSession)map.get(user.getUserName()));
            System.out.println("------------------======old sessionId"+old);
            if(!sessionId.equals(old)) { //判断是否同一个sessionid
                HttpSession session = MySessionContext.getSession(old);
                session.invalidate(); //使上个登陆者的session失效
            }
            map.put(user.getUserName(), userSession);
            application.setAttribute("onlines", map);
        } else { //第一次将该用户的信息放入到application中
            map.put(user.getUserName(), userSession);
            //    map.put("sessionId", request.getSession().getId());

            int c = Integer.parseInt((String) application
                    .getAttribute("online"));
            c++;
            application.setAttribute("onlines", map);
            application.setAttribute("online", String.valueOf(c));
        }
    }

    /**退出登陆时，消除用户信息
     * @param userSession
     * @param request
     */
    private void setOffline(UserSession userSession,HttpServletRequest request, ServletContext application) {
        String username = userSession.getUser().getUserName();
        HashMap map = (HashMap) application.getAttribute("onlines");
        if (map.containsKey(username)) {
            map.remove(username);
            int c = Integer.parseInt((String) application
                    .getAttribute("online"));
            if (c > 0) {
                c--;
            }
            application.setAttribute("onlines", map);
            application.setAttribute("online", String.valueOf(c));
        }
    }
}
