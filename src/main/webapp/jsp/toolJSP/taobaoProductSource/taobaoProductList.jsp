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
            <td>
              <label>ID,挂链ID,来源ID,货名</label>
            </td>
            <td>首图</td>
            <td>Links</td>
            <td>供应商</td>
            <td>是否包邮</td>
            <td>是否上架</td>
            <td>备注</td>
          </tr>
          <c:forEach items="${productList}" var="product" varStatus="loop">
            <tr class="productLine" 
                productName="${product.commodityName}" 
                productId="${product.id}">
              <td>
                <label class="label label-default" onclick="copyText(this)">${product.id}</label><br>
                <label class="label label-default" onclick="copyText(this)">${product.commodityId}</label><br>
                <label class="label label-default" onclick="copyText(this)">${product.sourceId}</label><br>
                <label class="label label-default" onclick="copyText(this)">${product.commodityName}</label><br>
                <label class="label label-default" onclick="copyText(this)">${product.commodityNameZhTw}</label><br>
                <label class="label label-default" onclick="copyText(this)">${product.commodityNameEn}</label><br>
              </td>
              <td>
                <img src='https://gw.alicdn.com/imgextra/${product.commodityImgName}_160x160xz_.webp' style='width: 80px;'>
              </td>
              <td>
                <a href="https://item.taobao.com/item.html?id=${product.commodityId}" target="_blank">
                  淘宝
                </a><br>
                <a href="https://upload.taobao.com/auction/publish/edit.htm?item_num_id=${product.commodityId}&auto=false&itemId=${product.commodityId}" target="_blank">
                  千牛编辑
                </a><br>
                <a href="https://detail.1688.com/offer/${product.sourceId}.html" target="_blank">
                  1688
                </a>
              </td>
              <td>
                <label>${product.supplierName}</label><br>
              </td>
              <c:choose>
                <c:when test="${product.includePostage == false}">
                  <td class="table-success">
                    ${product.includePostage}<br>
                    <button class="setIncludePostage" 
                      includePostage="1" 
                      productId="${product.id}">
                      setIncludePostage
                    </button>
                  </td>
                </c:when>
                <c:when test="${product.includePostage == true}">
                  <td class="table-danger">
                    ${product.includePostage}<br>
                    <button class="setIncludePostage" 
                      includePostage="0" 
                      productId="${product.id}">
                      setNotIncludePostage
                    </button>
                  </td>
                </c:when>
                <c:otherwise>
                  <td>
                    ${product.includePostage}<br>
                    <button class="setIncludePostage" 
                      includePostage="0" 
                      productId="${product.id}">
                      setNotIncludePostage
                    </button>
                    <button class="setIncludePostage" 
                      includePostage="1" 
                      productId="${product.id}">
                      setIncludePostage
                    </button>
                  </td>
                </c:otherwise>
              </c:choose>
              <c:choose>
                <c:when test="${product.isAvailable == true}">
                  <td class="table-success">
                    ${product.isAvailable}<br>
                    <button class="setAvailable" 
                      available="0" 
                      productId="${product.id}">
                      setUnavailable
                    </button>
                  </td>
                </c:when>
                <c:when test="${product.isAvailable == false}">
                  <td class="table-danger">
                    ${product.isAvailable}<br>
                    <button class="setAvailable" 
                      available="1" 
                      productId="${product.id}">
                      setAvailable
                    </button>
                  </td>
                </c:when>
                <c:otherwise>
                  <td>
                    ${product.isAvailable}<br>
                    <button class="setAvailable" 
                      available="0" 
                      productId="${product.id}">
                      setUnavailable
                    </button>
                    <button class="setAvailable" 
                      available="1" 
                      productId="${product.id}">
                      setAvailable
                    </button>
                  </td>
                </c:otherwise>
              </c:choose>
              <td>
                ${product.remark}
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

    $(".setAvailable").click(function () {
      var productId = $(this).attr("productId");
      var available = true;
      if($(this).attr("available") == "0"){
        available = false;
      }

      var url = "/taobaoProductSource/update";

      var jsonOutput = {
        idStr : productId,
        isAvailable : available,
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
    });

    $(".setIncludePostage").click(function () {
      var productId = $(this).attr("productId");
      var includePostage = true;
      if($(this).attr("includePostage") == "0"){
        includePostage = false;
      }

      var url = "/taobaoProductSource/update";

      var jsonOutput = {
        idStr : productId,
        includePostage : includePostage,
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
    });
    
  });

</script>
<script>
  function copyText(element) {
    // element 就是当前被点击的那个按钮对象
    const text = element.innerText; 
    
    navigator.clipboard.writeText(text).then(() => {});
  }
</script>
</footer>
</html>