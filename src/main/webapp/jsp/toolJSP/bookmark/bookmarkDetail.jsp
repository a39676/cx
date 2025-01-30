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
    <div class="row">
      <div class="col-md-12" id="info" bookmarkPK="${bookmarkVO.pk}">
        Bookmark name: ${bookmarkVO.bookmarkName}
        <table class="table table-striped">
          <thead class="">
            <tr>
              <td id="tagTd">
                <button class="btn btn-light btn-sm" id="unselectALlTag">Unselect ALL tag</button>
                <c:forEach items="${bookmarkVO.allTagList}" var="tagVO">
                  <button class="btn btn-light btn-sm bookmarkTag" 
                  tagPk="${tagVO.pk}" tagName="${tagVO.tagName}">
                    ${tagVO.tagName}
                  </button>
                </c:forEach>
                <br>
                <input type="" id="bookmarkKeyword" name="" placeholder="Search keyword">
              </td>
              <td>
                <button id="TagManager" class="btn btn-warning btn-sm" tagManagerMode="0">
                  Manager tags
                </button>
                <button id="StopTagManager" class="btn btn-warning btn-sm">
                  Stop manager tags
                </button>
              </td>
            </tr>
            <tr class="TagManagerTr">
              <td>
                <button id="deleteTag" class="btn btn-danger btn-sm">
                  Delete selected tags
                </button>
                <button id="addTag" class="btn btn-success btn-sm">
                  Add new tag
                </button>
                <input type="text" name="" id="newTagNameInTagManager" placeholder="New tag name">
                <button id="editTag" class="btn btn-success btn-sm">
                  Edit tag name
                </button>
                <input type="text" name="" id="editTagName" placeholder="Edit tag name">
                <button id="removeEmptyTags" class="btn btn-sm btn-warning">
                  Remove empty tags
                </button>
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

    <div class="row">
      <div class="col-md-12">
        <table class="table table-striped">
          <tbody id="bookmarkVoList">
            <tr id="editUrlVoTr" urlPK="" style="display: none">
              <td>
                <input type="text" name="" value="" id="editUrlName" placeholder="URL name"><br>
                <input type="text" name="" value="" id="editUrlUrl" placeholder="URL here">
              </td>
              <td id="urlTagListTd">
                <input type="text" name="" placeholder="Create new tag" id="createNewTagWhenUrlEdit">
                <button id="createNewTagWhenUrlEditBtn" class="btn btn-success btn-sm">
                  Create new tag
                </button><br>
                <c:forEach items="${bookmarkVO.allTagList}" var="tagVO">
                  <button class="btn btn-light btn-sm bookmarkTagForEdit" 
                  tagPk="${tagVO.pk}" tagName="${tagVO.tagName}">
                    ${tagVO.tagName}
                  </button>
                </c:forEach>
              </td>
              <td>
                <button class="btn btn-sm btn-primary" id="submitUrlEdit">Submit</button>
                <button class="btn btn-sm btn-primary" id="cancelUrlEdit">Cancel</button>
              </td>
            </tr>
            <tr>
              <td>
                <button class="btn btn-warning btn-sm" id="createNewBookmarkUrl">
                  Create new bookmark URL
                </button>
              </td>
            </tr>
            <c:forEach items="${bookmarkVO.urlList}" var="urlVO">
              <tr class="urlVoTr" tagNameList="${urlVO.tagNameList}" 
              urlName="${urlVO.name}" url="${urlVO.url}" urlPK="${urlVO.pk}">
                <td>
                  <a class="bookmarkUrl" urlPK="${urlVO.pk}" href="${urlVO.url}" target="_blank">
                    ${urlVO.name}
                  </a>
                </td>
                <td class="bookmarkUrlTagList" urlPK="${urlVO.pk}">
                  <c:forEach items="${urlVO.tagVoList}" var="tagVoInUrl">
                    <span class="badge badge-md badge-success tagVoInUrl" tagPk="${tagVoInUrl.pk}" tagName="${tagVoInUrl.tagName}">
                      ${tagVoInUrl.tagName}
                    </span>
                  </c:forEach>
                </td>
                <td>
                  <button class="btn btn-success btn-sm editUrl" urlPK="${urlVO.pk}">Edit</button>
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

      const tagWeightMap = new Map();
      const urlWeightMap = new Map();
      
      $(".TagManagerTr").hide();
      $("#StopTagManager").hide();

      $(".bookmarkTag").click(function(){
        var tagPK = $(this).attr("tagPk");
        tagClick(tagPK);
      })

      $(".bookmarkUrl").click(function() {
        var urlPK = $(this).attr("urlPK");
        if(urlWeightMap.has(urlPK)){
          urlWeightMap.set(urlPK, urlWeightMap.get(urlPK) + 1);
        }else{
          urlWeightMap.set(urlPK, 1);
        }
        updateBookmarkWeightData();
      })

      function tagClick(tagPK){
        var thisTag = $(".bookmarkTag[tagPK='"+tagPK+"']");
        if(thisTag.hasClass("btn-light")){
          thisTag.removeClass("btn-light");
          thisTag.addClass("btn-primary");
          
          if(tagWeightMap.has(tagPK)){
            tagWeightMap.set(tagPK, tagWeightMap.get(tagPK) + 1);
          }else{
            tagWeightMap.set(tagPK, 1);
          }
          updateBookmarkWeightData();
        } else {
          thisTag.removeClass("btn-primary");
          thisTag.addClass("btn-light");
        }

        filterBookmark();  
      }

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
        var urlVoTr = $(".urlVoTr");

        if(tagList.length === 0 && keyword.length < 1){
          urlVoTr.each(function(){
            $(this).show();
          })
          return;
        }

        if(tagList.length === 0 && keyword.length > 0){
          urlVoTr.each(function(){
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
          urlVoTr.each(function(){
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

        urlVoTr.each(function(){
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
        startTagManager();
      })

      $("#StopTagManager").click(function() {
        stopTagManager();
      })

      function startTagManager(){
        $("#TagManager").hide();
        $("#StopTagManager").show();

        $("#TagManager").attr("tagManagerMode", 1);
        $(".bookmarkTag").removeClass("btn-primary");
        $(".bookmarkTag").addClass("btn-light");
        $("#bookmarkKeyword").val("");

        $(".TagManagerTr").show();

        filterBookmark();
      }

      function stopTagManager(){
        $("#TagManager").show();
        $("#StopTagManager").hide();

        $("#TagManager").attr("tagManagerMode", 0);
        $(".bookmarkTag").removeClass("btn-primary");
        $(".bookmarkTag").addClass("btn-light");
        $("#bookmarkKeyword").val("");

        $(".TagManagerTr").hide();

        filterBookmark();
      }

      $("#deleteTag").click(function() {
        var bookmarkPK = $("#info").attr("bookmarkPK");

        var bookmarkTagInPrimary = $(".bookmarkTag.btn-primary");
        var tagPkList = [];
        var tagNameList = [];
        bookmarkTagInPrimary.each(function(){
          tagPkList.push($(this).attr("tagPk"));
          tagNameList.push($(this).attr("tagName"));
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
            for(let i = 0; i < tagNameList.length; i++){
              $(".bookmarkTagForEdit[tagName='"+tagNameList[i]+"']").remove();
              $(".tagVoInUrl[tagName='"+tagNameList[i]+"']").remove();
            }
          },
          error:function(e){
            $("#result").text(e);
          }
        });
      })

      $("#addTag").click(function() {
        var newTagName = $("#newTagNameInTagManager").val();
        createNewTag(newTagName);
      })

      function createNewTag(newTagName) {
        var bookmarkPK = $("#info").attr("bookmarkPK");

        var url = "/bookmark/addBookmarkTag";
        var jsonOutput = {
          bookmarkPK : bookmarkPK,
          tagName : newTagName,
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
              var newBtnHtml = "<button class=\"btn btn-light btn-sm bookmarkTag\"";
              newBtnHtml += "tagpk=\"" + data.pk + "\" ";
              newBtnHtml += "tagName=\"" + newTagName + "\" >";
              newBtnHtml += newTagName;
              newBtnHtml += "</button>";
              $("#tagTd").append(newBtnHtml);
              var thisNewTag = $(".bookmarkTag[tagPk='"+data.pk+"']");
              thisNewTag.bind( "click", function() {
                tagClick(data.pk);
              });
              $("#newTagNameInTagManager").val("");
              $("#createNewTagWhenUrlEdit").val("");

              newBtnHtml = "<button class=\"btn btn-light btn-sm bookmarkTagForEdit\""; 
              newBtnHtml += "tagPk=\""+data.pk+"\" tagName=\""+newTagName+"\">";
              newBtnHtml += newTagName;
              newBtnHtml += "</button>";
              $("#urlTagListTd").append(newBtnHtml);
              var thisNewTag = $(".bookmarkTagForEdit[tagPk='"+data.pk+"']");
                thisNewTag.bind( "click", function() {
                editBookmarkTagClick(data.pk);
              });
            }
          },
          error:function(e){
            $("#result").text(e);
          }
        });
      }

      $("#editTag").click(function() {
        var bookmarkTagInPrimary = $(".bookmarkTag.btn-primary");
        var tagPkList = [];
        bookmarkTagInPrimary.each(function(){
          tagPkList.push($(this).attr("tagPk"));
        })

        if(tagPkList.length != 1){
          $("#result").text("Please select ONE tag");
          return;
        }

        var bookmarkPK = $("#info").attr("bookmarkPK");
        var editTagName = $("#editTagName").val();
        var tagPK = tagPkList[0];

        var url = "/bookmark/editBookmarkTag";
        var jsonOutput = {
          bookmarkPK : bookmarkPK,
          tagPK : tagPK,
          tagName : editTagName,
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
              // change tag name in Tag list
              bookmarkTagInPrimary.html(editTagName);

              // change tag name in all url
              $(".tagVoInUrl[tagPk='"+tagPK+"']").text(editTagName);
              $(".bookmarkTagForEdit[tagPk='"+tagPK+"']").text(editTagName);
            }
          },
          error:function(e){
            $("#result").text(e);
          }
        });
      })

      $("#unselectALlTag").click(function() {
        $(".bookmarkTag").removeClass("btn-primary");
        $(".bookmarkTag").addClass("btn-light");
        tagClick($(this).attr("tagPk"));
      })

      $("#submitUrlEdit").click(function() {
        var bookmarkTagForEditUrl = $(".bookmarkTagForEdit.btn-primary");
        var tagNameList = [];
        var tagPkList = [];
        bookmarkTagForEditUrl.each(function(){
          tagNameList.push($(this).attr("tagName"));
          tagPkList.push($(this).attr("tagPK"));
        })

        var bookmarkPK = $("#info").attr("bookmarkPK");
        var bookmarkUrlPK = $("#editUrlVoTr").attr("urlPK");
        var bookmarkUrl = $("#editUrlUrl").val();
        var bookmarkUrlName = $("#editUrlName").val();
        var createNew = bookmarkUrlPK.length < 1;

        var url = "/bookmark/editBookmarkUrl";
        var jsonOutput = {
          bookmarkPK : bookmarkPK,
          bookmarkUrlPK : bookmarkUrlPK,
          bookmarkUrlName : bookmarkUrlName,
          bookmarkUrl : bookmarkUrl,
          tagNameList : tagNameList,
          createNew : createNew,
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
              // update target url
              $(".bookmarkUrl[urlPK='"+bookmarkUrlPK+"']").attr("href", bookmarkUrl);
              $(".bookmarkUrl[urlPK='"+bookmarkUrlPK+"']").text(bookmarkUrlName);

              var targetUrlTagList = $(".bookmarkUrlTagList[urlPK='"+bookmarkUrlPK+"']");
              targetUrlTagList.html("");

              for(let i = 0; i < tagNameList.length; i++){
                var newTagSpan = "<span class='badge badge-md badge-success tagVoInUrl' tagPK='"+tagPkList[i]+"' tagName='"+tagNameList[i]+"'>";
                newTagSpan += " " + tagNameList[i] + " ";
                newTagSpan += "</span>"
                targetUrlTagList.append(newTagSpan);
              }

              if(createNew){
                bookmarkUrlPK = data.bookmarkUrlPk;

                var tagNameListStr = "[";
                for(let i = 0; i < tagNameList.length; i++){
                  tagNameListStr += tagNameList[i] + ", ";
                }
                tagNameListStr = tagNameListStr.substring(0, tagNameListStr.length - 2);
                tagNameListStr += "]";

                var urlVoTr = "<tr class='urlVoTr' tagNameList='"+tagNameListStr+"' urlName='"+bookmarkUrlName+"' url='"+bookmarkUrl+"' urlpk='"+bookmarkUrlPK+"'>";
                urlVoTr += "<td>";
                urlVoTr += "<a class='bookmarkUrl' urlpk='"+bookmarkUrlPK+"' href='"+bookmarkUrl+"' target='_blank'>";
                urlVoTr += " " + bookmarkUrlName + " ";
                urlVoTr += "</a>"
                urlVoTr += "</td>"
                urlVoTr += "<td class='bookmarkUrlTagList' urlpk='"+bookmarkUrlPK+"'>";
                urlVoTr += "</td>"
                urlVoTr += "<td>"
                urlVoTr += "<button class='btn btn-success btn-sm editUrl' urlpk='"+bookmarkUrlPK+"'>Edit</button>";
                urlVoTr += "<button class='btn btn-danger btn-sm deleteUrl' urlpk='"+bookmarkUrlPK+"'>Delete</button>";
                urlVoTr += "</td>"
                urlVoTr += "</tr>"

                $("#bookmarkVoList").append(urlVoTr);

                var thisNewEditBtn = $(".editUrl[urlpk='"+bookmarkUrlPK+"']");
                thisNewEditBtn.bind( "click", function() {
                  editUrl(bookmarkUrlPK);
                });

                var thisNewDeleteBtn = $(".deleteUrl[urlpk='"+bookmarkUrlPK+"']");
                thisNewDeleteBtn.bind( "click", function() {
                  deleteUrl(bookmarkUrlPK);
                });

                var urlTag = "";
                for(let i = 0; i < tagNameList.length; i++){
                  urlTag = "";
                  urlTag += "<span class='badge badge-md badge-success tagVoInUrl' tagpk='"+tagPkList[i]+"' tagname='"+tagNameList[i]+"'>";
                  urlTag += tagNameList[i];
                  urlTag += "</span>";
                  $(".bookmarkUrlTagList[urlpk='"+bookmarkUrlPK+"']").append(urlTag);
                }
              }

              endEditUrl();
            }
          },
          error:function(e){
            $("#result").text(e);
          }
        });
      })

      $("#cancelUrlEdit").click(function() {
        endEditUrl();
      })

      function endEditUrl() {
        $("#editUrlVoTr").hide();

        $(".bookmarkTagForEdit").removeClass("btn-primary");
        $(".bookmarkTagForEdit").addClass("btn-light");

        $("#editUrlName").val("");
        $("#editUrlUrl").val("");
        $("#editUrlVoTr").attr("urlPK", "");

        $("#TagManager").prop("disabled", false);
        stopTagManager();
        $("#createNewBookmarkUrl").show();
      }

      $("#createNewTagWhenUrlEditBtn").click(function() {
        var newTagName = $("#createNewTagWhenUrlEdit").val();
        createNewTag(newTagName);
      })

      $(".editUrl").click(function() {
        editUrl($(this).attr("urlPK"));
      })

      function editUrl(urlPK){
        $(".bookmarkTagForEdit").removeClass("btn-primary");
        $(".bookmarkTagForEdit").addClass("btn-light");
        copyUrlInfoToEditArea(urlPK);
        $("#editUrlVoTr").show();

        $("#TagManager").prop("disabled", true);
        stopTagManager();
      }

      function copyUrlInfoToEditArea(urlPK){
        var bookmarkPK = $("#info").attr("bookmarkPK");
        var sourceUrl = $(".urlVoTr[urlPK='"+urlPK+"']");
        var url = sourceUrl.attr("url");
        var urlName = sourceUrl.attr("urlName");
        var sourceTagNameListStr = sourceUrl.attr("tagNameList");
        sourceTagNameListStr = sourceTagNameListStr.substring(1, sourceTagNameListStr.length - 1);
        var sourceTagNameList = sourceTagNameListStr.split(", ");

        $("#editUrlName").val(urlName);
        $("#editUrlUrl").val(url);
        $("#editUrlVoTr").attr("urlPK", urlPK);

        for(let tagListIndex = 0;
                tagListIndex < sourceTagNameList.length;
                tagListIndex++){
          $(".bookmarkTagForEdit[tagName='"+sourceTagNameList[tagListIndex]+"']").addClass("btn-primary");
          $(".bookmarkTagForEdit[tagName='"+sourceTagNameList[tagListIndex]+"']").removeClass("btn-light");
        }
      }

      $(".bookmarkTagForEdit").click(function(){
        editBookmarkTagClick($(this).attr("tagPk"));
      })

      function editBookmarkTagClick(tagPk){
        var thisTag = $(".bookmarkTagForEdit[tagPk='"+tagPk+"']");
        if(thisTag.hasClass("btn-light")){
          thisTag.removeClass("btn-light");
          thisTag.addClass("btn-primary");
        } else {
          thisTag.removeClass("btn-primary");
          thisTag.addClass("btn-light");
        }
      }

      $(".deleteUrl").click(function() {
        deleteUrl($(this).attr("urlPK"));
      })

      function deleteUrl(urlPK) {
        var bookmarkPK = $("#info").attr("bookmarkPK");
        var bookmarkUrlPK = urlPK;

        var url = "/bookmark/deleteBookmarkUrl";
        var jsonOutput = {
          bookmarkPK : bookmarkPK,
          bookmarkUrlPK : bookmarkUrlPK,
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
              $(".urlVoTr[urlPK='"+bookmarkUrlPK+"']").remove();
            }
          },
          error:function(e){
            $("#result").text(e);
          }
        });
      }

      $("#createNewBookmarkUrl").click(function() {
        $(".bookmarkTagForEdit").removeClass("btn-primary");
        $(".bookmarkTagForEdit").addClass("btn-light");
        $("#editUrlVoTr").show();

        $("#editUrlName").val("");
        $("#editUrlUrl").val("");
        $("#editUrlVoTr").attr("urlPK", "");

        $("#TagManager").prop("disabled", true);
        stopTagManager();
        $(this).hide();
      })

      $("#removeEmptyTags").click(function() {
        var bookmarkPK = $("#info").attr("bookmarkPK");

        var url = "/bookmark/removeEmptyTag";
        var jsonOutput = {
          bookmarkPK : bookmarkPK,
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
          },
          error:function(e){
            $("#result").text(e);
          }
        });
      })

      function updateBookmarkWeightData() {
        var bookmarkPK = $("#info").attr("bookmarkPK");
        var tagSubDataList = [];
        var urlSudDataList = [];
        var tmpTagData = {};
        var tmpUrlData = {};

        tagWeightMap.forEach(function(value, key) {
          tmpTagData = {tagPK:key, weight:value}
          tagSubDataList.push(tmpTagData);
        });

        urlWeightMap.forEach(function(value, key) {
          tmpUrlData = {urlPK:key, weight:value}
          urlSudDataList.push(tmpUrlData);
        });

        if(tagSubDataList.length < 1 && urlSudDataList.length < 1){
          return;
        }

        var url = "/bookmark/bookmarkWeightChange";
        var jsonOutput = {
          bookmarkPK : bookmarkPK,
          tagSubDataList : tagSubDataList,
          urlSudDataList : urlSudDataList,
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
              tagWeightMap.clear();
              urlWeightMap.clear();
            }
          },
          error:function(e){
            $("#result").text(e);
          }
        });
      }
    });

  </script>
</footer>
</html>
