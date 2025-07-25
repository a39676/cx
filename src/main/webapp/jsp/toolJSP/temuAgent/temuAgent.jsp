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
            <td>创建日期</td>
            <td>货名</td>
            <td>单价</td>
          </tr>
          <tr>
            <td>
              <input type="text" name="" id="releaseDateStr" placeholder="创建日期">
            </td>
            <td>
              <input type="text" name="" id="productName" placeholder="货名"><br>
            </td>
            <td>
              <input type="number" name="" id="unitPrice" placeholder="单价"><br>
            </td>
          </tr>
        </table>
        <button id="searchProduct">SearchProduct</button>
        <button id="createProduct">CreateProduct</button>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <div id="productList">
          
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <table class="table table-striped table-bordered table-hover">
          <tr>
            <td>创建日期</td>
            <td>货名_ID</td>
            <td>申报价</td>
            <td>数量_计数类型</td>
            <td>打包费</td>
            <td>spu_sku_skc</td>
          </tr>
          <tr>
            <td>
              <input type="text" name="" id="releaseDateStrInModel" placeholder="创建日期">
            </td>
            <td>
              <input type="text" name="" id="productNameInModel" placeholder="货名"><br>
              <input type="text" name="" id="productID" placeholder="货ID" disabled>
            </td>
            <td>
              <input type="number" name="" id="declearedPrice" placeholder="申报价"><br>
            </td>
            <td>
              <input type="number" name="" id="unitCounting" placeholder="数量"><br>
              <select id="merchantsSelectorForNewContract">
                <option value="">计数类型</option>
                <c:forEach items="${productModelUnitTypeList}"
                  var="productModelUnitType" varStatus="loop">
                  <option value="${productModelUnitType.code}">${productModelUnitType.name}</option>
                </c:forEach>
              </select>
            </td>
            <td>
              <input type="number" name="" id="packingFee" placeholder="打包费" value="0.5">
            </td>
            <td>
              <input type="text" name="" id="spu" placeholder="spu"><br>
              <input type="text" name="" id="sku" placeholder="sku"><br>
              <input type="text" name="" id="skc" placeholder="skc">
            </td>
          </tr>
        </table>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <button id="searchProductModel">SearchProductModel</button>
        <button id="createProductModel">CreateProductModel</button>
        <button id="updateProductModel">UpdateProductModel</button>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <div id="productModelDetailList">
          
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
      var productName = $("#productName").val();
      
      var url = "/temuAgent/searchProduct";

      var jsonOutput = {
        productName : productName,
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
      var releaseDateStr = $("#releaseDateStr").val();
      var productName = $("#productName").val();
      var unitPrice = $("#unitPrice").val();
      
      var url = "/temuAgent/createProduct";

      var jsonOutput = {
        releaseDateStr:releaseDateStr,
        productName:productName,
        unitPrice:unitPrice,
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

    $("#searchProductModel").click( function() {
      searchProductModel();
    });

    function searchProductModel(){ 
      var productId = $("#productID").val();
      
      var url = "/temuAgent/searchProductModel";

      var jsonOutput = {
        productId : productId,
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
          $("#productModelDetailList").html(datas);
        },  
        error: function(datas) {  
          $("#productModelDetailList").html(datas);
        }  
      });  
    };
  
  });

</script>
</footer>
</html>