package demo.joy.image.icon.service;

import javax.servlet.http.HttpServletResponse;

public interface JoyIconService {

	/**
	 * 2020-08-26
	 * 鉴于目前图标数量不多
	 * 程序启动时, 将所有游戏图标加载到 redis 中
	 * 
	 */
	void loadAllIconToRedis();

	void getIcon(Long id, HttpServletResponse response);

}
