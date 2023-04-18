<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<html>

  <head>
    <%@ include file="../baseElementJSP/normalHeader.jsp" %>
    <%@ include file="../baseElementJSP/normalJSPart.jsp" %>
  </head>

  <div class="container-fluid">
  
    <div class="row">
      <div class="col-lg-12">
        <table class="table">
          <thead>
            <tr>
              <th>
                <label id="result"></label>
              </th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>
                <input type="text" name="" id="colabUrl" placeholder="colabUrl">
                <button id="submitColabUrl">submitColabUrl</button>
                <button id="stopColabUrl">stopColabUrl</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    

  </div>

  <script type="text/javascript">
    $(document).ready(function() {

      $("#submitColabUrl").click(function() {
        setRunningColab();
      })

      function setRunningColab(){
        var url = "/aiArtManager/setRunningColab";

        var colabUrl = $("#colabUrl").val();

        var jsonOutput = {
          str:colabUrl,
        };

        console.log(jsonOutput);
  
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
            console.log(datas);
            if (datas.code == 0) {
              $("#result").text("success");
            } else {
              $("#result").text(datas.message);
            }
          },
          error: function(datas) {
            
          }
        });
      }

      $("#stopColabUrl").click(function() {
        setStopColab();
      })

      function setStopColab(){
        var url = "/aiArtManager/setStopColab";

        $.ajax({
          type : "POST",
          async : true,
          url : url,
          cache : false,
          timeout:50000,
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
          success:function(datas){
            console.log(datas);
            if (datas.code == 0) {
              $("#result").text("success");
            } else {
              $("#result").text(datas.message);
            }
          },
          error: function(datas) {
            
          }
        });
      }
    });
  </script>

</html>