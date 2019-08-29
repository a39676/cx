<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
<style type="text/css">
.fakePanel{
  background-color: #4169E1;
  color: #C0C0C0;
  border-style: solid;
  border-color: #FA8072 #87CEEB;
}
a {
    color: hotpink;
}
</style>
</head>

<body>

<div style="display:none;" name="hiddenMessage" bankId="" bankUnionId="" accountId="" ></div>
 
<table>

  <thead>
    <tr>
      <td class="fakePanel" name="basePart">
        <a href="${pageContext.request.contextPath}/">回到主页</a>
      </td>
    </tr>
    <tr>
      <td>
        <c:if test="${pageContext.request.userPrincipal.name != null}">
      
        Welcome! ${pageContext.request.userPrincipal.name} 
      
        </c:if>
    
        <a href="${pageContext.request.contextPath}/login/logout"/>Logout</a>
      </td>
    </tr>
  </thead>
  

  <thead>
    <tr><td class="fakePanel" name="accountInfoPart" some="something">我的统计</td></tr>
  </thead>  
  <tbody style="display: none" name="accountInfoPart">
    <tr><td>
          <label class="fakeButton" 
            data-url="${pageContext.request.contextPath}/accountInfo/accountStatistics">
            所有卡 
          </label>
    </td></tr>
    <tr><td>
          <label class="fakeButton" 
            data-url="${pageContext.request.contextPath}/accountInfo/insertNewTransationV4">
            新加交易记录V4
          </label>
    </td></tr>
    <tr><td>
          <label class="fakeButton" 
            data-url="${pageContext.request.contextPath}/accountInfo/transationHistory">
            临时交易记录展示
          </label>
    </td></tr>
    <tr><td>
          <label class="fakeButton" 
            data-url="${pageContext.request.contextPath}/trading/importTradingRecordFromFiles">
            从文件导入交易记录
          </label>
    </td></tr>
    <tr><td>
          <label class="fakeButton" 
            data-url="${pageContext.request.contextPath}/accountInfo/transationHistoryQuery">
            交易记录查询
          </label>
    </td></tr>
    <tr><td>
          <label class="fakeButton" 
            data-url="${pageContext.request.contextPath}/accountInfo/accountRegist">
            新增卡号
          </label>
    </td></tr>
  </tbody>

  <thead>
    <tr><td class="fakePanel" name="toolPart" some="something">工具模块</td></tr>
  </thead>  
  <tbody style="display: none" name="toolPart">
    <tr><td>
<%-- 
          <label class="fakeButton" 
            data-url="${pageContext.request.contextPath}tool/image/imageSave">
            imageSave
          </label>
           --%>
    </td></tr>
  </tbody>

  <thead>
    <tr>
      <td class="fakePanel" name="holder">个人模块</td>
    </tr>
  </thead>  
  <tbody style="display: none" name="holder">
    <tr>
      <td>holder2</td>
    </tr>
  </tbody>

  <thead>
    <tr>
      <td class="fakePanel" name="testing">测试模块</td>
    </tr>
  </thead>  
  <tbody style="display: none" name="testing">
    <tr><td>
          <label class="fakeButton" 
            data-url="${pageContext.request.contextPath}/accountInfo/insertNewTransationV4">
            新加交易记录V4
          </label>
    </td></tr>
    <tr><td>
          <label class="fakeButton" 
            data-url="${pageContext.request.contextPath}/bankInfo/bankSelectorV4">
            银行选择器V4
          </label>
    </td></tr>
    <tr><td>
          <label class="fakeButton" 
            data-url="${pageContext.request.contextPath}/accountInfo/accountSelectorV1">
            账户选择器V4
          </label>
    </td></tr>
  </tbody>

  <thead>
    <tr>
      <td class="fakePanel" name="something">something</td>
    </tr>
  </thead>  
  <tbody style="display: none" name="something">
    <tr>
      <td>something1</td>
    </tr>
    <tr>
      <td>something2</td>
    </tr>
    <tr>
      <td class="fakePanel" name="something_1">somethingSub</td>
      <tbody style="display: none" name="something_1">
        <tr>
          <td>sub1</td>
        </tr>
        <tr>
          <td>sub2</td>
        </tr>
      </tbody>
    </tr>
  </tbody>
</table>
</body>

<footer>
  <%@ include file="../baseElementJSP/normalFooter.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("td[class='fakePanel']").click(function(){
        
        var $panelName = $(this).attr('name');
        
        if($("tbody[name='" + $panelName + "']").is(":visible")) {
          $("tbody[name^='" + $panelName + "']").hide();
        } else {
        // if($("tbody[name='" + $panelName + "']").is(":hidden")) {
          $("tbody[name='" + $panelName + "']").show();
        }

      });

    });
  </script>
</footer>
</html>