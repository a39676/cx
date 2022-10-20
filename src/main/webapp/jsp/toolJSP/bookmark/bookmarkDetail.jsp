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
      <div class="col-md-12">
        Bookmark name: ${bookmarkVO.bookmarkName}
        <table class="table table-striped table-dark">
          <thead class="thead-dark">
            <tr>
              <td>
                <c:forEach items="${bookmarkVO.allTagList}" var="tagVO">
                  <button class="btn btn-light btn-sm bookmarkTag" tagName="${tagVO.tagName}">
                    ${tagVO.tagName}
                  </button>
                </c:forEach>
              </td>
              <td>
                <input type="" id="bookmarkKeyword" name="" placeholder="Search keyword">
              </td>
            </tr>
          </thead>
        </table>
      </div>
    </div>

    <div class="row" id="mainRow" filterTags="">
      <div class="col-md-12">
        <table class="table table-striped table-dark">
          <tbody id="bookmarkVoList">
            <c:forEach items="${bookmarkVO.urlList}" var="urlVO">
              <tr class="bookmarkVO" tagNameList="${urlVO.tagNameList}" 
              urlName="${urlVO.name}" url="${urlVO.url}">
                <td>
                  <a href="${urlVO.url}" target="_blank">
                    ${urlVO.name}
                  </a>
                </td>
                <td>${urlVO.tagNameList}</td>
                <td>
                  <button class="btn btn-success btn-sm" urlPK="${urlVO.pk}">Edit</button>
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
            var thisTagNameArr = thisTagNameStr.split(",");
            var common = thisTagNameArr.filter(x => tagList.indexOf(x) !== -1)
            if(common.length < 1){
              $(this).hide();
            } else {
              $(this).show();
            }
          })
          return;
        }

        bookmarkVoList.each(function(){
          var urlName = $(this).attr("urlName");
          var url = $(this).attr("url");
          var thisTagNameStr = $(this).attr("tagNameList");
          thisTagNameStr = thisTagNameStr.substring(1, thisTagNameStr.length - 1);
          var thisTagNameArr = thisTagNameStr.split(",");
          var common = thisTagNameArr.filter(x => tagList.indexOf(x) !== -1)
          if(common.length > 0 && (matchKeyword(urlName, url, keyword))){
            $(this).show();
          } else {
            $(this).hide();
          }
        })
      }

      

    });

  </script>
</footer>
</html>
