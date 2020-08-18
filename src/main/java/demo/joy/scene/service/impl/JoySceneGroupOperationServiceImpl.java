package demo.joy.scene.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.common.service.JoyCommonService;
import demo.joy.scene.mapper.JoySceneGroupMapper;
import demo.joy.scene.pojo.constant.SceneConstant;
import demo.joy.scene.pojo.dto.EditJoySceneGroupDTO;
import demo.joy.scene.pojo.dto.FindSceneGroupByConditionDTO;
import demo.joy.scene.pojo.po.JoySceneGroup;
import demo.joy.scene.pojo.po.JoySceneGroupExample;
import demo.joy.scene.pojo.po.JoySceneGroupExample.Criteria;
import demo.joy.scene.pojo.result.FindSceneGroupVOResult;
import demo.joy.scene.pojo.vo.JoySceneGroupVO;
import demo.joy.scene.service.JoySceneGroupOperationService;
import demo.joy.scene.service.JoySceneGroupRelationOperationService;
import demo.joy.scene.service.JoySceneGroupService;

@Service
public class JoySceneGroupOperationServiceImpl extends JoyCommonService implements JoySceneGroupOperationService {

	@Autowired
	private JoySceneGroupMapper sceneGroupMapper;
	@Autowired
	private JoySceneGroupService sceneGroupService;
	@Autowired
	private JoySceneGroupRelationOperationService sceneGroupRelationService;

	@Override
	public JoyCommonResult createJoySceneGroup(EditJoySceneGroupDTO dto) {
		JoyCommonResult r = new JoyCommonResult();
		if (StringUtils.isAllBlank(dto.getSceneGroupName())) {
			r.failWithMessage("请指定场景组名称");
			return r;
		} else if (dto.getSceneGroupName().length() < SceneConstant.MIN_SCENE_GROUP_NAME_LENGTH) {
			r.failWithMessage("场景组名称过短");
			return r;
		} else if (dto.getSceneGroupName().length() > SceneConstant.MAX_SCENE_GROUP_NAME_LENGTH) {
			r.failWithMessage("场景组名称过长");
			return r;
		}

		if (StringUtils.isNotBlank(dto.getRemark())
				&& dto.getRemark().length() > SceneConstant.MAX_SCENE_GROUP_REMARK_LENGTH) {
			r.failWithMessage("场景备注过长(可不填写场景备注)");
			return r;
		}

		JoySceneGroupExample example = new JoySceneGroupExample();
		example.createCriteria().andSceneGroupNameEqualTo(dto.getSceneGroupName());
		List<JoySceneGroup> poList = sceneGroupMapper.selectByExample(example);

		if (poList != null && !poList.isEmpty()) {
			r.failWithMessage("场景组名称重复");
			return r;
		}

		JoySceneGroup po = new JoySceneGroup();
		po.setId(snowFlake.getNextId());
		po.setCreateBy(baseUtilCustom.getUserId());
		po.setSceneGroupName(dto.getSceneGroupName());
		po.setRemark(dto.getRemark());

		int insertCount = sceneGroupMapper.insertSelective(po);
		if (insertCount > 0) {
			r.setIsSuccess();
		} else {
			r.failWithMessage("新加场景组异常");
		}

		return r;
	}

	@Override
	public JoyCommonResult deleteJoySceneGroup(EditJoySceneGroupDTO dto) {
		JoyCommonResult r = new JoyCommonResult();
		Long groupId = decryptPrivateKey(dto.getSceneGroupPK());
		if (groupId == null) {
			r.failWithMessage("error param");
			return r;
		}

		sceneGroupMapper.deleteByPrimaryKey(groupId);
		sceneGroupRelationService.deleteRelationByGroupId(groupId);

		r.isSuccess();
		return r;
	}

	@Override
	public FindSceneGroupVOResult findSceneGroupVOListByCondition(FindSceneGroupByConditionDTO dto) {
		FindSceneGroupVOResult r = new FindSceneGroupVOResult();
		
		JoySceneGroupExample example = new JoySceneGroupExample();
		Criteria criteria = example.createCriteria();
		
		if(StringUtils.isNotBlank(dto.getSceneGroupName())) {
			criteria.andSceneGroupNameLike("%" + dto.getSceneGroupName() + "%");
		}
		
		if(StringUtils.isNotBlank(dto.getSceneGroupRemark())) {
			criteria.andRemarkLike("%" + dto.getSceneGroupRemark() + "%");
		}
		
		if(StringUtils.isNotBlank(dto.getSceneGroupPK())) {
			Long sceneGroupId = decryptPrivateKey(dto.getSceneGroupPK());
			if(sceneGroupId != null) {
				criteria.andIdEqualTo(sceneGroupId);
			}
		}
		
		List<JoySceneGroup> poList = sceneGroupMapper.selectByExample(example);
		
		if(poList != null && !poList.isEmpty()) {
			List<JoySceneGroupVO> voList = poList.stream().map(po -> sceneGroupService.buildVOByPO(po)).collect(Collectors.toList());
			r.setSceneGroupVOList(voList);
		}
		
		r.setIsSuccess();
		return r;
	}
}
