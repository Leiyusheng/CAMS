<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="common/comm.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>

    <script type="text/javascript">
        var url = window.location.href.split("/").slice(0,4).join("/");
        window.location.href=url + "/login/forwardLogin.do";
    </script>
</head>

</html>