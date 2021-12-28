<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div>
  <input type="text" name="bankName" placeholder="请输入银行名称/简称 marrk2">
</div>

<div>
  <div id="bankIdSelector">
    <%@ include file="./bankButtons.jsp" %>
  </div>
</div>



<script type="text/javascript">
//bankIdListDynamic select dynamic
function bankIdListDynamic(){
  var $bankName = $("input[name='bankName']").val();
  var $bankList = $("div[name='bankList']");
  var jsonOutput = {
    bankName : $bankName
  };
  $.ajax({               
    type: "POST",  
    url: "/bankInfo/bankButtonList",   
    data: JSON.stringify(jsonOutput),
    dataType: 'json',
    contentType: "application/json",
    beforeSend: function(xhr) {
      xhr.setRequestHeader(csrfHeader, csrfToken);
    },
    timeout: 15000,
    success:function(data){  
      $bankList.empty();
      var bankList = data.bankList;
      $.each(bankList, function(index, bankInfo) {
        $bankList.append($("<button></button>").attr("bankId", bankInfo.bankId).text(bankInfo.bankChineseName));
        $("button[bankId='"+bankInfo.bankId+"']").addClass("bankButton");
        $("button[bankId='"+bankInfo.bankId+"']").bind("click", bankButtonClick);
      });
    }, 
    error:function(e){
      // console.log("error");
      // console.log(e);
    }
  });             
};

$("input[name='bankName']").keyup(function () { 
  bankIdListDynamic();
});


function bankButtonClick() {
  var $bankId = $(this).attr("bankId");
  $("div[name='hiddenMessage']").attr("bankId", $bankId);
};


</script>