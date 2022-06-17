package demo.tool.bbtOrder.underWayMonthTest.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testEvent.searchingDemo.pojo.dto.UnderWayMonthTestDTO;
import autoTest.testModule.pojo.type.TestModuleType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.automationTest.service.TestEventService;
import demo.automationTest.service.impl.AutomationTestCommonService;
import demo.tool.bbtOrder.underWayMonthTest.service.UnderWayMonthTestService;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class UnderWayMonthTestServiceImpl extends AutomationTestCommonService implements UnderWayMonthTestService {

	@Autowired
	private FileUtilCustom ioUtil;
	@Autowired
	private TestEventService eventService;
	
	@Override
	public ModelAndView monthTestView() {
		return new ModelAndView("toolJSP/publicTool/UnderWayMonthTest");
	}
	
	@Override
	public CommonResult monthTest(UnderWayMonthTestDTO inputDTO) {
		CommonResult r = new CommonResult();

		if (StringUtils.isAnyBlank(inputDTO.getUsername(), inputDTO.getPwd())) {
			r.setMessage("Empty param");
			return r;
		}

		String paramSavingPath = getParamFilePath(TestModuleType.SCHEDULE_CLAWING.getModuleName(),
				ScheduleClawingType.UNDER_WAY_MONTH_TEST.getFlowName(), UnderWayMonthTestDTO.class.getSimpleName());
		String paramStr = ioUtil.getStringFromFile(paramSavingPath);
		UnderWayMonthTestDTO localOptionDTO = null;
		try {
			JSONObject paramJson = JSONObject.fromObject(paramStr);
			localOptionDTO = buildObjFromJsonCustomization(paramJson.toString(), UnderWayMonthTestDTO.class);
			localOptionDTO.setUsername(inputDTO.getUsername());
			localOptionDTO.setPwd(inputDTO.getPwd());
		} catch (Exception e) {
			r.setMessage("Option service error, please call admin");
			return r;
		}

		sendMonthTestTask(localOptionDTO);

		r.setMessage("Task inserted");
		r.setIsSuccess();
		return r;
	}
	
	@Override
	public void sendMonthTestTask(UnderWayMonthTestDTO paramDTO) {
		AutomationTestInsertEventDTO mainDTO = new AutomationTestInsertEventDTO();
		mainDTO.setTestModuleType(TestModuleType.SCHEDULE_CLAWING.getId());
		mainDTO.setFlowType(ScheduleClawingType.UNDER_WAY_MONTH_TEST.getId());
		mainDTO.setFlowTypeName(ScheduleClawingType.UNDER_WAY_MONTH_TEST.getFlowName());
		mainDTO.setTestEventId(snowFlake.getNextId());

		mainDTO = automationTestInsertEventDtoAddParamStr(mainDTO, paramDTO);

		eventService.insertEvent(mainDTO);

		testEventInsertAckProducer.send(mainDTO);
	}
}
