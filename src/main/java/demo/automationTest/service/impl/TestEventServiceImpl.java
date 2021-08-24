package demo.automationTest.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.automationTest.pojo.po.TestEvent;
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
			if(!ioUtil.checkFolderExists(paramFile.getParentFile().getAbsolutePath())) {
				log.error("参数文件夹状态异常, Test module type: " + moduleType.getModuleName() + ", flow id: " + dto.getFlowType() + ", event id: " + dto.getTestEventId());
				throw new Exception();
			}
			ioUtil.byteToFile(dto.getParamStr(), paramSavingPath);
		} catch (Exception e) {
			log.error("参数文件保存异常, Test module type: " + moduleType.getModuleName() + ", flow id: " + dto.getFlowType() + ", event id: " + dto.getTestEventId() + ", " + e.getLocalizedMessage());
		}
		
		po.setId(dto.getTestEventId());
		po.setModuleId(moduleType.getId());
		po.setAppointment(dto.getAppointment());
		po.setFlowId(dto.getFlowType());
		po.setParameterFilePath(paramSavingPath);
		
		eventMapper.insertSelective(po);
	}
}
