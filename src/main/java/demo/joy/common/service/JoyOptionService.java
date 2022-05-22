package demo.joy.common.service;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import demo.joy.common.pojo.dto.JoyGardenOptionDTO;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class JoyOptionService extends CommonService {

	@Value("${optionFilePath.joy}")
	private String optionFilePath;

	private String imgStorePathPrefix;

	private JoyGardenOptionDTO gradenOptionDTO;

	public String getImgStorePathPrefix() {
		return imgStorePathPrefix;
	}

	public void setImgStorePathPrefix(String imgStorePathPrefix) {
		this.imgStorePathPrefix = imgStorePathPrefix;
	}

	public JoyGardenOptionDTO getGradenOptionDTO() {
		return gradenOptionDTO;
	}

	public void setGradenOptionDTO(JoyGardenOptionDTO gradenOptionDTO) {
		this.gradenOptionDTO = gradenOptionDTO;
	}

	@Override
	public String toString() {
		return "JoyOptionService [imgStorePathPrefix=" + imgStorePathPrefix + ", gradenOptionDTO=" + gradenOptionDTO
				+ "]";
	}

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			JoyOptionService tmp = new Gson().fromJson(jsonStr, JoyOptionService.class);
			BeanUtils.copyProperties(tmp, this);
		} catch (Exception e) {
			log.error("article option loading error: " + e.getLocalizedMessage());
		}
		log.error("article option loaded");
	}

}
