package demo.joy.image.icon.service.impl;

import demo.joy.image.common.service.impl.JoyImageCommonService;
import demo.joy.image.icon.pojo.constant.IconConstant;
import demo.joy.image.icon.pojo.po.JoyImageIcon;

public abstract class JoyIconCommonService extends JoyImageCommonService {

	protected void loadToRedis(JoyImageIcon po) {
		redisTemplate.opsForValue().set(IconConstant.ICON_REDIS_PREFIX + String.valueOf(po.getId()), po.getImgPath());
	}
}
