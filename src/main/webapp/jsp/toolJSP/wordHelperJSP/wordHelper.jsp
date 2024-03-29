<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

  <head>
    <title>${ title }Word helper</title>
    <%@ include file="../../baseElementJSP/normalHeader.jsp" %>
    <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  </head>

  <div class="container-fluid">
    <div class="row operationRow">
      <div class="col-lg-12">

        <input type="text" id="enInput" name="">
        <input type="text" id="cnInput" name="">

        <button id="addWord">Add word</button>
        <button id="deleteWord">Delete word</button>
        <button id="findWords">Find words</button>
      </div>
    </div>

    <div class="row operationRow">
      <div class="col-lg-12">
        <button id="append">Append</button>
        <button id="update">Update</button>
      </div>
    </div>

    <div class="row operationRow">
      <div class="col-lg-12">
        <label>Result: </label>
        <label id="result"></label>
      </div>
    </div>

    <div class="row operationRow">
      <div class="col-lg-12">
        <button id="printRandomWord">Print random word</button>
        <button id="printNewWord">Print new word</button><br>
        <button id="printRandomWordsInMarks">Print random word (EN in marks)</button>
        <button id="printNewWordsInMarks">Print new word (EN in marks)</button><br>
        <input type="number" id="printWordsCounting" name="" value="90"><br>

        <div class="form-check" onclick="enToggler()">
          <input class="form-check-input" type="checkbox" value="" id="showEN" checked>
          <label class="form-check-label" for="showEN">
            showEN
          </label>
        </div>

        <div class="form-check" onclick="cnToggler()">
          <input class="form-check-input" type="checkbox" value="" id="showCN">
          <label class="form-check-label" for="showCN">
            showCN
          </label>
        </div>

        <div class="form-check" onclick="enInMarkToggler()">
          <input class="form-check-input" type="checkbox" value="" id="showEnInMark">
          <label class="form-check-label" for="showEnInMark">
            showEnInMark
          </label>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-lg-12">
        <button id="operationRowSwitch" flag="1">operationRowSwitch</button>
      </div>
    </div>
    
    <div class="row">
      <div class="col-lg-12" id="randomWords">
        
      </div>
    </div>
  </div>

  <script type="text/javascript">

    $("#operationRowSwitch").click(function() {
      operationRowSwitch();
    });

    function operationRowSwitch() {
      var switchButton = $("#operationRowSwitch");
      if("1" == switchButton.attr("flag")){
        $(".operationRow").hide();
        switchButton.attr("flag", "0");
      } else {
        $(".operationRow").show();
        switchButton.attr("flag", "1");
      }
    }

    function enToggler(){
      var enToggleFlag = document.getElementById('showEN').checked;
      enToggle(enToggleFlag);
    }

    function enToggle(flag) {
      var enArray = document.getElementsByClassName("wordEn");
      if(!flag){
        for (let i = 0; i < enArray.length; i++) {
          enArray[i].style.display = "none";
        }
      } else {
        for (let i = 0; i < enArray.length; i++) {
          enArray[i].style.display = "block";
        }
      }
    }

    function cnToggler(){
      var cnToggleFlag = document.getElementById('showCN').checked;
      cnToggle(cnToggleFlag);
    }

    function cnToggle(flag) {
      var cnArray = document.getElementsByClassName("wordCn");
      if(!flag){
        for (let i = 0; i < cnArray.length; i++) {
          cnArray[i].style.display = "none";
        }
      } else {
        for (let i = 0; i < cnArray.length; i++) {
          cnArray[i].style.display = "block";
        }
      }
    }

    function enInMarkToggler(){
      var enInMarkToggleFlag = document.getElementById('showEnInMark').checked;
      enInMarkToggle(enInMarkToggleFlag);
    }

    function enInMarkToggle(flag) {
      var enInMarksArray = document.getElementsByClassName("wordEnInMarks");
      if(!flag){
        for (let i = 0; i < enInMarksArray.length; i++) {
          enInMarksArray[i].style.display = "none";
        }
      } else {
        for (let i = 0; i < enInMarksArray.length; i++) {
          enInMarksArray[i].style.display = "block";
        }
      }
    }
    
    $(document).ready(function() {

      $("#addWord").click(function() {
        addWord();
      })

      function addWord(){
        $("#result").text("Adding word");

        var url = "/wordHelper/addNewWord";

        var enInput = $("#enInput").val();
        var cnInput = $("#cnInput").val();

        var jsonOutput = {
          en:enInput,
          cn:cnInput,
        };

  
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
            $("#result").text(datas.code + ", " + datas.message);
          },
          error: function(datas) {
          }
        });
      }

      $("#findWords").click(function() {
        findWords();
      })

      function findWords(){
        $("#result").text("Finding word");

        var url = "/wordHelper/findWords";

        var enInput = $("#enInput").val();
        var cnInput = $("#cnInput").val();

        var jsonOutput = {
          en:enInput,
          cn:cnInput,
        };

  
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
            $("#result").text(datas.code + ", " + datas.message);
            var wordResult = "";
            for(var i = 0; i < datas.wordList.length; i++){
              wordResult = wordResult + "<tr>";
              wordResult = wordResult + "<td><label class='wordEn'>" + datas.wordList[i].en + "</label></td>";
              wordResult = wordResult + "<td><label class='wordCn'>" + datas.wordList[i].cn + "</label></td>";
              wordResult = wordResult + "</tr>";
            }
            var head = "<tr> <td>EN</td> <td>CN</td> </tr>"
            $("#randomWords").html(head + wordResult);
            enToggle(true);
            cnToggle(true);
          },
          error: function(datas) {
          }
        });
      }

      $("#deleteWord").click(function() {
        deleteWord();
      })

      function deleteWord(){
        $("#result").text("Delete word");

        var url = "/wordHelper/deleteWord";

        var enInput = $("#enInput").val();

        var jsonOutput = {
          en:enInput,
        };

  
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
            $("#result").text(datas.code + ", " + datas.message);
          },
          error: function(datas) {
          }
        });
      }

      $("#append").click(function() {
        updateOrAppendWord(false);
      });

      $("#update").click(function() {
        updateOrAppendWord(true);
      });

      function updateOrAppendWord(updateFlag){
        $("#result").text("Editing word");

        var url = "/wordHelper/updateOrAppendWord";

        var enInput = $("#enInput").val();
        var cnInput = $("#cnInput").val();

        var wordJson = {
          en:enInput,
          cn:cnInput,
        };

        var jsonOutput = {
          inputWord:wordJson,
          update:updateFlag,
        };

  
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
            $("#result").text(datas.code + ", " + datas.message);
          },
          error: function(datas) {
          }
        });
      }


      $("#printRandomWord").click(function() {
        printRandomWord();
      });

      function printRandomWord(){
        $("#result").text("Finding random words");
        $("#showEN").prop('checked', true);
        $("#showCN").prop('checked', false);
        $("#showEnInMark").prop('checked', false);

        var url = "/wordHelper/printRandomWords";

        var printWordsCounting = $("#printWordsCounting").val();

        var jsonOutput = {
          wordCount:printWordsCounting,
        };

  
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
            printWords(datas);
          },
          error: function(datas) {
          }
        });
      }

      $("#printNewWord").click(function() {
        printNewWord();
      });

      function printNewWord(){
        $("#result").text("Finding random words");
        $("#showEN").prop('checked', true);
        $("#showCN").prop('checked', false);
        $("#showEnInMark").prop('checked', false);

        var url = "/wordHelper/printNewWords";

        var printWordsCounting = $("#printWordsCounting").val();

        var jsonOutput = {
          wordCount:printWordsCounting,
        };

  
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
            printWords(datas);
          },
          error: function(datas) {
          }
        });
      }

      $("#printRandomWordsInMarks").click(function() {
        printRandomWordsInMarks();
      });

      function printRandomWordsInMarks(){
        $("#result").text("Finding random words");
        $("#showEN").prop('checked', false);
        $("#showCN").prop('checked', true);
        $("#showEnInMark").prop('checked', true);

        var url = "/wordHelper/printRandomWordsInMarks";

        var printWordsCounting = $("#printWordsCounting").val();

        var jsonOutput = {
          wordCount:printWordsCounting,
        };

  
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
            printWords(datas);
          },
          error: function(datas) {
          }
        });
      }

      $("#printNewWordsInMarks").click(function() {
        printNewWordsInMarks();
      });

      function printNewWordsInMarks(){
        $("#result").text("Finding random words");
        $("#showEN").prop('checked', false);
        $("#showCN").prop('checked', true);
        $("#showEnInMark").prop('checked', true);

        var url = "/wordHelper/printNewWordsInMarks";

        var printWordsCounting = $("#printWordsCounting").val();

        var jsonOutput = {
          wordCount:printWordsCounting,
        };

  
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
            printWords(datas);
          },
          error: function(datas) {
          }
        });
      }

      function printWords(datas){
        // <table class='table'>
        // </table>
        var printEn = $("#showEN").prop("checked");
        var printCn = $("#showCN").prop("checked");
        var printEnInMarks = $("#showEnInMark").prop("checked");
        var wordResult = "";
        var lineMaxLength = 33;
        var wordLengthCouting = 0;
        for(var i = 0; i < datas.wordList.length; i++){
          var word = datas.wordList[i];
          
          if(i == 0){
            wordResult = wordResult + "<table class='table'><tr>";
          }

          wordLengthCouting += word.en.length;

          wordResult = wordResult + "<td style='text-align: center;'>";
          wordResult = wordResult + "<label class='wordEn'>" + word.en + "</label>";
          wordResult = wordResult + "<label class='wordEnInMarks' style='font-size: 28px;'>" 
          wordResult = wordResult + word.enInMark + "</label>";
          wordResult = wordResult + "<label class='wordCn'>" + word.cn + "</label>";
          wordResult = wordResult + "</td>";

          if(wordLengthCouting > lineMaxLength) {
            wordResult = wordResult + "</tr></table><table class='table'><tr>";
            wordLengthCouting = 0;
            continue;
          }

          if(i == datas.wordList.length - 1){
            wordResult = wordResult + "</tr></table>";
            wordLengthCouting = 0;
            continue;
          }

          var nextWord = datas.wordList[i + 1];
          var longWordFlag = !(null === nextWord.en) && (nextWord.en.length + wordLengthCouting) > lineMaxLength;
          if(longWordFlag){
            wordResult = wordResult + "</tr></table><table class='table'><tr>";
            wordLengthCouting = 0;
            continue;
          }
        }

        $("#randomWords").html(wordResult);
        enToggle(printEn);
        cnToggle(printCn);
        enInMarkToggle(printEnInMarks);
      }

    });
  </script>

</html>