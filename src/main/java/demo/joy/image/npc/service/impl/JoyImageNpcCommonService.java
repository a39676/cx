package demo.joy.image.npc.service.impl;

import demo.joy.image.common.service.impl.JoyImageCommonService;
import demo.joy.image.npc.pojo.constant.ImageNpcConstant;
import demo.joy.image.npc.pojo.po.JoyImageNpc;

public abstract class JoyImageNpcCommonService extends JoyImageCommonService {

	protected void loadToRedis(JoyImageNpc po) {
		redisTemplate.opsForValue().set(ImageNpcConstant.NPC_REDIS_PREFIX + String.valueOf(po.getId()), po.getImgPath());
	}
	
}
