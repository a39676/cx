package demo.joy.image.manager.service.impl;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import demo.image.pojo.result.ImgHandleSrcDataResult;
import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.image.common.service.impl.JoyImageCommonService;
import demo.joy.image.icon.pojo.constant.IconConstant;
import demo.joy.image.manager.pojo.dto.JoyImageUploadDTO;
import demo.joy.image.manager.pojo.result.JoyImageUploadResult;
import demo.joy.image.manager.service.JoyImageManagerService;
import demo.joy.image.npc.pojo.constant.ImageNpcConstant;

@Service
public class JoyImageManagerServiceImpl extends JoyImageCommonService implements JoyImageManagerService {

	@Override
	public JoyCommonResult cleanJoyImageRedis() {
		JoyCommonResult r = new JoyCommonResult();
		Set<String> iconRedisKeys = redisTemplate.keys(IconConstant.ICON_REDIS_PREFIX + "*");
		int size = iconRedisKeys.size();
		for (String key : iconRedisKeys) {
			redisTemplate.delete(key);
		}

		Set<String> imageNpcRedisKeys = redisTemplate.keys(ImageNpcConstant.NPC_REDIS_PREFIX + "*");
		size += imageNpcRedisKeys.size();
		for (String key : imageNpcRedisKeys) {
			redisTemplate.delete(key);
		}

		r.successWithMessage("clean count: " + size);

		return r;
	}

	@Override
	public JoyImageUploadResult uploadImage(JoyImageUploadDTO dto, String savingPathPrefix) {
		JoyImageUploadResult r = new JoyImageUploadResult();
		if (dto == null || StringUtils.isBlank(dto.getSrc())) {
			r.setMessage("Null data: src");
			return r;
		}

		ImgHandleSrcDataResult srcHandleResult = imgService.imgHandleSrcData(dto.getSrc());
		if (srcHandleResult.isFail()) {
			r.setMessage(srcHandleResult.getMessage());
			return r;
		}

		String filename = String.valueOf(snowFlake.getNextId()) + "." + srcHandleResult.getImgFileType();
		r.setFilename(filename);
		
		String savingPath = imgBase64Saving(savingPathPrefix, dto.getSrc());
		if(savingPath == null) {
			r.setMessage("Upload saving error");
			return r;
		}
		
		r.setImgSavePath(savingPath);
		r.setIsSuccess();
		return r;
	}
}
