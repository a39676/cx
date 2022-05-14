package demo.tool.bbtOrder.hsbc.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testEvent.searchingDemo.pojo.dto.HsbcWechatPreregistDTO;
import autoTest.testEvent.searchingDemo.pojo.type.HsbcIdType;
import autoTest.testModule.pojo.type.TestModuleType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.bbtOrder.hsbc.service.HsbcService;
import demo.tool.bbtOrder.service.BbtOrderCommonService;
import tool.pojo.type.InternationalityType;

@Service
public class HsbcServiceImpl extends BbtOrderCommonService implements HsbcService {
	
	private List<LocalDateTime> hsbcWechatPreregistTaskInsertTime = new ArrayList<>();

	@Override
	public ModelAndView hsbcWechatPreregistView() {
		ModelAndView view = new ModelAndView("toolJSP/publicTool/HsbcWechatPreregist");
		view.addObject("idTypeList", HsbcIdType.values());
		view.addObject("internationalityTypeList", InternationalityType.values());
		return view;
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
		
		if(!canInsertPreregistTask()) {
			r.setMessage("Too much tasks, please insert later");
			return r;
		}
		
		dto.setMainUrl(optionService.getHsbcWechatPreregistUrl());
		sendHsbcWechatPreregistTask(dto);
		hsbcWechatPreregistTaskInsertTime.add(LocalDateTime.now());
		
		r.setMessage("Task inserted");
		r.setIsSuccess();
		return r;
	}
	
	private boolean canInsertPreregistTask() {
		if(systemOptionService.isDev()) {
			return true;
		}
		
		List<LocalDateTime> list = hsbcWechatPreregistTaskInsertTime;
		if(list == null) {
			hsbcWechatPreregistTaskInsertTime = new ArrayList<>();
			return true;
		}
		
		if(list.isEmpty()) {
			return true;
		}
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).isBefore(LocalDateTime.now().minusMinutes(10))) {
				list.remove(i);
				i--;
			}
		}
			
		hsbcWechatPreregistTaskInsertTime = list;
		return !(list.size() > 3);
	}
	
	@Override
	public void sendHsbcWechatPreregistTask(HsbcWechatPreregistDTO paramDTO) {
		AutomationTestInsertEventDTO mainDTO = new AutomationTestInsertEventDTO();
		mainDTO.setTestModuleType(TestModuleType.SCHEDULE_CLAWING.getId());
		mainDTO.setFlowType(ScheduleClawingType.HSBC_WECHAT_PREREGIST.getId());
		mainDTO.setFlowTypeName(ScheduleClawingType.HSBC_WECHAT_PREREGIST.getFlowName());
		mainDTO.setTestEventId(snowFlake.getNextId());
		
		mainDTO = automationTestInsertEventDtoAddParamStr(mainDTO, paramDTO);
		
		eventService.insertEvent(mainDTO);

		testEventInsertAckProducer.send(mainDTO);
	}
}
