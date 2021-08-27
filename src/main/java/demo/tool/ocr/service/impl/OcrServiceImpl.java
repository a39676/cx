package demo.tool.ocr.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.service.CommonService;
import demo.config.costom_component.Tess;
import demo.tool.ocr.pojo.constant.OcrView;
import demo.tool.ocr.pojo.result.UploadResult;
import demo.tool.ocr.pojo.type.TessLanguageType;
import demo.tool.ocr.service.OcrService;
import toolPack.constant.FileSuffixNameConstant;
import toolPack.stringHandle.StringUtilCustom;

@Service
public class OcrServiceImpl extends CommonService implements OcrService {

	@Autowired
	private Tess tess;

	private String getOcrImgTmpStorePath() {
		if (isLinux()) {
			return "/home/u2/ocrImg";
		} else {
			return "d:/home/u2/ocrImg";
		}
	}
	
	@Override
	public ModelAndView ocrView() {
		ModelAndView view = new ModelAndView(OcrView.OCR_VIEW);
		view.addObject("languageTypeList", TessLanguageType.values());
		return view;
	}

	@Override
	public void cleanOldOcrImg() {
		File storeFolder = new File(getOcrImgTmpStorePath());
		if (!storeFolder.exists()) {
			return;
		}
		LocalDateTime thirtyMinsAgo = LocalDateTime.now().minusMinutes(30);
		LocalDateTime fileCreatetime = null;
		Path p = null;
		BasicFileAttributes attr = null;
		for (File f : storeFolder.listFiles()) {
			if(f.isFile()) {
				p = Path.of(f.getAbsolutePath());
				try {
					attr = Files.readAttributes(p, BasicFileAttributes.class);
				} catch (IOException e) {
					continue;
				}
				fileCreatetime = LocalDateTime.ofInstant(attr.creationTime().toInstant(), ZoneId.systemDefault());
				if (thirtyMinsAgo.isAfter(fileCreatetime)) {
					f.deleteOnExit();
				}
			}
		}
	}

	@Override
	public CommonResult ocrImg(MultipartFile file, String language) {
		CommonResult r = new CommonResult();

		StringUtilCustom stringUtil = new StringUtilCustom();
		String filename = file.getOriginalFilename();
		String suffix = stringUtil.getFileSuffixName(filename);

		if (!FileSuffixNameConstant.IMAGE_SUFFIX.contains(suffix)) {
			r.addMessage("img files only");
			return r;
		}

//		TODO
		UploadResult uploadResult = new UploadResult();
//		UploadResult uploadResult = uploadService.__privateUploadV2(file, getOcrImgTmpStorePath());
		if (uploadResult.isFail()) {
			r.addMessage(uploadResult.getMessage());
			return r;
		}

		String imgPath = uploadResult.getUploadSuccessFileNameList().get(0);

		TessLanguageType languageType = TessLanguageType.getType(language);
		String ocrResultStr = tess.ocr(imgPath, languageType);

		r.setMessage(ocrResultStr);
		r.setIsSuccess();
		return r;
	}
	
}
