package demo.pmemo.pojo.constant;

public class PMemoConstant {

	public static final String PMemoRedisKey = "PMemoRedisKey";
	
	/** 默认 PMemo 有效期(秒), 一天 */
	public static final Long defaultValidSeconds = 3600L *24L;
	
	/** 暂定最大5m */
	public static final Long memoMaxSize = 1024L * 1024 * 5;
	
}
