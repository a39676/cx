package demo.interaction.autoTest.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import autoTest.jsonReport.pojo.constant.AutoTestJsonReportKeyConstant;
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
import demo.interaction.autoTest.pojo.vo.AutoTestJsonReportLineVO;
import demo.interaction.autoTest.pojo.vo.AutoTestJsonReportVO;
import demo.interaction.autoTest.service.AutoTestDemoService;
import httpHandel.HttpUtil;
import net.sf.json.JSONArray;
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
		ModelAndView v = new ModelAndView("ATDemoJSP/atReportPost");
		
		if(dto.getTestEventId() == null || dto.getTestEventId() <= 0) {
			return v;
		}
		
		FindReportByTestEventIdResult responseResult = null;
		AutoTestJsonReportVO vo = null;
		try {
			JSONObject requestJson = JSONObject.fromObject(dto);
	        
			String url = ServerHost.host2 + JsonReportInteractionUrl.root + JsonReportInteractionUrl.findReportByTestEventId;
			String responseStr = String.valueOf(httpUtil.sendPostRestful(url, requestJson.toString()));
			
			JSONObject responseJson = JSONObject.fromObject(responseStr);
			
			responseResult = new ObjectMapper().readValue(responseJson.toString(), FindReportByTestEventIdResult.class);
			
			vo = buildReportVOByFindReportByTestEventIdResult(responseResult);
		} catch (Exception e) {
			e.printStackTrace();
			vo = buildErrorReportVO();
		}
		
		v.addObject("reportVO", vo);
		return v;
	}
	
	private AutoTestJsonReportVO buildErrorReportVO() {
		AutoTestJsonReportVO vo = new AutoTestJsonReportVO();
		List<AutoTestJsonReportLineVO> contentLines = new ArrayList<AutoTestJsonReportLineVO>();
		AutoTestJsonReportLineVO lineVO = null;
		lineVO = new AutoTestJsonReportLineVO();
		lineVO.setLineArrtibute(AutoTestJsonReportKeyConstant.strKey);
		lineVO.setContent("报告不存在, 可能是以下情况(测试任务未运行或运行中, 此报告已过期删除, 报告读取异常)");
		contentLines.add(lineVO);
		vo.setContentLines(contentLines);
		return vo;
	}

	private AutoTestJsonReportVO buildReportVOByFindReportByTestEventIdResult(FindReportByTestEventIdResult r) {
		if(r == null || StringUtils.isBlank(r.getReportStr())) {
			return buildErrorReportVO();
		}
		
		try {
			JSONArray ja = JSONArray.fromObject(r.getReportStr());
			AutoTestJsonReportVO vo = new AutoTestJsonReportVO();
			List<AutoTestJsonReportLineVO> contentLines = new ArrayList<AutoTestJsonReportLineVO>();
			AutoTestJsonReportLineVO lineVO = null;
			
			JSONObject j = null;
			String line = null;
			for(int i = 0; i < ja.size(); i++) {
				j = ja.getJSONObject(i);
				lineVO = new AutoTestJsonReportLineVO();
				line = j.getString(AutoTestJsonReportKeyConstant.strKey);
				if(StringUtils.isNotBlank(line)) {
					lineVO.setLineArrtibute(AutoTestJsonReportKeyConstant.strKey);
				} else {
					lineVO.setLineArrtibute(AutoTestJsonReportKeyConstant.imgKey);
				}
				lineVO.setContent(line);
				contentLines.add(lineVO);
			}
			
			vo.setContentLines(contentLines);
			
			return vo;
		} catch (Exception e) {
			return buildErrorReportVO();
		}
		
		
	}
}
