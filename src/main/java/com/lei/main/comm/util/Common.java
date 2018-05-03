package com.lei.main.comm.util;

import com.lei.main.comm.bean.Message;
import com.lei.main.comm.bean.UserSession;
import com.lei.main.system.systemManager.bean.User;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Common {
    public static final String success = "1";
    public static final String failed = "0";
    private static final String successInfo = "成功";
    private static final String failedInfo = "失败";

    public static Message<String> messageBox(String method) {
        if (success.equals(method)) {
            return messageBox(success, successInfo);
        } else if (failed.equals(method)) {
            return messageBox(failed, failedInfo);
        } else {
            return new Message<String>();
        }
    }

    public static<T> Message<T> messageBox(T message) {
        if (message != null && message != "") {
            return messageBox(success, message);
        } else {
            return messageBox(failed, null);
        }
    }

    public static<T> Message<T> messageBox(String cd, T message) {
        return new Message<T>(cd, message);
    }

    public static<T> Message<T> messageBox(Boolean flag, T message) {
        return new Message<T>(flag.toString(), message);
    }

    public static String getNow() {
        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(now);
    }

    public static User getCurrentUser(HttpServletRequest request) {
        UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
        return userSession.getUser();
    }

    public static void updateSessionUser(HttpServletRequest request, User user) {
        UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
        userSession.setUser(user);
        request.getSession().setAttribute("userSession", userSession);
    }

    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        BeanUtils.populate(obj, map);

        return obj;
    }

    public static Map<String, ?> objectToMap(Object obj) {
        if(obj == null)
            return null;

        return new BeanMap(obj);
    }
}
