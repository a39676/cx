package demo.article.article.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;

@Scope("singleton")
@Service
public class ArticleConstantService extends CommonService {

	/*
	 * TODO 不再从数据库获取, 改从json 文件中读取 文件路径配置? application.yml 中配置? 先从文件读取, gson协助填充,
	 * 然后refresh 方法中再将 ARTICLE_STORE_PRE_FIX_PATH 之类的文件路径数据再重写, 加上
	 * articleSavingFolderPrefix
	 */

	@Value("${optionFilePath.article}")
	private String optionFilePath;

	public void testing() {
		System.out.println(optionFilePath);
	}

	private Long maxArticleLength = 0L;
	private Integer defaultPageSize = 6;
	private Integer maxPageSize = 30;
	private Long evaluationCacheLivingTime = 1000L * 60 * 60 * 24 * 30;
	/** 迷你评价数 */
	private Integer minEvaluationCount = 15;
	/** 评价平衡比例 */
	private Double balanceEvaluationRatio = 1.1;
	private String articleSavingFolderPrefix;
	private String articleBurnStorePrefixPath;
	private String articleStorePrefixPath;

	private String articleImageSavingFolder;
	private String articleSummaryStorePrefixPath;
	private String articleChannelPrefixStorePath;
	/** 一般用户可以浏览 * 个月内的非置顶内容- */
	private Long normalUserMaxReadingMonth = 12L;

	public void refreshConstant() {
//		TODO
	}

	public String getTesting() {
		return optionFilePath;
	}

	public String getArticleBurnStorePrefixPath() {
		return articleBurnStorePrefixPath;
	}

	public String getArticleStorePrefixPath() {
		return articleStorePrefixPath;
	}

	public Long getMaxArticleLength() {
		return maxArticleLength;
	}

	public Integer getDefaultPageSize() {
		return defaultPageSize;
	}

	public Integer getMaxPageSize() {
		return maxPageSize;
	}

	public Long getEvaluationCacheLivingTime() {
		return evaluationCacheLivingTime;
	}

	public Integer getMinEvaluationCount() {
		return minEvaluationCount;
	}

	public Double getBalanceEvaluationRatio() {
		return balanceEvaluationRatio;
	}

	public String getArticleSavingFolderPrefix() {
		return articleSavingFolderPrefix;
	}

	public String getArticleImageSavingFolder() {
		return articleImageSavingFolder;
	}

	public String getArticleSummaryStorePrefixPath() {
		return articleSummaryStorePrefixPath;
	}

	public String getArticleChannelPrefixStorePath() {
		return articleChannelPrefixStorePath;
	}

	public Long getNormalUserMaxReadingMonth() {
		return normalUserMaxReadingMonth;
	}

}
