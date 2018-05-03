<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.lang.String" %>
<%@ include file="/common/comm.jsp" %>
<html>
<body>
<SCRIPT>
try
{
    window.parent.location.href='./index.jsp';
    //document.top.document.location.href='login.do';

}
catch(E){
    document.location.href='./index.jsp';
}
</SCRIPT>
</body>
</html>