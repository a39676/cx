package demo.joy.scene.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.common.service.JoyCommonService;
import demo.joy.scene.mapper.JoySceneGroupRelationMapper;
import demo.joy.scene.pojo.dto.EditJoySceneGroupRelationDTO;
import demo.joy.scene.pojo.po.JoySceneGroupRelation;
import demo.joy.scene.pojo.po.JoySceneGroupRelationExample;
import demo.joy.scene.service.JoySceneGroupRelationOperationService;

@Service
public class JoySceneGroupRelationManagerServiceImpl extends JoyCommonService implements JoySceneGroupRelationOperationService {

	@Autowired
	private JoySceneGroupRelationMapper sceneGroupRelationMapper;
	
	@Override
	public JoyCommonResult deleteRelationByGroupId(Long groupId) {
		JoyCommonResult r = new JoyCommonResult();
		if(groupId == null) {
			return r;
		}
		
		JoySceneGroupRelationExample example = new JoySceneGroupRelationExample();
		example.createCriteria().andSceneGroupIdEqualTo(groupId);
		int delCount = sceneGroupRelationMapper.deleteByExample(example);
		if(delCount > 0) {
			r.setIsSuccess();
		} else {
			r.failWithMessage("删除场景组关联异常");
		}
		return r;
	}

	@Override
	public JoyCommonResult createSceneGroupRelation(EditJoySceneGroupRelationDTO dto) {
		JoyCommonResult r = new JoyCommonResult();
		
		if(StringUtils.isAnyBlank(dto.getSceneGroupPK(), dto.getScenePK())) {
			r.failWithMessage("null param");
			return r;
		}
		
		Long sceneGroupId = systemConstantService.decryptPrivateKey(dto.getSceneGroupPK());
		Long sceneId = systemConstantService.decryptPrivateKey(dto.getScenePK());
		
		if(sceneGroupId == null || sceneId == null) {
			r.failWithMessage("error param");
			return r;
		}
		
		JoySceneGroupRelation po = new JoySceneGroupRelation();
		po.setId(snowFlake.getNextId());
		po.setSceneGroupId(sceneGroupId);
		po.setSceneId(sceneId);
		try {
			int insertCount = sceneGroupRelationMapper.insertSelective(po);
			
			if(insertCount > 0) {
				r.setIsSuccess();
			}
		} catch (Exception e) {
		}
		return r;
	}
	
	@Override
	public JoyCommonResult deleteSceneGroupRelation(EditJoySceneGroupRelationDTO dto) {
		JoyCommonResult r = new JoyCommonResult();
		
		if(StringUtils.isAnyBlank(dto.getSceneGroupPK(), dto.getScenePK())) {
			r.failWithMessage("null param");
			return r;
		}
		
		Long sceneGroupId = systemConstantService.decryptPrivateKey(dto.getSceneGroupPK());
		Long sceneId = systemConstantService.decryptPrivateKey(dto.getScenePK());
		
		if(sceneGroupId == null || sceneId == null) {
			r.failWithMessage("error param");
			return r;
		}
		
		JoySceneGroupRelationExample example = new JoySceneGroupRelationExample();
		example.createCriteria().andSceneGroupIdEqualTo(sceneGroupId).andSceneIdEqualTo(sceneId);
		int delCount = sceneGroupRelationMapper.deleteByExample(example);
		
		if(delCount > 0) {
			r.setIsSuccess();
		}
		return r;
	}
}
