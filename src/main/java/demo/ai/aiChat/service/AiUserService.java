package demo.ai.aiChat.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import ai.aiChat.pojo.result.AiChatDailySignUpResult;
import ai.aiChat.pojo.result.GetAiChatAmountResult;
import ai.aiChat.pojo.type.AiServiceAmountType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.aiArt.pojo.dto.AiUserDetailInJsonDTO;
import demo.ai.aiChat.pojo.dto.NewPositiveAiUserDTO;
import demo.ai.aiChat.pojo.po.AiChatUserDetail;
import demo.ai.aiChat.pojo.result.CreateAiUserResult;
import demo.ai.manager.pojo.dto.AiChatUserEditNicknameDTO;

public interface AiUserService {

	CreateAiUserResult createAiChatUserDetailBySystemUserId(Long systemUserId);

	CreateAiUserResult createAiChatUserDetailByWechatOpenId(Long wechatUserLongId, String wechatOpenId);

	Long createNewTmpKey(Long wechatUserId, String openId);

	void extendTmpKeyValidity(Long tmpKey);

	CommonResult recharge(Long aiChatUserId, AiServiceAmountType amountType, BigDecimal amount);

	Long __getAiChatUserIdByWechatUserId(Long wechatUserId);

	CommonResult batchRecharge(List<Long> aiChatUserIdList, AiServiceAmountType amountType, BigDecimal amount);

	GetAiChatAmountResult getAiChatAmount(String tmpKeyStr);

	AiChatDailySignUpResult dailySignUpFromWechat(String tmpKeyStr);

	Boolean hadDailySignUp(String tmpKeyStr);

	CreateAiUserResult createAiChatUserDetailByWechatOpenId(Long wechatUserId, String wechatOid,
			Integer specialBonus);

	List<NewPositiveAiUserDTO> __findNewPositiveAiChatUserDtoListInYesterday();

	ModelAndView getAiChatUserListView();

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
