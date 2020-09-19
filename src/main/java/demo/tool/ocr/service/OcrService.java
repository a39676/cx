package demo.tool.ocr.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import auxiliaryCommon.pojo.result.CommonResult;

public interface OcrService {

	void cleanOldOcrImg() throws IOException;

	CommonResult ocrImg(MultipartFile file);

	void reInitCore();

}
