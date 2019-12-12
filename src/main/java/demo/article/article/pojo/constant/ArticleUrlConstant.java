package demo.article.article.pojo.constant;

public class ArticleUrlConstant {
	
	public static final String root = "/article";
	public static final String createBurnMessage = "/createBurnMessage";
	public static final String creatingBurnMessage = "/creatingBurnMessage";
	public static final String readBurningMessage = "/readBurningMessage";
	public static final String burnMessage = "/burnMessage";
	public static final String findChannels = "/findChannels";
	/** 创建文章数据接口 */
	public static final String createArticleLong = "/createArticleLong";
	/** 打开创建文章的界面 */
	public static final String creatingArticleLong = "/creatingArticleLong";
	/** 以频道uuid获取文章缩略列表 */
	public static final String articleLongSummaryListByChannel = "/articleLongSummaryListByChannel";
//	public static final String articleLongSummarySubListByChannel = "/articleLongSummarySubListByChannel";
	public static final String readArticleLong = "/readArticleLong";
	public static final String editArticleLong = "/editArticleLong";
	public static final String deleteArticle = "/deleteArticle";
	public static final String findEvaluationStatisticsByArticleId = "/findEvaluationStatisticsByArticleId";
	public static final String insertArticleLongEvaluation = "/insertArticleLongEvaluation";
	public static final String insertArticleLongCommentEvaluation = "/insertArticleLongCommentEvaluation";
	public static final String likeOrHateThisChannel = "/likeOrHateThisChannel";
	public static final String articleLongFeedback = "/articleLongFeedback";
}
