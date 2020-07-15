package demo.finance.metal.pojo.constant;

public class MetalConstant {

	/**
	 * 2020-07-15
	 * 按目前频率, 若10秒存一次数据, 保存8小时的缓存数据
	 */
	public static final int metalPriceCacheDateLiveHours = 8;
	
	/**
	 * 2020-07-15
	 * 5分钟的价格汇总, 保存10天
	 */
	public static final int metalPrice5MinuteDateLiveHours = 24 * 10;
	
	/**
	 * 2020-07-15
	 * 10分钟的价格汇总, 保存20天
	 */
	public static final int metalPrice10MinuteDateLiveHours = 24 * 20;
	
	/**
	 * 2020-07-15
	 * 30分钟的价格汇总, 保存30天
	 */
	public static final int metalPrice30MinuteDateLiveHours = 24 * 30;
	
	/**
	 * 2020-07-15
	 * 60分钟的价格汇总, 保存90天
	 */
	public static final int metalPrice60MinuteDateLiveHours = 24 * 90;
}
