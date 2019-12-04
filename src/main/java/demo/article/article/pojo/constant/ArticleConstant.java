package demo.article.article.pojo.constant;

public class ArticleConstant {
	
	public static final int defaultPageSize = 6;
	public static final int maxPageSize = 30;

//	channel coefficient
	public static final int passArticleCoefficient = 10;
	public static final int rejectArticleCoefficient = -3;
	public static final int clickUpCoefficient = 1;
	public static final int clickDownCoefficient = -1;
	public static final double coefficientToPostLimit = 10D;
	public static final int badUserCoefficientLimit = -20;

	public static final long evaluationCacheLivingTime = 1000L * 60 * 60 * 24 * 30;
	
	/** 迷你评价数 */
	public static final int miniEvaluationCount = 15;
	/** 评价平衡比例 */
	public static final double balanceEvaluationRatio = 1.1;
	
	public static final int firstVisitDailyPostLimit = 5;
	
	public static final String insteadOfNullImage = "https://res.cloudinary.com/dy20bdekn/image/upload/v1561961349/forest-4296305_640.jpg";
}
