package demo.ai.aiChat.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import ai.aiChat.pojo.result.AiChatDailySignUpResult;
import ai.aiChat.pojo.result.GetAiChatAmountResult;
import ai.aiChat.pojo.type.AiServiceAmountType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.aiArt.pojo.dto.AiUserDetailInJsonDTO;
import demo.ai.aiChat.pojo.dto.AiChatUserEditNicknameDTO;
import demo.ai.aiChat.pojo.dto.GetAiChatUserListDTO;
import demo.ai.aiChat.pojo.dto.NewPositiveAiChatUserDTO;
import demo.ai.aiChat.pojo.po.AiChatUserDetail;
import demo.ai.aiChat.pojo.result.CreateAiChatUserResult;
import demo.ai.aiChat.pojo.result.GetAiChatUserListResult;

public interface AiChatUserService {

	CreateAiChatUserResult createAiChatUserDetailBySystemUserId(Long systemUserId);

	CreateAiChatUserResult createAiChatUserDetailByWechatOpenId(Long wechatUserLongId, String wechatOpenId);

	Long createNewTmpKey(Long wechatUserId, String openId);

	void extendTmpKeyValidity(Long tmpKey);

	CommonResult recharge(Long aiChatUserId, AiServiceAmountType amountType, BigDecimal amount);

	Long __getAiChatUserIdByWechatUserId(Long wechatUserId);

	CommonResult batchRecharge(List<Long> aiChatUserIdList, AiServiceAmountType amountType, BigDecimal amount);

	GetAiChatAmountResult getAiChatAmount(String tmpKeyStr);

	AiChatDailySignUpResult dailySignUpFromWechat(String tmpKeyStr);

	Boolean hadDailySignUp(String tmpKeyStr);

	CreateAiChatUserResult createAiChatUserDetailByWechatOpenId(Long wechatUserId, String wechatOid,
			Integer specialBonus);

	List<NewPositiveAiChatUserDTO> __findNewPositiveAiChatUserDtoListInYesterday();

	GetAiChatUserListResult getAiChatUserList(GetAiChatUserListDTO dto);

	ModelAndView getAiChatUserListView();

	CommonResult blockUserByPk(String aiChatUserPk);

	CommonResult unlockUserByPk(String aiChatUserPk);

	CommonResult editNickname(AiChatUserEditNicknameDTO dto);

	CommonResult __giveUserWarningMark(Long aiChatUserId);

	CommonResult cleanUserWarningMark(String userPk);

	AiChatUserDetail __getUserDetail(Long aiUserId);

	CommonResult __debitAmountAndAddTokenUsage(Long aiUserId, BigDecimal debitAmount);

	AiUserDetailInJsonDTO getAiUserDetailInJson(Long userId);

	CommonResult refreshAiUserDetailInJson(AiUserDetailInJsonDTO dto);

	void tidyAiUserExtraDetail();

	void updateUsedTokenToDetailInJson();

}
