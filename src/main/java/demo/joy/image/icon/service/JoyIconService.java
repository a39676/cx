package demo.joy.image.icon.service;

public interface JoyIconService {

	/**
	 * 2020-08-26
	 * 鉴于目前图标数量不多
	 * 程序启动时, 将所有游戏图标加载到 redis 中
	 * 
	 */
	void loadAllIconToRedis();

	byte[] getIcon(Long id);

}
