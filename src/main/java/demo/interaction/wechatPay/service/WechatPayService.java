package demo.interaction.wechatPay.service;

import auxiliaryCommon.pojo.dto.EncryptDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import wechatPayApi.jsApi.pojo.dto.WechatPayJsApiFeedbackDTO;

public interface WechatPayService {

	EncryptDTO getWechatPayOptionDTO(EncryptDTO encrypt);

	EncryptDTO getJsApiTicket(EncryptDTO encryptDTO);

	EncryptDTO updateJsApiTicket(EncryptDTO encryptedDTO);

	CommonResult _insertPayHistoryBeforeAiChatHandle(WechatPayJsApiFeedbackDTO dto);

	boolean hadHandleSuccess(Long orderId);

	CommonResult updateHandleStatus(Long orderId, boolean flag);

}
