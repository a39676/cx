package demo.toyParts.vcode.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.service.CommonService;
import demo.tool.telegram.service.TelegramService;
import demo.toyParts.vcode.mapper.VCodeMapper;
import demo.toyParts.vcode.pojo.param.DeleteInvalidCodeParam;
import demo.toyParts.vcode.pojo.param.UpdateDeleteMarkParam;
import demo.toyParts.vcode.pojo.param.type.VCodeType;
import demo.toyParts.vcode.pojo.po.VCode;
import demo.toyParts.vcode.service.VCodeService;
import telegram.pojo.constant.TelegramBotType;
import telegram.pojo.constant.TelegramStaticChatID;

@Service
public class VCodeServiceImpl extends CommonService implements VCodeService {
	
	@Autowired
	private TelegramService telegramService;
	
	@Autowired
	private VCodeMapper vCodeMapper;
	
	@Override
	public VCode findVCode(String vcode) {
		if(StringUtils.isBlank(vcode)) {
			return null;
		}
		
		return vCodeMapper.getVCodeByValue(vcode);
	}
	
	@Override
	public int updateDeleteMark(UpdateDeleteMarkParam param) {
		if(param == null) {
			return 0;
		}
		return vCodeMapper.updateDeleteMark(param);
	}
	
	@Override
	public int deleteInvalidCode(DeleteInvalidCodeParam param) {
		if(param == null) {
			return 0;
		}
		return vCodeMapper.deleteInvalidCode(param);
	}

	@Override
	public CommonResult insertVcode(VCode newVCode) {
		CommonResult result = new CommonResult();
		if(StringUtils.isBlank(newVCode.getCodeValue())
				|| newVCode.getValidTime().getTime() <= System.currentTimeMillis()
				|| newVCode.getCodeType() == null
				|| VCodeType.getType(newVCode.getCodeType()) == null) {
			result.setMessage("Service error");
			return result;
		}
		
		if(newVCode.getValidTime() == null) {
			newVCode.setValidTime(dateHandler.dateDiffDays(3));
		}
		if(newVCode.getCreateTime() == null) {
			newVCode.setCreateTime(new Date());
		}
		newVCode.setIsDelete(false);
		
		int insertCount = vCodeMapper.insertSelective(newVCode);
		if(insertCount == 1) {
			result.setIsSuccess();
			return result;
		} else {
			result.setMessage("Service error");
			return result;
		}
	}
	
	@Override
	public CommonResult updateUseCount(VCode vcode) {
		CommonResult result = new CommonResult();
		if(vcode == null || vcode.getCodeId() == null) {
			result.setMessage("Service error");
			return result;
		}
		
		vCodeMapper.updateUseCount(vcode.getCodeId());
		
		String msg = "Vcode visit: " + vcode;
		
		telegramService.sendMessage(TelegramBotType.BOT_2, msg, TelegramStaticChatID.MY_ID);
		
		result.setIsSuccess();
		return result;
	}
}
