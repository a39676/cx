package demo.tool.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import demo.tool.pojo.dto.ExcelAnalysisByPkParam;
import demo.tool.pojo.result.UploadExcelResult;

public interface ExcelAnalysisService {
	
	ModelAndView uploadTestView(String pk, int chartType, String columnToRow, HttpServletRequest request);

	UploadExcelResult uploadExcel(Map<String, MultipartFile> fileMap, HttpServletRequest hostName);

	ModelAndView excelAnalysisByPk(ExcelAnalysisByPkParam param);

}
