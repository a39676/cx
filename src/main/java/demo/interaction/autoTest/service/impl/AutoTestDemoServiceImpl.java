package demo.interaction.autoTest.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import autoTest.jsonReport.pojo.constant.AutoTestJsonReportKeyConstant;
import autoTest.jsonReport.pojo.constant.AutoTestInteractionUrl;
import autoTest.jsonReport.pojo.dto.FindReportByTestEventIdDTO;
import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import autoTest.jsonReport.pojo.result.FindReportByTestEventIdResult;
import autoTest.testEvent.pojo.constant.BingDemoUrl;
import autoTest.testEvent.pojo.dto.InsertBingDemoTestEventDTO;
import autoTest.testEvent.pojo.result.InsertBingDemoEventResult;
import auxiliaryCommon.pojo.constant.ServerHost;
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
		String dateNow = localDateTimeHandler.dateToStr(LocalDateTime.now(), DateTimeUtilCommon.normalDateFormat);
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
				j.put("createStartTime", localDateTimeHandler.dateToStr(dto.getCreateStartTime()));
			}
			if(dto.getCreateEndTime() != null) {
				j.put("createEndTime", localDateTimeHandler.dateToStr(dto.getCreateEndTime()));
			}
			if(dto.getRunTimeStartTime() != null) {
				j.put("runTimeStartTime", localDateTimeHandler.dateToStr(dto.getRunTimeStartTime()));
			}
			if(dto.getRunTimeEndTime() != null) {
				j.put("runTimeEndTime", localDateTimeHandler.dateToStr(dto.getRunTimeEndTime()));
			}
			if(dto.getEndTime() != null) {
				j.put("endTime", localDateTimeHandler.dateToStr(dto.getEndTime()));
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
	        
			String url = ServerHost.host2 + AutoTestInteractionUrl.root + AutoTestInteractionUrl.findReportsByCondition;
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
		AutoTestJsonReportVO vo = null;
		
		if(dto.getTestEventId() == null || dto.getTestEventId() <= 0) {
			vo = buildErrorReportVO();
			v.addObject("reportVO", vo);
			return v;
		}
		
		try {
			JSONObject requestJson = JSONObject.fromObject(dto);
	        
			String url = ServerHost.host2 + AutoTestInteractionUrl.root + AutoTestInteractionUrl.findReportByTestEventId;
			String responseStr = String.valueOf(httpUtil.sendPostRestful(url, requestJson.toString()));
			
			JSONObject responseJson = JSONObject.fromObject(responseStr);
			
			FindReportByTestEventIdResult responseResult = new FindReportByTestEventIdResult();
			responseResult.setId(responseJson.getLong("id"));
			responseResult.setCode(responseJson.getString("code"));
			responseResult.setTitle(responseJson.getString("title"));
			
			responseResult.setCreateTime(localDateTimeHandler.localDateTimeStrToLocalDateTime(responseJson.getString("createTime")));
			responseResult.setStartTime(localDateTimeHandler.localDateTimeStrToLocalDateTime(responseJson.getString("startTime")));
			responseResult.setEndTime(localDateTimeHandler.localDateTimeStrToLocalDateTime(responseJson.getString("endTime")));
			
			responseResult.setReportStr(responseJson.getString("reportStr"));
			
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

		AutoTestJsonReportVO vo = new AutoTestJsonReportVO();
		vo.setId(r.getId());
		vo.setTitle(r.getTitle());
		
		vo.setCreateTime(r.getCreateTime());
		vo.setStartTime(r.getStartTime());
		vo.setEndTime(r.getEndTime());
		
		vo.setCreateTimeStr(localDateTimeHandler.dateToStr(r.getCreateTime()));
		vo.setStartTimeStr(localDateTimeHandler.dateToStr(r.getStartTime()));
		vo.setEndTimeStr(localDateTimeHandler.dateToStr(r.getEndTime()));
		
		try {
			JSONArray ja = JSONArray.fromObject(r.getReportStr());
			List<AutoTestJsonReportLineVO> contentLines = new ArrayList<AutoTestJsonReportLineVO>();
			AutoTestJsonReportLineVO lineVO = null;
			
			JSONObject j = null;
			String line = null;
			for(int i = 0; i < ja.size(); i++) {
				j = ja.getJSONObject(i);
				lineVO = new AutoTestJsonReportLineVO();
				
				if(j.containsKey(AutoTestJsonReportKeyConstant.strKey)) {
					lineVO.setLineArrtibute(AutoTestJsonReportKeyConstant.strKey);
					line = j.getString(AutoTestJsonReportKeyConstant.strKey);
				} else {
					lineVO.setLineArrtibute(AutoTestJsonReportKeyConstant.imgKey);
					line = j.getString(AutoTestJsonReportKeyConstant.imgKey);
					line = line.replaceAll("upload/", "upload/c_scale,h_347/");
				}
				lineVO.setContent(line);
				contentLines.add(lineVO);
			}
			
			vo.setContentLines(contentLines);
			
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
			return buildErrorReportVO();
		}
		
		
	}

	@Override
	public InsertBingDemoEventResult insertBingDemoTestEvent(InsertBingDemoTestEventDTO dto, HttpServletRequest request) {
		InsertBingDemoEventResult r = new InsertBingDemoEventResult();
		
		int maxCountIn30Min = 6;
		int count = visitDataService.checkATDemoVisitData(request);
		if(count > maxCountIn30Min) {
			r.failWithMessage("30分钟内可加入6次任务, 请稍后再试");
			return r;
		}
		
		try {
			Map<String, String> m = new HashMap<String, String>();
			if(dto.getAppointment() != null) {
				m.put("appointment", localDateTimeHandler.dateToStr(dto.getAppointment()));
			}
			m.put("searchKeyWord", dto.getSearchKeyWord());
	        
			String url = ServerHost.host2 + BingDemoUrl.root + BingDemoUrl.insert;
			String response = String.valueOf(httpUtil.sendGet(url, m));

			JSONObject responseJson = JSONObject.fromObject(response);
			
			r.setCode(responseJson.getString("code"));
			r.setEventId(responseJson.getLong("eventId"));
			r.setMessage(responseJson.getString("message"));
			r.setResult(responseJson.getString("result"));
			r.setWaitingEventCount(responseJson.getInt("waitingEventCount"));
			if(r.getResult() != null && "0".equals(r.getResult())) {
				r.setIsSuccess();
				visitDataService.insertATDemoVisitData(request);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			r.failWithMessage("通讯异常, 请稍后再试, 或联系管理员");
		}
		
		return r;
	}

}
