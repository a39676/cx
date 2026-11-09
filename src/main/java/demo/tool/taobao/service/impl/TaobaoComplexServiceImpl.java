package demo.tool.taobao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.service.CommonService;
import demo.tool.taobao.mapper.TaobaoProductSourceMapper;
import demo.tool.taobao.pojo.po.TaobaoProductSource;
import demo.tool.taobao.pojo.po.TaobaoProductSourceExample;
import demo.tool.taobao.service.TaobaoComplexService;

@Service
public class TaobaoComplexServiceImpl extends CommonService implements TaobaoComplexService {

//	@Autowired
//	private TimeBasedOneTimePassword totp;
//	@Autowired
//	private SystemOptionService systemOptionService;
//	@Autowired
//	private TelegramService telegramService;
	@Autowired
	private TaobaoProductSourceMapper productSourceMapper;
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

	@Override
	public CommonResult getSellingSourceProductId() {
		TaobaoProductSourceExample example = new TaobaoProductSourceExample();
		example.createCriteria().andIsDeleteEqualTo(false).andIsAvailableEqualTo(true);
		List<TaobaoProductSource> list = productSourceMapper.selectByExample(example);
		List<Long> sourceIdList = new ArrayList<>();
		for (TaobaoProductSource source : list) {
			sourceIdList.add(source.getSourceId());
		}
		CommonResult r = new CommonResult();
		r.setMessage(String.valueOf(sourceIdList));
		r.setIsSuccess();
		return r;
	}
}
