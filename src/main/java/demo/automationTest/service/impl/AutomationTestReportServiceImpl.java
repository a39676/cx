package demo.automationTest.service.impl;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import autoTest.testEvent.common.pojo.dto.AutomationTestResultDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.automationTest.pojo.po.TestEvent;
import demo.automationTest.pojo.po.TestEventExample;
import demo.automationTest.pojo.po.TestEventExample.Criteria;
import demo.automationTest.pojo.vo.TestReportSummaryVO;
import demo.automationTest.service.AutomationTestReportService;
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

			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, localDateTimeAdapter).create();
			gson.toJson(dto.getReport());

			ioUtil.byteToFile(gson.toJson(dto.getReport()).getBytes(StandardCharsets.UTF_8), reportFilePath);

			r.setMessage(reportFilePath);
			r.setIsSuccess();
		} catch (Exception e) {
			log.error("参数文件保存异常, event id: " + dto.getTestEventId() + ", " + e.getLocalizedMessage());
		}

		return r;
	}

	@Override
	public boolean checkReportSavingFloder(String path) {
		String folderPath = getAutomationTestReportSavingFolder();
		File folder = new File(folderPath);
		return ioUtil.checkFolderExists(folder.getAbsolutePath());
	}

	@Override
	public List<TestEvent> findReportPage(FindTestEventPageByConditionDTO dto) {

		TestEventExample example = new TestEventExample();
		Criteria cc = example.createCriteria();
		example.setOrderByClause("id desc");

		if (dto.getEndTime() != null) {
			if (dto.getCreateEndTime() != null) {
				if (dto.getEndTime().isBefore(dto.getCreateEndTime())) {
					dto.setCreateEndTime(dto.getEndTime());
				}
			} else {
				dto.setCreateEndTime(dto.getEndTime());
			}
			dto.setEndTime(null);
		}

		if (dto.getIsSuccess() != null) {
			cc.andIsPassEqualTo(dto.getIsSuccess());
		}
		if (dto.getRunFlag() != null) {
			if (dto.getRunFlag()) {
				cc.andEndTimeIsNotNull();
			} else {
				cc.andEndTimeIsNull();
			}
		}
		if (dto.getCreateEndTime() != null) {
			cc.andCreateTimeLessThanOrEqualTo(dto.getCreateEndTime());
		}
		if (dto.getCreateStartTime() != null) {
			cc.andCreateTimeGreaterThanOrEqualTo(dto.getCreateStartTime());
		}
		if (StringUtils.isNotBlank(dto.getEventName())) {
			cc.andFlowNameEqualTo(dto.getEventName());
		}
		if (dto.getFlowId() != null) {
			cc.andFlowIdEqualTo(dto.getFlowId());
		}
		if (dto.getId() != null) {
			cc.andIdEqualTo(dto.getId());
		}

		if (dto.getModuleId() != null) {
			cc.andModuleIdEqualTo(dto.getModuleId());
		}
		if (StringUtils.isNotBlank(dto.getReportPath())) {
			cc.andReportPathEqualTo(dto.getReportPath());
		}
		if (dto.getRunTimeEndTime() != null) {
			cc.andStartTimeLessThanOrEqualTo(dto.getRunTimeEndTime());
		}
		if (dto.getRunTimeStartTime() != null) {
			cc.andStartTimeGreaterThanOrEqualTo(dto.getRunTimeStartTime());
		}
		if (dto.getLimit() == null) {
			dto.setLimit(NORMAL_PAGE_SIZE.longValue());
			cc.andCreateTimeLessThan(dto.getCreateEndTime());
		}
		RowBounds rowBounds = new RowBounds(0, dto.getLimit().intValue());
		List<TestEvent> list = eventMapper.selectByExampleWithRowbounds(example, rowBounds);

		return list;
	}

	@Override
	public void deleteOldData(LocalDateTime limitDateTime) {
		if (limitDateTime == null) {
			limitDateTime = LocalDateTime.now().minusMonths(optionService.getTestEventLiveLimitMonth());
		}

		TestEventExample example = new TestEventExample();
		example.createCriteria().andIsDeleteEqualTo(false).andCreateTimeLessThanOrEqualTo(limitDateTime);
		List<TestEvent> poList = eventMapper.selectByExample(example);
		if (poList == null || poList.isEmpty()) {
			return;
		}

		List<String> reportPathStrList = new ArrayList<>();
		List<String> paramPathStrList = new ArrayList<>();
		List<Long> testEventIdList = new ArrayList<>();

		for (TestEvent po : poList) {
			reportPathStrList.add(po.getReportPath());
			paramPathStrList.add(po.getParameterFilePath());
			testEventIdList.add(po.getId());
		}

		File tmpReportFile = null;
		for (String reportFilePath : reportPathStrList) {
			try {
				tmpReportFile = new File(reportFilePath);
				tmpReportFile.delete();
				File[] tmpFileList = tmpReportFile.getParentFile().listFiles();
				if (tmpFileList == null || tmpFileList.length == 0) {
					tmpReportFile.getParentFile().delete();
				}
			} catch (Exception e) {
			}
		}

		File tmpParamFile;
		for (String paramFilePath : paramPathStrList) {
			try {
				tmpParamFile = new File(paramFilePath);
				tmpParamFile.delete();
			} catch (Exception e) {
			}
		}

		TestEvent tmpPO = new TestEvent();
		tmpPO.setIsDelete(true);
		TestEventExample deleteExample = new TestEventExample();
		deleteExample.createCriteria().andIdIn(testEventIdList);
		eventMapper.updateByExampleSelective(tmpPO, deleteExample);
	}

	@Override
	public TestReportSummaryVO buildReportSummaryVO(TestEvent po) {
		TestReportSummaryVO vo = new TestReportSummaryVO();
		vo.setIdStr(po.getId().toString());
		vo.setFlowName(po.getFlowName());
		vo.setStartTime(po.getStartTime());
		vo.setEndTime(po.getEndTime());
		vo.setCreateTime(po.getCreateTime());
		return vo;
	}

}
