package demo.automationTest.service.impl;

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
import autoTest.jsonReport.pojo.dto.FindReportByTestEventIdDTO;
import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import autoTest.pojo.constant.AutoTestUrl;
import autoTest.report.pojo.dto.JsonReportElementDTO;
import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import autoTest.report.pojo.dto.JsonReportOfFlowDTO;
import autoTest.report.pojo.vo.JsonReportOfEventVO;
import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.searchingDemo.pojo.constant.SearchingDemoConstant;
import autoTest.testEvent.searchingDemo.pojo.dto.BingSearchInHomePageDTO;
import autoTest.testEvent.searchingDemo.pojo.type.BingDemoSearchFlowType;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.automationTest.pojo.po.TestEvent;
import demo.automationTest.pojo.result.InsertSearchingDemoEventResult;
import demo.automationTest.pojo.vo.TestReportSummaryVO;
import demo.automationTest.service.AutomationTestHomepageService;
import demo.automationTest.service.AutomationTestReportService;
import demo.automationTest.service.TestEventService;
import demo.base.system.pojo.constant.SystemRedisKey;
import demo.base.system.pojo.result.HostnameType;
import demo.base.system.service.ExceptionService;
import demo.base.system.service.HostnameService;
import net.sf.json.JSONObject;
import toolPack.dateTimeHandle.DateTimeUtilCommon;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class AutomationTestHomepageServiceImpl extends AutomationTestCommonService implements AutomationTestHomepageService {
	
	@Autowired
	private FileUtilCustom ioUtil;
	@SuppressWarnings("unused")
	@Autowired
	private ExceptionService exceptionService;
	@Autowired
	private TestEventService eventService;
	@Autowired
	private AutomationTestReportService reportService;
	@Autowired
	private HostnameService hostnameService;
	
	
	@Override
	public ModelAndView linkToATHome(HttpServletRequest request) {
		if(baseUtilCustom.hasAdminRole()) {
			return new ModelAndView("ATDemoJSP/atDemoLink");	
		}
		
		HostnameType hostnameType = hostnameService.findHostnameType(request);
		if(HostnameType.zhang3.equals(hostnameType)) {
			return new ModelAndView("ATDemoJSP/atDemoLink");
		}
		
		return null;
	}

	@Override
	public ModelAndView index() {

//		if(!baseUtilCustom.hasAdminRole() && !isDev()) {
//			return exceptionService.handle404Exception(null);
//		}

		ModelAndView v = new ModelAndView("ATDemoJSP/atDemoIndex");
		v.addObject("title", "自动化测试");
		String dateNow = localDateTimeHandler.dateToStr(LocalDateTime.now(), DateTimeUtilCommon.normalDateFormat);
		v.addObject("createEndTime", dateNow);
		v.addObject("runTimeEndTime", dateNow);
		if (baseUtilCustom.hasAdminRole()) {
			Map<Long, String> modules = new HashMap<Long, String>();
			for (TestModuleType i : TestModuleType.values()) {
				modules.put(i.getId(), i.getModuleName());
			}
			v.addObject("modules", modules);
		}
		v.addObject("defaultStartTime", localDateTimeHandler.dateToStr(LocalDateTime.now().withMonth(1).withDayOfMonth(1), DateTimeUtilCommon.normalDateFormat));

		return v;
	}

	@Override
	public List<TestReportSummaryVO> findReportsByCondition(FindTestEventPageByConditionDTO dto) {
		
//		if (!baseUtilCustom.hasAdminRole()) {
//			dto.setModuleId(TestModuleType.ATDemo.getId());
//		}
		
		List<TestEvent> poList = reportService.findReportPage(dto);
		List<TestReportSummaryVO> voList = new ArrayList<>();
		
		for(TestEvent po : poList ) {
			voList.add(buildReportSummaryVO(po));
		}
		return voList;
	}
	
	private TestReportSummaryVO buildReportSummaryVO(TestEvent po){
		TestReportSummaryVO vo = new TestReportSummaryVO();
		vo.setIdStr(po.getId().toString());
		vo.setFlowName(po.getFlowName());
		vo.setStartTime(po.getStartTime());
		vo.setEndTime(po.getEndTime());
		vo.setCreateTime(po.getCreateTime());
		return vo;
	}

	@Override
	public ModelAndView findReportByTestEventId(HttpServletRequest request, FindReportByTestEventIdDTO dto) {
		
//		if(!baseUtilCustom.hasAdminRole()) {
//			return exceptionService.handle404Exception(request);
//		}
		
		ModelAndView view = new ModelAndView("ATDemoJSP/atReportPost");
		JsonReportOfEventVO vo = null;

		Long visitCount = visitDataService.getVisitCount();
		view.addObject("visitCount", visitCount);

		if (dto.getTestEventId() == null || dto.getTestEventId() <= 0) {
			vo = buildErrorReportVO();
			view.addObject("reportVO", vo);
			return view;
		}

		try {
			TestEvent po = eventMapper.selectByPrimaryKey(dto.getTestEventId());
			vo = buildReportVOByFindReportByTestEventIdResult(po);
			
		} catch (Exception e) {
			e.printStackTrace();
			vo = reportContentReplaceWithErrorMsg(vo, "报告读取异常");
		}

		view.addObject("reportVO", vo);
		return view;
	}

	private JsonReportOfEventVO buildErrorReportVO() {
		return reportVOFillErrorMsg("报告不存在");
	}

	/**
	 * 输出异常原因报告
	 * 
	 * @param errorMsg
	 * @return
	 */
	private JsonReportOfEventVO reportContentReplaceWithErrorMsg(JsonReportOfEventVO vo, String errorMsg) {
		if (vo == null) {
			vo = new JsonReportOfEventVO();
		}
		
		List<JsonReportElementDTO> subReport = new ArrayList<JsonReportElementDTO>();
		JsonReportElementDTO reportElement = new JsonReportElementDTO();
		reportElement.setContent(errorMsg);
		subReport.add(reportElement);
		
		JsonReportOfCaseDTO caseReport = new JsonReportOfCaseDTO();
		caseReport.setReportElementList(subReport);
		
		vo.getCaseReportList().add(caseReport);
		return vo;
	}

	private JsonReportOfEventVO reportVOFillErrorMsg(String errorMsg) {
		return reportContentReplaceWithErrorMsg(null, errorMsg);
	}

	private JsonReportOfEventVO reportFillCommonData(JsonReportOfEventVO vo, TestEvent po) {
		if (vo == null) {
			return vo;
		}
		vo.setFlowTypeName(po.getFlowName());
		vo.setModuleName(TestModuleType.getType(po.getModuleId()).getModuleName());

		vo.setCreateTimeStr(localDateTimeHandler.dateToStr(po.getCreateTime()));
		vo.setStartTimeStr(localDateTimeHandler.dateToStr(po.getStartTime()));
		vo.setEndTimeStr(localDateTimeHandler.dateToStr(po.getEndTime()));
		return vo;
	}

	private JsonReportOfEventVO buildReportVOByFindReportByTestEventIdResult(TestEvent po) {
		if (po == null) {
			return buildErrorReportVO();
		}

		JsonReportOfEventVO vo = new JsonReportOfEventVO();
		reportFillCommonData(vo, po);


		if (po.getStartTime() == null) {
			return reportContentReplaceWithErrorMsg(vo, "任务尚未开始执行, 请耐心等待");

		} else if (po.getEndTime() == null) {
			return reportContentReplaceWithErrorMsg(vo, "任务正在执行, 执行完成后将输出报告, 请耐心等待");
		
		} else if (StringUtils.isBlank(po.getReportPath())) {
			return reportContentReplaceWithErrorMsg(vo, "任务正在执行, 执行完成后将输出报告, 请耐心等待");
			
		} else {
			try {

				String reportStr = ioUtil.getStringFromFile(po.getReportPath());
				JSONObject jsonReport = JSONObject.fromObject(reportStr);
				JsonReportOfFlowDTO reportDTO = reportService.buildReportFromDatabase(jsonReport);
				
				vo.setCaseReportList(reportDTO.getCaseReportList());
				
				vo.setPassCount(po.getPassCount());
				vo.setFailedCount(po.getFailCount());
				vo.setBlockedCount(po.getBlockCount());
				
				return vo;
			} catch (Exception e) {
				e.printStackTrace();
				return buildErrorReportVO();
			}
		}

	}

	@Override
	public InsertSearchingDemoEventResult insertSearchingDemoTestEvent(BingSearchInHomePageDTO dto,
			HttpServletRequest request) {
		InsertSearchingDemoEventResult r = new InsertSearchingDemoEventResult();
		
		int count = redisConnectService.checkFunctionalModuleVisitData(request, SystemRedisKey.searchingDemoInsertCountingKeyPrefix);

		if(!isBigUser() && !isDev()) {
			if (count >= SearchingDemoConstant.maxInsertCountIn30Minutes) {
				r.failWithMessage("短时间内加入的任务太多了, 请稍后再试");
				return r;
			}
		}

		try {
			AutomationTestInsertEventDTO insertEventDTO = new AutomationTestInsertEventDTO();
			insertEventDTO.setTestEventId(snowFlake.getNextId());
			insertEventDTO.setTestModuleType(TestModuleType.ATDemo.getId());
			insertEventDTO.setFlowType(BingDemoSearchFlowType.SEARCH_IN_HOMEPAGE.getId());
			insertEventDTO.setFlowTypeName(BingDemoSearchFlowType.SEARCH_IN_HOMEPAGE.getFlowName());
			insertEventDTO.setAppointment(dto.getAppointment());
			JSONObject paramJson = new JSONObject();
			paramJson.put(dto.getClass().getSimpleName(), JSONObject.fromObject(dto).toString());
			insertEventDTO.setParamStr(paramJson.toString());
			
			eventService.insertEvent(insertEventDTO);
			
			if(dto.getAppointment() == null || !LocalDateTime.now().isBefore(dto.getAppointment())) {
				testEventInsertAckProducer.send(insertEventDTO);
			}
			r.setEventId(insertEventDTO.getTestEventId());
			r.setIsSuccess();
			redisConnectService.insertFunctionalModuleVisitData(request, SystemRedisKey.searchingDemoInsertCountingKeyPrefix);
			r.setHasInsertCount(count + 1);
			r.setMaxInsertCount(SearchingDemoConstant.maxInsertCountIn30Minutes);
			r.setMessage(AutoTestUrl.root + AutoTestInteractionUrl.FIND_REPORT_BY_TEST_EVENT_ID + "?testEventId=" + r.getEventId());

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			r.failWithMessage("通讯异常, 请稍后再试, 或联系管理员");
		}

		return r;
	}

}
