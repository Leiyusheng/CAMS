//扩展easyui表单的验证
$.extend($.fn.validatebox.defaults.rules, {
      phoneRex: {
            validator: function(value){
            var rex=/^1[3-8]+\d{9}$/;
            //var rex=/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
            //区号：前面一个0，后面跟2-3位数字 ： 0\d{2,3}
            //电话号码：7-8位数字： \d{7,8
            //分机号：一般都是3位数字： \d{3,}
             //这样连接起来就是验证电话的正则表达式了：/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/
            var rex2=/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
            if(rex.test(value)||rex2.test(value))
            {
              // alert('t'+value);
              return true;
            }else
            {
             //alert('false '+value);
               return false;
            }

            },
            message: '请输入正确电话或手机格式'
          },
        number: {
            validator: function (value, param) {
                return /^\d+$/.test(value);
            },
            message: '请输入正整数'
        },
        intOrFloat : {// 验证整数或小数
            validator : function(value) {
                return /^\d+(\.\d+)?$/i.test(value);
            },
            message : '请输入数字，并确保格式正确'
        },

        ip:{// 验证IP地址
            validator : function(value) {
            //edit by zhangbaoxin 2016.05.04
               var re = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;//正则表达式
               if(re.test(value))
               {
                   if( RegExp.$1<256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256)
                   return true;
               }
               return false;
                //return /d+.d+.d+.d+/i.test(value);
            //end
            },
            message : 'IP地址格式不正确'
        },
        idcard: {
            validator: function (value) {
                //return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
                return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/i.test(value);
            },
            message:'请输入正确的身份证号码'
        },
        //datetimebox对比, 1、加上时分秒的对比 2、解决未选开始时间，先选结束时间，再选大于结束时间的开始时间 不报错问题; edit by zhangbaoxin 2016.12.09
        compareDate: {
            validator: function (value, param) {
                startTime = $(param[0]).datetimebox('getValue');
                endTime = $(param[1]).datetimebox('getValue');
                if(startTime == "") {
                    return false;
                } else {
                    if(endTime != "") {
                        if ((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0)){
                            if (startTime.length > 0 && endTime.length > 0) {
                                var startTimeTemp = startTime.split(" ");
                                var endTimeTemp = endTime.split(" ");

                                var arrstartTime = startTimeTemp[0].split("-");
                                var arrendTime = endTimeTemp[0].split("-");

                                var arrStartTime = startTimeTemp[1].split(":");
                                var arrEndTime = endTimeTemp[1].split(":");

                                var allstartTime = new Date(arrstartTime[0], arrstartTime[1], arrstartTime[2], arrStartTime[0], arrStartTime[1], arrStartTime[2]);
                                var allendTime = new Date(arrendTime[0], arrendTime[1], arrendTime[2], arrEndTime[0], arrEndTime[1], arrEndTime[2]);

                                return allstartTime.getTime() -allendTime.getTime()>0?false:true;
                            }
                        } else {
                            var d1 = Date.parse(new Date(startTime));
                            var d2 = Date.parse(new Date(endTime));
                            return d2>d1;
                        }
                    } else {
                        return true;
                    }
                }
            },
            message: '开始时间不能大于结束时间'
           },

         dateFormat: {// 验证日期格式
               validator: function (value) {
                   //格式yyyy-MM-dd
                   return /^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/i.test(value);
               },
               message: '请输入合适的日期格式'
           },

       compareBeforeNow:{//控制选择日期不大于当天日期
            validator: function(value){
                var now = getNow(1);
                var date = value;
                return date>=now;
            },
            message: '选择时间不能小于当前时间'
        },
        compareToday:{//控制选择日期不大于当天日期
            validator: function(value){
                var today = new Date();
                var date = $.fn.datebox.defaults.parser(value);
                return date<=today;
            },
            message: '选择日期不能大于今天日期'
        },



        md: {//开始日期不大于结束日期（参数是开始日期）

            validator: function(value, param){
                startTime2 = $(param[0]).datebox('getValue');
                var d1 = startTime2;
                var d2 = value;

            //    if(d1.length>10){

                var timeReg = /^(?:19|20)[0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1])) (?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]:[0-5][0-9]$/;
                if(!timeReg.test(d2)){

                    return false;

                }
                return d2>=d1;
            },
            message: '结束日期小于开始日期或结束时间格式错误'
        },
        //验证时间跨度不大于7天
        mdseven: {
            validator: function(value, param){
                startTime2 = $(param[0]).datetimebox('getValue');
                var d1 = $.fn.datebox.defaults.parser(startTime2);
                var d2 = $.fn.datebox.defaults.parser(value);
                if(d1.getYear()!=d2.getYear()){
                    return false;
                }else if(d1.getMonth()!=d2.getMonth()){
                    return false;
                }else{

                }
                var day1 = d1.getDate();
                var day2 = d2.getDate();
                return day2<=day1+6&&d2>=d1;
            },
            message: '结束时间大于开始时间，且时间间隔小于七天'
        },
    //验证汉字
    CHS: {
        validator: function (value) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message: '只能输入汉字'
    },
    //移动手机号码验证
    mobile: {//value值为文本框中的值  edit by zhangbaoxin 2016.06.13
        validator: function (value) {
            var reg = /^1[3|4|5|8|9]\d{9}$/;
            if(reg.test(value) && value.length ==11) 
                return true;
            return false;
        },
        message: '输入手机号码格式不准确'
    },
    //车牌号码验证
    carNum: {//value值为文本框中的值
        validator: function (value) {
            var reg=/^[\u4e00-\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9\?]{4}[a-zA-Z_0-9_\u4e00-\u9fa5]$/;
            return reg.test(value);
        },
        message: '请输入正确的车牌号码'
    },
    //车牌号省份验证
    carFNum: {//value值为下拉框中的值
        validator: function (value) {
            if(value=="全部"||value=="-1"){
                return false;
            }else {
                return true;
            }
        },
        message: '请选择省份'
    },
    //国内邮编验证
    zipcode: {
        validator: function (value) {
            var reg = /^[1-9]\d{5}$/;
            return reg.test(value);
        },
        message: '邮编必须是非0开始的6位数字'
    },
    //用户账号验证(只能包括 _ 数字 字母) 
    account: {//param的值为[]中值
        validator: function (value, param) {
            if (value.length < param[0] || value.length > param[1]) {
                $.fn.validatebox.defaults.rules.account.message = '用户名长度必须在' + param[0] + '至' + param[1] + '范围';
                return false;
            } else {
                if (!/^[\w]+$/.test(value)) {
                    $.fn.validatebox.defaults.rules.account.message = '用户名只能数字、字母、下划线组成';
                    return false;
                } else {
                    return true;
                }
            }
        }, message: ''
    },
    loginName: {
        validator: function (value) {
            return /^[\u0391-\uFFE5\w]+$/.test(value);
        },
        message: '登录名称只允许汉字、英文字母、数字及下划线'
    },
    textLength:{
        validator:function(value){
            var reg=/^(.|\n){0,100}$/;
            return reg.test(value);
        },
    message: '限制文本长度'
    },
   
    //车牌号码右边的（除了省份）验证
    carNumRight: {//value值为文本框中的值
        validator: function (value) {
            var reg=/^[a-zA-Z\?]{1}[a-zA-Z_0-9\?]{4}[a-zA-Z_0-9_\u4e00-\u9fa5\?]$/;
            return reg.test(value);
        },
        message: '请输入正确的车牌号码(省份简称不包括)'
    }    
    ,
    longitude:{//经度
         validator: function (value) {
             if(/^\d+(\.\d+)?$/i.test(value)){

                if(value>=-72.004&&value<=134.8347){
                    return true;
                }
             }
             return false;

         },
         message: '请输入正确的经度格式'
    },
    latitude:{//纬度
        validator: function (value) {
         if(/^\d+(\.\d+)?$/i.test(value)){

            if(value>=0.8293&&value<=55.8271){
                return true;
            }
         }
         return false;

     },
          message: '请输入正确的纬度格式'


    },
    carNumsearch: {//value值为文本框中的值
        validator: function (value) {
            var reg=/^[a-zA-Z\?]{0,1}[a-zA-Z_0-9\?]{0,4}[a-zA-Z_0-9_\u4e00-\u9fa5\?]{0,1}$/;
            return reg.test(value);
        },
        message: '只能输入数字，中文，字母(最多六位，长度为六时第一位只能是字母)'
    },

    
    
    carBNum: {//value值为文本框中的值
        validator: function (value) {
            var reg=/^[a-zA-Z\?]{0,1}[a-zA-Z_0-9_\u4e00-\u9fa5\?]{0,5}$/;
            return reg.test(value);
        },
        message: '只能输入数字，中文，字母(最多六位，长度为六时第一位只能是字母)'
    },
    
    //验证设备尺寸：长*宽  add by zhangbaoxin 2016.06.13
    size: {
        validator: function (value) {
            if(value !='' || value != undefined) {
                if(value.indexOf("*") >0) {
                    var val = value.split("*");
                    if(val.length == 2) {
                       var re = /^\d+(\.\d+)?$/i;//正则表达式
                         if(re.test(val[0]) && re.test(val[1])) {
                             return true;
                         }
                         return false;
                    }
                    return false;
                }
                return false;
            }
            return false;
        },
        message: '格式不正确，应为：长*宽'
    },
    //验证数字和字母  add by zhangbaoxin 2016.08.17
    numOrLetter: {
        validator: function (value) {
            //if(value != null && value.trim() =='') {
            //    return false;
            //}
            //var reg = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>~！@#￥……&*（）&mdash;—|{}【】‘；：”“'。，、？]");
            var reg =  /^[\da-z]+$/i;
            return reg.test(value);
        },
        message: '只能输入英文字母、数字，请重新输入'
    },
    //限制文本长度（带参数）
    maxLength: {  
        validator: function(value, param){ 
            //全空输入
            if(value.replace(/\s+/g,"")==""){
                $.fn.validatebox.defaults.rules.maxLength.message = '禁止全空格输入';
                return false;
            }
            else if(value.length > param[0]){
                $.fn.validatebox.defaults.rules.maxLength.message = '最多输入'+param[0]+'个字符';
                return false;
            }else {
                return true;  }
        },  
        message: '' 
    } ,
    name: {
        validator: function (value) {
            return /^[\u4E00-\u9FA5A-Za-z0-9_——]+$/.test(value);
        },
        message: '只允许汉字、<br/>英文字母、数字、<br/>下划线及一字线'
    },
});