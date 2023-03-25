package demo.aiChat.service;

import java.math.BigDecimal;
import java.util.List;

import aiChat.pojo.result.GetAiChatAmountResult;
import aiChat.pojo.type.AiChatAmountType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiChat.pojo.result.CreateAiChatUserResult;

public interface AiChatUserService {

	CreateAiChatUserResult createAiChatUserDetailBySystemUserId(Long systemUserId);

	CreateAiChatUserResult createAiChatUserDetailByWechatUid(Long wechatUserLongId, String wechatOid);

	Long createNewTmpKey(Long wechatUserId, String openId);

	void extendTmpKeyValidity(Long tmpKey);

	CommonResult recharge(Long aiChatUserId, AiChatAmountType amountType, BigDecimal amount);

	Long __getAiChatUserIdByWechatUserId(Long wechatUserId);

	CommonResult batchRecharge(List<Long> aiChatUserIdList, AiChatAmountType amountType, BigDecimal amount);

	GetAiChatAmountResult getAiChatAmount(String tmpKeyStr);

}
