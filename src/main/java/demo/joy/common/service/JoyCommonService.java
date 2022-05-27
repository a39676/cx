package demo.joy.common.service;

import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import demo.base.system.service.impl.SystemOptionService;
import demo.base.user.service.UserDetailService;
import demo.common.service.CommonService;
import demo.joy.image.service.JoyImageService;
import demo.tool.other.service.TextFilter;

public class JoyCommonService extends CommonService {

	@Autowired
	protected SystemOptionService systemOptionService;
	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;
	@Autowired
	protected UserDetailService userDetailService;
	@Autowired
	protected JoyOptionService optionService;
	@Autowired
	protected JoyImageService imageService;
	@Autowired
	private TextFilter textFilter;
	
	protected String sanitize(String content) {
		PolicyFactory filter = textFilter.getArticleFilter();
		return filter.sanitize(content);
	}
}
