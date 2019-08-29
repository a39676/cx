<%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="container-fluid">

  <div class="row">
  <div class="col-md-12">

    <input type="text" name="tradingRecordTxtPath" placeholder="d:/auxiliary/tmp/ssm/">
    <label name="importTradingRecordFromFile">import</label>

  </div>
  </div>

  <hr>

  <div class="row">
  <div class="col-md-12">
    
    <pre>
      <label name="message">${message}</label>
    </pre>

  </div>
  </div>

 
</div>


<footer>


<script type="text/javascript">

  $(document).ready(function() {

  	$(function(){  
      $("label[name='importTradingRecordFromFile']").click(function(){ 
        // $(this).css("background-color","blue");
        var tradingRecordTxtPath = $("input[name='tradingRecordTxtPath']").val();
        if (tradingRecordTxtPath.length < 1) {
        	tradingRecordTxtPath = "d:/auxiliary/tmp/ssm/";
        };

        $.ajax({               
          type: "POST",  
          url: "${pageContext.request.contextPath}/trading/importTradingRecordFromFiles",   
          data: {tradingRecordTxtPath:tradingRecordTxtPath},
          dataType: 'json',
          // contentType: "application/json",
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
         
          timeout: 15000,
          success:function(data){  
        	console.log(data);
            $("label[name='message']").text(data.data);
          }, 

          error:function(e){
            // console.log("error");
            console.log(e);
          }
        });
      });  
    });

  });
</script>
</footer>
</html>