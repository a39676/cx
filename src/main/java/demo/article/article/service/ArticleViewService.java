package demo.article.article.service;

import java.util.List;

import demo.article.article.pojo.po.ArticleViewCount;

public interface ArticleViewService {

	ArticleViewCount findArticleViewCountByArticleId(Long articleId);

	List<ArticleViewCount> findArticleViewCountByArticleId(List<Long> articleIdList);

	int insertOrUpdateViewCount(Long articleId);

}
