package demo.finance.cryptoCoin.pojo.constant;

public class CryptoCoinConstant {

	/**
	 * 2020-07-15
	 * 按目前频率, 若10秒存一次数据, 保存8小时的缓存数据
	 */
	public static final int CRYPTO_COIN_CACHE_DATA_LIVE_HOURS = 8;
	
	/**
	 * 2020-07-15
	 * 5分钟的价格汇总, 保存10天
	 */
	public static final int CRYPTO_COIN_5MINUTE_DATA_LIVE_HOURS = 24 * 10;
	
	/**
	 * 2020-07-15
	 * 10分钟的价格汇总, 保存20天
	 */
	public static final int CRYPTO_COIN_10MINUTE_DATA_LIVE_HOURS = 24 * 20;
	
	/**
	 * 2020-07-15
	 * 30分钟的价格汇总, 保存30天
	 */
	public static final int CRYPTO_COIN_30MINUTE_DATA_LIVE_HOURS = 24 * 30;
	
	/**
	 * 2020-07-15
	 * 60分钟的价格汇总, 保存90天
	 */
	public static final int CRYPTO_COIN_60MINUTE_DATA_LIVE_HOURS = 24 * 90;
}
