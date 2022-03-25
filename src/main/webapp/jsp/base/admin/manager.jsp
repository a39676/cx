<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> --%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%> --%>
<%-- <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> --%>
<%-- <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> --%>


<header>
  <div class="container-fluid position-relative no-side-padding">
    <div class="menu-nav-icon" data-nav-menu="#main-menu"><i class="ion-navicon"></i></div>
    <ul class="main-menu visible-on-click" id="main-menu">
      <li style="background-color:yellow;">System</li>
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
      <li>
        <a href="/admin/userManager" target="_blank">userManager</a>
      </li>
      <li>
        <a href="/admin/updateAccountMarker" target="_blank">updateAccountMarker</a>
      </li>
    </ul><!-- system -->

    <ul class="main-menu visible-on-click" id="telegramTool">
      <li style="background-color:yellow;">TelegramTool</li>
      <li>
        <a href="/tool/telegram/testing" target="_blank">telegram api testing</a>
      </li>
    </ul> <%-- telegramTool --%>

    <ul class="main-menu visible-on-click" id="cryptoCoinTool">
      <li style="background-color:yellow;">CryptoCoinTool</li>
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

    <ul class="main-menu visible-on-click" id="qrcode">
      <li style="background-color:yellow;">qrcode</li>
      <li>
        <a href="/tool/qrcode/" target="_blank">qrcode</a>
      </li>
    </ul><!-- canlendarNotice -->

    <ul class="main-menu visible-on-click" id="canlendarNotice">
      <li style="background-color:yellow;">canlendarNotice</li>
      <li>
        <a href="/tool/canlendarNotice/manager" target="_blank">canlendarNoticeManager</a>
      </li>
    </ul><!-- canlendarNotice -->

    <ul class="main-menu visible-on-click" id="notes">
      <li style="background-color:yellow;">notes</li>
      <li>
        <a href="/pNote/edit" target="_blank">note edit</a>
      </li>
      <li>
        <a href="/pMemo/get?key=key" target="_blank">pmemo</a>
      </li>
    </ul> <%-- notes --%>

    <ul class="main-menu visible-on-click" id="finance">
      <li style="background-color:yellow;">finance</li>
      <li>
        <a href="/financeclear" target="_blank">financeclear</a>
      </li>
    </ul><!-- finance -->

    <ul class="main-menu visible-on-click" id="educate">
      <li style="background-color:yellow;">educate</li>
      <li>
        <a href="/educate/" target="_blank">educate homepage</a>
      </li>
      <li>
        <a href="/login/loginForStudent" target="_blank">loginForStudent</a>
      </li>
      <li>
        <a href="/user/studentRegist" target="_blank">studentRegist</a>
      </li>
    </ul><!-- educate -->
  </div>
</header>
