<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
  <div class="container-fluid">
    <div class="row">
      <div class="col-md-12">
        <table class="table table-striped table-dark">
          <thead class="thead-dark">
            <tr>
              <td>Name</td>
              <td>Need pwd</td>
              <td></td>
            </tr>
          </thead>
          <tbody id="bookmarkVoList">
            <c:forEach items="${bookmarkList}" var="bookmarkVO">
              <tr class="bookmarkVO">
                <td>
                  <a href="/bookmark/detail?pk=${bookmarkVO.pk}">${bookmarkVO.bookmarkName}</a>
                </td>
                <td>
                  <input name="needPwd" bookmarkPK="${bookmarkVO.pk}" type="text"
                  value="${bookmarkVO.needPwd}">
                </td>
                <td>
                  Edit
                </td>
              </tr>
            </c:forEach>
            <tr>
              <input type="text" name="" placeholder="New bookmark name" id="newBookmarkName">
              <input type="file" name="" id="uploadHtmlInput">
              <input type="text" name="" id="uploadHtmlContent" style="display: none">
              <input type="password" name="" id="pwd1">
              <input type="password" name="" id="pwd2">
              <button id="updateHtmlBookmark">Submit html bookmark</button>
              <span id="uploadResult"></span>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</body>

<footer>
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("#uploadHtmlInput").change(function() {
        const file = this.files[0];
        let reader = new FileReader();
        reader.onload = function(event){
          var content = event.target.result;
          $("#uploadHtmlContent").val(content);
        }
        reader.readAsDataURL(file);
      });

      $("#updateHtmlBookmark").click(function () {
        var url = "/bookmark/uploadBookmark";

        var newBookmarkName = $("#newBookmarkName").val();
        var pwd = "";
        var bookmarkHtmlInBase64 = $("#uploadHtmlContent").val();

        // TODO
        if(pwd1.length > 0){
          if(pwd1 != pwd2){
            $("#uploadResult").text("The passwords entered twice do not match");  
          } else {
            pwd = pwd1;
          }
        }

        var jsonOutput = {
          bookmarkName : newBookmarkName,
          pwd : pwd,
          bookmarkHtmlInBase64 : bookmarkHtmlInBase64,
        };

        console.log(jsonOutput);

        $.ajax({
          type : "POST",
          url : url,
          data: JSON.stringify(jsonOutput),
          dataType: 'json',
          contentType: "application/json",
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
          timeout: 15000,
          success:function(data){
            if(data.code == 0){
              newBookmarkName.val("");
              bookmarkHtmlSrc.val("");
            }
            $("#uploadResult").text(data.message);
          },
          error:function(e){
          }
        });

      });

    });

  </script>
</footer>
</html>
