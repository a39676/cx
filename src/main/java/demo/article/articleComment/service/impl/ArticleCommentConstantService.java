package demo.article.articleComment.service.impl;

import org.springframework.stereotype.Service;

import demo.common.service.CommonService;

@Service
public class ArticleCommentConstantService extends CommonService {

	private String articleCommentStorePrefixPath = null;
	private Long maxArticleCommentLength = 0L;
	
	
	public String getArticleCommentStorePrefixPath() {
//		TODO building article comment constant service
		return articleCommentStorePrefixPath;
	}
	
	public Long getMaxArticleCommentLength() {
//		TODO building article comment constant service
		return maxArticleCommentLength;
	}
	
	
}
