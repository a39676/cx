package demo.joy.image.npc.service;

import javax.servlet.http.HttpServletResponse;

public interface JoyImageNpcService {

	void loadAllImageNpcToRedis();

	void getImageNpc(Long id, HttpServletResponse response);

}
