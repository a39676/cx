<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- 
<div>
  <input type="text" name="bankUnionName" placeholder="请输入银行组织名称/简称">
</div>
 -->

<div>
  <div class="btn-group" name="bankUnionList">
    <button type="button" name="bankUnionButton" class="btn btn-sm btn-light" bankUnionId="8601">银联</button>
    <button type="button" name="bankUnionButton" class="btn btn-sm btn-light" bankUnionId="8602">移动支付</button>
  </div>
</div>

<div>
  <input type="text" name="fuzzyBankName" placeholder="请输入银行名称/简称">
</div>

<div name="bankButtonList"></div>

<div name="bankHiddenMessage" bankUnionId="" bankId=""></div>

<script type="text/javascript">

$("button[name='bankUnionButton']").click(function () {
  var $bankUnionId = $(this).attr("bankUnionId");
  $("button[name='bankUnionButton']").removeClass("btn-primary");
  $("button[name='bankUnionButton']").addClass("btn-light");
  $(this).removeClass("btn-light");
  $(this).addClass("btn-primary");
  $("div[name='bankHiddenMessage']").attr("bankUnionId", $bankUnionId);
  bankIdListDynamic();
});

//bankIdListDynamic select dynamic
function bankIdListDynamic(){
  var $fuzzyBankName = $("input[name='fuzzyBankName']").val();
  var $bankUnionId = $("div[name='bankHiddenMessage']").attr("bankUnionId");
  var $bankButtonList = $("div[name='bankButtonList']");
  var jsonOutput = {
    fuzzyBankName : $fuzzyBankName,
    bankUnionId: $bankUnionId
  };
  $.ajax({               
    type: "POST",  
    url: "/bankInfo/bankSelectorV4",   
    data: JSON.stringify(jsonOutput),
    dataType: 'json',
    contentType: "application/json",
    beforeSend: function(xhr) {
      xhr.setRequestHeader(csrfHeader, csrfToken);
    },
    timeout: 15000,
    success:function(data){
      $bankButtonList.empty();
      var bankButtonList = data.bankList;
      $.each(bankButtonList, function(index, bankInfo) {
        $bankButtonList.append($("<button class='btn btn-sm btn-light'></button>").attr("bankId", bankInfo.bankId).text(bankInfo.bankChineseName));
        $bankButtonList.append($("<label>&nbsp;&nbsp;|&nbsp;&nbsp;</label>"));
        $("button[bankId='"+bankInfo.bankId+"']").addClass("bankButton");
        $("button[bankId='"+bankInfo.bankId+"']").bind("click", bankButtonClick);
      });
    }, 
    error:function(e){
    }
  });             
};

$("input[name='fuzzyBankName']").keyup(function () {
  bankIdListDynamic();
});


function bankButtonClick() {
  var $bankId = $(this).attr("bankId");
  $(".bankButton").addClass("btn-light")
  $(".bankButton").removeClass("btn-primary")
  $(this).removeClass("btn-light")
  $(this).addClass("btn-primary")
  $("div[name='bankHiddenMessage']").attr("bankId", $bankId);
};




</script>