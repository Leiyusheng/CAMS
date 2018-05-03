<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="common/comm.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>登陆</title> 
    <link type="text/css" rel="stylesheet" href="${path}/css/base.css"/>
    <link rel="stylesheet" type="text/css" href="${path}/css/login.css"/>
    <link rel="stylesheet" type="text/css" href="${path}/easyui/themes/bootstrap/easyui.css"/>
    <script type="text/javascript" src="${path}/easyui/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${path}/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${path}/jquery/jquery-placeholder.min.js"></script>
    <script type="text/javascript" src="${path}/easyui/locale/easyui-lang-zh_CN.js"></script>
        
<script type="text/javaScript">
//检测父窗口,解决session超时,iframe跳到父窗口
function checkParent(){
    if(window.parent.length>0){
        window.parent.location="./logout.jsp";
    }
}

window.onload = function(){
        checkParent();    //检测父窗口
        //checkNavigator();
}

//检测当前浏览器版本，版本过低提示升级
function checkNavigator(){
     var src;
     var browserName = navigator.appName;
     if(detectOS() == 'Win2003'){
        src = "${path}/document/IE8-Server2003.exe";
     }else{
        src = "${path}/document/IE8.exe";
     }
     if (browserName == "Microsoft Internet Explorer") {
        if(navigator.appVersion.match(/8\./i) == '8.'){
              //alert(navigator.appVersion.match(/8./i));
        }         
        else if(navigator.appVersion.match(/7\./i) == '7.'){
              //alert(navigator.appVersion.match(/7./i));
        }
        else if (navigator.appVersion.match(/6\./i) == '6.'|| navigator.appVersion.match(/5\./i) == '5.') {
           if (confirm("您使用的ie版本太低，建议安装IE8，是否安装?")) {
                 window.location.href =src;
           }
        }
     }
}

//检测当前操作系统
function detectOS(){
   var sUserAgent = navigator.userAgent; 
   var isWin = (navigator.platform == 'Win32') || (navigator.platform == 'Windows');
   if(isWin){
     var isWin2K = sUserAgent.indexOf('Windows NT 5.0') > -1 || sUserAgent.indexOf('Windows 2000') > -1; 
     if(isWin2K)
         return 'Win2000';
     var isWinXp = sUserAgent.indexOf('Windows NT 5.1') > -1 || sUserAgent.indexOf('Windows XP') > -1;
     if(isWinXp)
         return 'WinXp';
     var isWin2003 = sUserAgent.indexOf('Windows NT 5.2') > -1 || sUserAgent.indexOf('Windows 2003') > -1;
     if(isWin2003)
         return 'Win2003';
   }
}


function dosubmit(){
    var user = $("#user").val();
    var pwd = $("#password").val();
    if(user == "") {
        $("#tip").html("用户名不能为空，请输入用户名");
        return;
    } 
    if(pwd == "") {
        $("#tip").html("密码不能为空，请输入密码");
        return;
    } else {
        $("#pwd").val(pwd);
    }
    
    $.ajax({
         type: "post",
         url: "checkLogin.do",
         data: {phone:user, mm:$("#pwd").val()},
         dataType: "json",
         success: function(data){
             if(data.code != 1) {
                 $("#tip").html(decodeURI(data["message"]));
             } else {
                 $("#tip").html(decodeURI(data["message"]));
                 window.location.href = "login.do";
             }
         }
    });
}

$(function(){
    $("#myform").keydown(function(event){
        event.stopPropagation();
        if(event.keyCode == 13)
            dosubmit();
    });
});

</script>

<style>
.tip_parent{
    position:relative;
}
.validateErrorMsg{
    position:absolute;
    top:10px;
    left:278px;
    color:red;
    width:100px;
    font-weight:bold;
}
.jcbk_login_footer{
    text-align: center;
    color: #1963af;
}
</style>
</head>
<body>
    <div class="login_con">
            <div class="logo" style="line-height: 70px"></div>
            
            <div class="main_div">
                <div class="container" style="width: 1270px;height: 0px;">
                    <div style="margin:auto;width:1051px;height: 0px;">
                        <form name="myform" id="myform" action="login.do" method="post" style="left: 0%;padding-left: 0px;">
                            <div class="login_conbg" >
                                <div class="login">
                                    <div class="login_name">
                                        <!-- 隐藏input输入框的背景 -->
                                        <input type="text" alt="用户名" id="user" name="user" class="login_text" hasplaceholder="true"  placeholder="请输入用户名"  />
                                    </div>
                                    <div class="login_password">
                                        <input type="hidden" id="pwd" name="pwd" />
                                        <input type="password" alt="密码" id="password" name="password" class="login_text" hasplaceholder="true"  placeholder="请输入密码" />                                
                                    </div>
                                </div>
                                <div id="tip" style="width:250px; height:25px;text-align:center;padding:10px 50px 0px 60px;font-size:14px;color:red"></div>
                                <div class="login_btnbox" style="padding-top:20px">
                                
                                    <input type="button" id="btnSubmit" name="btnSubmit"  class="login_btn" onclick="dosubmit();"  />
                                </div>
                                
                            </div>
                            <div class="left_icon"></div>
                        </form>
                    </div>
                </div>
                <div class="city_bg"></div>
            </div>
            
        <div class="jcbk_login_footer">
            <p style="letter-spacing:1px;line-height:120%;">2018-私人订制<br />
        </div>
        </div>
    
<c:if test="${message!=null}">
<script>
    $.messager.alert('提示框','<c:out value='${message}'/>','info');
    
</script>
</c:if>  
   
</body>
<script type="text/javascript">
    $(document).ready(function(){
        // 输入栏占位符
        $("input[hasPlaceholder='true'],textarea[hasPlaceholder='true']").placeholder({
            labelMode : true,
            labelStyle : {
                margin : "0",
                lineHeight : "inherit"
            },
            labelClass : "s_placeholder"
        });
        $('#user').focus();
          $(".login_con").css("margin-top",($(window).height()-$(".login_con").height())/2-10 +"px");
           $(".background-house").css("top",($(window).height()-$(".login_con").height())/2-20+$(".login_con").height() - 129+ "px");
           $("#footer").css("top",($(window).height()-$(".login_con").height())/2-28+$(".login_con").height() - 129 + 129 + "px");
           
            $(window).resize(function(){
               var login_con_height = ($(window).height()-$(".login_con").height())/2-20 ;
               $(".login_con").css("margin-top",login_con_height +"px");
               $(".background-house").css("top",($(window).height()-$(".login_con").height())/2-20+$(".login_con").height()-129+ "px");
               $("#footer").css("top",($(window).height()-$(".login_con").height())/2-28+$(".login_con").height() - 129 + 129 + "px");
           }); 
    });

</script>
</html>
