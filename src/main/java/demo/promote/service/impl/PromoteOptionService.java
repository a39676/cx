package demo.promote.service.impl;

import java.io.File;
import java.util.HashMap;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import demo.config.customComponent.OptionFilePathConfigurer;
import demo.promote.pojo.dto.PromoteImgDTO;
import jakarta.annotation.PostConstruct;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class PromoteOptionService extends CommonService {

	private HashMap<String, PromoteImgDTO> promoteImgDtoMap = new HashMap<>();

	public HashMap<String, PromoteImgDTO> getPromoteImgDtoMap() {
		return promoteImgDtoMap;
	}

	public void setPromoteImgDtoMap(HashMap<String, PromoteImgDTO> promoteImgDtoMap) {
		this.promoteImgDtoMap = promoteImgDtoMap;
	}

	@Override
	public String toString() {
		return "PromoteOptionService [promoteImgDtoMap=" + promoteImgDtoMap + "]";
	}

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(OptionFilePathConfigurer.PROMOTE);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.PROMOTE);
			PromoteOptionService tmp = new Gson().fromJson(jsonStr, this.getClass());
			BeanUtils.copyProperties(tmp, this);
		} catch (Exception e) {
			log.error("promote option loading error: " + e.getLocalizedMessage());
		}
		log.error("promote option loaded");
	}

}
