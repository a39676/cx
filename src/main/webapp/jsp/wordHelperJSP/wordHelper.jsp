<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

  <head>
    <title>${ title }AI art image wall manager</title>
    <%@ include file="../baseElementJSP/normalHeader.jsp" %>
    <%@ include file="../baseElementJSP/normalJSPart.jsp" %>
  </head>

  <div class="container-fluid">
    <div class="row">
      <div class="col-lg-12">

        <input type="text" id="enInput" name="">
        <input type="text" id="cnInput" name="">

        <button id="addWord">Add word</button>
        <button id="deleteWord">Delete word</button>
      </div>
    </div>

    <div class="row">
      <div class="col-lg-12">
        <button id="append">Append</button>
        <button id="update">Update</button>
      </div>
    </div>

    <div class="row">
      <div class="col-lg-12">
        <input type="number" id="randomWordsCounting" name="" value="10">
        <button id="printRandomWord">Print random word</button><br>
        <div class="form-check">
          <input class="form-check-input" type="checkbox" value="" id="showEN" checked>
          <label class="form-check-label" for="showEN">
            showEN
          </label>
        </div>

        <div class="form-check">
          <input class="form-check-input" type="checkbox" value="" id="showCN">
          <label class="form-check-label" for="showCN">
            showCN
          </label>
        </div>

        <label id="randomWords"></label>
      </div>
    </div>

    <div class="row">
      <div class="col-lg-12">
        <label>Result: </label>
        <label id="result"></label>
      </div>
    </div>

  </div>

  <script type="text/javascript">
    
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
            console.log(datas);
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
            console.log(datas);
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
            console.log(datas);
          }
        });
      }


      $("#printRandomWord").click(function() {
        printRandomWord();
      });

      function printRandomWord(){
        $("#result").text("Finding random words");

        var url = "/wordHelper/printRandomWords";

        var randomWordsCounting = $("#randomWordsCounting").val();
        var printEn = $("#showEN").prop("checked");;
        var printCn = $("#showCN").prop("checked");;

        var jsonOutput = {
          printEn:printEn,
          printCn:printCn,
          wordCount:randomWordsCounting,
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
            var wordResult = "";
            for(var i = 0; i < datas.wordList.length; i++){
              wordResult = wordResult + "<br>";
              if(printEn){
                wordResult = wordResult + datas.wordList[i].en;
              }
              if(printCn){
                wordResult = wordResult + ": " + datas.wordList[i].cn;
              }
            }
            $("#randomWords").html(wordResult);
          },
          error: function(datas) {
            console.log(datas);
          }
        });
      }

    });
  </script>

</html>