package demo.config.costom_component;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * 2020-09-15 a copy from ATTool
 *
 */
public class Tess {

	private ITesseract instance = null;

	public void initITesseract() {
		String dataPath = null;
		String osName = System.getProperty("os.name");
		if (osName != null && osName.toLowerCase().contains("linux")) {
			dataPath = "/home/u2/auxiliary/tessdata";
		} else {
			dataPath = "d:/soft/tessdataInUse";
		}
		if (instance == null) {
			instance = new Tesseract();
			instance.setDatapath(dataPath);
		}
	}

	public void initITesseract(String dataPath) {
		if (instance == null) {
			instance = new Tesseract();
			instance.setDatapath(dataPath);
		}
	}

	public ITesseract getITesseract() {
		if (instance == null) {
			initITesseract();
			if (instance == null) {
				throw new RuntimeException("please initITesseract before use");
			}
		}
		return instance;
	}

	public String ocr(String imgPath) {
		File imageFile = new File(imgPath);
		String result = null;
		try {
			result = getITesseract().doOCR(imageFile);
		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}
		return result;
	}

	public String ocr(String imgPath, boolean numberAndLetterOnly) {
		String result = ocr(imgPath);
		if (numberAndLetterOnly && StringUtils.isNotBlank(result)) {
			result = result.replaceAll("[^\\dA-Za-z]", "");
		}
		return result;
	}

}
