package demo.ai.complexTool.service.impl;

import org.springframework.stereotype.Service;

import demo.ai.aiArt.service.AiArtCommonService;
import demo.ai.complexTool.service.AiComplexToolService;

@Service
public class AiComplexToolServiceImpl extends AiArtCommonService implements AiComplexToolService {

	@Override
	public void setRecharMarkForAdmin() {
		addRechargeMarkLiveAWeek(aiArtOptionService.getIdOfAdmin());
	}
}
