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
        <a href="/articleAdmin/loadPublicChannels" target="_blank">reload article public channel</a>
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
      <li>
        <a href="/task/fixRuningTaskStatus" target="_blank">fixRuningTaskStatus</a>
      </li>
      <li>
        <a href="/task/getRuningTaskName" target="_blank">getRuningTaskName</a>
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
      <li>
        <a href="/cryptoCoinSharingCalculate/home" target="_blank">Sharing manager</a>
      </li>
      <li>
        <a href="/cryptoCoinSharingCalculate/sharingCalculateDetailListSearchView" target="_blank">Sharing detail search</a>
      </li>
    </ul> <%-- cryptoCoin --%>

    <ul class="main-menu visible-on-click" id="Tools">
      <li style="background-color:yellow;">Tools</li>
      <li>
        <a href="/tool/canlendarNotice/manager" target="_blank">canlendarNoticeManager</a>
      </li>
      <li>
        <a href="/ocr/uploadImg" target="_blank">ocr</a>
      </li>
      <li>
        <a href="/bookmark/manager" target="_blank">bookmark</a>
      </li>
    </ul><!-- Tools -->

    <ul class="main-menu visible-on-click" id="notes">
      <li style="background-color:yellow;">notes</li>
      <li>
        <a href="/pNote/edit" target="_blank">note edit</a>
      </li>
      <li>
        <a href="/pMemo/get?key=key" target="_blank">pmemo</a>
      </li>
      <li>
        <a href="/urgeNoticeManager/setUpdateMsgWebhook" target="_blank">
          update urge notice message web hook
        </a>
      </li>
    </ul> <%-- notes --%>

    <ul class="main-menu visible-on-click" id="finance">
      <li style="background-color:yellow;">finance</li>
      <li>
        <a href="/financeclear" target="_blank">financeclear</a>
      </li>
      <li>
        <a href="/currencyExchangeRateNotice/insertNotice" target="_blank">
          Manager currency exchange rate notice
        </a>
      </li>
    </ul><!-- finance -->

    <ul class="main-menu visible-on-click" id="educate">
      <li style="background-color:yellow;">educate</li>
      <li>
        <a href="/educate/" target="_blank">educate homepage</a>
      </li>
      <li>
        <a href="/login/loginForStudent" target="_blank">Login for student</a>
      </li>
      <li>
        <a href="/user/studentRegist" target="_blank">Student regist</a>
      </li>
      <li>
        <a href="/educate/forParent" target="_blank">For parent</a>
      </li>
    </ul><!-- educate -->

    <ul class="main-menu visible-on-click" id="joy">
      <li style="background-color:yellow;">joy</li>
      <li>
        <a href="/joy/garden/index" target="_blank">garden index</a>
      </li>
      <li>
        <a href="/joyManager/garden/plantCatalogManager" target="_blank">joy garden plant catalog manager</a>
      </li>
      <li>
        <a href="/joyManager/garden/plantCatalogCreator" target="_blank">joy garden plant catalog creator</a>
      </li>
      <li>
        <a href="/joyManager/image/cleanIdPathMap" target="_blank">cleanJoyImageRedis</a>
      </li>
      <li>
        <a href="/joyManager/garden/shop/index/" target="_blank">Garden Shop manager</a>
      </li>
      <li>
        <a href="/joy/garden/shop/index/" target="_blank">Garden Shop</a>
      </li>
    </ul><!-- joy -->


  </div>
</header>
