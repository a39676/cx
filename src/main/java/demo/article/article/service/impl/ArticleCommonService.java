package demo.article.article.service.impl;

import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;

import demo.base.system.service.HostnameService;
import demo.base.system.service.impl.RedisOriginalConnectService;
import demo.base.system.service.impl.SystemConstantService;
import demo.common.service.CommonService;
import demo.tool.other.service.TextFilter;
import demo.tool.other.service.VisitDataService;
import toolPack.numericHandel.NumericUtilCustom;

public class ArticleCommonService extends CommonService {

	@Autowired
	private TextFilter textFilter;
	@Autowired
	protected ArticleConstantService articleConstantService;
	@Autowired
	protected NumericUtilCustom numberUtil;
	@Autowired
	protected VisitDataService visitDataService;
	@Autowired
	protected SystemConstantService systemConstantService;
	@Autowired
	protected HostnameService hostnameService;
	@Autowired
	protected RedisOriginalConnectService redisOriginalConnectService;

	protected String sanitize(String content) {
		PolicyFactory filter = textFilter.getArticleFilter();
		return filter.sanitize(content);
	}

}
