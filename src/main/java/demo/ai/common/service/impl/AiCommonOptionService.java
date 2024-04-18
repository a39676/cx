package demo.ai.common.service.impl;

import java.io.File;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import demo.config.costom_component.OptionFilePathConfigurer;
import jakarta.annotation.PostConstruct;

@Scope("singleton")
@Service
public class AiCommonOptionService extends CommonService {

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
		File optionFile = new File(OptionFilePathConfigurer.AI_COMMON);
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
