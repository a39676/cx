package demo.config.costom_component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import demo.tool.ocr.pojo.type.TessLanguageType;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * 2020-09-15 a copy from ATTool
 * 2020-09-19 modify
 */
public class Tess {

	private Map<TessLanguageType, ITesseract> tesseractInstanceMap = new HashMap<>();

	private String getDataPath() {
		String osName = System.getProperty("os.name");
		if (osName != null && osName.toLowerCase().contains("linux")) {
			return "/home/u2/auxiliary/tessdata";
		} else {
			return "d:/soft/tessdataInUse";
		}
	}
	
	public ITesseract initITesseract(TessLanguageType languageType) {
		ITesseract instance = new Tesseract();
		instance.setDatapath(getDataPath());
		if(languageType != null) {
			instance.setLanguage(languageType.getName());
		}
		return instance;
	}
	
	public ITesseract getITesseractV2(boolean reInit, TessLanguageType languageType) {
		if (languageType == null) {
			languageType = TessLanguageType.ENG;
		}
		
		ITesseract result = tesseractInstanceMap.get(languageType);
		if(reInit == true || result == null) {
			result = initITesseract(languageType);
			tesseractInstanceMap.put(languageType, result);
		}
		return result;
	}
	
	public ITesseract getITesseractV2(TessLanguageType languageType) {
		return getITesseractV2(false, languageType);
	}
	
	public ITesseract getITesseractV2() {
		return getITesseractV2(false, TessLanguageType.ENG);
	}

	public String ocr(String imgPath) {
		return ocr(imgPath, TessLanguageType.ENG);
	}
	
	public String ocr(String imgPath, TessLanguageType languageType) {
		File imageFile = new File(imgPath);
		String result = null;
		try {
			result = getITesseractV2(languageType).doOCR(imageFile);
		} catch (TesseractException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String ocr(String imgPath, String language, boolean numberAndLetterOnly) {
		TessLanguageType languageType = TessLanguageType.getType(language);
		String result = ocr(imgPath, languageType);
		if (numberAndLetterOnly && StringUtils.isNotBlank(result)) {
			result = result.replaceAll("[^\\dA-Za-z]", "");
		}
		return result;
	}

//	public static void main(String[] args) {
//		Tess t = new Tess();
//		String result = t.ocr("", TessLanguageType.CHI_SIM);
//		System.out.println(result);
//	}
}
