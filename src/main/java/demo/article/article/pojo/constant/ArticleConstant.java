package demo.article.article.pojo.constant;

public class ArticleConstant {
	
	public static final int defaultPageSize = 6;
	public static final int maxPageSize = 30;

	public static final long evaluationCacheLivingTime = 1000L * 60 * 60 * 24 * 30;
	/** 迷你评价数 */
	public static final int miniEvaluationCount = 15;
	/** 评价平衡比例 */
	public static final double balanceEvaluationRatio = 1.1;
	
	public static final String articleImgSavingFolder = "/home/u2/articleImg";
}
