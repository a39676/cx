package demo.base.system.service.impl;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.system.pojo.bo.SystemConstant;
import demo.base.system.service.GlobalOptionService;
import demo.common.service.CommonService;

@Service
public class GlobalOptionServiceImpl extends CommonService implements GlobalOptionService {

	@Autowired
	private SystemConstantService constantService;

	private String mainSavingFolder_linx = "/home/u2";
	private String mainSavingFolder_win = "d:" + mainSavingFolder_linx;
	private String parameterSavingFolder = "/cx/parameterFiles";

	private String parameterSavingFolderRedisKey = "parameterSavingFolderRedisKey";

	
	@Override
	public boolean checkFolderExists(String path) {
		File f = new File(path);
		if(!f.exists() || !f.isDirectory()) {
			return f.mkdirs();
		} else {
			return true;
		}
	}

	@Override
	public String getParameterSavingFolder() {
		String dir = constantService.getValByName(parameterSavingFolderRedisKey);

		if (StringUtils.isNotBlank(dir)) {
			checkFolderExists(dir);
			return pathChangeByDetectOS(dir);
		}

		if (isWindows()) {
			dir = mainSavingFolder_win + parameterSavingFolder;
		} else {
			dir = mainSavingFolder_linx + parameterSavingFolder;
		}
		dir = pathChangeByDetectOS(dir);

		SystemConstant systemConstant = new SystemConstant();
		systemConstant.setConstantName(parameterSavingFolderRedisKey);
		systemConstant.setConstantValue(dir);
		constantService.setValByName(systemConstant);

		checkFolderExists(dir);
		return dir;
	}
	

}
