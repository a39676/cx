<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

  <head>
    <title>${ title }AI art image wall manager</title>
    <%@ include file="../baseElementJSP/normalHeader.jsp" %>
    <%@ include file="../baseElementJSP/normalJSPart.jsp" %>
  </head>

  <div class="container-fluid">
    <div class="row">
      <div class="col-lg-12">
        <label id="result"></label>
      </div>
    </div>

    <div class="row">
      <div class="col-lg-12">
        <table class="table">
          ${imgUrlMap}
          <tbody>
            <c:forEach items="${imgVoList}" var="imgVO" varStatus="status">
              <c:if test="${status.index % 3 == 0}">
                <tr>
              </c:if>
              <td name="imgTd" imgPk="${imgVO.imgPk}">
                <c:if test="${imgVO.imgUrl != null && imgVO.imgUrl != ''}">
                  <img name="thumbnail" imgPk="${imgVO.imgPk}" src="${imgVO.imgUrl}" style="width:200px; height:200px"><br>
                  <img name="sourceImg" imgPk="${imgVO.imgPk}" src="${imgVO.imgUrl}" style="display: none;"><br>
                </c:if>
                <c:if test="${imgVO.imgUrl == null}">
                  <c:url value="/image/getThumbnail" var="thumbnailUrl">
                    <c:param name="imgPK" value="${imgVO.imgPk}" />
                    <c:param name="width" value="200" />
                    <c:param name="height" value="200" />
                  </c:url>
                  <img name="thumbnail" imgPk="${imgVO.imgPk}" src="${thumbnailUrl}"><br>
                  <img name="sourceImg" imgPk="${imgVO.imgPk}" src="" style="display: none;"><br>
                </c:if>
                <label>jobId: ${imgVO.jobId}</label><br>
                <label>imgId: ${imgVO.imgId}</label><br>
                <button class="btn btn-sm btn-danger" name="removeFromImageWall" imgPk="${imgVO.imgPk}">
                  Remove
                </button>
                <button class="btn btn-sm btn-success" name="genLikeThat" jobPk="${imgVO.jobPk}">
                  genLikeThat
                </button>
              </td>
              <c:if test="${status.index % 3 == 2 or status.last}">
                </tr>
              </c:if>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    
  </div>

  <script type="text/javascript">
    
    $(document).ready(function() {

      $("button[name='removeFromImageWall']").click(function() {
        $("#result").text("");
        removeFromImageWall($(this).attr("imgPk"));
      })

      function removeFromImageWall(imgPk){
        var url = "/aiArtManager/removeFromImageWall";

        var jsonOutput = {
          pk:imgPk,
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
            if (datas.code == 0) {
              $("td[name='imgTd'][imgPk='"+imgPk+"']").hide();
            } else {
              $("#result").text(datas.code + ", " + datas.message);
            }
          },
          error: function(datas) {
          }
        });
      }

      $("button[name='genLikeThat']").click(function() {
        $("#result").text("");
        genLikeThat($(this).attr("jobPk"));
      })

      function genLikeThat(jobPk){
        var url = "/aiArtManager/generateOtherLikeThat";

        var jsonOutput = {
          pk:jobPk,
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
            $("#result").text(datas.code + ", " + datas.message);
          },
          error: function(datas) {
          }
        });
      }

      $("img[name='thumbnail']").click(function() {
        var imgPk = $(this).attr("imgPk");
        var sourceImg = $("img[name='sourceImg'][imgPk='"+imgPk+"']");
        var sourceImgSrc = sourceImg.attr("src");
        if('' == sourceImgSrc || 0 == sourceImgSrc.length){
          var imgPk = $(this).attr("imgPk");
          var imgPkUrlEncode = encodeURIComponent(imgPk);
          var url = "/image/getImage" + "?imgPK=" + imgPkUrlEncode;
          sourceImg.attr("src", url);
        }
        $(this).hide();
        sourceImg.show();
      })

      $("img[name='sourceImg']").click(function() {
        $(this).hide();
        var imgPk = $(this).attr("imgPk");
        $("img[name='thumbnail'][imgPk='"+imgPk+"']").show();
      })

    });
  </script>

</html>