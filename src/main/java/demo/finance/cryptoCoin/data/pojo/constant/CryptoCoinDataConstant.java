package demo.finance.cryptoCoin.data.pojo.constant;

public class CryptoCoinDataConstant {
	
	
	/**
	 * 2021-01-19
	 * redis 价格缓存, key格式
	 * crypto_coin_cache_%s_%s_%s ---> crypto_coin_cache_BTC_USD_2021-12-31_23:59
	 */
	public static final String CRYPTO_COIN_CACHE_REDIS_KEY_FORMAT = "crypto_coin_cache_%s_%s_%s";
	
	/**
	 * 2021-01-19
	 * 从 crytpo compare web socket 实时获取数据, 最多保存10分钟
	 * 并且每种虚拟币 兑 货币, 应只保存最新数值
	 */
	public static final int CRYPTO_COIN_CACHE_DATA_LIVE_MINUTES = 1;
	
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
