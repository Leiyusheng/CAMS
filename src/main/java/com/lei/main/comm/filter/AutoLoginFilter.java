package com.lei.main.comm.filter;

import com.lei.main.comm.bean.Message;
import com.lei.main.comm.util.Common;
import net.sf.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


public class AutoLoginFilter implements Filter {

    public void destroy() {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        String url = request.getServletPath();
        String prefix = url.split("/")[1];
        //这里判断目录，后缀名，当然也可以写在web.xml中，用url-pattern进行拦截映射  
        if ((!prefix.equals("login"))) {
            if (session.getAttribute("userSession") == null) {
                session.invalidate();   //清空所有已定义的session
                Message me = Common.messageBox("99", "由于长时间没操作或者账号在其他地方登录，请重新登录!");
                JSONObject oj = JSONObject.fromObject(me);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.append(oj.toString());
            } else {
                chain.doFilter(request, response);  
            }  
        } else {  
            chain.doFilter(request, response);  
        }  
    }

    public void init(FilterConfig arg0) throws ServletException {

    }

}
