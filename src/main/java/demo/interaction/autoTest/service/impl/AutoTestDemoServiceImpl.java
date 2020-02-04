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

import autoTest.jsonReport.pojo.constant.AutoTestInteractionUrl;
import autoTest.jsonReport.pojo.constant.AutoTestJsonReportKeyConstant;
import autoTest.jsonReport.pojo.dto.FindReportByTestEventIdDTO;
import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import autoTest.jsonReport.pojo.result.FindReportByTestEventIdResult;
import autoTest.testEvent.pojo.constant.SearchingDemoConstant;
import autoTest.testEvent.pojo.constant.SearchingDemoUrl;
import autoTest.testEvent.pojo.dto.InsertSearchingDemoTestEventDTO;
import autoTest.testEvent.pojo.result.InsertSearchingDemoEventResult;
import autoTest.testModule.pojo.type.TestModuleType;
import auxiliaryCommon.pojo.constant.ServerHost;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.system.pojo.constant.SystemRedisKey;
import demo.base.system.pojo.result.HostnameType;
import demo.base.system.service.ExceptionService;
import demo.base.system.service.HostnameService;
import demo.baseCommon.service.CommonService;
import demo.interaction.autoTest.pojo.vo.AutoTestJsonReportLineVO;
import demo.interaction.autoTest.pojo.vo.AutoTestJsonReportVO;
import demo.interaction.autoTest.service.AutoTestDemoService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import toolPack.dateTimeHandle.DateTimeUtilCommon;
import toolPack.httpHandel.HttpUtil;

@Service
public class AutoTestDemoServiceImpl extends CommonService implements AutoTestDemoService {

	@Autowired
	private HttpUtil httpUtil;
	@Autowired
	private HostnameService hostnameService;
	@Autowired
	protected ExceptionService exceptionService;

	@Override
	public ModelAndView linkToATHome(HttpServletRequest request) {
		if(isInSeekOrDev(request)) {
			return new ModelAndView("ATDemoJSP/atDemoLink");
		}

		return null;
	}

	@Override
	public ModelAndView index(HttpServletRequest request) {

		if(!isInSeekOrDev(request)) {
			return exceptionService.handle404Exception(request);
		}

		ModelAndView v = new ModelAndView("ATDemoJSP/atDemoIndex");
		v.addObject("title", "自动化测试");
		String dateNow = localDateTimeHandler.dateToStr(LocalDateTime.now(), DateTimeUtilCommon.normalDateFormat);
		v.addObject("createEndTime", dateNow);
		v.addObject("runTimeEndTime", dateNow);
		if (baseUtilCustom.hasAdminRole()) {
			Map<Long, String> modules = new HashMap<Long, String>();
			for (TestModuleType i : TestModuleType.values()) {
				modules.put(i.getId(), i.getEventName());
			}
			v.addObject("modules", modules);
		}
		v.addObject("defaultStartTime", localDateTimeHandler.dateToStr(LocalDateTime.now().withMonth(1).withDayOfMonth(1), DateTimeUtilCommon.normalDateFormat));

		return v;
	}

	@Override
	public String findReportsByCondition(HttpServletRequest request, FindTestEventPageByConditionDTO dto) {
		
		if(!isInSeekOrDev(request)) {
			return null;
		}
		
		if (!baseUtilCustom.hasAdminRole()) {
			dto.setModuleId(TestModuleType.ATDemo.getId());
		}
		
		
		if(dto.getEndTime() != null) {
			if(dto.getCreateEndTime() != null) {
				if(dto.getEndTime().isBefore(dto.getCreateEndTime())) {
					dto.setCreateEndTime(dto.getEndTime());
				}
			} else {
				dto.setCreateEndTime(dto.getEndTime());
			}
			dto.setEndTime(null);
		}
		
		try {
			JSONObject j = new JSONObject();
			if (dto.getCreateStartTime() != null) {
				j.put("createStartTime", localDateTimeHandler.dateToStr(dto.getCreateStartTime()));
			}
			if (dto.getCreateEndTime() != null) {
				j.put("createEndTime", localDateTimeHandler.dateToStr(dto.getCreateEndTime()));
			}
			if (dto.getRunTimeStartTime() != null) {
				j.put("runTimeStartTime", localDateTimeHandler.dateToStr(dto.getRunTimeStartTime()));
			}
			if (dto.getRunTimeEndTime() != null) {
				j.put("runTimeEndTime", localDateTimeHandler.dateToStr(dto.getRunTimeEndTime()));
			}
			if (StringUtils.isNotBlank(dto.getEventName())) {
				j.put("eventName", dto.getEventName());
			}
			if (StringUtils.isNotBlank(dto.getReportPath())) {
				j.put("reportPath", dto.getReportPath());
			}
			if (dto.getModuleId() != null) {
				j.put("moduleId", dto.getModuleId());
			}
			if (dto.getId() != null) {
				j.put("id", dto.getId());
			}
			if (dto.getCaseId() != null) {
				j.put("caseId", dto.getCaseId());
			}
			if (dto.getLimit() != null) {
				j.put("limit", dto.getLimit());
			}
			if (!dto.getRunFlag()) {
				j.put("runFlag", "false");
			}
			if(dto.getIsSuccess() == null || dto.getIsSuccess()) {
				j.put("isSuccess", "true");
			} else {
				j.put("isSuccess", "false");
			}

			String url = ServerHost.localHost10002 + AutoTestInteractionUrl.root
					+ AutoTestInteractionUrl.findReportsByCondition;
			String response = String.valueOf(httpUtil.sendPostRestful(url, j.toString()));
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ModelAndView findReportByTestEventId(HttpServletRequest request, FindReportByTestEventIdDTO dto) {
		
		if(!isInSeekOrDev(request)) {
			return exceptionService.handle404Exception(request);
		}
		
		ModelAndView v = new ModelAndView("ATDemoJSP/atReportPost");
		AutoTestJsonReportVO vo = null;

		Long visitCount = visitDataService.getVisitCount();
		v.addObject("visitCount", visitCount);

		if (dto.getTestEventId() == null || dto.getTestEventId() <= 0) {
			vo = buildErrorReportVO();
			v.addObject("reportVO", vo);
			return v;
		}

		try {
			JSONObject requestJson = JSONObject.fromObject(dto);

			String url = ServerHost.localHost10002 + AutoTestInteractionUrl.root
					+ AutoTestInteractionUrl.findReportByTestEventId;
			String responseStr = String.valueOf(httpUtil.sendPostRestful(url, requestJson.toString()));

			JSONObject responseJson = JSONObject.fromObject(responseStr);

			FindReportByTestEventIdResult responseResult = new FindReportByTestEventIdResult();
			responseResult.setId(responseJson.getLong("id"));
			responseResult.setCode(responseJson.getString("code"));
			responseResult.setTitle(responseJson.getString("title"));

			responseResult.setCreateTime(
					localDateTimeHandler.localDateTimeJsonStrToLocalDateTime(responseJson.getString("createTime")));
			responseResult.setStartTime(
					localDateTimeHandler.localDateTimeJsonStrToLocalDateTime(responseJson.getString("startTime")));
			responseResult.setEndTime(
					localDateTimeHandler.localDateTimeJsonStrToLocalDateTime(responseJson.getString("endTime")));

			responseResult.setReportStr(responseJson.getString("reportStr"));

			vo = buildReportVOByFindReportByTestEventIdResult(responseResult);
		} catch (Exception e) {
			e.printStackTrace();
			vo = reportContentReplaceWithErrorMsg(vo, "报告读取异常");
		}

		v.addObject("reportVO", vo);
		return v;
	}

	private AutoTestJsonReportVO buildErrorReportVO() {
		return reportVOFillErrorMsg("报告不存在");
	}

	/**
	 * 输出异常原因报告
	 * 
	 * @param errorMsg
	 * @return
	 */
	private AutoTestJsonReportVO reportContentReplaceWithErrorMsg(AutoTestJsonReportVO vo, String errorMsg) {
		if (vo == null) {
			vo = new AutoTestJsonReportVO();
		}
		List<AutoTestJsonReportLineVO> contentLines = new ArrayList<AutoTestJsonReportLineVO>();
		AutoTestJsonReportLineVO lineVO = null;
		lineVO = new AutoTestJsonReportLineVO();
		lineVO.setLineArrtibute(AutoTestJsonReportKeyConstant.strKey);
		lineVO.setContent(errorMsg);
		contentLines.add(lineVO);
		vo.setContentLines(contentLines);
		return vo;
	}

	private AutoTestJsonReportVO reportVOFillErrorMsg(String errorMsg) {
		return reportContentReplaceWithErrorMsg(null, errorMsg);
	}

	private AutoTestJsonReportVO reportVOFillCommonData(AutoTestJsonReportVO vo, FindReportByTestEventIdResult r) {
		if (vo == null) {
			vo = new AutoTestJsonReportVO();
		}
		vo.setId(r.getId());
		vo.setTitle(r.getTitle());

		vo.setCreateTime(r.getCreateTime());
		vo.setStartTime(r.getStartTime());
		vo.setEndTime(r.getEndTime());

		vo.setCreateTimeStr(localDateTimeHandler.dateToStr(r.getCreateTime()));
		vo.setStartTimeStr(localDateTimeHandler.dateToStr(r.getStartTime()));
		vo.setEndTimeStr(localDateTimeHandler.dateToStr(r.getEndTime()));
		return vo;
	}

	private AutoTestJsonReportVO buildReportVOByFindReportByTestEventIdResult(FindReportByTestEventIdResult r) {
		if (r == null) {
			return buildErrorReportVO();
		}

		AutoTestJsonReportVO vo = new AutoTestJsonReportVO();
		reportVOFillCommonData(vo, r);

		String line = null;
		AutoTestJsonReportLineVO lineVO = null;
		List<AutoTestJsonReportLineVO> contentLines = new ArrayList<AutoTestJsonReportLineVO>();

		if (r.getStartTime() == null) {
			return reportContentReplaceWithErrorMsg(vo, "任务尚未开始执行, 请耐心等待");

		} else if (r.getEndTime() == null) {
			return reportContentReplaceWithErrorMsg(vo, "任务正在执行, 执行完成后将输出报告, 请耐心等待");

		} else {
			try {

				JSONArray ja = JSONArray.fromObject(r.getReportStr());

				JSONObject j = null;
				for (int i = 0; i < ja.size(); i++) {
					j = ja.getJSONObject(i);
					lineVO = new AutoTestJsonReportLineVO();

					if (j.containsKey(AutoTestJsonReportKeyConstant.strKey)) {
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

	}

	@Override
	public InsertSearchingDemoEventResult insertSearchingDemoTestEvent(InsertSearchingDemoTestEventDTO dto,
			HttpServletRequest request) {
		
		if(!isInSeekOrDev(request)) {
			return null;
		}
		
		InsertSearchingDemoEventResult r = new InsertSearchingDemoEventResult();

		int count = 0;
		if(!isBigUser()) {
			count = visitDataService.checkFunctionalModuleVisitData(request, SystemRedisKey.articleBurnInsertCountingKeyPrefix);
		}
		if (!"dev".equals(constantService.getValByName(SystemConstantStore.envName))) {
			if (count >= SearchingDemoConstant.maxInsertCountIn30Minutes) {
				r.failWithMessage("短时间内加入的任务太多了, 请稍后再试");
				return r;
			}
		}

		try {
			JSONObject requestJson = new JSONObject();
			if (dto.getAppointment() != null) {
				requestJson.put("appointment", localDateTimeHandler.dateToStr(dto.getAppointment()));
			}
			requestJson.put("searchKeyWord", dto.getSearchKeyWord());
			requestJson.put("caseId", dto.getCaseId());

			String url = ServerHost.localHost10002 + SearchingDemoUrl.root + SearchingDemoUrl.insert;
			String response = String.valueOf(httpUtil.sendPostRestful(url, requestJson.toString()));

			JSONObject responseJson = JSONObject.fromObject(response);

			r.setCode(responseJson.getString("code"));
			r.setEventId(responseJson.getLong("eventId"));
			r.setResult(responseJson.getString("result"));
			r.setWaitingEventCount(responseJson.getInt("waitingEventCount"));
			if (r.getResult() != null && "0".equals(r.getResult())) {
				r.setIsSuccess();
				visitDataService.insertFunctionalModuleVisitData(request, SystemRedisKey.searchingDemoInsertCountingKeyPrefix);
				r.setHasInsertCount(count + 1);
				r.setMaxInsertCount(SearchingDemoConstant.maxInsertCountIn30Minutes);
				r.setMessage("/atDemo/findReportByTestEventId?testEventId=" + r.getEventId());
			} else {
				r.setMessage(responseJson.getString("message"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			r.failWithMessage("通讯异常, 请稍后再试, 或联系管理员");
		}

		return r;
	}

	private boolean isInSeekOrDev(HttpServletRequest request) {
		HostnameType hostnameType = hostnameService.findHostname(request);
		if (HostnameType.seek.equals(hostnameType)) {
			return true;
		} else {
			String envName = constantService.getValByName(SystemConstantStore.envName);
			return "dev".equals(envName);
		}
	}
}
