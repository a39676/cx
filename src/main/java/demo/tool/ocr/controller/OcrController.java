package demo.tool.ocr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.controller.CommonController;
import demo.tool.ocr.pojo.constant.OcrUrl;
import demo.tool.ocr.service.OcrService;

@Controller
@RequestMapping(value = OcrUrl.ROOT)
public class OcrController extends CommonController {

	@Autowired
	private OcrService ocrService;

	@GetMapping(value = OcrUrl.UPLOAD_IMG)
	public ModelAndView ocrView() {
		return ocrService.ocrView();
	}

	@PostMapping(value = OcrUrl.UPLOAD_IMG)
	@ResponseBody
	public CommonResult ocr(@RequestParam("file") MultipartFile file, @RequestParam("language") String language) {
		return ocrService.ocrImg(file, language);
	}
	
}
