package demo.aiArt.service;

import org.springframework.beans.factory.annotation.Autowired;

import demo.aiArt.service.impl.AiArtOptionService;
import demo.common.service.ToolCommonService;

public abstract class AiArtCommonService extends ToolCommonService {

	@Autowired
	protected AiArtOptionService aiArtOptionService;
}
