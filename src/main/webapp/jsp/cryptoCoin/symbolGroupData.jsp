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
      <table class="table">
        <tr>
          <td colspan=100%>
            <button id="allShortingSymbolData" symbols='${allShortingSymbols}'>
              AllSymbol
            </button>
            <c:forEach items="${shortingSymbolData}" var="subData" varStatus="loop">
              <button class="shortingSymbolData" 
              name="${subData.key}" symbols='${subData.value}'>
                ${subData.key}
              </button>
            </c:forEach>
          </td>
        </tr>
        <tr>
          <td>
            <input type="text" name="" id="newSymbolGroup" placeholder="newSymbolGroup">
            <input type="text" name="" id="newSymbolGroupName" placeholder="newSymbolGroupName">
            <button id="addSymbolGroup">addSymbolGroup</button>
          </td>
          <td>
            <input type="text" name="" id="delShortingSymbolByKey" placeholder="delShortingSymbolByKey">
            <button id="delSymbolGroup">delSymbolGroup</button>
          </td>
        </tr>
      </table>
      
    </div>
  </div>

</body>



<footer>
  
</footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp"%>
<script src="https://challenges.cloudflare.com/turnstile/v0/api.js?" async defer></script>
<script type="text/javascript">
  $(document).ready(function() {
    $("#addSymbolGroup").click(function() {
      addSymbolGroup();
    });

    function addSymbolGroup(){
      var url = "/cryptoTradingFutureUm/addSymbolGroupData";

      var newSymbolGroup = $("#newSymbolGroup").val();
      var newSymbolGroupName = $("#newSymbolGroupName").val();

      var jsonOutput = {
        symbolGroupStr:newSymbolGroup,
        groupName:newSymbolGroupName,
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

    $("#delSymbolGroup").click(function() {
      delSymbolGroup();
    });

    function delSymbolGroup(){
      var url = "/cryptoTradingFutureUm/delSymbolGroupData";

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
      var symbolsStr = targetButton.attr("symbols");
      $("#orderSymbols").val(symbolsStr);
    }

    $("#allShortingSymbolData").click(function() {
      var symbolsStr = $(this).attr("symbols");
      $("#orderSymbols").val(symbolsStr);
    });

  });
</script>



