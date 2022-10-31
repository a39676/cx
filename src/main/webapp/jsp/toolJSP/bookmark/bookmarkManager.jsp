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
              <td>
                <span>New bookmark name</span><br>
                <input type="text" name="" placeholder="New bookmark name" id="newBookmarkName">
              </td>
              <td>
                <input type="file" name="" id="uploadHtmlInput">
                <input type="text" name="" id="uploadHtmlContent" style="display: none">
              </td>
              <td>
                <div id="pwdInputDiv">
                  <input type="password" name="" id="pwd1" placeholder="Input password if need"><br>
                  <input type="password" name="" id="pwd2" placeholder="Repeat password">
                </div>
              </td>
              <td>
                <button class="btn btn-primary btn-sm" id="updateHtmlBookmark">
                  Submit html bookmark
                </button>
              </td>
            </tr>
            <tr>
              <td>
                <span id="uploadResult"></span>
              </td>
            </tr>
          </thead>
        </table>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <table class="table table-striped table-dark">
          <thead class="thead-dark">
            <tr>
              <td>Name</td>
              <td></td>
              <td></td>
            </tr>
          </thead>
          <tbody id="bookmarkVoList">
            <c:forEach items="${bookmarkList}" var="bookmarkVO">
              <tr class="bookmarkVO" bookmarkPK="${bookmarkVO.pk}">
                <td>
                  <a href="/bookmark/detail?pk=${bookmarkVO.pkUrlEncoded}">${bookmarkVO.bookmarkName}</a>
                </td>
                <td>
                  <c:if test="${bookmarkVO.needPwd}">
                    凭密码访问
                  </c:if>
                </td>
                <td>
                  <button class="btn btn-primary btn-sm editBookmark" 
                    bookmarkPK="${bookmarkVO.pk}">
                    Edit
                  </button>
                  <button class="btn btn-danger btn-sm deleteBookmark" 
                    bookmarkPK="${bookmarkVO.pk}">
                    Delete
                  </button>
                </td>
              </tr>
            </c:forEach>
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

      $(".deleteBookmark").click(function() {
        var url = "/bookmark/deleteBookmark";

        var bookmarkPK = $(this).attr("bookmarkPK");

        var jsonOutput = {
          bookmarkPK : bookmarkPK,
        };

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
            $("#uploadResult").text(data.message);
            if(data.code == 0){
              $(".bookmarkVO[bookmarkPK='"+bookmarkPK+"']").remove();
            }
          },
          error:function(e){
          }
        });
      });

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

        if(newBookmarkName.length < 1){
          $("#uploadResult").text("Please set bookmark name");
          return;
        }

        var pwd1 = $("#pwd1").val();
        var pwd2 = $("#pwd2").val();

        if(pwd1.length > 0){
          if(pwd1 != pwd2){
            $("#uploadResult").text("The passwords entered twice do not match");
            return;
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
              $("#newBookmarkName").val("");
              $("#uploadHtmlContent").val("");
              $("#pwd1").val("");
              $("#pwd2").val("");
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
