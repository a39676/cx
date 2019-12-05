package demo.interaction.autoTest.service.impl;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import autoTest.jsonReport.pojo.constant.JsonReportInteractionUrl;
import autoTest.jsonReport.pojo.dto.FindReportByTestEventIdDTO;
import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import autoTest.jsonReport.pojo.result.FindReportByTestEventIdResult;
import auxiliaryCommon.pojo.constant.ServerHost;
import dateTimeHandle.DateTimeHandle;
import dateTimeHandle.DateTimeUtilCommon;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.system.service.impl.SystemConstantService;
import demo.baseCommon.service.CommonService;
import demo.interaction.autoTest.service.AutoTestDemoService;
import httpHandel.HttpUtil;
import net.sf.json.JSONObject;

@Service
public class AutoTestDemoServiceImpl extends CommonService implements AutoTestDemoService {

	@Autowired
	private HttpUtil httpUtil;
	
	@Autowired
	private SystemConstantService systemConstantService;
	
	@Override
	public ModelAndView linkToATHome(HttpServletRequest request) {
		String hostName = findHostNameFromRequst(request);
		
		if (hostName.contains(systemConstantService.getValByName(SystemConstantStore.hostName2))) {
			return new ModelAndView("ATDemoJSP/atDemoLink");
		}

		String envName = systemConstantService.getValByName(SystemConstantStore.envName, true);
		if("dev".equals(envName)) {
			return new ModelAndView("ATDemoJSP/atDemoLink");
		}
		
		return null;
	}
	
	@Override
	public ModelAndView index() {
		ModelAndView v = new ModelAndView("ATDemoJSP/atDemoIndex");
		v.addObject("title", "自动化测试");
		String dateNow = DateTimeHandle.dateToStr(LocalDateTime.now(), DateTimeUtilCommon.normalDateFormat);
		v.addObject("createEndTime", dateNow);
		v.addObject("runTimeEndTime", dateNow);
		return v;
	}
	
	@Override
	public String findReportsByCondition(FindTestEventPageByConditionDTO dto) {
		/*
		 * 2019-12-04 
		 * 目前只为 bing 搜索作为样例展示, 估 hard code 此id
		 */
//		ATDemo(3L, "ATDemo")
		dto.setModuleId(3L);
//		bingDemo(1L, "bingDemo")
		dto.setCaseId(1L);
		try {
			JSONObject j = new JSONObject();
			if(dto.getCreateStartTime() != null) {
				j.put("createStartTime", DateTimeHandle.dateToStr(dto.getCreateStartTime()));
			}
			if(dto.getCreateEndTime() != null) {
				j.put("createEndTime", DateTimeHandle.dateToStr(dto.getCreateEndTime()));
			}
			if(dto.getRunTimeStartTime() != null) {
				j.put("runTimeStartTime", DateTimeHandle.dateToStr(dto.getRunTimeStartTime()));
			}
			if(dto.getRunTimeEndTime() != null) {
				j.put("runTimeEndTime", DateTimeHandle.dateToStr(dto.getRunTimeEndTime()));
			}
			if(dto.getEndTime() != null) {
				j.put("endTime", DateTimeHandle.dateToStr(dto.getEndTime()));
			}
			if(StringUtils.isNotBlank(dto.getEventName())) {
				j.put("eventName", dto.getEventName());
			}
			if(StringUtils.isNotBlank(dto.getReportPath())) {
				j.put("reportPath", dto.getReportPath());
			}
			if(dto.getModuleId() != null) {
				j.put("moduleId", dto.getModuleId());
			}
			if(dto.getId() != null) {
				j.put("id", dto.getId());
			}
			if(dto.getCaseId() != null) {
				j.put("caseId", dto.getCaseId());
			}
			if(dto.getLimit() != null) {
				j.put("limit", dto.getLimit());
			}
	        
			String url = ServerHost.host2 + JsonReportInteractionUrl.root + JsonReportInteractionUrl.findReportsByCondition;
			String response = String.valueOf(httpUtil.sendPostRestful(url, j.toString()));
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ModelAndView findReportByTestEventId(FindReportByTestEventIdDTO dto) {
//		TODO
//		未有报告页面
		ModelAndView v = new ModelAndView("");
		
		if(dto.getTestEventId() == null || dto.getTestEventId() <= 0) {
			return v;
		}
		
		try {
			JSONObject requestJson = JSONObject.fromObject(dto);
	        
			String url = ServerHost.host2 + JsonReportInteractionUrl.root + JsonReportInteractionUrl.findReportByTestEventId;
			String responseStr = String.valueOf(httpUtil.sendPostRestful(url, requestJson.toString()));
			
			JSONObject responseJson = JSONObject.fromObject(responseStr);
			
			FindReportByTestEventIdResult responseResult = new ObjectMapper().readValue(responseJson.toString(), FindReportByTestEventIdResult.class);
			
			v.addObject("reportVO", responseResult);
//			TODO
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return v;
	}
}
