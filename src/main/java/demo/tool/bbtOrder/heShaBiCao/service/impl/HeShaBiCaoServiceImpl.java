package demo.tool.bbtOrder.heShaBiCao.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testEvent.searchingDemo.pojo.dto.HeShaBiCaoWechatPreregistDTO;
import autoTest.testEvent.searchingDemo.pojo.type.HeShaBiCaoIdType;
import autoTest.testModule.pojo.type.TestModuleType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.automationTest.pojo.po.TestEvent;
import demo.automationTest.service.AutomationTestReportService;
import demo.automationTest.service.TestEventService;
import demo.automationTest.service.impl.AutomationTestCommonService;
import demo.tool.bbtOrder.heShaBiCao.pojo.vo.HeShaBiCaoWechatPreregistReportVO;
import demo.tool.bbtOrder.heShaBiCao.service.HeShaBiCaoService;
import net.sf.json.JSONObject;
import tool.pojo.type.InternationalityType;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class HeShaBiCaoServiceImpl extends AutomationTestCommonService implements HeShaBiCaoService {

	@Autowired
	private FileUtilCustom ioUtil;
	@Autowired
	private AutomationTestReportService reportService;
	@Autowired
	private TestEventService eventService;

	private List<LocalDateTime> hsbcWechatPreregistTaskInsertTime = new ArrayList<>();

	@Override
	public ModelAndView heShaBiCaoWechatPreregistView() {
		ModelAndView view = new ModelAndView("toolJSP/publicTool/HeShaBiCaoWechatPreregist");
		view.addObject("idTypeList", HeShaBiCaoIdType.values());
		view.addObject("internationalityTypeList", InternationalityType.values());

		return view;
	}
	
	@Override
	public ModelAndView getReportSummaryPage(HeShaBiCaoWechatPreregistDTO dto) {
		ModelAndView view = new ModelAndView("toolJSP/publicTool/HeShaBiCaoWechatReportSummaryPage");

		if(!checkPwd(dto)) {
			return view;
		}
		
		FindTestEventPageByConditionDTO queryDTO = new FindTestEventPageByConditionDTO();
		queryDTO.setLimit(30L);
		queryDTO.setFlowId(ScheduleClawingType.HE_SHA_BI_CAO_WECHAT_PREREGIST.getId());
		queryDTO.setModuleId(TestModuleType.SCHEDULE_CLAWING.getId());
		queryDTO.setIsSuccess(null);
		queryDTO.setRunFlag(null);
		List<TestEvent> reportPoList = reportService.findReportPage(queryDTO);
		List<HeShaBiCaoWechatPreregistReportVO> reportVoList = new ArrayList<>();

		for (TestEvent po : reportPoList) {
			reportVoList.add(buildReportVO(po));
		}
		view.addObject("reportVoList", reportVoList);

		return view;
	}

	private HeShaBiCaoWechatPreregistReportVO buildReportVO(TestEvent po) {
		HeShaBiCaoWechatPreregistReportVO vo = new HeShaBiCaoWechatPreregistReportVO();
		vo.setIsPass(po.getIsPass());
		vo.setStartTimeStr(localDateTimeHandler.dateToStr(po.getStartTime()));
		vo.setEndTimeStr(localDateTimeHandler.dateToStr(po.getEndTime()));

		try {
			String paramStr = ioUtil.getStringFromFile(po.getParameterFilePath());
			JSONObject json = JSONObject.fromObject(paramStr);
			HeShaBiCaoWechatPreregistDTO paramDTO = new Gson().fromJson(json.getString(HeShaBiCaoWechatPreregistDTO.class.getSimpleName()), HeShaBiCaoWechatPreregistDTO.class);
			InternationalityType areaType = InternationalityType.getType(paramDTO.getAreaType(),
					paramDTO.getAreaName());
			vo.setAreaTypeCnName(areaType.getCnName());
			InternationalityType phoneAreaType = InternationalityType.getType(paramDTO.getPhoneAreaType(),
					paramDTO.getPhoneAreaName());
			vo.setPhoneAreaCnName(phoneAreaType.getCnName());
			HeShaBiCaoIdType idType = HeShaBiCaoIdType.getType(paramDTO.getIdType());
			vo.setIdTypeCnName(idType.getCnName());
			vo.setIdNumber(paramDTO.getIdNumber());
			vo.setPhoneNumber(paramDTO.getPhoneNumber());
			vo.setCustomerLastName(paramDTO.getCustomerLastName());
			vo.setCustomerFirstName(paramDTO.getCustomerFirstName());

		} catch (Exception e) {
		}

		return vo;
	}

	@Override
	public CommonResult heShaBiCaoWechatPreregist(HeShaBiCaoWechatPreregistDTO dto) {
		CommonResult r = new CommonResult();
		if(!checkPwd(dto)) {
			r.setMessage("password error");
			return r;
		}

		if (StringUtils.isAnyBlank(dto.getIdNumber(), dto.getPhoneNumber())) {
			r.setMessage("Empty param");
			return r;
		}

		if (!canInsertPreregistTask()) {
			r.setMessage("Too much tasks, please insert later");
			return r;
		}

		String paramSavingPath = getParamFilePath(TestModuleType.SCHEDULE_CLAWING.getModuleName(),
				ScheduleClawingType.HE_SHA_BI_CAO_WECHAT_PREREGIST.getFlowName(), HeShaBiCaoWechatPreregistDTO.class.getSimpleName());
		String paramStr = ioUtil.getStringFromFile(paramSavingPath);
		try {
			JSONObject paramJson = JSONObject.fromObject(paramStr);
			dto.setMainUrl(paramJson.getString("shaBiYinHangWechatPreregistUrl"));
		} catch (Exception e) {
			r.setMessage("Option service error, please call admin");
			return r;
		}

		sendHeShaBiCaoWechatPreregistTask(dto);
		hsbcWechatPreregistTaskInsertTime.add(LocalDateTime.now());

		r.setMessage("Task inserted");
		r.setIsSuccess();
		return r;
	}
	
	private boolean checkPwd(HeShaBiCaoWechatPreregistDTO dto) {
		if (StringUtils.isBlank(dto.getApkDownloadPassword())) {
			return false;
		}

//		String correctPwd = "shabiyinhang";
		String correctPwd = "hsbc";
		Integer month = LocalDate.now().getMonthValue();
		if(month < 10) {
			correctPwd = correctPwd + "0" + month;
		} else {
			correctPwd = correctPwd + month;
		}

		return correctPwd.equals(dto.getApkDownloadPassword());
	}

	private boolean canInsertPreregistTask() {
		if (systemOptionService.isDev()) {
			return true;
		}

		List<LocalDateTime> list = hsbcWechatPreregistTaskInsertTime;
		if (list == null) {
			hsbcWechatPreregistTaskInsertTime = new ArrayList<>();
			return true;
		}

		if (list.isEmpty()) {
			return true;
		}

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).isBefore(LocalDateTime.now().minusMinutes(10))) {
				list.remove(i);
				i--;
			}
		}

		hsbcWechatPreregistTaskInsertTime = list;
		return !(list.size() > 3);
	}

	@Override
	public void sendHeShaBiCaoWechatPreregistTask(HeShaBiCaoWechatPreregistDTO paramDTO) {
		AutomationTestInsertEventDTO mainDTO = new AutomationTestInsertEventDTO();
		mainDTO.setTestModuleType(TestModuleType.SCHEDULE_CLAWING.getId());
		mainDTO.setFlowType(ScheduleClawingType.HE_SHA_BI_CAO_WECHAT_PREREGIST.getId());
		mainDTO.setFlowTypeName(ScheduleClawingType.HE_SHA_BI_CAO_WECHAT_PREREGIST.getFlowName());
		mainDTO.setTestEventId(snowFlake.getNextId());

		mainDTO = automationTestInsertEventDtoAddParamStr(mainDTO, paramDTO);

		eventService.insertEvent(mainDTO);

		testEventInsertAckProducer.send(mainDTO);
	}
}
