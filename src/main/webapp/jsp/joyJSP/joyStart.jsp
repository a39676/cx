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
        <table class="table table-hover">
          <thead>
            <tr>
              <th>场景组</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <c:forEach items="${sceneGroupVOList}" var="sceneGroupVO">
                <td class="sceneGroupVO" pk="${sceneGroupVO.pk}">
                  <button class="btn btn-sm btn-primary sceneGroupButton">
                    <div class="badge badge-primary">${sceneGroupVO.name}</div>
                  </button>
                </td>
              </c:forEach>
            </tr>
            <tr>
              <c:forEach items="${sceneGroupVOList}" var="sceneGroupVO">
                <td class="sceneGroupVO" pk="${sceneGroupVO.pk}">
                  <div class="badge badge-light sceneGroupVORemark" 
                    style="display: none" pk="${sceneGroupVO.pk}">
                    ${sceneGroupVO.remark}
                  </div>
                </td>
              </c:forEach>
            </tr>
          </tbody>
        </table>

        <table class="table table-hover">
          <thead>
            <tr id="sceneListTR">
              
            </tr>
          </thead>
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

      <div class="col-sm-6" id="mainDynamicDIV">
        
      </div>

      <div class="col-sm-3" id="sideDynamicDIV">
        
      </div>
    </div>
    
    <div class="row" id="footRow">
      <div class="col-sm-3">
        <p>foot row</p>
      </div>

      <div class="col-sm-6">
        <p id="resultView"></p>
      </div>
    </div>

  </div>
  
</body>
<footer>
</footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp" %>
<script type="text/javascript">
  $(document).ready(function() {

    $(".sceneGroupVO").click(function () {
      sceneGroupVOClick($(this).attr("pk"));  
    });

    function sceneGroupVOClick(sceneGroupPK) {
      var url = "/joy/joyScene/findSceneVOListBySceneGroupPK";
      var sceneListTR = $("#sceneListTR");
      var jsonOutput = {
        sceneGroupPK:sceneGroupPK,
      };

      $(".sceneGroupVORemark").hide();
      $(".sceneGroupVORemark[pk='"+sceneGroupPK+"']").show();

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
          var sceneVOList = datas.sceneVOList;
          sceneListTR.empty();
          $.each(sceneVOList, function(index, sceneVO) {
            sceneListTR.append($("<button class='btn btn-sm btn-success'></button>").attr("pk", sceneVO.pk).text(sceneVO.sceneName));
            // $("button[pk='"+sceneVO.pk+"']").addClass("bankButton");
            // $("button[pk='"+sceneVO.pk+"']").bind("click", bankButtonClick);
          });
        },  
        error: function(datas) {
          console.log("error: " + datas);
        }  
      });  
    }

    function getCharacterDetail() {
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