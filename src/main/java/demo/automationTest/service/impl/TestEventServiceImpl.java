package demo.automationTest.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.automationTest.pojo.po.TestEvent;
import demo.automationTest.pojo.po.TestEventExample;
import demo.automationTest.service.TestEventService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class TestEventServiceImpl extends AutomationTestCommonService implements TestEventService {

	@Autowired
	private FileUtilCustom ioUtil;

	@Override
	public void insertEvent(AutomationTestInsertEventDTO dto) {
		TestEvent po = new TestEvent();

		TestModuleType moduleType = TestModuleType.getType(dto.getTestModuleType());
		String paramSavingPath = buildAutomationParamSavingPath(moduleType, dto.getFlowType(), dto.getTestEventId());

		try {
			File paramFile = new File(paramSavingPath);
			if (!ioUtil.checkFolderExists(paramFile.getParentFile().getAbsolutePath())) {
				log.error("参数文件夹状态异常, Test module type: " + moduleType.getModuleName() + ", flow id: "
						+ dto.getFlowType() + ", event id: " + dto.getTestEventId());
				throw new Exception();
			}
			ioUtil.byteToFile(dto.getParamStr(), paramSavingPath);
		} catch (Exception e) {
			log.error("参数文件保存异常, Test module type: " + moduleType.getModuleName() + ", flow id: " + dto.getFlowType()
					+ ", event id: " + dto.getTestEventId() + ", " + e.getLocalizedMessage());
		}

		po.setId(dto.getTestEventId());
		po.setModuleId(moduleType.getId());
		po.setAppointment(dto.getAppointment());
		po.setFlowId(dto.getFlowType());
		po.setFlowName(dto.getFlowTypeName());
		po.setParameterFilePath(paramSavingPath);

		eventMapper.insertSelective(po);
	}

	@Override
	public void sendTestEventToRun() {
		TestEventExample example = new TestEventExample();
		example.createCriteria().andIsDeleteEqualTo(false).andSendTimeIsNull()
				.andAppointmentLessThanOrEqualTo(LocalDateTime.now()).andStartTimeIsNull();
		List<TestEvent> poList = eventMapper.selectByExample(example);
		AutomationTestInsertEventDTO insertEventDTO = null;

		List<Long> idList = new ArrayList<>();

		for (TestEvent po : poList) {
			idList.add(po.getId());
			insertEventDTO = new AutomationTestInsertEventDTO();
			insertEventDTO.setTestEventId(po.getId());
			insertEventDTO.setTestModuleType(po.getModuleId());
			insertEventDTO.setFlowType(po.getFlowId());

			if (po.getParameterFilePath() != null) {
				try {
					insertEventDTO.setParamStr(ioUtil.getStringFromFile(po.getParameterFilePath()));
				} catch (Exception e) {
					log.error("Run test event error, try get param error: " + e.getLocalizedMessage());
					continue;
				}
			}
			testEventInsertAckProducer.send(insertEventDTO);
		}

		example = new TestEventExample();
		example.createCriteria().andIdIn(idList);
		TestEvent tmpPO = new TestEvent();
		tmpPO.setSendTime(LocalDateTime.now());
		eventMapper.updateByExampleSelective(tmpPO, example);
	}

	@Override
	public void handleLongWaitingEvent() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime limitWaitingTime = now.minusHours(constantService.getMaxWaitingRunHour());
		TestEventExample example = new TestEventExample();
		example.createCriteria().andIsDeleteEqualTo(false).andStartTimeIsNull().andSendTimeLessThan(limitWaitingTime);
		List<TestEvent> poList = eventMapper.selectByExample(example);

		List<Long> idList = new ArrayList<>();

		for (TestEvent po : poList) {
			idList.add(po.getId());
		}

		example = new TestEventExample();
		example.createCriteria().andIdIn(idList);
		TestEvent tmpPO = new TestEvent();
		tmpPO.setIsPass(false);
		tmpPO.setStartTime(now);
		tmpPO.setEndTime(now);
		eventMapper.updateByExampleSelective(tmpPO, example);
	}
}