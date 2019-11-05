package demo.tool.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import demo.tool.pojo.dto.ExcelAnalysisByPkParam;
import demo.tool.pojo.result.UploadExcelResult;

public interface ExcelAnalysisService {

	UploadExcelResult uploadExcel(Map<String, MultipartFile> fileMap, String hostName);

	ModelAndView excelAnalysisByPk(ExcelAnalysisByPkParam param);

}
