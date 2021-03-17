package demo.joy.image.icon.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.image.service.ImageService;
import demo.joy.image.icon.mapper.JoyImageIconMapper;
import demo.joy.image.icon.pojo.constant.IconConstant;
import demo.joy.image.icon.pojo.po.JoyImageIcon;
import demo.joy.image.icon.pojo.po.JoyImageIconExample;
import demo.joy.image.icon.service.JoyIconService;

@Service
public class JoyIconServiceImpl extends JoyIconCommonService implements JoyIconService {

	@Autowired
	private JoyImageIconMapper iconMapper;

	@Autowired
	private ImageService imgService;

	@Override
	public void getIcon(Long id, HttpServletResponse response) {
		if (id == null) {
			return;
		}

		String keyName = IconConstant.ICON_REDIS_PREFIX + String.valueOf(id);

		if (redisTemplate.hasKey(keyName)) {
			String imgPath = String.valueOf(redisTemplate.opsForValue().get(keyName));
			imgService.getImageByPath(response, imgPath);
		} else {
			JoyImageIcon po = iconMapper.selectByPrimaryKey(id);
			if (po == null || StringUtils.isBlank(po.getImgPath())) {
				return;
			}
			loadToRedis(po);
			
			imgService.getImageByPath(response, po.getImgPath());
		}

	}

	@Override
	public void loadAllIconToRedis() {
		JoyImageIconExample example = new JoyImageIconExample();
		example.createCriteria();
		List<JoyImageIcon> poList = iconMapper.selectByExample(example);
		for (JoyImageIcon po : poList) {
			loadToRedis(po);
		}
	}
}
