package demo.article.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.article.article.service.ArticleSummaryService;

@Component
public class ArticleTaskService {

	@Autowired
	private ArticleSummaryService summaryService;
	
	@Scheduled(cron="20 19 13 * * *")
	@Scheduled(cron="20 19 01 * * *")
	public void updateArticleHotExpired() {
		summaryService.updateArticleHotExpired();
	}
}
