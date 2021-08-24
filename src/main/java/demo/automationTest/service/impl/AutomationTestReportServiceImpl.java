package demo.automationTest.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.report.pojo.dto.JsonReportElementDTO;
import at.report.pojo.dto.JsonReportOfCaseDTO;
import at.report.pojo.dto.JsonReportOfEventDTO;
import autoTest.testEvent.pojo.dto.AutomationTestResultDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.automationTest.service.AutomationTestReportService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class AutomationTestReportServiceImpl extends AutomationTestCommonService
		implements AutomationTestReportService {

	@Autowired
	private FileUtilCustom ioUtil;

	@Override
	public CommonResult saveReport(AutomationTestResultDTO dto) {
		CommonResult r = new CommonResult();

		try {
			String folderPath = getAutomationTestReportSavingFolder();

			if (!checkReportSavingFloder(folderPath)) {
				throw new Exception("无法找到测试报告文件夹: " + folderPath);
			}

			String reportFilePath = folderPath + File.separator + dto.getTestEventId() + ".json";

			ioUtil.byteToFile(JSONObject.fromObject(dto.getReport()).toString(), reportFilePath);

			r.setMessage(reportFilePath);
			r.setIsSuccess();
		} catch (Exception e) {
			log.error("参数文件保存异常, event id: " + dto.getTestEventId() + ", " + e.getLocalizedMessage());
		}

		return r;
	}

	@Override
	public JsonReportOfEventDTO buildReportFromJson(JSONObject json) {
		JsonReportOfEventDTO report = new JsonReportOfEventDTO();

		JSONObject reportJson = json.getJSONObject("report");

		report.setEventTypeID(reportJson.getLong("eventTypeID"));
		report.setModuleName(reportJson.getString("moduleName"));
		report.setEventTypeName(reportJson.getString("eventTypeName"));

		report.setCaseReportList(buildCaseReportList(reportJson));

		return report;
	}

	private List<JsonReportOfCaseDTO> buildCaseReportList(JSONObject reportJson) {
		List<JsonReportOfCaseDTO> caseReportList = new ArrayList<>();

		JsonReportOfCaseDTO caseReportDTO = null;

		JSONArray caseReportListJsonArray = reportJson.getJSONArray("caseReportList");

		for (int i = 0; i < caseReportListJsonArray.size(); i++) {
			caseReportDTO = buildJsonReportOfCaseDTO(caseReportListJsonArray.getJSONObject(i));
			caseReportList.add(caseReportDTO);
		}

		return caseReportList;
	}

	private JsonReportOfCaseDTO buildJsonReportOfCaseDTO(JSONObject caseReportJson) {
		JsonReportOfCaseDTO dto = new JsonReportOfCaseDTO();
		dto.setCaseTypeID(caseReportJson.getLong("caseTypeID"));
		dto.setCaseTypeName(caseReportJson.getString("caseTypeName"));
		dto.setReportElementList(buildReportElementList(caseReportJson.getJSONArray("reportElementList")));
		return dto;
	}

	private List<JsonReportElementDTO> buildReportElementList(JSONArray jsonReportElementArray) {
		List<JsonReportElementDTO> list = new ArrayList<>();
		JsonReportElementDTO dto = null;
		for (int i = 0; i < jsonReportElementArray.size(); i++) {
			dto = buildJsonReportElementDTO(jsonReportElementArray.getJSONObject(i));
			list.add(dto);
		}

		return list;
	}

	private JsonReportElementDTO buildJsonReportElementDTO(JSONObject jsonReportElement) {
		JsonReportElementDTO dto = new JsonReportElementDTO();

		dto.setMarktime(localDateTimeHandler.jsonStrToLocalDateTime(jsonReportElement.getString("marktime")));
		dto.setContent(jsonReportElement.getString("content"));
		dto.setImgUrl(jsonReportElement.getString("imgUrl"));

		return dto;
	}

	@Override
	public boolean checkReportSavingFloder(String path) {
		String folderPath = getAutomationTestReportSavingFolder();
		File folder = new File(folderPath);
		return ioUtil.checkFolderExists(folder.getAbsolutePath());
	}
}
