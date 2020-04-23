package demo.article.article.service;

import javax.servlet.http.HttpServletRequest;

import demo.article.article.pojo.dto.FindArticleLongSummaryListDTO;
import demo.article.article.pojo.result.jsonRespon.FindArticleLongSummaryListResultV3;

public interface ArticleSummaryService {

	int insertArticleLongSummary(Long userId, Long articleId, String title, String finalFilePath);
	
	FindArticleLongSummaryListResultV3 summaryListByChannelIdV4(FindArticleLongSummaryListDTO param,
			HttpServletRequest request);

}
