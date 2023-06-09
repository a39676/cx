package demo.tool.service.impl;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class ToolOptionService extends CommonService {

	@Value("${optionFilePath.tool}")
	private String optionFilePath;

	private String promoteImgUrl;

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			ToolOptionService tmp = new Gson().fromJson(jsonStr, ToolOptionService.class);
			BeanUtils.copyProperties(tmp, this);
		} catch (Exception e) {
			log.error("tool option loading error: " + e.getLocalizedMessage());
		}
		log.error("tool option loaded");
	}

	public String getPromoteImgUrl() {
		return promoteImgUrl;
	}

	public void setPromoteImgUrl(String promoteImgUrl) {
		this.promoteImgUrl = promoteImgUrl;
	}

	@Override
	public String toString() {
		return "ToolOptionsService [promoteImgUrl=" + promoteImgUrl + "]";
	}

}
