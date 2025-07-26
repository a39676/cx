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
        <button id="resetProductCondition">reset</button>
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
            <td>货名_productID_modelID</td>
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
              <input type="text" name="" id="productNameInModel" placeholder="货名" disabled><br>
              <input type="text" name="" id="productID" placeholder="货ID" disabled><br>
              <input type="text" name="" id="modelID" placeholder="modelID" disabled>
            </td>
            <td>
              <input type="number" name="" id="declearedPrice" placeholder="申报价"><br>
            </td>
            <td>
              <input type="number" name="" id="unitCounting" placeholder="数量"><br>
              <select id="unitTypeCode">
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
        <button id="resetProductModelCondition">reset</button><br>
        <input type="number" id="stockingUpdateCounting" name=""  placeholder="stockingUpdateCounting">
        <input type="number" id="sellingPrice" name=""  placeholder="sellingPrice">
        <button id="AddStocking">AddStocking</button>
        <button id="AddInternationalStocking">AddInternationalStocking</button>
        <button id="AddSelled">AddSelled</button>
        <button id="AddRepackage">AddRepackage</button>
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

    $("#resetProductCondition").click(function () {
      $("#releaseDateStr").val("");
      $("#productName").val("");
      $("#unitPrice").val("");
    });

    $("#resetProductModelCondition").click(function () {
      $("#releaseDateStrInModel").val("")
      $("#productNameInModel").val("")
      $("#productID").val("")
      $("#modelID").val("")
      $("#declearedPrice").val("")
      $("#unitCounting").val("")
      $("#packingFee").val("")
      $("#spu").val("")
      $("#sku").val("")
      $("#skc").val("")
      $("#unitTypeCode").val("").change();
    });

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

    $("#createProductModel").click(function () {
      $("#modelID").val("");
      var url = "/temuAgent/createProductModel";
      createOrUpdateProductModel(url);
    });

    $("#updateProductModel").click(function () {
      var url = "/temuAgent/updateProductModel";
      createOrUpdateProductModel(url);
    });

    function createOrUpdateProductModel(url) {
      $("#msg").text("");
      var productID = $("#productID").val();
      var modelID = $("#modelID").val();
      var declearedPrice = $("#declearedPrice").val();
      var unitCounting = $("#unitCounting").val();
      var packingFee = $("#packingFee").val();
      var spu = $("#spu").val();
      var sku = $("#sku").val();
      var skc = $("#skc").val();
      var unitTypeCode = $('#unitTypeCode').find(":selected").val();

      <%-- url --%>

      var jsonOutput = {
        productId:productID,
        productModelId:modelID,
        declearedPrice:declearedPrice,
        unitCounting:unitCounting,
        packingFee:packingFee,
        spu:spu,
        sku:sku,
        skc:skc,
        unitTypeCode:unitTypeCode,
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
          $("#msg").text(datas.message);
        },  
        error: function(datas) {  
          $("#msg").text(datas.message);
        }  
      });  
    }

    $("#AddStocking").click(function () {
      $("#sellingPrice").val("");
      productModelAddFlow("/temuAgent/productModelAddStocking", 1);
    });

    $("#AddInternationalStocking").click(function () {
      $("#sellingPrice").val("");
      productModelAddFlow("/temuAgent/productModelAddInternationalStocking", 2);
    });

    $("#AddSelled").click(function () {
      productModelAddFlow("/temuAgent/productModelAddSelled", 3);
    });

    $("#AddRepackage").click(function () {
      $("#sellingPrice").val("");
      productModelAddFlow("/temuAgent/productModelAddRepackage", 4);
    });


    function productModelAddFlow(url, flowTypeCode) {
      $("#msg").text("");
      var counting = $("#stockingUpdateCounting").val();
      var price = $("#sellingPrice").val();
      var modelId = $("#modelID").val();
      
      <%-- url --%>

      var jsonOutput = {
        flowTypeCode:flowTypeCode,
        modelId:modelId,
        counting:counting,
        price:price,
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
          $("#msg").text(datas.message);
        },  
        error: function(datas) {  
          $("#msg").text(datas.message);
        }  
      });  
    }
  
  });

</script>
</footer>
</html>