<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
  <table>
    <tr>
      <td><label>最大上传30m</label></td>
      <td><input type="file" id="fileUpload" /></td>
      <td>
        <p>选择语言</p>
        <select id="languageSelector">
          <c:forEach items="${languageTypeList}" var="languageType">
            <option value="${languageType.name}">${languageType.name}</option>
          </c:forEach>
        </select>
      </td>
    </tr>
    <tr>
      <td><button id="uploadButton">上传</button></td>
    </tr>
  </table>
<div>
  <textarea style="height: 200px; width: 100%;" id="uploadResultMessage"></textarea>
</div>
</body>
<footer>
<%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
<script type="text/javascript">

  $(document).ready(function() {

    $("#uploadButton").click( function() {
      upload();
    });

    function upload(){ 
      var language = $("#languageSelector").val();

      var uploadFile = new FormData();
      uploadFile.append("file", fileUpload.files[0]);
      uploadFile.append("language", language);
      
      var url = "/ocr/uploadImg";

      $.ajax({  
        type : "POST",  
        async : true,
        url : url,  
        data : uploadFile,
        cache : false,
        contentType : false,
        processData : false,
        timeout:50000,  
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success:function(datas){
          $("#uploadResultMessage").val(datas.message);
        },  
        error: function(datas) {  
          $("#uploadResultMessage").val(datas.message);
        }  
      });  
    };
  
  });

</script>
</footer>
</html>