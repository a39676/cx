package demo.joy.image.manager.service.impl;

import java.util.Set;

import org.springframework.stereotype.Service;

import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.image.common.service.impl.JoyImageCommonService;
import demo.joy.image.icon.pojo.constant.IconConstant;
import demo.joy.image.manager.service.JoyImageManagerService;
import demo.joy.image.npc.pojo.constant.ImageNpcConstant;

@Service
public class JoyImageManagerServiceImpl extends JoyImageCommonService implements JoyImageManagerService {

	@Override
	public JoyCommonResult cleanJoyImageRedis() {
		JoyCommonResult r = new JoyCommonResult();
		Set<String> iconRedisKeys = redisTemplate.keys(IconConstant.ICON_REDIS_PREFIX + "*");
		int size = iconRedisKeys.size();
		for(String key : iconRedisKeys) {
			redisTemplate.delete(key);
		}
		
		Set<String> imageNpcRedisKeys = redisTemplate.keys(ImageNpcConstant.NPC_REDIS_PREFIX + "*");
		size += imageNpcRedisKeys.size();
		for(String key : imageNpcRedisKeys) {
			redisTemplate.delete(key);
		}
		
		r.successWithMessage("clean count: " + size);
		
		return r;
	}
}
