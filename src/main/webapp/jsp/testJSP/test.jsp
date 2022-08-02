<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>
<sec:csrfMetaTags />
<title>${ title }</title>

<button id="t2">t2</button>

<footer>
</footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp" %>

<script type="text/javascript">

  $(document).ready(function() {

    $("#t2").click(function () {
      var url = "/test2/t2";

      var jsonOutput = {
        
      };

      console.log(jsonOutput);

      $.ajax({
        type : "POST",
        url : url,
        data: JSON.stringify(jsonOutput),
        dataType: 'json',
        contentType: "application/json",
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        timeout: 15000,
        success:function(data){
          console.log(data);
          // $("#searchResult").html(data);
        },
        error:function(e){
          console.log(data);
          // $("#result").text(e);
        }
      });
    });

    $("#uploadImageInput").change(function () {
      console.log("some");
      const file = this.files[0];
      if (file){
        let reader = new FileReader();
        reader.onload = function(event){
          console.log(event.target.result);
          $("#uploadImgPreview").attr("src", event.target.result);
          $("#uploadImgPreview").attr("style", "width:80px; hight:80px;")
        }
        reader.readAsDataURL(file);
      }
    });
  });

</script>
