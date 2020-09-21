package demo.tool.ocr.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;

public interface OcrService {
	
	ModelAndView ocrView();

	void cleanOldOcrImg();

	CommonResult ocrImg(MultipartFile file, String language);

}
