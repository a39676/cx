package demo.article.article.service;

import demo.article.article.pojo.dto.FindArticleLongSummaryListDTO;
import demo.article.article.pojo.result.jsonRespon.FindArticleLongSummaryListResult;
import jakarta.servlet.http.HttpServletRequest;

public interface ArticleSummaryService {

	int insertArticleLongSummary(Long userId, Long articleId, String finalFilePath);

	int updateArticleLongSummary(Long userId, Long articleId, String finalFilePath);

	FindArticleLongSummaryListResult summaryListByChannelId(FindArticleLongSummaryListDTO param,
			HttpServletRequest request);

	FindArticleLongSummaryListResult summaryListWithoutChannel(FindArticleLongSummaryListDTO param,
			HttpServletRequest request);

	void updateArticleHotExpired();

}
