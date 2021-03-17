package demo.article.article.service.impl;

import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;

import demo.common.service.CommonService;
import demo.tool.service.TextFilter;

public class ArticleCommonService extends CommonService {

	@Autowired
	private TextFilter textFilter;

	protected String sanitize(String content) {
		PolicyFactory filter = textFilter.getArticleFilter();
		return filter.sanitize(content);
	}

}
