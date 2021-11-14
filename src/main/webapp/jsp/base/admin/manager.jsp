<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> --%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%> --%>
<%-- <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> --%>
<%-- <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> --%>


<header>
  <div class="container-fluid position-relative no-side-padding">
    <div class="menu-nav-icon" data-nav-menu="#main-menu"><i class="ion-navicon"></i></div>
    <ul class="main-menu visible-on-click" id="main-menu">
        <li>
          <a href="/tool/systemOption" target="_blank">systemOption</a>
        </li>
        <li>
          <a href="/fakeFTP/getFilePathDetail" target="_blank">fakeFTP</a>
        </li>
        <li>
          <a href="/articleAdmin/articleChannelManager" target="_blank">article channel manager</a>
        </li>
        <li>
          <a href="/org/orgManager" target="_blank">orgManager</a>
        </li>
    </ul><!-- main-menu -->
    <ul class="main-menu visible-on-click" id="telegramTool">
      <li>
        <a href="/tool/telegram/testing" target="_blank">telegram api testing</a>
      </li>
    </ul> <%-- telegramTool --%>
    <ul class="main-menu visible-on-click" id="cryptoCoinTool">
      <li>
        <a href="/cryptoCoin/insertCryptoCoinNoticeSetting" target="_blank">insertCryptoCoinNoticeSetting</a>
      </li>
      <li>
        <a href="/cryptoCoinManager/checkDataAPI" target="_blank">checkDataAPI</a>
      </li>
      <li>
        <a href="/cryptoCoin/dataCompare/CryptoCoinDailyDataComparetor" target="_blank">CryptoCoinDailyDataComparetor</a>
      </li>
      <li>
        <a href="/cryptoCoinManager/cryptoCoinWebSocketManager" target="_blank">cryptoCoinWebSocketManager</a>
      </li>
      <li>
        <a href="/cryptoCoinManager/getDailyDataWaitingQuerySet" target="_blank">getDailyDataWaitingQuerySet</a>
      </li>
      <li>
        <a href="/cryptoCoinManager/resetDailyDataWaitingQuerySet" target="_blank">resetDailyDataWaitingQuerySet</a>
      </li>
      <li>
        <a href="/cryptoCoinManager/sendAllCryptoCoinDailyDataQueryMsg" target="_blank">sendAllCryptoCoinDailyDataQueryMsg</a>
      </li>
    </ul> <%-- cryptoCoin --%>
    <ul class="main-menu visible-on-click" id="canlendarNotice">
      <li>
        <a href="/tool/canlendarNotice/manager" target="_blank">canlendarNoticeManager</a>
      </li>
    </ul>
  </div><!-- canlendarNotice -->
</header>
