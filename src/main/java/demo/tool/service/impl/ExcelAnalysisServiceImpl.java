package demo.tool.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import demo.base.system.pojo.constant.BaseViewConstant;
import demo.common.pojo.type.ResultTypeCX;
import demo.common.service.CommonService;
import demo.tool.ToolViewConstant;
import demo.tool.mapper.ExcelAnalysisMapper;
import demo.tool.pojo.constant.ToolPathConstant;
import demo.tool.pojo.constant.UploadUrlConstant;
import demo.tool.pojo.dto.ExcelAnalysisByPkParam;
import demo.tool.pojo.po.ExcelAnalysis;
import demo.tool.pojo.po.example.ExcelAnalysisExample;
import demo.tool.pojo.po.example.ExcelAnalysisExample.Criteria;
import demo.tool.pojo.result.UploadExcelResult;
import demo.tool.pojo.type.ChartType;
import demo.tool.service.ExcelAnalysisService;
import demo.toyParts.chart.controller.ChartController;
import demo.toyParts.chart.pojo.constant.ChartUrl;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class ExcelAnalysisServiceImpl extends CommonService implements ExcelAnalysisService {

	@Autowired
	private ExcelAnalysisMapper excelAnalysisMapper;

	@Autowired
	private ChartController chartController;
	@Autowired
	private FileUtilCustom ioUtil;
	
	@Override
	public ModelAndView uploadTestView(String pk, int chartType, String columnToRow,
			HttpServletRequest request) {
		ModelAndView view = new ModelAndView(ToolViewConstant.uploadExcel);
		
		view.addObject("pk", pk);
		view.addObject("chartType", chartType);
		view.addObject("columnToRow", columnToRow);
		view.addObject("uploadUrl", UploadUrlConstant.uploadExcel);
		view.addObject("chartViewUri", ChartUrl.root + ChartUrl.simpleChart);
		view.addObject("chartViewUrl", findHostNameFromRequst(request) + ChartUrl.root + ChartUrl.simpleChart);
		List<ChartType> chartTypeList = new ArrayList<ChartType>();
		for(ChartType subChartType : ChartType.values()) {
			chartTypeList.add(subChartType);
		}
		view.addObject("chartTypeList", chartTypeList);
		return view;
	}

	@Override
	public UploadExcelResult uploadExcel(Map<String, MultipartFile> fileMap, HttpServletRequest request) {
		UploadExcelResult result = new UploadExcelResult();
		MultipartFile tmpFile = null;
		String fileName = null;
		String suffixFileName = null;

		for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
			tmpFile = entry.getValue();
			fileName = tmpFile.getOriginalFilename();
			if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
				result.fillWithResult(ResultTypeCX.errorParam);
				return result;
			}
		}

		if (!fileName.endsWith(".xlsx")) {
			suffixFileName = ".xlsx";
		} else {
			suffixFileName = ".xls";
		}

		ExcelAnalysis newRecord = new ExcelAnalysis();
		Long newId = snowFlake.getNextId();
		newRecord.setId(newId);
		newRecord.setCreateTime(new Date());
		excelAnalysisMapper.insertSelective(newRecord);
		if (newRecord.getId() == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}

		String pk = encryptId(newRecord.getId());
		newRecord.setPrivateKey(pk);
		String path = ToolPathConstant.getExcelAnalysisStorePath() + newRecord.getId().toString() + suffixFileName;
		newRecord.setPath(path);

		ExcelAnalysisExample excelAnalysisExample = new ExcelAnalysisExample();
		Criteria criteria = excelAnalysisExample.createCriteria();
		criteria.andIdEqualTo(newRecord.getId());
		excelAnalysisMapper.updateByExample(newRecord, excelAnalysisExample);

		for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
			tmpFile = entry.getValue();
			fileName = tmpFile.getOriginalFilename();
			try {
				ioUtil.byteToFile(tmpFile.getBytes(), path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		result.setIsSuccess();
		result.setPk(newRecord.getPrivateKey());
		result.setUrl(
				findHostNameFromRequst(request) + ChartUrl.root + ChartUrl.simpleChart);
		result.setUri(ChartUrl.root + ChartUrl.simpleChart);
		return result;
	}

	@Override
	public ModelAndView excelAnalysisByPk(ExcelAnalysisByPkParam param) {
		ModelAndView view = new ModelAndView(BaseViewConstant.viewError);
		if (StringUtils.isBlank(param.getPk()) || param.getChartType() == null) {
			view.addObject("exception", ResultTypeCX.errorParam.getName());
			return view;
		}

		ChartType ct = ChartType.getType(param.getChartType());
		if (ct == null) {
			view.addObject("exception", ResultTypeCX.errorParam.getName());
			return view;
		}

		Long excelId = decryptPrivateKey(param.getPk());
		if (excelId == null) {
			view.addObject("exception", ResultTypeCX.errorParam.getName());
			return view;
		}

		ExcelAnalysis excelAnalysis = excelAnalysisMapper.findOne(excelId);
		if (excelAnalysis == null || StringUtils.isBlank(excelAnalysis.getPath())) {
			view.addObject("exception", ResultTypeCX.errorParam.getName());
			return view;
		}

		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(new File(excelAnalysis.getPath()));
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
			view.addObject("exception", ResultTypeCX.errorParam.getName());
			return view;
		}

		view = chartController.dataToViewHandler(workbook, ct, param.getPk(), param.getNeedColumnToRow());
		
		List<ChartType> chartTypeList = new ArrayList<ChartType>();
		for(ChartType subChartType : ChartType.values()) {
			chartTypeList.add(subChartType);
		}
		
		view.addObject("uri", ChartUrl.root + ChartUrl.simpleChart);
		view.addObject("pk", param.getPk());
		view.addObject("chartTypeList", chartTypeList);

		try {
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return view;

	}
	
}
