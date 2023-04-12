package demo.aiChat.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import aiChat.pojo.result.AiChatDailySignUpResult;
import aiChat.pojo.result.GetAiChatAmountResult;
import aiChat.pojo.type.AiChatAmountType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiChat.pojo.dto.AiChatUserEditNicknameDTO;
import demo.aiChat.pojo.dto.GetAiChatUserListDTO;
import demo.aiChat.pojo.dto.NewPositiveAiChatUserDTO;
import demo.aiChat.pojo.result.CreateAiChatUserResult;
import demo.aiChat.pojo.result.GetAiChatUserListResult;

public interface AiChatUserService {

	CreateAiChatUserResult createAiChatUserDetailBySystemUserId(Long systemUserId);

	CreateAiChatUserResult createAiChatUserDetailByWechatOpenId(Long wechatUserLongId, String wechatOpenId);

	Long createNewTmpKey(Long wechatUserId, String openId);

	void extendTmpKeyValidity(Long tmpKey);

	CommonResult recharge(Long aiChatUserId, AiChatAmountType amountType, BigDecimal amount);

	Long __getAiChatUserIdByWechatUserId(Long wechatUserId);

	CommonResult batchRecharge(List<Long> aiChatUserIdList, AiChatAmountType amountType, BigDecimal amount);

	GetAiChatAmountResult getAiChatAmount(String tmpKeyStr);

	AiChatDailySignUpResult dailySignUpFromWechat(String tmpKeyStr);

	Boolean hadDailySignUp(String tmpKeyStr);

	CommonResult blockUser(String aiChatUserIdStr);

	CommonResult unlockUser(String aiChatUserIdStr);

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

}
