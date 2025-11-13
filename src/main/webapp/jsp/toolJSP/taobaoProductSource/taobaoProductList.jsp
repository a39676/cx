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
        <label id="productMsg">${msg}</label>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <table class="table table-striped table-bordered table-hover">
          <tr>
            <td>ID 货名</td>
            <td>首图</td>
            <td>挂链ID</td>
            <td>来源ID</td>
            <td>是否包邮</td>
            <td>备注</td>
            <td>是否上架</td>
          </tr>
          <c:forEach items="${productList}" var="product" varStatus="loop">
            <tr class="productLine" 
                productName="${product.commodityName}" 
                productId="${product.id}">
              <td>
                <label class="label label-default">${product.id}</label><br>
                <label class="label label-default">${product.commodityName}</label><br>
                <label class="label label-default">${product.commodityNameZhTw}</label><br>
                <label class="label label-default">${product.commodityNameEn}</label><br>
              </td>
              <td>
                <img src='https://gw.alicdn.com/imgextra/${product.commodityImgName}_160x160xz_.webp' style='width: 80px;'>
              </td>
              <td>
                <label>${product.commodityId}</label><br>
                <a href="https://item.taobao.com/item.html?id=${product.commodityId}" target="_blank">
                  https://item.taobao.com/item.htm?id=${product.commodityId}
                </a>
              </td>
              <td>
                <label>${product.sourceId}</label><br>
                <a href="https://detail.1688.com/offer/${product.sourceId}.html" target="_blank">
                  https://detail.1688.com/offer/${product.sourceId}.html
                </a>
              </td>
              <c:choose>
                <c:when test="${product.includePostage == false}">
                  <td class="table-success">
                    ${product.includePostage}
                  </td>
                </c:when>
                <c:when test="${product.includePostage == true}">
                  <td class="table-danger">
                    ${product.includePostage}
                  </td>
                </c:when>
                <c:otherwise>
                  <td>${product.includePostage}</td>
                </c:otherwise>
              </c:choose>
              <td>
                ${product.remark}
              </td>
              <c:choose>
                <c:when test="${product.isAvailable == true}">
                  <td class="table-success">
                    ${product.isAvailable}
                  </td>
                </c:when>
                <c:when test="${product.isAvailable == false}">
                  <td class="table-danger">
                    ${product.isAvailable}
                  </td>
                </c:when>
                <c:otherwise>
                  <td>${product.isAvailable}</td>
                </c:otherwise>
              </c:choose>
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
    
  });

</script>
</footer>
</html>