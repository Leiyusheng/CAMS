<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/comm.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta charset="UTF-8">
  <title>Swagger UI</title>
  <link rel="icon" type="image/png" href="${path}/swagger/images/favicon-32x32.png" sizes="32x32" />
  <link rel="icon" type="image/png" href="${path}/swagger/images/favicon-16x16.png" sizes="16x16" />
  <link href='${path}/swagger/css/typography.css' media='screen' rel='stylesheet' type='text/css'/>
  <link href='${path}/swagger/css/reset.css' media='screen' rel='stylesheet' type='text/css'/>
  <link href='${path}/swagger/css/screen.css' media='screen' rel='stylesheet' type='text/css'/>
  <link href='${path}/swagger/css/reset.css' media='print' rel='stylesheet' type='text/css'/>
  <link href='${path}/swagger/css/print.css' media='print' rel='stylesheet' type='text/css'/>

  <script src='${path}/swagger/lib/object-assign-pollyfill.js' type='text/javascript'></script>
  <script src='${path}/swagger/lib/jquery-1.8.0.min.js' type='text/javascript'></script>
  <script src='${path}/swagger/lib/jquery.slideto.min.js' type='text/javascript'></script>
  <script src='${path}/swagger/lib/jquery.wiggle.min.js' type='text/javascript'></script>
  <script src='${path}/swagger/lib/jquery.ba-bbq.min.js' type='text/javascript'></script>
  <script src='${path}/swagger/lib/handlebars-2.0.0.js' type='text/javascript'></script>
  <script src='${path}/swagger/lib/lodash.min.js' type='text/javascript'></script>
  <script src='${path}/swagger/lib/backbone-min.js' type='text/javascript'></script>
  <script src='${path}/swagger/swagger-ui.js' type='text/javascript'></script>
  <script src='${path}/swagger/lib/highlight.9.1.0.pack.js' type='text/javascript'></script>
  <script src='${path}/swagger/lib/highlight.9.1.0.pack_extended.js' type='text/javascript'></script>
  <script src='${path}/swagger/lib/jsoneditor.min.js' type='text/javascript'></script>
  <script src='${path}/swagger/lib/marked.js' type='text/javascript'></script>
  <script src='${path}/swagger/lib/swagger-oauth.js' type='text/javascript'></script>

  <!-- Some basic translations -->
  <!-- <script src='lang/translator.js' type='text/javascript'></script> -->
  <!-- <script src='lang/ru.js' type='text/javascript'></script> -->
  <!-- <script src='lang/en.js' type='text/javascript'></script> -->
  <script src='${path}/swagger/lang/translator.js' type='text/javascript'></script>
  <script src='${path}/swagger/lang/zh-cn.js' type='text/javascript'></script>

  <script type="text/javascript">
    $(function () {
      var url = window.location.search.match(/url=([^&]+)/);
      if (url && url.length > 1) {
        url = decodeURIComponent(url[1]);
      } else {
        url = window.location.href.split("/").slice(0,4).join("/") + "/api-docs";
        //url = "http://localhost:8123/CAMS/api-docs";
      }

      hljs.configure({
        highlightSizeThreshold: 5000
      });

      // Pre load translate...
      if(window.SwaggerTranslator) {
        window.SwaggerTranslator.translate();
      }
      window.swaggerUi = new SwaggerUi({
        url: url,
        dom_id: "swagger-ui-container",
        supportedSubmitMethods: ['get', 'post', 'put', 'delete', 'patch'],
        onComplete: function(swaggerApi, swaggerUi){
          if(typeof initOAuth == "function") {
            initOAuth({
              clientId: "your-client-id",
              clientSecret: "your-client-secret-if-required",
              realm: "your-realms",
              appName: "your-app-name",
              scopeSeparator: ",",
              additionalQueryStringParams: {}
            });
          }

          if(window.SwaggerTranslator) {
            window.SwaggerTranslator.translate();
          }
        },
        onFailure: function(data) {
          log("Unable to Load SwaggerUI");
        },
        docExpansion: "none",
        jsonEditor: false,
        defaultModelRendering: 'schema',
        showRequestHeaders: false
      });

      window.swaggerUi.load();

      function log() {
        if ('console' in window) {
          console.log.apply(console, arguments);
        }
      }
  });

    //退出登录
    function LoginOutHandler(){
        window.location.href = 'logout1.do';
    }
  </script>
</head>

<body class="swagger-section">
<div id='header'>
  <div class="swagger-ui-wrap">
      <a id="logo" href="http://swagger.io"><img class="logo__img" alt="swagger" height="30" width="30" src="${path}/swagger/images/logo_small.png" /><span class="logo__title">swagger</span></a>
      <form id='api_selector'>
          <div class='input'><input placeholder="http://example.com/api" id="input_baseUrl" name="baseUrl" type="text"/></div>
          <div id='auth_container'></div>
          <div class='input'><a id="explore" class="header__btn" href="#" data-sw-translate>Explore</a></div>&nbsp;
          <div class='input'><a id="exit" class="header__btn" href="#" data-sw-translate  onclick="LoginOutHandler()">Exit</a></div>
      </form>

  </div>
</div>

<div id="message-bar" class="swagger-ui-wrap" data-sw-translate>&nbsp;</div>
<div id="swagger-ui-container" class="swagger-ui-wrap"></div>
</body>
</html>
