package demo.joy.garden.service.impl;

import java.io.File;
import java.util.HashMap;

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
public class JoyGardenOptionService extends CommonService {

	@Value("${optionFilePath.joyGarden}")
	private String optionFilePath;

	private HashMap<Integer, Integer> createFieldConsumePointMap;

	private Integer fieldMaxSize;

	@Override
	public String toString() {
		return "JoyGardenOptionService [createFieldConsumePointMap=" + createFieldConsumePointMap + ", fieldMaxSize="
				+ fieldMaxSize + "]";
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
			JoyGardenOptionService tmp = new Gson().fromJson(jsonStr, JoyGardenOptionService.class);
			BeanUtils.copyProperties(tmp, this);
		} catch (Exception e) {
			log.error("Joy Garden option loading error: " + e.getLocalizedMessage());
		}
		log.error("Joy Garden option loaded");
	}

	public HashMap<Integer, Integer> getCreateFieldConsumePointMap() {
		return createFieldConsumePointMap;
	}

	public void setCreateFieldConsumePointMap(HashMap<Integer, Integer> createFieldConsumePointMap) {
		this.createFieldConsumePointMap = createFieldConsumePointMap;
	}

	public Integer getFieldMaxSize() {
		return fieldMaxSize;
	}

	public void setFieldMaxSize(Integer fieldMaxSize) {
		this.fieldMaxSize = fieldMaxSize;
	}

}
