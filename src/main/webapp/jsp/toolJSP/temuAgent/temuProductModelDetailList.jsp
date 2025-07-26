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
        <label id="productModelDetailMsg">${msg}</label>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <table class="table table-striped table-bordered table-hover">
          <tr>
            <td>名_productID_modelID</td>
            <td>数量_计数类型</td>
            <td>拿货价_申报价_打包费</td>
            <td>SPU</td>
            <td>SKU</td>
            <td>SKC</td>
            <td>创建日期</td>
          </tr>
          <c:forEach items="${productModelStatisticsList}" var="productModel" varStatus="loop">
            <tr>
              <td class="productModelInfoLine" 
                productName="${productModel.productName}" 
                productId="${productModel.productId}" 
                productModelId="${productModel.modelId}"
                unitCounting="${productModel.unitCounting}"
                unitTypeCode="${productModel.unitTypeCode}"
                declearedPrice="${productModel.declearedPrice}"
                packingFee="${productModel.packingFee}"
                spu="${productModel.spu}"
                sku="${productModel.sku}"
                skc="${productModel.skc}"
                createTimeStr="${productModel.createTimeStr}"
                >
                ${productModel.productName}<br>
                ${productModel.productId}<br>
                ${productModel.modelId}
              </td>
              <td>
                ${productModel.unitCounting}_${productModel.unitTypeName}
              </td>
              <td>
                ${productModel.purchasePrice}_${productModel.declearedPrice}_${productModel.packingFee}
              </td>
              <td>
                ${productModel.spu}
              </td>
              <td>
                ${productModel.sku}
              </td>
              <td>
                ${productModel.skc}
              </td>
              <td>
                ${productModel.createTimeStr}
              </td>
            </tr>
          </c:forEach>
        </table>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <table class="table table-striped table-bordered table-hover">
          <tr>
            <td>名_productID_modelID</td>
            <td>数量_计数类型</td>
            <td>
              备货_国际仓<br>
              已售_重打包(数量)
            </td>
            <td>投入_回报</td>
            <td>创建日期</td>
          </tr>
          <c:forEach items="${productModelStatisticsList}" var="productModel" varStatus="loop">
            <tr>
              <td class="productModelDetailLine" 
                productName="${productModel.productName}" 
                productId="${productModel.productId}" 
                productModelId="${productModel.modelId}">
                ${productModel.productName}<br>
                ${productModel.productId}<br>
                ${productModel.modelId}
              </td>
              <td>
                ${productModel.unitCounting}_${productModel.unitTypeName}
              </td>
              <td>
                ${productModel.stockingCounting}_${productModel.internationalStockingCounting}<br>
                ${productModel.selledCounting}_${productModel.repackageCounting}
              </td>
              <c:choose>
                <c:when test="${productModel.totalSelledAmount - productModel.totalCost > 0}">
                  <td class="table-success">
                </c:when>
                <c:when test="${productModel.totalSelledAmount - productModel.totalCost < 0}">
                  <td class="table-danger">
                </c:when>
                <c:otherwise>
                  <td>
                </c:otherwise>
              </c:choose>
                ${productModel.totalCost}(${productModel.totalStockingCost}+${productModel.totalPackingFeeCost})<br>
                ${productModel.totalSelledAmount}<br>
                ${productModel.totalSelledAmount - productModel.totalCost }
              </td>
              <td>
                ${productModel.createTimeStr}
              </td>
            </tr>
          </c:forEach>
        </table>
      </div>
    </div>

  </div>
</body>
<footer>
<%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
<script type="text/javascript">

  $(document).ready(function() {
    $(".productModelInfoLine").click(function () {
      var productName = $(this).attr("productName");
      var productId = $(this).attr("productId");
      var productModelId = $(this).attr("productModelId");
      var unitCounting = $(this).attr("unitCounting");
      var unitTypeCode = $(this).attr("unitTypeCode");
      var declearedPrice = $(this).attr("declearedPrice");
      var packingFee = $(this).attr("packingFee");
      var spu = $(this).attr("spu");
      var sku = $(this).attr("sku");
      var skc = $(this).attr("skc");
      var createTimeStr = $(this).attr("createTimeStr");
      $("#productNameInModel").val(productName);
      $("#productID").val(productId);
      $("#modelID").val(productModelId);
      $("#unitCounting").val(unitCounting);
      $("#unitTypeCode").val(unitTypeCode).change();
      $("#declearedPrice").val(declearedPrice);
      $("#packingFee").val(packingFee);
      $("#spu").val(spu);
      $("#sku").val(sku);
      $("#skc").val(skc);
      $("#releaseDateStrInModel").val(createTimeStr);
    });
   
  
  });

</script>
</footer>
</html>