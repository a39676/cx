<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<html>

  <head>
    <title>${ title }AI art manager</title>
    <%@ include file="../baseElementJSP/normalHeader.jsp" %>
    <%@ include file="../baseElementJSP/normalJSPart.jsp" %>
  </head>

  <div class="container-fluid">
  
    <div class="row" id="searchCondition" isLock="1">
      <div class="col-lg-12 fixed-top">
        <table class="table">
          <thead>
            <tr>
              <th>
                <label>is running: ${isRunning}</label><br>
                <label id="result"></label>
              </th>
              <td>
                <button id="startJobQueue">startJobQueue</button>
                <button id="stopJobQueue">stopJobQueue</button>
              </td>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>
                <label>createTimeStartStr</label>
                <input type="text" name="" id="createTimeStartStr" placeholder="createTimeStartStr"><br>
                <label>createTimeEndStr</label>
                <input type="text" name="" id="createTimeEndStr" placeholder="createTimeEndStr"><br>
                <label>orderBy</label>
                <input type="text" name="" id="orderBy" placeholder="orderBy"><br>
              </td>
              <td>
                <label>Review</label>
                <div class="form-check">
                  <input value="true" class="form-check-input" type="radio" name="hasReviewRadio" id="reviewed">
                  <label class="form-check-label" for="reviewed">
                    reviewed
                  </label>
                </div>
                <div class="form-check">
                  <input value="false" class="form-check-input" type="radio" name="hasReviewRadio" id="notReview" checked>
                  <label class="form-check-label" for="notReview">
                    notReview
                  </label>
                </div>
              </td>
              <td>
                <label>From API</label>
                <div class="form-check">
                  <input value="true" class="form-check-input" type="radio" name="fromApiRadio" id="fromApi">
                  <label class="form-check-label" for="fromApi">
                    fromApi
                  </label>
                </div>
                <div class="form-check">
                  <input value="false" class="form-check-input" type="radio" name="fromApiRadio" id="notFromAapi">
                  <label class="form-check-label" for="notFromAapi">
                    notFromAapi
                  </label>
                </div>
              </td>
              <td>
                <label>isFreeJob</label>
                <div class="form-check">
                  <input value="true" class="form-check-input" type="radio" name="isFreeJobRadio" id="isFreeJob">
                  <label class="form-check-label" for="isFreeJob">
                    isFreeJob
                  </label>
                </div>
                <div class="form-check">
                  <input value="false" class="form-check-input" type="radio" name="isFreeJobRadio" id="notFreeJob">
                  <label class="form-check-label" for="notFreeJob">
                    notFreeJob
                  </label>
                </div>
              </td>
              <td>
                <label>jobStatus</label>
                <div class="form-check">
                  <input value="0" class="form-check-input" type="radio" name="jobStatusRadio" id="waiting">
                  <label class="form-check-label" for="waiting">
                    waiting
                  </label>
                </div>
                <div class="form-check">
                  <input value="1" class="form-check-input" type="radio" name="jobStatusRadio" id="success">
                  <label class="form-check-label" for="success">
                    success
                  </label>
                </div>
                <div class="form-check">
                  <input value="-1" class="form-check-input" type="radio" name="jobStatusRadio" id="failed">
                  <label class="form-check-label" for="failed">
                    failed
                  </label>
                </div>
            </tr>
          </tbody>
        </table>
        <button id="loadMoreJobResult">load more</button>
        <button id="cleanSearch">cleanSearch</button>
      </div>
    </div>
    
    <div class="row">
      <div class="col-lg-12">
        <br><br><br><br><br><br><br><br><br><br><br><br><br>
        <table class="table">
          <tbody id="jobResult" lastJobPk="">
            
          </tbody>
        </table>
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

    function setHadReview(ele){
      var url = "/aiArtManager/setHadReview";

      var jobPk = ele.getAttribute("jobPk");

      var jsonOutput = {
        pk:jobPk,
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
            $("#result").text("Review success, jobPk: " + jobPk);
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

      $("#startJobQueue").click(function() {
        $("#result").text("");
        aiArtJobQueueSetting("1");
      })

      $("#stopJobQueue").click(function() {
        $("#result").text("");
        aiArtJobQueueSetting("0");
      })

      $("#loadMoreJobResult").click(function (){
        getJobResultList();
      })

      $("#cleanSearch").click(function() {
        cleanSearch();
        $("#searchCondition").attr("isLock", "1");
      })

      function cleanSearch() {
        $("#jobResult").attr("lastJobPk", "");
        $("#orderBy").val("");
        $("input[name='hasReviewRadio']").filter('[value=true]').prop('checked', false);
        $("input[name='hasReviewRadio']").filter('[value=false]').prop('checked', false);
        $("input[name='isFreeJobRadio']").filter('[value=false]').prop('checked', false);
        $("input[name='isFreeJobRadio']").filter('[value=true]').prop('checked', false);
        $("input[name='jobStatusRadio']").filter('[value=-1]').prop('checked', false);
        $("input[name='jobStatusRadio']").filter('[value=0]').prop('checked', false);
        $("input[name='jobStatusRadio']").filter('[value=1]').prop('checked', false);
        $("input[name='fromApiRadio']").filter('[value=false]').prop('checked', false);
        $("input[name='fromApiRadio']").filter('[value=true]').prop('checked', false);
        $("#createTimeStartStr").val("");
        $("#createTimeEndStr").val("");

        $("#jobResult").html("");
      }

      function getJobResultList(){
        var url = "/aiArtManager/getJobResultList";

        var lastJobPk = $("#jobResult").attr("lastJobPk");
        var orderBy = $("#orderBy").val();
        var hasReview = $("input[name='hasReviewRadio']:checked").val();
        var isFreeJob = $("input[name='isFreeJobRadio']:checked").val();
        var jobStatus = $("input[name='jobStatusRadio']:checked").val();
        var isFromApi = $("input[name='fromApiRadio']:checked").val();
        var createTimeStartStr = $("#createTimeStartStr").val();
        var createTimeEndStr = $("#createTimeEndStr").val();

        var jsonOutput = {
          lastJobPk:lastJobPk,
          orderBy:orderBy,
          hasReview:hasReview,
          isFreeJob:isFreeJob,
          jobStatus:jobStatus,
          isFromApi:isFromApi,
          createTimeStartStr:createTimeStartStr,
          createTimeEndStr:createTimeEndStr,
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
              if(datas.jobResultList && datas.jobResultList.length > 1){
                $("#searchCondition").attr("isLock", "0");
              }
              if(!datas.jobResultList){
                return;
              }
              for(i=0;i<datas.jobResultList.length;i++){
                appendJobResult(datas.jobResultList[i], datas.userDetailInRedisMap);
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

      function appendJobResult(vo, userDetailInRedisMap) {
        var tr = "";
        var parameter = vo.parameter;
        tr += "<tr>";
        if(vo.imgPkList){
          for(j=0;j<vo.imgPkList.length;j++){
            var imgPk = vo.imgPkList[j];
            tr += "<td>";
            tr += "<img src='/image/getThumbnail?imgPK="+encodeURIComponent(imgPk)+"' imgPk='"+imgPk+"' name='aiArtImg' onclick='imgFlod(this)'> <br>";
            tr += "<label>"+imgPk.substring(0, 10)+"</label> <br>";
            tr += "<button class='btn btn-sm btn-danger' name='setInvalidImg' jobPk='"+vo.jobPk+"' imgPK='"+imgPk+"' onclick='setInvalidImg(this)'>setInvalidImg</button><br>"
            tr += "<button class='btn btn-sm btn-success' name='addToImageWall' jobPk='"+vo.jobPk+"' imgPK='"+imgPk+"' onclick='addToImageWall(this)'>addToImageWall</button><br>"
            tr += "</td>";
          }
        }
        tr += "</tr>";
        tr += "<tr>";
        tr += "<td>";
        tr += "<label>jobPk: "+vo.jobPk+"</label><br>";
        tr += "<label>aiUserPk: "+vo.aiUserPk+"</label><br>";
        tr += "<label>rechargeMarkThisWeek: "+userDetailInRedisMap[vo.aiUserPk].rechargeMarkThisWeek+"</label><br>";
        tr += "<label>freeJobCountingToday: "+userDetailInRedisMap[vo.aiUserPk].freeJobCountingToday+"</label><br>";
        tr += "<label>freeJobCountingLastThreeDays: "+userDetailInRedisMap[vo.aiUserPk].freeJobCountingLastThreeDays+"</label><br>";
        tr += "<label>user nsfw today counting: "+vo.nsfwJobCounting+"</label><br>";
        tr += "<label><button class='btn btn-sm btn-success' name='unlockUser' userPk='"+vo.aiUserPk+"'>Unlock</button> <button class='btn btn-sm btn-danger' name='blockUser' userPk='"+vo.aiUserPk+"'>Block</button></label><br>";
        tr += "<label>createTimeStr: "+vo.createTimeStr+"</label><br>";
        tr += "<label>job status: " + vo.jobStatus + " run count: " + vo.runCount + "</label><br>";
        tr += "<label>is free job: " + vo.isFreeJob + "</label><br>";
        if(vo.hasReview == false){
          tr += "<label class='badge badge-sm badge-danger'>has review: " + vo.hasReview + "</label><br>";
          tr += "<button class='btn btn-sm btn-warning' name='setHadReview' jobPk='"+vo.jobPk+"' imgPK='"+imgPk+"' onclick='setHadReview(this)'>setHadReview</button><br>"
        } else {
          tr += "<label class='badge badge-sm'>has review: " + vo.hasReview + "</label><br>";
        }
        tr += "<label>is delete: " + vo.isDelete + "</label><br>";
        if(vo.isFromApi == false){
          tr += "<label class='badge badge-sm badge-danger'>is from API: " + vo.isFromApi + "</label><br>";
        } else {
          tr += "<label class='badge badge-sm'>is from API: " + vo.isFromApi + "</label><br>";
        }
        tr += "</td>";
        if(parameter){
          tr += "<td>";
          var prompts = parameter.prompts.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
          tr += "<label>prompts: "+prompts+"</label><br>";
          var negativePrompts = parameter.negativePrompts.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
          tr += "<label>negativePrompts: "+negativePrompts+"</label><br>";
          tr += "</td>";
          tr += "<td>";
          tr += "<label>modelName: "+parameter.modelName+"</label><br>";
          tr += "<label>sampler: "+vo.samplerName+"</label><br>";
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

        $("button[name='blockUser'][userPk='"+vo.aiUserPk+"']").bind("click", function() {
          blockUser(vo.aiUserPk);
        });
        $("button[name='unlockUser'][userPk='"+vo.aiUserPk+"']").bind("click", function() {
          unlockUser(vo.aiUserPk);
        });
      }

      function blockUser(userPk){
        var url = "/aiChatManager/blockUserByPk";

        var jsonOutput = {
          pk:userPk,
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
              $("#result").text("Block: " + userPk);
            } else {
              $("#result").text(datas.message);
            }
          },
          error: function(datas) {
            
          }
        });
      }

      function unlockUser(userPk){
        var url = "/aiChatManager/unlockUserByPk";

        var jsonOutput = {
          pk:userPk,
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
              $("#result").text("Unlock: " + userPk);
            } else {
              $("#result").text(datas.message);
            }
          },
          error: function(datas) {
            
          }
        });
      }

      function aiArtJobQueueSetting(jobQueueStatus){
        var url = "/aiArtManager/aiArtJobQueueSetting";

        var jsonOutput = {
          str:jobQueueStatus,
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
            $("#result").text(datas.code + ", " + datas.message);
          },
          error: function(datas) {
            
          }
        });
      }

      var lastScrollTop = 0;
      $(window).scroll(function(event){
        var st = $(this).scrollTop();
        var searchCondition = $("#searchCondition");
        var lockFlag = searchCondition.attr("isLock");
        if (st > lastScrollTop){
          console.log("downscroll");
          if("1" != lockFlag){
            searchCondition.hide();
          }
        } else {
          console.log("upscroll");
          searchCondition.show();
        }
        lastScrollTop = st;
      });
    });
  </script>

</html>