package demo.aiArt.service;

import org.springframework.beans.factory.annotation.Autowired;

import demo.aiArt.service.impl.AiArtOptionService;
import demo.aiChat.service.impl.AiChatCommonService;

public abstract class AiArtCommonService extends AiChatCommonService {

	@Autowired
	protected AiArtOptionService aiArtOptionService;
}
