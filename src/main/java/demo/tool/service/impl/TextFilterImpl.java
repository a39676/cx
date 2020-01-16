package demo.tool.service.impl;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.tool.service.TextFilter;

@Service
public class TextFilterImpl extends CommonService implements TextFilter {

	@Override
	public PolicyFactory getFilter() {
		PolicyFactory policy = new HtmlPolicyBuilder()
			    .allowElements("a").allowUrlProtocols("https").allowAttributes("href").onElements("a").requireRelNofollowOnLinks()
			    .allowElements("img").allowAttributes("src").onElements("img")
			    .allowElements("span").allowStyling()
			    .toFactory();
		return policy;
	}
}
