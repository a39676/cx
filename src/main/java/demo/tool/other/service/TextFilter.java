package demo.tool.other.service;

import org.owasp.html.PolicyFactory;

public interface TextFilter {

	PolicyFactory getArticleFilter();

	PolicyFactory getAllFilter();

}
