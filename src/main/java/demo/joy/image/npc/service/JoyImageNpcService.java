package demo.joy.image.npc.service;

import javax.servlet.http.HttpServletResponse;

public interface JoyImageNpcService {

	/**
	 * 2020-08-26
	 * 鉴于目前图标数量不多
	 * 程序启动时, 将所有游戏图标加载到 redis 中
	 * 
	 */
	void loadAllImageNpcToRedis();

	void getImageNpc(Long id, HttpServletResponse response);

}
