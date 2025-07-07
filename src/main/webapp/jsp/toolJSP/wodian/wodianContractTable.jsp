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
  <table class="table table-striped table-bordered table-hover">
    <tr>
      <td>
        <label>客户</label>
      </td>
      <td>
        <label>业务</label>
      </td>
      <td>
        <label>商家</label>
      </td>
      <td>
        <label>日期</label>
      </td>
      <td>
        <label>金额</label>
      </td>
      <td>
        <label>金币(客)</label>
      </td>
      <td>
        <label>金币(商家)</label>
      </td>
      <td>
        <label>积分(客)</label>
      </td>
      <td>
        <label>积分(商家)</label>
      </td>
      <td>
        <label>份数</label>
      </td>
      <td>
        <label>备注</label>
      </td>
    </tr>
    <c:forEach items="${contractVoList}" var="contractVO" varStatus="loop">
      <tr>
        <td>
          ${contractVO.clientName}
          <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
            <a href="/wodian/client?id=${contractVO.clientId}" target="_blank">
              ${contractVO.clientName}
            </a>
          </sec:authorize>
        </td>
        <td>
          ${contractVO.salesmanName}
        </td>
        <td>
          ${contractVO.merchantsName}
        </td>
        <td>
          ${contractVO.contractCreateTimeStr}
        </td>
        <td>
          ${contractVO.contractAmount}
        </td>
        <td>
          ${contractVO.goldCoinForClient}
        </td>
        <td>
          ${contractVO.goleCoinForMerchants}
        </td>
        <td>
          ${contractVO.integralForClient}
        </td>
        <td>
          ${contractVO.integralForMerchants}
        </td>
        <td>
          ${contractVO.partCounts}
        </td>
        <td>
          ${contractVO.remark}
        </td>
      </tr>
    </c:forEach>
  </table>

  <table class="table table-striped table-bordered table-hover">
    <tr>
      <td>
        <label>业务</label>
      </td>
      <td>
        <label>金额</label>
      </td>
    </tr>
    <c:forEach items="${summaryList}" var="summaryVO" varStatus="loop">
      <tr>
        <td>
          ${summaryVO.salesmanName}
        </td>
        <td>
          ${summaryVO.summaryAmount}
        </td>
      </tr>
    </c:forEach>
  </table>
</body>
<footer>
<%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
<script type="text/javascript">

  $(document).ready(function() {

    
  
  });

</script>
</footer>
</html>