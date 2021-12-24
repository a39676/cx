package demo.automationTest.service.impl;

import java.io.File;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import autoTest.testModule.pojo.type.TestModuleType;
import demo.article.article.service.impl.ArticleCommonService;
import demo.automationTest.mapper.TestEventMapper;
import demo.automationTest.mq.producer.TestEventInsertAckProducer;
import demo.tool.other.service.VisitDataService;
import toolPack.dateTimeHandle.DateTimeUtilCommon;
import toolPack.ioHandle.FileUtilCustom;

public abstract class AutomationTestCommonService extends ArticleCommonService {

	@Autowired
	private FileUtilCustom ioUtil;
	@Autowired
	protected TestEventMapper eventMapper;
	@Autowired
	protected AutomationTestConstantService constantService;
	@Autowired
	protected TestEventInsertAckProducer testEventInsertAckProducer;
	@Autowired
	protected VisitDataService visitDataService;

	protected String getAutomationTestReportSavingFolder() {
		String path = constantService.getReportStorePrefixPath() + File.separator
				+ localDateTimeHandler.dateToStr(LocalDateTime.now(), DateTimeUtilCommon.dateFormatNoSymbol);
		File folder = new File(path);
		if (!folder.exists() || !folder.isDirectory()) {
			if (!folder.mkdirs()) {
				return null;
			}
		}
		return path;
	}

	protected String buildAutomationParamSavingPath(TestModuleType moduleType, Long flowId, Long eventID) {
		String path = constantService.getParamStorePrefixPath() + File.separator + moduleType.getModuleName()
				+ File.separator + flowId.toString() + File.separator + eventID.toString() + ".json";
		File folder = new File(path);
		ioUtil.checkFolderExists(folder.getParentFile().getAbsolutePath());
		return path;
	}
}
