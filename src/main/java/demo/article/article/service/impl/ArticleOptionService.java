package demo.article.article.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.article.article.pojo.vo.ArticleChannelVO;
import demo.base.system.pojo.result.HostnameType;
import demo.common.service.CommonService;
import demo.config.customComponent.OptionFilePathConfigurer;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class ArticleOptionService extends CommonService {

	private Long maxArticleLength = 0L;
	private Integer defaultPageSize = 6;
	private Integer maxPageSize = 30;
	private Long evaluationCacheLivingTime = 1000L * 60 * 60 * 24 * 30;
	/** 迷你评价数 */
	private Integer minEvaluationCount = 15;
	/** 评价平衡比例 */
	private Double balanceEvaluationRatio = 1.1;
	private String articleBurnStorePrefixPath;
	private String articleStorePrefixPath;
	private String articleImageSavingFolder;
	private String articleSummaryStorePrefixPath;
	private String articleChannelPrefixStorePath;
	/** 一般用户可以浏览 * 个月内的非置顶内容- */
	private Long normalUserMaxReadingMonth = 12L;
	private String donateImgUrl;

	private HashMap<HostnameType, List<ArticleChannelVO>> publicChannels = new HashMap<>();

	@Override
	public String toString() {
		return "ArticleOptionService [maxArticleLength=" + maxArticleLength + ", defaultPageSize=" + defaultPageSize
				+ ", maxPageSize=" + maxPageSize + ", evaluationCacheLivingTime=" + evaluationCacheLivingTime
				+ ", minEvaluationCount=" + minEvaluationCount + ", balanceEvaluationRatio=" + balanceEvaluationRatio
				+ ", articleBurnStorePrefixPath=" + articleBurnStorePrefixPath + ", articleStorePrefixPath="
				+ articleStorePrefixPath + ", articleImageSavingFolder=" + articleImageSavingFolder
				+ ", articleSummaryStorePrefixPath=" + articleSummaryStorePrefixPath
				+ ", articleChannelPrefixStorePath=" + articleChannelPrefixStorePath + ", normalUserMaxReadingMonth="
				+ normalUserMaxReadingMonth + ", donateImgUrl=" + donateImgUrl + ", publicChannels=" + publicChannels
				+ "]";
	}

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(OptionFilePathConfigurer.ARTICLE);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.ARTICLE);
			ArticleOptionService tmp = new Gson().fromJson(jsonStr, this.getClass());
			BeanUtils.copyProperties(tmp, this);
			log.error("article option loaded");
		} catch (Exception e) {
			log.error("article option loading error: " + e.getLocalizedMessage());
		}
	}

	public Long getMaxArticleLength() {
		return maxArticleLength;
	}

	public void setMaxArticleLength(Long maxArticleLength) {
		this.maxArticleLength = maxArticleLength;
	}

	public Integer getDefaultPageSize() {
		return defaultPageSize;
	}

	public void setDefaultPageSize(Integer defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}

	public Integer getMaxPageSize() {
		return maxPageSize;
	}

	public void setMaxPageSize(Integer maxPageSize) {
		this.maxPageSize = maxPageSize;
	}

	public Long getEvaluationCacheLivingTime() {
		return evaluationCacheLivingTime;
	}

	public void setEvaluationCacheLivingTime(Long evaluationCacheLivingTime) {
		this.evaluationCacheLivingTime = evaluationCacheLivingTime;
	}

	public Integer getMinEvaluationCount() {
		return minEvaluationCount;
	}

	public void setMinEvaluationCount(Integer minEvaluationCount) {
		this.minEvaluationCount = minEvaluationCount;
	}

	public Double getBalanceEvaluationRatio() {
		return balanceEvaluationRatio;
	}

	public void setBalanceEvaluationRatio(Double balanceEvaluationRatio) {
		this.balanceEvaluationRatio = balanceEvaluationRatio;
	}

	public String getArticleBurnStorePrefixPath() {
		return System.getProperty("user.home") + articleBurnStorePrefixPath;
	}

	public void setArticleBurnStorePrefixPath(String articleBurnStorePrefixPath) {
		this.articleBurnStorePrefixPath = articleBurnStorePrefixPath;
	}

	public String getArticleStorePrefixPath() {
		return System.getProperty("user.home") + articleStorePrefixPath;
	}

	public void setArticleStorePrefixPath(String articleStorePrefixPath) {
		this.articleStorePrefixPath = articleStorePrefixPath;
	}

	public String getArticleImageSavingFolder() {
		return System.getProperty("user.home") + articleImageSavingFolder;
	}

	public void setArticleImageSavingFolder(String articleImageSavingFolder) {
		this.articleImageSavingFolder = articleImageSavingFolder;
	}

	public String getArticleSummaryStorePrefixPath() {
		return System.getProperty("user.home") + articleSummaryStorePrefixPath;
	}

	public void setArticleSummaryStorePrefixPath(String articleSummaryStorePrefixPath) {
		this.articleSummaryStorePrefixPath = articleSummaryStorePrefixPath;
	}

	public String getArticleChannelPrefixStorePath() {
		return System.getProperty("user.home") + articleChannelPrefixStorePath;
	}

	public void setArticleChannelPrefixStorePath(String articleChannelPrefixStorePath) {
		this.articleChannelPrefixStorePath = articleChannelPrefixStorePath;
	}

	public Long getNormalUserMaxReadingMonth() {
		return normalUserMaxReadingMonth;
	}

	public void setNormalUserMaxReadingMonth(Long normalUserMaxReadingMonth) {
		this.normalUserMaxReadingMonth = normalUserMaxReadingMonth;
	}

	public HashMap<HostnameType, List<ArticleChannelVO>> getPublicChannels() {
		return publicChannels;
	}

	public void setPublicChannels(HashMap<HostnameType, List<ArticleChannelVO>> publicChannels) {
		this.publicChannels = publicChannels;
	}

	public String getDonateImgUrl() {
		return donateImgUrl;
	}

	public void setDonateImgUrl(String donateImgUrl) {
		this.donateImgUrl = donateImgUrl;
	}

}
