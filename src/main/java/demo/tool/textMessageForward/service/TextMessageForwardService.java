package demo.tool.textMessageForward.service;

import auxiliaryCommon.pojo.dto.ServiceMsgDTO;
import auxiliaryCommon.pojo.result.CommonResult;

public interface TextMessageForwardService {

	CommonResult textMessageForwarding(ServiceMsgDTO dto);

}
