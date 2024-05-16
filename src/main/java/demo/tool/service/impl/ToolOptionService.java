package demo.tool.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import demo.config.customComponent.OptionFilePathConfigurer;
import jakarta.annotation.PostConstruct;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class ToolOptionService extends CommonService {

	private List<String> imgbbApiKeyList;

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(OptionFilePathConfigurer.TOOL);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.TOOL);
			ToolOptionService tmp = new Gson().fromJson(jsonStr, this.getClass());
			BeanUtils.copyProperties(tmp, this);
		} catch (Exception e) {
			log.error("tool option loading error: " + e.getLocalizedMessage());
		}
		log.error("tool option loaded");
	}

	public List<String> getImgbbApiKeyList() {
		return imgbbApiKeyList;
	}

	public void setImgbbApiKeyList(List<String> imgbbApiKeyList) {
		this.imgbbApiKeyList = imgbbApiKeyList;
	}

	@Override
	public String toString() {
		return "ToolOptionService [imgbbApiKeyList=" + imgbbApiKeyList + "]";
	}

}
