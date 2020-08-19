<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>
<body>

  <div class="container-fluid">

    <div class="row" id="topRow">
      <div class="col-sm-3">
        <p>joy start top row  </p>
      </div>
    </div>

    <div class="row" id="sceneGroupRow">
      <div class="col-sm-12">
        <table>
          <tr>
            <td>场景组</td>
          </tr>
          <tr>
            <c:forEach items="${sceneGroupVOList}" var="sceneGroupVO">
              <td pk="${sceneGroupVO.pk}">
                <%-- <p>pk: ${sceneGroupVO.pk}</p> --%>
                <p>groupName: ${sceneGroupVO.name}</p>
                <p>remark: ${sceneGroupVO.remark}</p>
              </td>
            </c:forEach>
          </tr>
        </table>
      </div>
    </div>

    <div class="row" id="mainRow">
      <div class="col-sm-3">
        <%-- ${characterDetailVO} --%>
        <p>pk: ${characterDetailVO.pk}</p>
        <p>name: ${characterDetailVO.name}</p>
        <p>gender: ${characterDetailVO.gender}</p>
      </div>
    </div>
    
  </div>
  
  <p id="resultView"></p>
</body>
<footer>
</footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp" %>
<script type="text/javascript">
  $(document).ready(function() {

    function getCharacterDetail() {
      console.log("in getCharacterDetail");
      var url = "/joy/character/getCharacterDetail";
      var jsonOutput = {
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
          console.log(datas);
          var characterDetailVO = datas.characterVO;

          $("#getCharacterDetail").val(
            "character pk: " + characterDetailVO.pk 
            + "gender: " + characterDetailVO.gender
            + "name: " + characterDetailVO.name
            );
        },  
        error: function(datas) {
          console.log("error: " + datas);
          $("#resultView").html(datas);
        }  
      });  
    };

  });
</script>
</html>