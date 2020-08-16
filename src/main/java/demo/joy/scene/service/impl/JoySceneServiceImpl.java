package demo.joy.scene.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.joy.common.service.JoyCommonService;
import demo.joy.scene.mapper.JoySceneMapper;
import demo.joy.scene.pojo.constant.SceneConstant;
import demo.joy.scene.pojo.dto.CreateJoySceneDTO;
import demo.joy.scene.pojo.po.JoyScene;
import demo.joy.scene.service.JoySceneService;

@Service
public class JoySceneServiceImpl extends JoyCommonService implements JoySceneService {

	@Autowired
	private JoySceneMapper joySceneMapper;

	@Override
	public CommonResult createScene(CreateJoySceneDTO dto) {
		CommonResult r = validCreateJoySceneDTO(dto);
		if(r.isFail()) {
			return r;
		}
		
		JoyScene po = new JoyScene();
		po.setId(snowFlake.getNextId());
		po.setSceneName(dto.getSceneName());
		po.setIsOpen(dto.getIsOpen());
		po.setIsPrivate(dto.getIsPrivate());
		
		int insertCount = joySceneMapper.insertSelective(po);
		if(insertCount > 0) {
			r.setIsSuccess();
		} else {
			r.failWithMessage("插入场景异常");
		}
		return r;
	}

	private CommonResult validCreateJoySceneDTO(CreateJoySceneDTO dto) {
		CommonResult r = new CommonResult();

		if (StringUtils.isBlank(dto.getSceneName())) {
			r.failWithMessage("请输入场景名称");
			return r;
		} else if (dto.getSceneName().length() < SceneConstant.MIN_SCENE_NAME_LENGTH) {
			r.failWithMessage("场景名称过短");
			return r;
		} else if (dto.getSceneName().length() > SceneConstant.MAX_SCENE_NAME_LENGTH) {
			r.failWithMessage("场景名称过长");
			return r;
		}
		
		r.isSuccess();
		return r;
	}
}
