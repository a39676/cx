<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<head>
<%@ include file="../baseElementJSP/normalHeader.jsp"%>
</head>
<sec:csrfMetaTags />

<body>

  <div class="row">
    <div class="col-md-12">
      <button id="allShortingSymbolData" symbols='${allShortingSymbols}'>AllSymbol</button>
      <c:forEach items="${shortingSymbolData}" var="subData" varStatus="loop">
        <button class="shortingSymbolData" name="${subData.key}" symbols='${subData.value}'>
          ${subData.key}
        </button>
      </c:forEach>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      <input type="text" name="" id="shortingSymbol" placeholder="shortingSymbol">
      <button id="addShortingSymbol">addShortingSymbol</button>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      <input type="text" name="" id="delShortingSymbolByKey" placeholder="delShortingSymbolByKey">
      <button id="delShortingSymbol">delShortingSymbol</button>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      <textarea type="text" name="" id="showShortingSymbolData" disabled></textarea>
    </div>
  </div>


</body>



<footer>
  
</footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp"%>
<script src="https://challenges.cloudflare.com/turnstile/v0/api.js?" async defer></script>
<script type="text/javascript">
  $(document).ready(function() {
    $("#addShortingSymbol").click(function() {
      addShortingSymbol();
    });

    function addShortingSymbol(){
      var url = "/cryptoTrading/addShortingSymbolData";

      var shortingSymbol = $("#shortingSymbol").val();

      var jsonOutput = {
        str:shortingSymbol,
      };
      $("#msg").text("sending shorting symbol(s)");
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
          if(datas.code != 0){
            $("#msg").text("Done: " + datas.message);
          } else {
            $("#msg").text(datas.message);
          }
        },
        error: function(datas) {
          $("#msg").text(datas.message);
        }
      });
    }

    $("#delShortingSymbol").click(function() {
      delShortingSymbol();
    });

    function delShortingSymbol(){
      var url = "/cryptoTrading/delShortingSymbolData";

      var delShortingSymbolByKey = $("#delShortingSymbolByKey").val();

      var jsonOutput = {
        str:delShortingSymbolByKey,
      };
      $("#msg").text("sending delete shorting symbol by key" + delShortingSymbolByKey);
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
          if(datas.code != 0){
            $("#msg").text("Done: " + datas.message);
          } else {
            $("#msg").text(datas.message);
          }
        },
        error: function(datas) {
          $("#msg").text(datas.message);
        }
      });
    }


    $(".shortingSymbolData").click(function() {
      clickShortingSymbolDataButton($(this).attr("name"));
    });
    function clickShortingSymbolDataButton(buttonName) {
      var targetButton = $(".shortingSymbolData[name='"+buttonName+"']");
      var symbolsStr = targetButton.attr("symbols").replaceAll("\"", "").replaceAll("[","").replaceAll("]","");
      $("#showShortingSymbolData").val(symbolsStr);
    }

    $("#allShortingSymbolData").click(function() {
      var symbolsStr = $(this).attr("symbols").replaceAll("\"", "").replaceAll("[","").replaceAll("]","");
      $("#showShortingSymbolData").val(symbolsStr);
    });
  
  });
</script>



