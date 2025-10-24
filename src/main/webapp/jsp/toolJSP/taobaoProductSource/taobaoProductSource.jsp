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
  <div class="container-fluid">
    <div class="row">
      <div class="col-md-12">
        <label id="msg">${msg}</label>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <table class="table table-striped table-bordered table-hover">
          <tr>
            <td>货名</td>
            <td>挂链ID</td>
            <td>来源ID</td>
            <td>首图</td>
            <td>是否包邮</td>
            <td>备注</td>
          </tr>
          <tr>
            <td>
              <input type="text" name="" id="commodityName" placeholder="货名">
            </td>
            <td>
              <input type="text" name="" id="commodityId" placeholder="挂链ID"><br>
            </td>
            <td>
              <input type="text" name="" id="sourceId" placeholder="来源ID"><br>
            </td>
            <td>
              <input type="text" name="" id="commodityImgName" placeholder="首图(仅图片名称部分)"><br>
            </td>
            <td>
              <input type="checkbox" id="includePostage">
              <label for="includePostage">是否包邮</label>
            </td>
            <td>
              <input type="text" name="" id="remark" placeholder="备注"><br>
            </td>
          </tr>
        </table>
        <button id="searchProduct">SearchProduct</button>
        <button id="createProduct">CreateProduct</button>
        <button id="resetProductCondition">reset</button>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <div id="productList">
          
        </div>
      </div>
    </div>
    
  </div>
</body>
<footer>
<%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
<script type="text/javascript">

  $(document).ready(function() {

    $("#searchProduct").click( function() {
      searchProduct();
    });

    function searchProduct(){ 
      var commodityName = $("#commodityName").val();
      var commodityId = $("#commodityId").val();
      var sourceId = $("#sourceId").val();
      var commodityImgName = $("#commodityImgName").val();
      var includePostage = $("#includePostage").is(":checked");
      var remark = $("#remark").val();
      
      var url = "/taobaoProductSource/search";

      var jsonOutput = {
        commodityName : commodityName,
        commodityId : commodityId,
        sourceId : sourceId,
        commodityImgName : commodityImgName,
        includePostage : includePostage,
        remark : remark,
      };

      $.ajax({  
        type : "POST",  
        async : true,
        url : url, 
        data: JSON.stringify(jsonOutput),
        cache : false,
        contentType: "application/json",
        <%-- dataType: "json", --%>
        timeout:50000,
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success:function(datas){
          $("#productList").html(datas);
        },  
        error: function(datas) {  
          $("#productList").html(datas);
        }  
      });  
    };

    $("#createProduct").click( function() {
      createProduct();
    });

    function createProduct(){
      var commodityName = $("#commodityName").val();
      var commodityId = $("#commodityId").val();
      var sourceId = $("#sourceId").val();
      var commodityImgName = $("#commodityImgName").val();
      var includePostage = $("#includePostage").is(":checked");
      var remark = $("#remark").val();

      
      var url = "/taobaoProductSource/add";

      var jsonOutput = {
        commodityName : commodityName,
        commodityId : commodityId,
        sourceId : sourceId,
        commodityImgName : commodityImgName,
        includePostage : includePostage,
        remark : remark,
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
          $("#msg").html(datas.message);
        },  
        error: function(datas) {  
          $("#msg").html(datas.message);
        }  
      });  
    };

    $("#resetProductCondition").click(function () {
      $("#commodityName").val("");
      $("#commodityId").val("");
      $("#sourceId").val("");
      $("#commodityImgName").val("");
      $("#includePostage").prop("checked", false);
      $("#remark").val("");
    });
  
  });

</script>
</footer>
</html>