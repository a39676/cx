<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>

<div class="container-fluid">
  <div class="row">
    <div class="col-md-12">
      <input type="" name="" value="${greenProbability}" id="greenProbability">
      <input type="" name="" value="${blueProbability}" id="blueProbability">
      <input type="" name="" value="${purpleProbability}" id="purpleProbability">
      <input type="" name="" value="${purpleToBlueProbability}" id="purpleToBlueProbability">
      <br>
      <span>紫卡 = 蓝卡 * 9</span>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      <span>理论上 27000z ---> 85.19 蓝卡</span><br>
      <span>蓝卡 约等于 ${blueCostDiamond}z</span><br>
      <span>紫卡 约等于 ${purpleCostDiamond}z</span><br>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      <span>蓝石 约等于 ${blueStoneCostDiamond}z</span><br>
      <span>紫石 约等于 ${purpleStoneCostDiamond}z</span><br>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      <span>大月卡 ${bigMonCardCostUSD}d ---> ${bigMonCardTotalDiamond}z </span><br>
      <span>小月卡 ${smallMonCardCostUSD}d ---> ${smallMonCardTotalDiamond}z </span><br>
      <span>双月卡 ${doubleMonCardCostUSD}d ---> ${doubleMonCardTotalDiamond}z </span><br>
      <span>大月卡 1d ---> ${bigMonCardUSDToDiamond}z</span><br>
      <span>小月卡 1d ---> ${smallMonCardUSDToDiamond}z</span><br>
      <span>双月卡 1d ---> ${doubleMonCardUSDToDiamond}z</span><br>
    </div>
  </div>

</div>

<div class="container-fluid">
  <div class="row">
    <div class="col-md-12">
      <span>皇家犒赏令 价格: ${rewardCardCostUSD}</span><br>
      <span>皇家犒赏令 获得钻石: ${rewardCardDiamond}</span><br>
      <span>皇家犒赏令 获得紫石: ${rewardCardPurpleStone}</span><br>
      <span>皇家犒赏令 约合钻石: ${rewardTotalDimaond}</span><br>
      <span>皇家犒赏令 约合: 1d ---> ${rewardTotalDimaond / rewardCardCostUSD}</span><br>
    </div>
  </div>
</div>


<footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp" %>
<script type="text/javascript">
  $(document).ready(function() {

   

    
  });
</script>
</footer>