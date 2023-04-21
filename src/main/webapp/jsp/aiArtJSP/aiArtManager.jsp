<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
                <label>is running: ${isRunning}</label><br>
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
    
    <div class="row">
      <div class="col-lg-12">
        <table class="table">
          <thead>
            <tr>
              <td>.</td>
              <td>.</td>
              <td>.</td>
              <td>.</td>
              <td>.</td>
              <td>.</td>
              <td>.</td>
              <td>.</td>
              <td>.</td>
            </tr>
          </thead>
          <tbody id="jobResult" lastJobPk="">
            
          </tbody>
        </table>
        <button id="loadMoreJobResult">load more</button>
      </div>
    </div>
  </div>

  <script type="text/javascript">
    function imgFlod(ele) {
      var src = ele.getAttribute("src");
      if(src.includes("/getImage")){
        ele.setAttribute("src", src.replace("/getImage", "/getThumbnail"));
      } else {
        ele.setAttribute("src", src.replace("/getThumbnail", "/getImage"));
      }
    }

    function setInvalidImg(ele){
      var url = "/aiArtManager/setInvalidImageAndRetunTokens";

      var imgPk = ele.getAttribute("imgPk");
      var jobPk = ele.getAttribute("jobPk");

      var jsonOutput = {
        imgPk:imgPk,
        jobPk:jobPk,
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
            $("#result").text("Img invalid success, imgPk: " + imgPk);
            var img = document.querySelector('img[imgPk="'+imgPk+'"]');
            img.setAttribute("src", "");
          } else {
            $("#result").text(datas.message);
          }
        },
        error: function(datas) {            
        }
      });
    }

    function addToImageWall(ele){
      var url = "/aiArtManager/addToImageWall";

      var imgPk = ele.getAttribute("imgPk");
      var jobPk = ele.getAttribute("jobPk");

      var jsonOutput = {
        imgPk:imgPk,
        jobPk:jobPk,
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
            $("#result").text("Image add to image wall success, imgPk: " + imgPk);
          } else {
            $("#result").text(datas.message);
          }
        },
        error: function(datas) {            
        }
      });
    }


    $(document).ready(function() {

      $("#submitColabUrl").click(function() {
        $("#result").text("");
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
              $("#result").text("Set colab running success");
            } else {
              $("#result").text(datas.message);
            }
          },
          error: function(datas) {
            
          }
        });
      }

      $("#stopColabUrl").click(function() {
        $("#result").text("");
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
              $("#result").text("Set stop colab success");
            } else {
              $("#result").text(datas.message);
            }
          },
          error: function(datas) {
            
          }
        });
      }
    
      $("#loadMoreJobResult").click(function (){
        var lastJobPk = $("#jobResult").attr("lastJobPk");
        getJobResultList(lastJobPk);
      })

      function getJobResultList(lastJobPk){
        var url = "/aiArtManager/getJobResultList";

        var jsonOutput = {
          pk:lastJobPk,
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
              $("#result").text("Load job result success");
              for(i=0;i<datas.jobResultList.length;i++){
                appendJobResult(datas.jobResultList[i]);
              }
            } else {
              $("#result").text(datas.message);
            }
          },
          error: function(datas) {
            
          }
        });
      }

      getJobResultList();

      function appendJobResult(vo) {
        var tr = "";
        var parameter = vo.parameter;
        tr += "<tr>";
        if(vo.imgPkList){
          for(j=0;j<vo.imgPkList.length;j++){
            var imgPk = vo.imgPkList[j];
            tr += "<td>";
            tr += "<img src='/image/getThumbnail?imgPK="+encodeURIComponent(imgPk)+"' imgPk='"+imgPk+"' name='aiArtImg' onclick='imgFlod(this)'> <br>";
            tr += "<label>"+imgPk.substring(0, 10)+"</label> <br>";
            tr += "<button class='btn btn-md btn-danger' name='setInvalidImg' jobPk='"+vo.jobPk+"' imgPK='"+imgPk+"' onclick='setInvalidImg(this)'>setInvalidImg</button><br>"
            tr += "<button class='btn btn-md btn-success' name='addToImageWall' jobPk='"+vo.jobPk+"' imgPK='"+imgPk+"' onclick='addToImageWall(this)'>addToImageWall</button><br>"
            tr += "</td>";
          }
        }
        tr += "</tr>";
        tr += "<tr>";
        tr += "<td>";
        tr += "<label>jobPk: "+vo.jobPk+"</label><br>";
        tr += "<label>aiUserPk: "+vo.aiUserPk+"</label><br>";
        tr += "<label>createTimeStr: "+vo.createTimeStr+"</label><br>";
        tr += "<label>job status: " + vo.jobStatus + "</label><br>";
        tr += "<label>run count: " + vo.runCount + "</label><br>";
        tr += "<label>is delete: " + vo.isDelete + "</label><br>";
        tr += "<label>is from API: " + vo.isFromApi + "</label><br>";
        tr += "</td>";
        if(parameter){
          tr += "<td>";
          tr += "<label>prompts: "+parameter.prompts+"</label><br>";
          tr += "<label>negativePrompts: "+parameter.negativePrompts+"</label><br>";
          tr += "</td>";
          tr += "<td>";
          tr += "<label>sampler: "+parameter.sampler+"</label><br>";
          tr += "<label>width: "+parameter.width+"</label><br>";
          tr += "<label>height: "+parameter.height+"</label><br>";
          tr += "<label>step: "+parameter.steps+"</label><br>";
          tr += "<label>cfg scale: "+parameter.cfgScale+"</label><br>";
          tr += "<label>batch size: "+parameter.batchSize+"</label><br>";
          tr += "<label>seed: "+parameter.seed+"</label><br>";
          tr += "</td>";
        } else {
          tr += "<td>";
          tr += "<label>prompts: </label><br>";
          tr += "<label>negativePrompts: </label><br>";
          tr += "</td>";
          tr += "<td>";
          tr += "<label>sampler: </label><br>";
          tr += "<label>width: </label><br>";
          tr += "<label>height: </label><br>";
          tr += "<label>step: </label><br>";
          tr += "<label>cfg scale: </label><br>";
          tr += "<label>batch size: </label><br>";
          tr += "<label>seed: </label><br>";
          tr += "</td>";
        }
        tr += "</tr>";

        var jobResultList = $("#jobResult");
        jobResultList.append(tr);
        jobResultList.attr("lastJobPk", vo.jobPk);
      }


    });
  </script>

</html>