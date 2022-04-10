package demo.article.article.service;

import javax.servlet.http.HttpServletRequest;

import demo.article.article.pojo.dto.FindArticleLongSummaryListDTO;
import demo.article.article.pojo.result.jsonRespon.FindArticleLongSummaryListResult;

public interface ArticleSummaryService {

	int insertArticleLongSummary(Long userId, Long articleId, String title, String finalFilePath);
	
	FindArticleLongSummaryListResult summaryListByChannelId(FindArticleLongSummaryListDTO param,
			HttpServletRequest request);

	FindArticleLongSummaryListResult summaryListWithoutChannel(FindArticleLongSummaryListDTO param,
			HttpServletRequest request);

}
