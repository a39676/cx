package demo.article.article.pojo.constant;

public class ArticleConstant {
	
	public static final int DEFAULT_PAGE_SIZE = 6;
	public static final int MAX_PAGE_SIZE = 30;

	public static final long EVALUATION_CACHE_LIVING_TIME = 1000L * 60 * 60 * 24 * 30;
	/** 迷你评价数 */
	public static final int MINI_EVALUATION_COUNT = 15;
	/** 评价平衡比例 */
	public static final double BALANCE_EVALUATION_RATIO = 1.1;
	
	public static final String ARTICLE_IMG_SAVING_FOLDER = "/home/u2/articleImg";
	
	public static final String ARTICLE_STORE_PRE_FIX_PATH = "articleStorePrefixPath";
	public static final String ARTICLE_SUMMARY_STORE_PRE_FIX_PATH = "articleSummaryStorePrefixPath";
	public static final String ARTICLE_BURN_STORE_PRE_FIX_PATH = "articleBurnStorePrefixPath";
	public static final String MAX_ARTICLE_LENGTH = "maxArticleLength";
	public static final String ARTICLE_CHANNEL_REFRESH_MINUTE = "articleChannelRefreshMinute";
	public static final String ARTICLE_CHANNEL_PRE_FIX_STORE_PATH = "articleChannelPrefixStorePath";
	/** 一般用户可以浏览 * 个月内的非置顶内容- */
	public static final String NORMAL_USER_MAX_READING_MONTH = "normalUserMaxReadingMonth";
}
