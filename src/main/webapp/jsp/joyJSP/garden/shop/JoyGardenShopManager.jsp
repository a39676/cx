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
    <div class="col-sm-12">
      <div class="btn-group" role="group" id="shopTag">
        <button type="button" class="btn btn-primary" id="shopTagPlant">植物</button>
        <button type="button" class="btn btn-primary" id="shopTagFertilizer">肥料</button>
      </div>
    </div>
  </div>

  <div class="row" id="shopManagerDetail">
  </div>

  <div class="row">
    <div class="col-sm-12">
      <span>数量</span> <input type="number" name="" value="0" id="addStoreCounting"><br>
      <span>价格</span> <input type="number" name="" value="0" id="addStorePrice"><br>
      <button type="button" name="" class="btn btn-sm btn-primary" id="addStore">增加商店库存</button>
    </div>
  </div>

  <div class="row" id="shopSellingDetail">
  </div>

  <div class="row">
    <div class="col-sm-12">
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

      goodsSourceSearch();

      $("#shopTagPlant").click(function () {
        switchCurrentTag($(this).attr("id"));
        goodsSourceSearch();
      });

      $("#shopTagFertilizer").click(function () {
        switchCurrentTag($(this).attr("id"));
      });

      function goodsSourceSearch() {

        var url = "/joyManager/garden/shop/plantSearchView";

        var jsonOutput = {};

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
            $("#shopManagerDetail").html(data);
          },
          error:function(e){
          }
        });
      }

      function switchCurrentTag(tagId) {
        $("#shopInfo").attr("currentTag", tagId);
        $("#shopInfo").attr("selectGoodsPK", "");
      }

      $("#addStore").click(function () {
        $("#shopFeedback").text("");
        
        var url = "/joyManager/garden/shop/addShopStore";

        var goodsMainType = "";
        var currentMainTag = $("#shopInfo").attr("currentTag");
        if(currentMainTag == "shopTagPlant"){
          goodsMainType = 1;
        } else if(currentMainTag == "shopTagFertilizer"){
          goodsMainType = 2;
        }

        var goodsSubType = 1;
        var goodsPK = $("#shopInfo").attr("selectGoodsPK");
        var addCounting = $("#addStoreCounting").val();
        var price = $("#addStorePrice").val();

        var jsonOutput = {
          "goodsMainType":goodsMainType,
          "goodsSubType":goodsSubType,
          "goodsPK":goodsPK,
          "addCounting":addCounting,
          "price":price,
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
            if(data.code == 0){
              $("#shopFeedback").text("添加库存成功");
            } else {
              $("#shopFeedback").text(data.message);
            }

          },
          error:function(e){
          }
        });
      });

    });

  </script>
</footer>
</html>
