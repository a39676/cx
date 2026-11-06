package demo.tool.taobao.service.impl;

import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import demo.tool.taobao.service.TaobaoComplexService;

@Service
public class TaobaoComplexServiceImpl extends CommonService implements TaobaoComplexService {

//	@Autowired
//	private TimeBasedOneTimePassword totp;
//	@Autowired
//	private SystemOptionService systemOptionService;
//	@Autowired
//	private TelegramService telegramService;
//
//	@Override
//	public CommonResult forwardSkuMonitorMsg(TaobaoSkuMonitorMsgDTO dto) {
//		CommonResult r = new CommonResult();
//		if (!totp.isValid(systemOptionService.getTotpSecretKey(), dto.getCode())) {
//			return r;
//		}
//		telegramService.sendMessageByChatRecordId(TelegramBotType.NORMAL_MSG, dto.getMsg(), TelegramStaticChatID.MY_ID);
//		r.setIsSuccess();
//		return r;
//	}
}
