package demo.tool.bbtOrder.hsbc.service.impl;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testEvent.searchingDemo.pojo.dto.HsbcWechatPreregistDTO;
import autoTest.testModule.pojo.type.TestModuleType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.bbtOrder.hsbc.service.HsbcService;
import demo.tool.bbtOrder.service.BbtOrderCommonService;

@Service
public class HsbcServiceImpl extends BbtOrderCommonService implements HsbcService {

	@Override
	public ModelAndView hsbcWechatPreregistView() {
		return new ModelAndView("toolJSP/publicTool/HsbcWechatPreregist");
	}
	
	@Override
	public CommonResult hsbcWechatPreregist(HsbcWechatPreregistDTO dto) {
		CommonResult r = new CommonResult();
		
		if(StringUtils.isBlank(dto.getApkDownloadPassword())) {
			r.setMessage("password error");
			return r;
		}
		
		String correctPwd = "hsbc";
		int monthValue = LocalDate.now().getMonthValue();
		if(monthValue < 10) {
			correctPwd = correctPwd + "0" + monthValue;
		} else {
			correctPwd = correctPwd + monthValue;
		}
		
		if(!correctPwd.equals(dto.getApkDownloadPassword())) {
			r.setMessage("password error");
			return r;
		}
		
		if(StringUtils.isAnyBlank(dto.getIdNumber(), dto.getPhoneNumber())) {
			r.setMessage("Empty param");
			return r;
		}
		
		dto.setMainUrl(optionService.getHsbcWechatPreregistUrl());
		sendHsbcWechatPreregistTask(dto);
		
		r.setMessage("Task inserted");
		r.setIsSuccess();
		return r;
	}
	
	@Override
	public void sendHsbcWechatPreregistTask(HsbcWechatPreregistDTO paramDTO) {
		AutomationTestInsertEventDTO mainDTO = new AutomationTestInsertEventDTO();
		mainDTO.setTestModuleType(TestModuleType.SCHEDULE_CLAWING.getId());
		mainDTO.setFlowType(ScheduleClawingType.HSBC_WECHAT_PREREGIST.getId());
		mainDTO.setTestEventId(snowFlake.getNextId());
		
		mainDTO = automationTestInsertEventDtoAddParamStr(mainDTO, paramDTO);

		testEventInsertAckProducer.send(mainDTO);
	}
}
