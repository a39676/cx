package demo.toyParts.vcode.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.service.CommonService;
import demo.tool.telegram.service.TelegramService;
import demo.toyParts.vcode.mapper.VcodeHistoryMapper;
import demo.toyParts.vcode.mapper.VcodeMapper;
import demo.toyParts.vcode.pojo.po.Vcode;
import demo.toyParts.vcode.pojo.po.VcodeExample;
import demo.toyParts.vcode.pojo.po.VcodeHistory;
import demo.toyParts.vcode.service.VCodeService;
import telegram.pojo.constant.TelegramBotType;
import telegram.pojo.constant.TelegramStaticChatID;

@Service
public class VCodeServiceImpl extends CommonService implements VCodeService {

	@Autowired
	private TelegramService telegramService;

	@Autowired
	private VcodeMapper vCodeMapper;
	@Autowired
	private VcodeHistoryMapper historyMapper;

	@Override
	public Vcode findVCode(String vcode) {
		if (StringUtils.isBlank(vcode)) {
			return null;
		}

		VcodeExample example = new VcodeExample();
		example.createCriteria().andIsDeleteEqualTo(false).andValidTimeGreaterThan(LocalDateTime.now())
				.andCodeValueEqualTo(vcode);
		List<Vcode> poList = vCodeMapper.selectByExample(example);

		if (poList == null || poList.isEmpty()) {
			return null;
		}

		return poList.get(0);
	}

	@Override
	public CommonResult updateUseCount(Vcode vcode) {
		CommonResult result = new CommonResult();
		if (vcode == null || vcode.getCodeId() == null) {
			result.setMessage("Service error");
			return result;
		}

		if (vcode.getUseCount() == null) {
			vcode.setUseCount(1);
		} else {
			vcode.setUseCount(vcode.getUseCount() + 1);
		}

		vCodeMapper.updateByPrimaryKeySelective(vcode);

		VcodeHistory newHistory = new VcodeHistory();
		newHistory.setCodeId(vcode.getCodeId());
		newHistory.setVisitTime(LocalDateTime.now());
		historyMapper.insertSelective(newHistory);

		String msg = "Vcode visit: " + vcode.getCodeValue();
		telegramService.sendMessage(TelegramBotType.BOT_2, msg, TelegramStaticChatID.MY_ID);

		result.setIsSuccess();
		return result;
	}
}
