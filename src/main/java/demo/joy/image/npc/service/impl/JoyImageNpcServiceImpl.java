package demo.joy.image.npc.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.image.service.ImageService;
import demo.joy.image.icon.pojo.constant.IconConstant;
import demo.joy.image.npc.mapper.JoyImageNpcMapper;
import demo.joy.image.npc.pojo.po.JoyImageNpc;
import demo.joy.image.npc.pojo.po.JoyImageNpcExample;
import demo.joy.image.npc.service.JoyImageNpcService;

@Service
public class JoyImageNpcServiceImpl extends JoyImageNpcCommonService implements JoyImageNpcService {

	@Autowired
	private JoyImageNpcMapper imageNpcMapper;

	@Autowired
	private ImageService imgService;

	@Override
	public void getImageNpc(Long id, HttpServletResponse response) {
		if (id == null) {
			return;
		}

		String keyName = IconConstant.ICON_REDIS_PREFIX + String.valueOf(id);

		if (redisTemplate.hasKey(keyName)) {
			String imgPath = String.valueOf(redisTemplate.opsForValue().get(keyName));
			imgService.getImageByPath(response, imgPath);
		} else {
			JoyImageNpc po = imageNpcMapper.selectByPrimaryKey(id);
			if (po == null || StringUtils.isBlank(po.getImgPath())) {
				return;
			}
			loadToRedis(po);
			
			imgService.getImageByPath(response, po.getImgPath());
		}

	}

	@Override
	public void loadAllImageNpcToRedis() {
		JoyImageNpcExample example = new JoyImageNpcExample();
		example.createCriteria();
		List<JoyImageNpc> poList = imageNpcMapper.selectByExample(example);
		for (JoyImageNpc po : poList) {
			loadToRedis(po);
		}
	}
}
