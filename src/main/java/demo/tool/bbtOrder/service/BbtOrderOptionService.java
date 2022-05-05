package demo.tool.bbtOrder.service;

import java.io.File;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class BbtOrderOptionService extends CommonService {

	@Value("${optionFilePath.scheduleClawing}")
	private String optionFilePath;

	private String paramSavingPath;

	private String hsbcWechatPreregistUrl;

	public void refreshConstant() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			BbtOrderOptionService tmp = new Gson().fromJson(jsonStr, BbtOrderOptionService.class);
			BeanUtils.copyProperties(tmp, this);
		} catch (Exception e) {
			log.error("schedule clawing constant loading error: " + e.getLocalizedMessage());
		}
	}

	public String getParamSavingPath() {
		return paramSavingPath;
	}

	public void setParamSavingPath(String paramSavingPath) {
		this.paramSavingPath = paramSavingPath;
	}

	public String getHsbcWechatPreregistUrl() {
		return hsbcWechatPreregistUrl;
	}

	public void setHsbcWechatPreregistUrl(String hsbcWechatPreregistUrl) {
		this.hsbcWechatPreregistUrl = hsbcWechatPreregistUrl;
	}

	@Override
	public String toString() {
		return "BbtOrderOptionService [paramSavingPath=" + paramSavingPath + ", hsbcWechatPreregistUrl="
				+ hsbcWechatPreregistUrl + "]";
	}

}
