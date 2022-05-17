<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
<div class="container-fluid">

<div class="row">
  <div class="col-md-12" >
    <label>此信息内容为其他用户产生,切莫轻信.</label><br>
  </div>
</div>

<hr>

<div class="row">
  <div class="col-md-12" style="word-break:break-word;">
    <p id="burningMessage">${content}</p>
  </div>
</div>

<div class="row">
  <div class="col-md-12" style="word-break:break-word;">
    <c:if test="${needPwd}">
      <div class="input-group mb-3" id="pwdInputDiv">
        <div class="input-group-prepend ">
          <span class="input-group-text" style="font-size: small;">请输入阅读密码</span>
        </div>
        <input class="form-control" type="password" id="messagePwd" maxlength="16" value="">
        <div class="input-group-append">
          <button class="btn btn-primary" type="button" id="readBurningMessageByPwd">提交</button>
        </div>
      </div>
    </c:if>
  </div>
</div>

<hr>

<div class="row destoryDiv">
  <div class="col-md-12">
    <span style="font-size: small;" name="burnUri"></span>
  </div>
</div>

<div class="row destoryDiv">
  <div class="col-md-2">
    <button class="btn btn-danger btn-sm" name="destoryButton" style="display: none">
      <span style="font-size: small;" >马上销毁本信息</span>
    </button>
  </div>
</div>

<hr>

<div class="row">
  <div class="col-md-2">
    <a href="/articleBurn/createBurnMessage">点此新建另一条信息</a>
  </div>
</div>

<hr>

<div class="row">
  <div class="col-md-12">
    <div id="createBurnMessage"></div>
  </div>
</div>

</div>
</body>

<footer>
  <%@ include file="../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      var urlPerfix = window.location.host;
      var burnUri = "";

      if(${needPwd}){
        $(".destoryDiv").hide();
      } else {
        ${burnUri}
        showDestoryDiv(${burnUri});
      }

      $("button[name='destoryButton']").click(function() {
        destoryButton();
      });

      function destoryButton() {
        var url = burnUri;
        $.ajax({
          type : "GET",
          async : true,
          url : url,
          cache : false,
          timeout:50000,
          success:function(datas){
            $("span[name='burnUri']").text("信息已销毁");
            $("#burningMessage").html("");
            $("#messagePwd").val("");
          },
          error: function(datas) {
          }
        });
      };

      $("#readBurningMessageByPwd").click(function () {
        readBurningMessageByPwd();
      });

      function readBurningMessageByPwd() {
        var url = "/articleBurn/readBurningMessageByPwd";
        var readKey = "${readKey}";
        var pwd = $("#messagePwd").val();

        var jsonOutput = {
          readKey : readKey,
          pwd : pwd
        };

        $.ajax({
          type : "POST",
          async : true,
          url : url,
          data: JSON.stringify(jsonOutput),
          cache : false,
          contentType: "application/json",
          dataType: "json",
          timeout:50000,
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
          success:function(datas){
            $("#burningMessage").html(datas.content);
            if(datas.code == 0){
              $("#readBurningMessageByPwd").prop('disabled', true);
              showDestoryDiv(datas.burnUri);
              $("#pwdInputDiv").hide();
            }
          },
          error: function(datas) {
          }
        });
      };

      function showDestoryDiv(newBurnUri) {
        burnUri = newBurnUri;
        $("span[name='burnUri']").append("访问此地址销毁信息: ");
        $("span[name='burnUri']").append("<br>");
        $("span[name='burnUri']").append(urlPerfix);
        $("span[name='burnUri']").append(burnUri);
        $("button[name='destoryButton']").show();
        $(".destoryDiv").show();
      }
    });

  </script>
</footer>
</html>
