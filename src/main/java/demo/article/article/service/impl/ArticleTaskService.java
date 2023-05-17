package demo.article.article.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.article.article.service.ArticleAdminService;
import demo.article.article.service.ArticleSummaryService;
import demo.article.article.service.ArticleValidService;
import demo.base.task.service.CommonTaskService;

@Component
public class ArticleTaskService extends CommonTaskService {

	@Autowired
	private ArticleSummaryService summaryService;
	@Autowired
	private ArticleValidService validService;
	@Autowired
	private ArticleAdminService articleAdminService;
	

	@Scheduled(cron = "20 19 13 * * *")
	@Scheduled(cron = "20 19 01 * * *")
	public void updateArticleHotExpired() {
		summaryService.updateArticleHotExpired();
	}

	@Scheduled(cron = "20 22 * * * *")
	public void deleteArticleByValidSetting() {
		List<Long> expiredIdList = validService.getExpiredId();
		if (expiredIdList != null && !expiredIdList.isEmpty()) {
			for (Long id : expiredIdList) {
				try {
					articleAdminService.deleteArticle(id);
				} catch (Exception e) {
				}
			}
		}
		
		validService.cleanOldData();
	}
}
