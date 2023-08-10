package demo.ai.common.service.impl;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;

@Scope("singleton")
@Service
public class AiCommonOptionService extends CommonService {

	@Value("${optionFilePath.aiCommon}")
	private String optionFilePath;

	private Long idOfAdmin;

	public Long getIdOfAdmin() {
		return idOfAdmin;
	}

	public void setIdOfAdmin(Long idOfAdmin) {
		this.idOfAdmin = idOfAdmin;
	}

	@Override
	public String toString() {
		return "AiCommonOptionService [idOfAdmin=" + idOfAdmin + "]";
	}

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			log.error("AI common option loaded");
		} catch (Exception e) {
			log.error("AI common option loading error: " + e.getLocalizedMessage());
		}
	}
}
