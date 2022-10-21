<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
  <div class="container-fluid">
    <div class="row" id="mainRow" filterTags="">
      <div class="col-md-12" id="info" bookmarkPK="${bookmarkVO.pk}">
        Bookmark name: ${bookmarkVO.bookmarkName}
        <table class="table table-striped table-dark">
          <thead class="thead-dark">
            <tr>
              <td>
                <c:forEach items="${bookmarkVO.allTagList}" var="tagVO">
                  <button class="btn btn-light btn-sm bookmarkTag" 
                  tagPk="${tagVO.pk}" tagName="${tagVO.tagName}">
                    ${tagVO.tagName}
                  </button>
                </c:forEach>
              </td>
              <td>
                <button id="TagManager" class="btn btn-warning btn-sm" tagManagerMode="0">
                  Manager tags
                </button>
              </td>
            </tr>
            <tr class="TagManagerTr">
              <td>
                <button id="deleteTag" class="btn btn-danger btn-sm">
                  Delete selected tags
                </button>
              </td>
            </tr>
            <tr>
              <td>
                <input type="" id="bookmarkKeyword" name="" placeholder="Search keyword">
              </td>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>
                <span id="result"></span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="row" id="mainRow" filterTags="">
      <div class="col-md-12">
        <table class="table table-striped">
          <tbody id="bookmarkVoList">
            <c:forEach items="${bookmarkVO.urlList}" var="urlVO">
              <tr class="bookmarkVO" tagNameList="${urlVO.tagNameList}" 
              urlName="${urlVO.name}" url="${urlVO.url}">
                <td>
                  <a href="${urlVO.url}" target="_blank">
                    ${urlVO.name}
                  </a>
                </td>
                <td>
                  <c:forEach items="${urlVO.tagVoList}" var="tagVoInUrl">
                    <span class="badge badge-md badge-success tagVoInUrl" tagPk="${tagVoInUrl.pk}">
                      ${tagVoInUrl.tagName}    
                    </span>
                  </c:forEach>
                </td>
                <td>
                  <button class="btn btn-success btn-sm" urlPK="${urlVO.pk}">Edit</button>
                  <button class="btn btn-danger btn-sm deleteUrl" urlPK="${urlVO.pk}">Delete</button>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</body>

<footer>
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $(".TagManagerTr").hide();

      
      $(".bookmarkTag").click(function(){
        if($(this).hasClass("btn-light")){
          $(this).removeClass("btn-light");
          $(this).addClass("btn-primary");
        } else {
          $(this).removeClass("btn-primary");
          $(this).addClass("btn-light");
        }

        filterBookmark();  
      })

      $("#bookmarkKeyword").change(function(){
        filterBookmark();  
      })

      function filterBookmark(){
        if($("#TagManager").attr("tagManagerMode") == 1){
          return;
        }
        var tagList = findAllTagsInFilter();
        var keyword = $("#bookmarkKeyword").val();
        searchBookmark(tagList, keyword);
      }

      function findAllTagsInFilter(){
        var bookmarkTagInPrimary = $(".bookmarkTag.btn-primary");
        var tagList = [];
        bookmarkTagInPrimary.each(function(){
          tagList.push($(this).attr("tagName"));
        })
        return tagList;
      }

      function matchKeyword(urlName, url, keyword){
        return (urlName.indexOf(keyword) >= 0) || (url.indexOf(keyword) >= 0)
      }

      function searchBookmark(tagList, keyword){
        var bookmarkVoList = $(".bookmarkVO");

        if(tagList.length === 0 && keyword.length < 1){
          bookmarkVoList.each(function(){
            $(this).show();
          })
          return;
        }

        if(tagList.length === 0 && keyword.length > 0){
          bookmarkVoList.each(function(){
            var urlName = $(this).attr("urlName");
            var url = $(this).attr("url");
            if(matchKeyword(urlName, url, keyword)){
              $(this).show();
            } else {
              $(this).hide();
            }
          })
          return;
        }

        if(tagList.length != 0 && keyword.length < 1){
          bookmarkVoList.each(function(){
            var thisTagNameStr = $(this).attr("tagNameList");

            thisTagNameStr = thisTagNameStr.substring(1, thisTagNameStr.length - 1);
            var thisTagNameArr = thisTagNameStr.split(", ");

            var tagNameMatch = false;
            for(let thisTagNameArrIndex = 0; 
              thisTagNameArrIndex < thisTagNameArr.length && !tagNameMatch;
              thisTagNameArrIndex++){
              for(let tagListIndex = 0;
                tagListIndex < tagList.length && !tagNameMatch;
                tagListIndex++){
                tagNameMatch = (tagList[tagListIndex] == thisTagNameArr[thisTagNameArrIndex]);
              }
            }

            if(tagNameMatch){
              $(this).show();
            } else {
              $(this).hide();
            }
          })
          return;
        }

        bookmarkVoList.each(function(){
          var urlName = $(this).attr("urlName");
          var url = $(this).attr("url");
          var thisTagNameStr = $(this).attr("tagNameList");
          thisTagNameStr = thisTagNameStr.substring(1, thisTagNameStr.length - 1);
          var thisTagNameArr = thisTagNameStr.split(", ");

          var tagNameMatch = false;
          for(let thisTagNameArrIndex = 0; 
            thisTagNameArrIndex < thisTagNameArr.length && !tagNameMatch;
            thisTagNameArrIndex++){
            for(let tagListIndex = 0;
              tagListIndex < tagList.length && !tagNameMatch;
              tagListIndex++){
              tagNameMatch = (tagList[tagListIndex] == thisTagNameArr[thisTagNameArrIndex]);
            }
          }

          if(tagNameMatch && matchKeyword(urlName, url, keyword)){
            $(this).show();
          } else {
            $(this).hide();
          }
        })
      }

      $("#TagManager").click(function(){
        $(".bookmarkTag").removeClass("btn-primary");
        $(".bookmarkTag").addClass("btn-light");
        $("#bookmarkKeyword").val("");
        
        if($(this).attr("tagManagerMode") == 0){
          $(".TagManagerTr").show();
          $(this).attr("tagManagerMode", 1);
        }else{
          $(".TagManagerTr").hide();
          $(this).attr("tagManagerMode", 0);
        }
        filterBookmark();
      })

      $("#deleteTag").click(function() {
        var bookmarkPK = $("#info").attr("bookmarkPK");

        var bookmarkTagInPrimary = $(".bookmarkTag.btn-primary");
        var tagPkList = [];
        bookmarkTagInPrimary.each(function(){
          tagPkList.push($(this).attr("tagPk"));
        })

        var url = "/bookmark/deleteBookmarkTag";
        var jsonOutput = {
          bookmarkPK : bookmarkPK,
          tagPkList : tagPkList,
        };

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
            $("#result").text(data.message);
            if(data.code == 0){
              bookmarkTagInPrimary.remove();
            }
          },
          error:function(e){
            $("#result").text(e);
          }
        });
      })

    });

  </script>
</footer>
</html>
