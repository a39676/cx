package demo.tool.ocr.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;

public interface OcrService {
	
	ModelAndView ocrView();

	void cleanOldOcrImg() throws IOException;

	CommonResult ocrImg(MultipartFile file, String language);

}
