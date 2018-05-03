/**
 * 存放当前input对象
 */
var current;

//项目路径
var contextPath ="/"+ window.location.pathname.split("/")[1];
/**
 * 宽度缩小比例
 */
var wpercenage = 0.73;

$(document).ready(function(){
    //禁止非法字符输入 ，禁止输入\"'[]<>,
    $('input').bind('input propertychange', function (event) {
        var value = $(this).val();
        if(value.indexOf('\\')>=0||value.indexOf('\"')>=0||value.indexOf('\'')>=0||value.indexOf('\[')>=0||value.indexOf('\]')>=0||value.indexOf('\,')>=0||value.indexOf('\<')>=0||value.indexOf('\>')>=0){
            $(this).val($(this).val().replace(/\\/g,"").replace(/\"/g,"").replace(/\'/g,"").replace(/\[/g,"").replace(/\]/g,"").replace(/\</g,"").replace(/\>/g,"").replace(/\,/g,""));
            alert("请勿输入非法字符  \" ' \\ [ ] , < > ");
        }
    });
    $('textarea').bind('input propertychange', function (event) {
        var value = $(this).val();
        if(value.indexOf('\\')>=0||value.indexOf('\"')>=0||value.indexOf('\'')>=0||value.indexOf('\[')>=0||value.indexOf('\]')>=0||value.indexOf('\,')>=0||value.indexOf('\<')>=0||value.indexOf('\>')>=0){
            $(this).val($(this).val().replace(/\\/g,"").replace(/\"/g,"").replace(/\'/g,"").replace(/\[/g,"").replace(/\]/g,"").replace(/\</g,"").replace(/\>/g,"").replace(/\,/g,""));
            alert("请勿输入非法字符  \" ' \\ [ ] , < > ");

        }
    });
});

$(function() {
});

/**
 * 小写字母转大写字母
 * @param classname
 * @return
 */
function toUpperAllLetter(classname){
    var _input = $('.' + classname);
    var _value = _input.val();
    if(_value != ''){
        var result = _value.toUpperCase();
        _input.val(result);
    }
}

/**
 * 小写字母转大写字母
 * @param classname
 * @return
 */
function toUpperAllLetterById(id){
    var _input = $('#' + id);
    var _value = _input.val();
    if(_value != ''){
        var result = _value.toUpperCase();
        _input.val(result);
    }
}

/**
 * 小于10的数字前加零 或都长度小于2的数据字符串
 * @param param
 * @return
 */
function addZeroBeforStr(param) {
    var temp = '';
    if (typeof param == 'number') {
        if (param >= 0 && param < 10) {
            temp = '0' + param;
            return temp;
        } else {
            return param;
        }
    } else if (typeof param == 'string') {
        var pattern = new RegExp("^[0-9]+$", "g");
        var result = pattern.exec(param);
        if (!result) {
            alert('参数类型不正确!');
            return param;
        }
        var len = param.length;
        if (len < 2) {
            temp = '0' + param;
        } else {
            return param;
        }
    }
}

/**
 * conj:连接符个一般为'-'或'/' 默认实现'-'如下： yyyy-MM-dd 24hh:mi:ss
 *
 * @param timestamp
 * @param conj
 * @return
 */
function formatDateString(timestamp, conj) {
    var default_conj = '-';
    var _conj = '';
    // 默认连接符串
    if (arguments.length == 1) {
        _conj = default_conj;
    } else {
        _conj = conj;
    }
    // 构造日期时间变量
    var d = new Date(timestamp);
    // 年
    var year = d.getFullYear();
    // 月
    var month = addZeroBeforStr(d.getMonth() + 1);
    // 天
    var day = addZeroBeforStr(d.getDate());
    // 小时
    var hour = addZeroBeforStr(d.getHours());
    // 分
    var minute = addZeroBeforStr(d.getMinutes());
    // 钞
    var seconde = addZeroBeforStr(d.getSeconds());
    var _rq = year + _conj + month + _conj + day;
    var _time = hour + ":" + minute + ":" + seconde;
    var result = _rq + " " + _time;
    return result;
}

/**
 * conj:连接符个一般为'-'或'/' 默认实现'-'如下：  24hh:mi:ss
 *
 * @param timestamp
 * @param conj
 * @return
 */
function formatDateString_hhmiss(timestamp, conj) {
    var default_conj = '-';
    var _conj = '';
    // 默认连接符串
    if (arguments.length == 1) {
        _conj = default_conj;
    } else {
        _conj = conj;
    }
    // 构造日期时间变量
    var d = new Date(timestamp);
    // 小时
    var hour = addZeroBeforStr(d.getHours());
    // 分
    var minute = addZeroBeforStr(d.getMinutes());
    // 钞
    var seconde = addZeroBeforStr(d.getSeconds());
    var _time = hour + ":" + minute + ":" + seconde;
    var result = _time;
    return result;
}

/**
 * conj:连接符个一般为'-'或'/' 默认实现'-'如下： yyyy-MM-dd
 *
 * @param timestamp
 * @param conj
 * @return
 */
function formatDateString_yyyyMMdd(timestamp, conj) {
    var default_conj = '-';
    var _conj = '';
    // 默认连接符串
    if (arguments.length == 1) {
        _conj = default_conj;
    } else {
        _conj = conj;
    }
    // 构造日期时间变量
    var d = new Date(timestamp);
    // 年
    var year = d.getFullYear();
    // 月
    var month = addZeroBeforStr(d.getMonth() + 1);
    // 天
    var day = addZeroBeforStr(d.getDate());
    var _rq = year + _conj + month + _conj + day;
    var result = _rq;
    return result;
}

/**
 *
 * @param date
 * @return
 */
function formatDate(date) {
    if (date instanceof Date) {
        var y = date.getFullYear();
        var m = addZeroBeforStr(date.getMonth() + 1);
        var d = addZeroBeforStr(date.getDate());
        var h = addZeroBeforStr(date.getHours());
        var i = addZeroBeforStr(date.getMinutes());
        var s = addZeroBeforStr(date.getSeconds());
        var ms = addZeroBeforStr(date.getMilliseconds());
        if (ms > 0)
            return y + '-' + m + '-' + d + ' ' + h + ':' + i + ':' + s + '.'
                    + ms;
        if (h > 0 || i > 0 || s > 0)
            return y + '-' + m + '-' + d + ' ' + h + ':' + i + ':' + s;
        return y + '-' + m + '-' + d;
    }
    return '';
}

/**
 * 列表宽
 * @param percent
 * @return
 */
function fixWidth(percent){
    var bodyWidth = document.body.clientWidth;
    return (bodyWidth - 90) * percent;
}

/**
 *
 * @param gridId
 * @return
 */
function resetDatagridWidth(gridId){
    var bodyWidth = document.body.clientWidth;
    /*datagrid重设大小*/
    $(window).resize(function() {
        $('#' + gridId).datagrid('resize');
        if($('#databs')){
            $('#databs').tabs('resize');
        }
    });
}

function resetWindSize(wid){
    /*Window重设大小*/
    if($(window).width()<=1000){
        wpercenage = 0.9;
    }else{
        wpercenage = 0.73;
    }
    $(window).resize(function() {
        var _wth = getClientWidth() * wpercenage ;
        var _left = (getClientWidth() - _wth) / 2 ;
        $('#' + wid).window('resize',{width:_wth ,top:10, height:'auto', left:_left});
        if($('#databs')){
            $('#databs').tabs('resize');
        }
    });
}

function getPageInfo(gridId){
    var p = $('#'+gridId).datagrid('getPager');
    $(p).pagination({
        beforePageText:'第',
        afterPageText:'页,共{pages}页',
        displayMsg:'{from}-{to}/{total}'
    });
}

function getClientWidth(){
    var viewWith = document.body.clientWidth;
    return viewWith;
}


function clearDiv(){
    $('#suspDiv').css('display','none');
    $('#carDiv').css('display','none');
    $('#cmdListDiv').css('display','none');
    $('#liveTraceDiv').css('display','none');

    $('#myform').form('clear');
}

/**
 * 全选
 * @param inputName 表单域名(复选框)
 * @return
 */
function allselect(inputname){
    $("input[name='"+inputname+"']").each(function(){
        $(this).attr('checked',true);
    });
}

/**
 * 反选
 * @param inputName 表单域名(复选框)
 * @return
 */
function noselect(inputname){
    $("input[name='"+inputname+"']").each(function(){
        var flag = $(this).attr('checked') == 'checked';
        if(flag) {
            $(this).attr('checked',false);
        } else {
            $(this).attr('checked',true);
        }
    });
}

/**
 * 获取选中复选框信息(文本、值)
 * @param inputname 复选框表单域名
 * @param desIdtext 存复选框名称
 * @param desIdval 存复选框值
 * @return
 */
function dosubmit(inputname, desIdtext, desIdval){
    // 二维数组
    var s = new Array(2);
    // 单位代码
    s[0] = "";
    // 单位代码名称
    s[1] = "";
    $("input[name='"+inputname+"']").each(function(){
        if($(this).attr('checked')){
            var nr = $("#mc" + $(this).val()).html();
            var nrs = nr.split(':');
            s[0] = s[0] + nrs[0] + ",";
            s[1] = s[1] + nrs[1] + ",";
        }
    });
    $('#' + desIdtext).val(s[1]);
    $('#' + desIdval).val(s[0]);
}

function ajaxLoading(msg,msgMaginTop){
    //信息提示
    var default_msg = '正在处理，请稍后。。。';
    //信息布局位置向上内边距
    var default_msgMaginTop= -45;
    var _msg = '';
    var _msgMaginTop=0;
    // 判断参数
    if (msg == 'undefined') {
        _msg = default_msg;
    } else {
        _msg = msg;
    }
    if (msgMaginTop == 'undefined') {
        _msgMaginTop = default_msgMaginTop;
    } else {
        _msgMaginTop = msgMaginTop;
    }
    //$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
    $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:document.body.offsetHeight}).appendTo("body");
    $("<div class=\"datagrid-mask-msg\"></div>").html(_msg).appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:(document.body.offsetHeight + _msgMaginTop) / 2});
}

function ajaxLoadEnd(){
    $(".datagrid-mask").remove();
    $(".datagrid-mask-msg").remove();
}

//使datagrid列表的特殊字符、html正常显示
function formatEncodeHtml(value, row, index) {
    return encodeHtml(value);
}

this.REGX_HTML_ENCODE = /"|&|‘|<|>|[\x00-\x20]|[\x7F-\xFF]|[\u0100-\u2700]/g;
function encodeHtml(s) {
    return (typeof s != "string") ? s :
            s.replace(this.REGX_HTML_ENCODE,
                    function ($0) {
                        var c = $0.charCodeAt(0), r = ["&#"];
                        c = (c == 0x20) ? 0xA0 : c;
                        r.push(c);
                        r.push(";");
                        return r.join("");
                    });
}