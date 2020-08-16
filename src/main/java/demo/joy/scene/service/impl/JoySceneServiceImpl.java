package demo.joy.scene.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.joy.character.pojo.type.JoySceneOperationType;
import demo.joy.common.service.JoyCommonService;
import demo.joy.scene.mapper.JoySceneMapper;
import demo.joy.scene.pojo.constant.SceneConstant;
import demo.joy.scene.pojo.dto.JoySceneOperationDTO;
import demo.joy.scene.pojo.po.JoyScene;
import demo.joy.scene.pojo.po.JoySceneExample;
import demo.joy.scene.pojo.po.JoySceneExample.Criteria;
import demo.joy.scene.pojo.result.JoySceneOperationDTOValidResult;
import demo.joy.scene.service.JoySceneService;

@Service
public class JoySceneServiceImpl extends JoyCommonService implements JoySceneService {

	@Autowired
	private JoySceneMapper joySceneMapper;

	@Override
	public CommonResult createScene(JoySceneOperationDTO dto) {
		CommonResult r = validJoySceneOperationDTO(dto);
		if (r.isFail()) {
			return r;
		}

		JoyScene po = new JoyScene();
		po.setId(snowFlake.getNextId());
		po.setSceneName(dto.getSceneName());
		po.setIsOpen(dto.getIsOpen());
		po.setIsPrivate(dto.getIsPrivate());
		po.setCreateBy(baseUtilCustom.getUserId());

		int insertCount = joySceneMapper.insertSelective(po);
		if (insertCount > 0) {
			r.setIsSuccess();
		} else {
			r.failWithMessage("插入场景异常");
		}
		return r;
	}

	private JoySceneOperationDTOValidResult validJoySceneOperationDTO(JoySceneOperationDTO dto) {
		JoySceneOperationDTOValidResult r = new JoySceneOperationDTOValidResult();

		JoySceneOperationType operationType = JoySceneOperationType.getType(dto.getOperationType());
		if (operationType == null) {
			r.failWithMessage("未指定操作类型");
			return r;
		}

		if (JoySceneOperationType.create.equals(operationType)) {
			return validCreateSceneDTO(dto);

		} else if (JoySceneOperationType.edit.equals(operationType)) {
			return validEditSceneDTO(dto);
			
		} else if (JoySceneOperationType.delete.equals(operationType) || JoySceneOperationType.restore.equals(operationType)) {
			return validDeleteSceneDTO(dto);
		}

		r.isSuccess();
		return r;
	}

	private JoySceneOperationDTOValidResult validCreateSceneDTO(JoySceneOperationDTO dto) {
		JoySceneOperationDTOValidResult r = validSceneName(dto.getSceneName());
		if (r.isFail()) {
			return r;
		}
		r = validDuplicateSceneName(dto.getSceneName());
		if (r.isFail()) {
			return r;
		}

		Long sceneId = decryptPrivateKey(dto.getScenePK());
		if (sceneId == null) {
			r.failWithMessage("参数异常");
			return r;
		}

		r.setIsSuccess();
		return r;
	}

	private JoySceneOperationDTOValidResult validEditSceneDTO(JoySceneOperationDTO dto) {
		JoySceneOperationDTOValidResult r = validSceneName(dto.getSceneName());
		if (r.isFail()) {
			return r;
		}

		Long sceneId = decryptPrivateKey(dto.getScenePK());
		if (sceneId == null) {
			r.failWithMessage("参数异常");
			return r;
		}

		r = validDuplicateSceneName(dto.getSceneName(), sceneId);
		if (r.isFail()) {
			return r;
		}

		r.setIsSuccess();
		return r;
	}

	private JoySceneOperationDTOValidResult validDeleteSceneDTO(JoySceneOperationDTO dto) {
		JoySceneOperationDTOValidResult r = new JoySceneOperationDTOValidResult();

		Long sceneId = decryptPrivateKey(dto.getScenePK());
		if (sceneId == null) {
			r.failWithMessage("参数异常");
			return r;
		}
		
		r.setSceneId(sceneId);
		r.setIsSuccess();
		return r;
	}

	private JoySceneOperationDTOValidResult validSceneName(String sceneName) {
		JoySceneOperationDTOValidResult r = new JoySceneOperationDTOValidResult();

		if (StringUtils.isBlank(sceneName)) {
			r.failWithMessage("请输入场景名称");
			return r;
		} else if (sceneName.length() < SceneConstant.MIN_SCENE_NAME_LENGTH) {
			r.failWithMessage("场景名称过短");
			return r;
		} else if (sceneName.length() > SceneConstant.MAX_SCENE_NAME_LENGTH) {
			r.failWithMessage("场景名称过长");
			return r;
		}

		r.setIsSuccess();
		return r;
	}

	private JoySceneOperationDTOValidResult validDuplicateSceneName(String sceneName) {
		return validDuplicateSceneName(sceneName, null);
	}

	private JoySceneOperationDTOValidResult validDuplicateSceneName(String sceneName, Long inputId) {
		JoySceneOperationDTOValidResult r = new JoySceneOperationDTOValidResult();

		JoySceneExample example = new JoySceneExample();
		Criteria criteria = example.createCriteria();
		criteria.andSceneNameEqualTo(sceneName).andIsDeleteEqualTo(false);
		if (inputId != null) {
			criteria.andIdNotEqualTo(inputId);
		}
		List<JoyScene> poList = joySceneMapper.selectByExample(example);
		if (poList != null && !poList.isEmpty()) {
			r.failWithMessage("已存同名场景, 请换一个名字吧");
			return r;
		}

		r.setIsSuccess();
		r.setSceneId(inputId);

		return r;
	}

	@Override
	public CommonResult editScene(JoySceneOperationDTO dto) {
		JoySceneOperationDTOValidResult validResult = validJoySceneOperationDTO(dto);
		if (validResult.isFail()) {
			return validResult;
		}

		JoyScene po = joySceneMapper.selectByPrimaryKey(validResult.getSceneId());

		po.setSceneName(dto.getSceneName());
		po.setIsOpen(dto.getIsOpen());
		po.setIsPrivate(dto.getIsPrivate());
		po.setEditTime(LocalDateTime.now());
		po.setEditBy(baseUtilCustom.getUserId());

		int updateCount = joySceneMapper.updateByPrimaryKeySelective(po);
		CommonResult updateResult = new CommonResult();
		if (updateCount > 0) {
			updateResult.setIsSuccess();
		} else {
			updateResult.failWithMessage("更新场景异常, 请联系管理员");
		}

		return updateResult;
	}

	@Override
	public CommonResult deleteScene(JoySceneOperationDTO dto) {
		/*
		 * TODO
		 * 需要同时删除传送点关联数据
		 */
		JoySceneOperationDTOValidResult validResult = validJoySceneOperationDTO(dto);
		if (validResult.isFail()) {
			return validResult;
		}
		
		JoyScene po = joySceneMapper.selectByPrimaryKey(validResult.getSceneId());
		
		CommonResult updateResult = new CommonResult();
		if(po == null || po.getId() == null) {
			updateResult.failWithMessage("环境异常, 请联系管理员");
			return updateResult;
		}

		po.setIsDelete(true);
		po.setEditTime(LocalDateTime.now());
		po.setEditBy(baseUtilCustom.getUserId());

		int updateCount = joySceneMapper.updateByPrimaryKeySelective(po);
		if (updateCount > 0) {
			updateResult.setIsSuccess();
		} else {
			updateResult.failWithMessage("删除场景异常, 请联系管理员");
		}

		return updateResult;
	}
	
	@Override
	public CommonResult restoreScene(JoySceneOperationDTO dto) {
		JoySceneOperationDTOValidResult validResult = validJoySceneOperationDTO(dto);
		if (validResult.isFail()) {
			return validResult;
		}
		
		CommonResult updateResult = new CommonResult();
		JoyScene po = joySceneMapper.selectByPrimaryKey(validResult.getSceneId());
		if(po == null || po.getId() == null) {
			updateResult.failWithMessage("环境异常, 请联系管理员");
			return updateResult;
		}

		po.setIsDelete(false);
		po.setEditTime(LocalDateTime.now());
		po.setEditBy(baseUtilCustom.getUserId());

		int updateCount = joySceneMapper.updateByPrimaryKeySelective(po);
		if (updateCount > 0) {
			updateResult.setIsSuccess();
		} else {
			updateResult.failWithMessage("恢复场景异常, 请联系管理员");
		}

		return updateResult;
	}
}
