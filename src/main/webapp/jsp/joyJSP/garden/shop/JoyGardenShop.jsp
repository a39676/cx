<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
<div class="container-fluid">
  <div class="row">
    <div class="col-md-12">
      <div class="btn-group" role="group" id="shopTag" currentTag="">
        <button type="button" class="btn btn-primary" id="shopTagSeed">种子</button>
        <button type="button" class="btn btn-primary" id="shopTagFertilizer">肥料</button>
      </div>
    </div>
  </div>

  <div class="row" id="shopSellingDetail">
  </div>

  <div class="row">
    <div class="col-md-12">
      <span id="shopFeedback"></span>
    </div>
  </div>

  <div class="row">
    <div class="col-sm-12">
      <span id="shopInfo" currentTag="shopTagPlant" selectGoodsPK=""></span>
    </div>
  </div>


</div>
</body>

<footer>
  <%@ include file="../../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      goodsStoreSearch();

      $("#shopTagSeed").click(function () {
        switchCurrentTag($(this).attr("id"));
        goodsStoreSearch();
      });

      $("#shopTagFertilizer").click(function () {
        switchCurrentTag($(this).attr("id"));
      });

      function goodsStoreSearch() {
        var url = "/joy/garden/shop/plantSearchView";

        var goodsMainType = "";
        var currentMainTag = $("#shopInfo").attr("currentTag");
        if(currentMainTag == "shopTagPlant"){
          goodsMainType = 1;
        } else if(currentMainTag == "shopTagFertilizer"){
          goodsMainType = 2;
        }

        var goodsSubType = 1;

        var jsonOutput = {
          "goodsMainType":goodsMainType,
          "goodsSubType":goodsSubType,
        };

        $.ajax({
          type : "POST",
          url : url,
          data: JSON.stringify(jsonOutput),
          // dataType: 'json',
          contentType: "application/json",
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
          timeout: 15000,
          success:function(data){
            $("#shopSellingDetail").html(data);
          },
          error:function(e){
          }
        });
      }

      function switchCurrentTag(tagId) {
        $("#shopTag").attr("currentTag", tagId);
      }
    });

  </script>
</footer>
</html>
