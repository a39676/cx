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
            <td>名_productID_modelID</td>
            <td>数量_计数类型</td>
            <td>拿货价_申报价_打包费</td>
            <td>SPU_SKU_SKC</td>
            <td>创建日期</td>
          </tr>
          <c:forEach items="${productModelStatisticsList}" var="productModel" varStatus="loop">
            <tr>
              <td class="productModelLine" 
                productName="${productModel.productName}" 
                productId="${productModel.productId}" 
                productModelId="${productModel.modelId}">
                ${productModel.productName}_${productModel.productId}_${productModel.modelId}
              </td>
              <td>
                ${productModel.unitCounting}_${productModel.unitTypeName}
              </td>
              <td>
                ${productModel.purchasePrice}_${productModel.declearedPrice}_${productModel.packingFee}
              </td>
              <td>
                ${productModel.spu}_${productModel.spu}_${productModel.skc}
              </td>
              <td>
                ${productModel.spu}_${productModel.spu}_${productModel.skc}
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
    $(".productLine").click(function () {
      var productName = $(this).attr("productName");
      var productId = $(this).attr("productId");
      $("#productName").val(productName);
      $("#productID").val(productId);
    });
   
  
  });

</script>
</footer>
</html>