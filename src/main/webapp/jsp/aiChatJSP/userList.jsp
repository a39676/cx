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
          <tbody id="searchCondition">
            <tr>
              <td>
                <label>createTimeMinStr</label>
                <input type="text" name="" id="createTimeMinStrInput" placeholder="createTimeMinStr">
              </td>
              <td>
                <label>createTimeMaxStr</label>
                <input type="text" name="" id="createTimeMaxStrInput" placeholder="createTimeMaxStr">
              </td>
              <td>
                <label>bonusAmountMin</label>
                <input type="text" name="" id="bonusAmountMinInput" placeholder="bonusAmountMin">
              </td>
              <td>
                <label>bonusAmountMax</label>
                <input type="text" name="" id="bonusAmountMaxInput" placeholder="bonusAmountMax">
              </td>
              <td>
                <label>rechargeAmountMin</label>
                <input type="text" name="" id="rechargeAmountMinInput" placeholder="rechargeAmountMin">
              </td>
              <td>
                <label>rechargeAmountMax</label>
                <input type="text" name="" id="rechargeAmountMaxInput" placeholder="rechargeAmountMax">
              </td>
            </tr>
            <tr>
              <td>
                <label>usedTokensMin</label>
                <input type="text" name="" id="usedTokensMinInput" placeholder="usedTokensMin">
              </td>
              <td>
                <label>usedTokensMax</label>
                <input type="text" name="" id="usedTokensMaxInput" placeholder="usedTokensMax">
              </td>
              <td>
                <label>isDelete</label>
                <div class="form-check">
                  <input value="true" class="form-check-input" type="radio" name="isDeleteRadio" id="isDelete">
                  <label class="form-check-label" for="isDelete">
                    isDelete
                  </label>
                </div>
                <div class="form-check">
                  <input value="false" class="form-check-input" type="radio" name="isDeleteRadio" id="isNotDelete" checked>
                  <label class="form-check-label" for="isNotDelete">
                    isNotDelete
                  </label>
                </div>
              </td>
              <td>
                <label>isBlock</label>
                <div class="form-check">
                  <input value="true" class="form-check-input" type="radio" name="isBlockRadio" id="isBlock">
                  <label class="form-check-label" for="isBlock">
                    isBlock
                  </label>
                </div>
                <div class="form-check">
                  <input value="false" class="form-check-input" type="radio" name="isBlockRadio" id="isNotBlock" checked>
                  <label class="form-check-label" for="isNotBlock">
                    isNotBlock
                  </label>
                </div>
              </td>
              <td>
                <label>aesc</label>
                <div class="form-check">
                  <input value="true" class="form-check-input" type="radio" name="isAescRadio" id="isAesc">
                  <label class="form-check-label" for="isAesc">
                    isAesc
                  </label>
                </div>
                <div class="form-check">
                  <input value="false" class="form-check-input" type="radio" name="isAescRadio" id="isDesc" checked>
                  <label class="form-check-label" for="isDesc">
                    isDesc
                  </label>
                </div>
              </td>
              <td>
                <label>nickname</label>
                <input type="text" name="" id="nicknameInput" placeholder="nickname">
              </td>
            </tr>
            <tr>
              <td>
                <label>lastUpdateTimeMinStr</label>
                <input type="text" name="" id="lastUpdateTimeMinStrInput" placeholder="lastUpdateTimeMinStr">
              </td>
              <td>
                <label>lastUpdateTimeMaxStr</label>
                <input type="text" name="" id="lastUpdateTimeMaxStrInput" placeholder="lastUpdateTimeMaxStr">
              </td>
              <td>
                <label>Source QR code</label>
                <select id="sourceQrCodeSelector">
                  <option value=""></option>
                  <c:forEach items="${qrCodeList}" var="qrCode" varStatus="status">
                    <option value="${qrCode.pk}">${qrCode.sceneName},${qrCode.remark}</option>
                  </c:forEach>
                </select>
              </td>
              <td>
                <label>orderBy</label>
                <select id="orderBySelector">
                  <option value=""></option>
                  <option value="id">id</option>
                  <option value="nickname">nickname</option>
                  <option value="bonus_amount">bonus_amount</option>
                  <option value="recharge_amount">recharge_amount</option>
                  <option value="used_tokens">used_tokens</option>
                  <option value="last_update">last_update</option>
                  <option value="create_time">create_time</option>
                  <option value="is_delete">is_delete</option>
                  <option value="is_block">is_block</option>
                </select>
              </td>
              <td>
                <label>limit</label>
                <input type="text" name="" id="limitInput" placeholder="limit">
              </td>
            </tr>
            <tr>
              <td>
                <button class="btn btn-primary" id="serach">Search</button>
                <button class="btn btn-primary" id="clear">Clear</button>
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
              <td>nickname</td>
              <td>Chat history</td>
              <td>bonusAmount</td>
              <td>rechargeAmount</td>
              <td>usedTokens</td>
              <td>lastUpdateTime</td>
              <td>createTime</td>
              <td>isDelete</td>
              <td>isBlock</td>
            </tr>
          </thead>
          <tbody id="userList" lastUserPk="">
            
          </tbody>
        </table>
      </div>
    </div>
  
  </div>

  <script type="text/javascript">
    $(document).ready(function() {

      getAiChatUserList();

      $("#serach").click(function() {
        getAiChatUserList();
      });

      $("#clear").click(function() {
        $("#userList").html("");
        $("#userList").attr("lastUserPk", "");
        $("#createTimeMinStrInput").val("");
        $("#createTimeMaxStrInput").val("");
        $("#lastUpdateTimeMinStrInput").val("");
        $("#lastUpdateTimeMaxStrInput").val("");
        $("#bonusAmountMinInput").val("");
        $("#bonusAmountMaxInput").val("");
        $("#rechargeAmountMinInput").val("");
        $("#rechargeAmountMaxInput").val("");
        $("#usedTokensMinInput").val("");
        $("#usedTokensMaxInput").val("");
        $("input[name='isDeleteRadio']").filter('[value=false]').prop('checked', true);
        $("input[name='isBlockRadio']").filter('[value=false]').prop('checked', true);
        $("input[name='isAescRadio']").filter('[value=false]').prop('checked', true);
        $("#nicknameInput").val("");
        $("#sourceQrCodeSelector").val("");
        $("#orderBySelector").val("");
        $("#limitInput").val("");
      });

      $("#searchCondition").change(function() {
        $("#userList").attr("lastUserPk", "");
      }).change();

      function getAiChatUserList() {
        $("#userList").html("");

        var url = "/aiChatManager/userList";

        var lastUserPk = $("#userList").attr("lastUserPk");
        var createTimeMinStr = $("#createTimeMinStrInput").val();
        var createTimeMaxStr = $("#createTimeMaxStrInput").val();
        var lastUpdateTimeMinStr = $("#lastUpdateTimeMinStrInput").val();
        var lastUpdateTimeMaxStr = $("#lastUpdateTimeMaxStrInput").val();
        var bonusAmountMin = $("#bonusAmountMinInput").val();
        var bonusAmountMax = $("#bonusAmountMaxInput").val();
        var rechargeAmountMin = $("#rechargeAmountMinInput").val();
        var rechargeAmountMax = $("#rechargeAmountMaxInput").val();
        var usedTokensMin = $("#usedTokensMinInput").val();
        var usedTokensMax = $("#usedTokensMaxInput").val();
        var isDelete = $("input[name='isDeleteRadio']:checked").val();
        var isBlock = $("input[name='isBlockRadio']:checked").val();
        var isAesc = $("input[name='isAescRadio']:checked").val();
        var nickname = $("#nicknameInput").val();
        var sourceQrCode = $("#sourceQrCodeSelector").val();
        var orderBy = $("#orderBySelector").val();
        var limit = $("#limitInput").val();

  
        var jsonOutput = {
          createTimeMinStr:createTimeMinStr,
          createTimeMaxStr:createTimeMaxStr,
          lastUpdateTimeMinStr:lastUpdateTimeMinStr,
          lastUpdateTimeMaxStr:lastUpdateTimeMaxStr,
          bonusAmountMin:bonusAmountMin,
          bonusAmountMax:bonusAmountMax,
          rechargeAmountMin:rechargeAmountMin,
          rechargeAmountMax:rechargeAmountMax,
          usedTokensMin:usedTokensMin,
          usedTokensMax:usedTokensMax,
          isDelete:isDelete,
          isBlock:isBlock,
          isAesc:isAesc,
          nickname:nickname,
          sourceQrCodePk: sourceQrCode,
          orderBy:orderBy,
          limit:limit,
        };

        if(lastUserPk){
          jsonOutput.startPk = lastUserPk;
        }
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
            for(i=0;i<datas.userList.length;i++){
              appendUserTr(datas.userList[i]);
            }
          },
          error: function(datas) {
  
          }
        });
      }

      function appendUserTr(userVO){
        encodeURIComponent
        var tr = "";
        tr += "<tr name='userInfo' userPk='"+userVO.userPk+"' wechatUserPk='"+userVO.wechatUserPk+"'>";
        tr += "<td><input type='text' name='nicknameEdit' userPk='"+userVO.userPk+"' value='"+userVO.nickname+"'> <br> <button class='btn btn-sm btn-primary' name='editNickname' userPk='"+userVO.userPk+"'>editNickname</button><br> <label name='nicknameEditResult' userPk='"+userVO.userPk+"'></label> </td>";
        tr += "<td><a href='/aiChatManager/checkChatHistoryByPk?aiChatUserPk="+encodeURIComponent(userVO.userPk)+"' target='_blank'>chatHistory</a></td>";
        tr += "<td>"+userVO.bonusAmount+"</td>";
        tr += "<td>"+userVO.rechargeAmount+"</td>";
        tr += "<td>"+userVO.usedTokens+"</td>";
        tr += "<td>"+userVO.lastUpdateTime+"</td>";
        tr += "<td>"+userVO.createTime+"</td>";
        tr += "<td>"+userVO.isDelete+"<br> </td>";
        tr += "<td> <label name='blockStatus' userPk='"+userVO.userPk+"'>"+userVO.isBlock+"</label><br> <button class='btn btn-sm btn-success' name='unlockUser' userPk='"+userVO.userPk+"'>Unlock</button> <button class='btn btn-sm btn-danger' name='blockUser' userPk='"+userVO.userPk+"'>Block</button></td>";
        tr += "</tr>";

        var userList = $("#userList");
        userList.append(tr);
        userList.attr("lastUserPk", userVO.userPk);

        $("button[name='blockUser'][userPk='"+userVO.userPk+"']").bind("click", function() {
          blockUser(userVO.userPk);
        });
        $("button[name='unlockUser'][userPk='"+userVO.userPk+"']").bind("click", function() {
          unlockUser(userVO.userPk);
        });
        $("button[name='editNickname'][userPk='"+userVO.userPk+"']").bind("click", function() {
          editNickname(userVO.userPk);
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
              $("label[name='blockStatus'][userPk='"+userPk+"']").text("true");
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
              $("label[name='blockStatus'][userPk='"+userPk+"']").text("false");
            }
          },
          error: function(datas) {
            
          }
        });
      }

      function editNickname(userPk){
        var url = "/aiChatManager/editNickname";

        var nickname = $("input[name='nicknameEdit'][userPk='"+userPk+"']").val();

        var jsonOutput = {
          userPk:userPk,
          nickname:nickname,
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
            $("label[name='nicknameEditResult'][userPk='"+userPk+"']").text(datas.code);
          },
          error: function(datas) {
            
          }
        });
      }

    });
  </script>

</html>