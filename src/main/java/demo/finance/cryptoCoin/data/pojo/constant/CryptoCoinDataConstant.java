package demo.finance.cryptoCoin.data.pojo.constant;

public class CryptoCoinDataConstant {

	/**
	 * 2020-07-15
	 * 按目前频率, 若10秒存一次数据, 保存8小时的缓存数据
	 */
	public static final int CRYPTO_COIN_CACHE_DATA_LIVE_HOURS = 8;
	
	/**
	 * 2020-10-26
	 * 1分钟的价格汇总, 保存2天
	 */
	public static final int CRYPTO_COIN_1MINUTE_DATA_LIVE_HOURS = 24 * 2;
	
	/**
	 * 2020-07-15
	 * 5分钟的价格汇总, 保存3天
	 */
	public static final int CRYPTO_COIN_5MINUTE_DATA_LIVE_HOURS = 24 * 3;
	
	/**
	 * 2020-07-15
	 * 10分钟的价格汇总, 保存3天
	 */
	public static final int CRYPTO_COIN_10MINUTE_DATA_LIVE_HOURS = 24 * 3;
	
	/**
	 * 2020-07-15
	 * 30分钟的价格汇总, 保存3天
	 */
	public static final int CRYPTO_COIN_30MINUTE_DATA_LIVE_HOURS = 24 * 3;
	
	/**
	 * 2020-07-15
	 * 60分钟的价格汇总, 保存5天
	 */
	public static final int CRYPTO_COIN_60MINUTE_DATA_LIVE_HOURS = 24 * 5;
}
