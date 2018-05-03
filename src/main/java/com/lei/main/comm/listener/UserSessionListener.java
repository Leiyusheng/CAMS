package com.lei.main.comm.listener;

import com.lei.main.comm.bean.UserSession;
import com.lei.main.comm.util.MySessionContext;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;


/**
 * Session监听
 *
 */
public class UserSessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent event) {
         MySessionContext.AddSession(event.getSession());  //当打开会话时，向session map中添加sessionid以及session信息
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        MySessionContext.DelSession(session);

        ServletContext application = session.getServletContext();
        String sessionId = session.getId();
        HashMap map = (HashMap) application.getAttribute("onlines");
        int c = 0;
        String count = (String)application.getAttribute("online") ;
        if(StringUtils.isNotEmpty(count))
            c = Integer.parseInt(count);
        if(map != null) {
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Entry<String, UserSession> entry = (Entry<String, UserSession>) iter.next();
                String key = entry.getKey().toString();
                UserSession val = (UserSession)entry.getValue();
                if(sessionId.equals(val.getSessionId())) {
                    iter.remove();
                    if (c > 0) {
                        c--;
                    }
                }
            }
            application.setAttribute("onlines", map);
            application.setAttribute("online", String.valueOf(c));
        }
    }
}