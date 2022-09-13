package demo.tool.bbtOrder.hsbc.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import autoTest.testEvent.hsbc.pojo.dto.HsbcWechatPreregistDTO;
import autoTest.testEvent.hsbc.pojo.type.HsbcIdType;
import autoTest.testEvent.hsbc.pojo.type.HsbcWechatPreregistBranchCityNameType;
import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testModule.pojo.type.TestModuleType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.automationTest.pojo.po.TestEvent;
import demo.automationTest.service.AutomationTestReportService;
import demo.automationTest.service.TestEventService;
import demo.automationTest.service.impl.AutomationTestCommonService;
import demo.tool.bbtOrder.hsbc.pojo.vo.HsbcWechatPreregistReportVO;
import demo.tool.bbtOrder.hsbc.pojo.vo.RandomIdDataVO;
import demo.tool.bbtOrder.hsbc.service.HsbcService;
import demo.tool.other.service.impl.RandomChineseNameGenerator;
import net.sf.json.JSONObject;
import tool.pojo.type.InternationalityType;
import toolPack.complexTool.ChinaMainLandIdNumGenerator;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class HsbcServiceImpl extends AutomationTestCommonService implements HsbcService {

	@Autowired
	private FileUtilCustom ioUtil;
	@Autowired
	private AutomationTestReportService reportService;
	@Autowired
	private TestEventService eventService;
	@Autowired
	private RandomChineseNameGenerator randomChineseNameGenerator;
	@Autowired
	private ChinaMainLandIdNumGenerator chinaMainLandIdNumGenerator;

	private List<LocalDateTime> hsbcWechatPreregistTaskInsertTime = new ArrayList<>();

	@Override
	public ModelAndView hsbcWechatPreregistView() {
		ModelAndView view = new ModelAndView("toolJSP/publicTool/HsbcWechatPreregist");
		view.addObject("idTypeList", HsbcIdType.values());
		view.addObject("internationalityTypeList", InternationalityType.values());
		view.addObject("randomIdNumber", chinaMainLandIdNumGenerator.getRandomId());
		view.addObject("randomLastName", randomChineseNameGenerator.getRandomLastName());
		view.addObject("randomFirstName", randomChineseNameGenerator.getRandomFirstName());
		
		ThreadLocalRandom t = ThreadLocalRandom.current();
		int randomNum = t.nextInt(10000000, 99999999);
		String randomPhone = String.valueOf("187" + randomNum);
		view.addObject("randomPhoneNumber", randomPhone);
		
		return view;
	}
	
	@Override
	public RandomIdDataVO getRandomIdData() {
		RandomIdDataVO vo = new RandomIdDataVO();
		
		ThreadLocalRandom t = ThreadLocalRandom.current();
		int randomNum = t.nextInt(10000000, 99999999);
		String randomPhone = String.valueOf("187" + randomNum);
		vo.setRandomPhoneNumber(randomPhone);
		vo.setRandomIdNumber(chinaMainLandIdNumGenerator.getRandomId());
		vo.setRandomFirstname(randomChineseNameGenerator.getRandomFirstName());
		vo.setRandomLastname(randomChineseNameGenerator.getRandomLastName());
		return vo;
	}

	@Override
	public ModelAndView getReportSummaryPage(HsbcWechatPreregistDTO dto) {
		ModelAndView view = new ModelAndView("toolJSP/publicTool/HsbcWechatReportSummaryPage");

		if (!checkPwd(dto)) {
			return view;
		}

		FindTestEventPageByConditionDTO queryDTO = new FindTestEventPageByConditionDTO();
		queryDTO.setLimit(30L);
		queryDTO.setFlowId(ScheduleClawingType.HSBC_WECHAT_PREREGIST.getId());
		queryDTO.setModuleId(TestModuleType.SCHEDULE_CLAWING.getId());
		queryDTO.setIsSuccess(null);
		queryDTO.setRunFlag(null);
		List<TestEvent> reportPoList = reportService.findReportPage(queryDTO);
		List<HsbcWechatPreregistReportVO> reportVoList = new ArrayList<>();

		for (TestEvent po : reportPoList) {
			reportVoList.add(buildReportVO(po));
		}
		view.addObject("reportVoList", reportVoList);

		return view;
	}

	private HsbcWechatPreregistReportVO buildReportVO(TestEvent po) {
		HsbcWechatPreregistReportVO vo = new HsbcWechatPreregistReportVO();
		vo.setIsPass(po.getIsPass());
		vo.setStartTimeStr(localDateTimeHandler.dateToStr(po.getStartTime()));
		vo.setEndTimeStr(localDateTimeHandler.dateToStr(po.getEndTime()));

		try {
			String paramStr = ioUtil.getStringFromFile(po.getParameterFilePath());
			JSONObject json = JSONObject.fromObject(paramStr);
			HsbcWechatPreregistDTO paramDTO = new Gson().fromJson(
					json.getString(HsbcWechatPreregistDTO.class.getSimpleName()), HsbcWechatPreregistDTO.class);
			InternationalityType areaType = InternationalityType.getType(paramDTO.getAreaType(),
					paramDTO.getAreaName());
			vo.setAreaTypeCnName(areaType.getCnName());
			InternationalityType phoneAreaType = InternationalityType.getType(paramDTO.getPhoneAreaType(),
					paramDTO.getPhoneAreaName());
			vo.setPhoneAreaCnName(phoneAreaType.getCnName());
			HsbcIdType idType = HsbcIdType.getType(paramDTO.getIdType());
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
	public CommonResult hsbcWechatPreregist(HsbcWechatPreregistDTO dto) {
		CommonResult r = new CommonResult();
		if (!checkPwd(dto)) {
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
				ScheduleClawingType.HSBC_WECHAT_PREREGIST.getFlowName(), HsbcWechatPreregistDTO.class.getSimpleName());
		String paramStr = ioUtil.getStringFromFile(paramSavingPath);
		try {
			HsbcWechatPreregistDTO paramDTO = buildObjFromJsonCustomization(paramStr, HsbcWechatPreregistDTO.class);
			dto.setMainUrl(paramDTO.getMainUrl());
			dto.setProvinceRegionMap(paramDTO.getProvinceRegionMap());
		} catch (Exception e) {
			r.setMessage("Option service error, please call admin");
			return r;
		}

		sendHsbcWechatPreregistTask(dto);
		hsbcWechatPreregistTaskInsertTime.add(LocalDateTime.now());

		r.setMessage("Task inserted");
		r.setIsSuccess();
		return r;
	}

	private boolean checkPwd(HsbcWechatPreregistDTO dto) {
		if (StringUtils.isBlank(dto.getApkDownloadPassword())) {
			return false;
		}

//		String correctPwd = "shabiyinhang";
		String correctPwd = "hsbc";
		Integer month = LocalDate.now().getMonthValue();
		if (month < 10) {
			correctPwd = correctPwd + "0" + month;
		} else {
			correctPwd = correctPwd + month;
		}

		return correctPwd.equals(dto.getApkDownloadPassword());
	}

	private boolean canInsertPreregistTask() {
		if (baseUtilCustom.hasAdminRole() || systemOptionService.isDev()) {
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

	@Override
	public CommonResult hsbcWechatPreregistRandomInsert() {
		CommonResult r = new CommonResult();
		if (systemOptionService.isDev()) {
			return r;
		}

		LocalDateTime now = LocalDateTime.now();
		ThreadLocalRandom t = ThreadLocalRandom.current();
		if (now.getHour() < 10 || now.getHour() > 21 || t.nextInt(1, 101) > 10) {
			return r;
		}

		HsbcWechatPreregistDTO dto = new HsbcWechatPreregistDTO();

		String paramSavingPath = getParamFilePath(TestModuleType.SCHEDULE_CLAWING.getModuleName(),
				ScheduleClawingType.HSBC_WECHAT_PREREGIST.getFlowName(), HsbcWechatPreregistDTO.class.getSimpleName());
		String paramStr = ioUtil.getStringFromFile(paramSavingPath);
		try {
			HsbcWechatPreregistDTO paramDTO = buildObjFromJsonCustomization(paramStr, HsbcWechatPreregistDTO.class);
			dto.setMainUrl(paramDTO.getMainUrl());
			dto.setProvinceRegionMap(paramDTO.getProvinceRegionMap());

		} catch (Exception e) {
			r.setMessage("Option service error, please call admin");
			return r;
		}

		InternationalityType phoneArea = InternationalityType.CN;
		dto.setPhoneAreaType(phoneArea.getCode());
		dto.setPhoneAreaName(phoneArea.getCnName());

		dto.setAreaName(phoneArea.getCnName());
		dto.setAreaType(phoneArea.getCode());

		dto.setCustomerFirstName(randomChineseNameGenerator.getRandomFirstName());
		dto.setCustomerLastName(randomChineseNameGenerator.getRandomLastName());

		HsbcIdType idType = HsbcIdType.MAIN_LAND_ID;
		dto.setIdType(idType.getId());

		int randomNum = t.nextInt(10000000, 99999999);
		String randomPhone = String.valueOf("187" + randomNum);
		dto.setPhoneNumber(randomPhone);

		List<String> citynameList = new ArrayList<>();
		for (HsbcWechatPreregistBranchCityNameType data : HsbcWechatPreregistBranchCityNameType.values()) {
			citynameList.add(data.getName());
		}
		dto.setCityNameOfOpeningAccountBranch(citynameList.get(t.nextInt(0, citynameList.size())));

		ChinaMainLandIdNumGenerator g = new ChinaMainLandIdNumGenerator();
		String randomId = g.getRandomId();
		dto.setIdNumber(randomId);

		if (StringUtils.isAnyBlank(dto.getIdNumber(), dto.getPhoneNumber())) {
			r.setMessage("Empty param");
			return r;
		}

		sendHsbcWechatPreregistTask(dto);

		r.setMessage("Task inserted");
		r.setIsSuccess();
		return r;
	}

	
}
