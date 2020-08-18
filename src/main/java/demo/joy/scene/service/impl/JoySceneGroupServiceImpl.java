package demo.joy.scene.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.common.service.JoyCommonService;
import demo.joy.scene.mapper.JoySceneGroupMapper;
import demo.joy.scene.pojo.constant.SceneConstant;
import demo.joy.scene.pojo.dto.CreateJoySceneGroupDTO;
import demo.joy.scene.pojo.po.JoySceneGroup;
import demo.joy.scene.pojo.po.JoySceneGroupExample;
import demo.joy.scene.service.JoySceneGroupService;

@Service
public class JoySceneGroupServiceImpl extends JoyCommonService implements JoySceneGroupService {

	@Autowired
	private JoySceneGroupMapper sceneGroupMapper;

	@Override
	public JoyCommonResult createJoySceneGroup(CreateJoySceneGroupDTO dto) {
		JoyCommonResult r = new JoyCommonResult();
		if(StringUtils.isAllBlank(dto.getSceneGroupName())) {
			r.failWithMessage("请指定场景组名称");
			return r;
		} else if(dto.getSceneGroupName().length() < SceneConstant.MIN_SCENE_GROUP_NAME_LENGTH) {
			r.failWithMessage("场景组名称过短");
			return r;
		} else if(dto.getSceneGroupName().length() > SceneConstant.MAX_SCENE_GROUP_NAME_LENGTH) {
			r.failWithMessage("场景组名称过长");
			return r;
		}
		
		if(StringUtils.isNotBlank(dto.getRemark()) && dto.getRemark().length() > SceneConstant.MAX_SCENE_GROUP_REMARK_LENGTH) {
			r.failWithMessage("场景备注过长(可不填写场景备注)");
			return r;
		}
		
		JoySceneGroupExample example = new JoySceneGroupExample();
		example.createCriteria().andSceneGroupNameEqualTo(dto.getSceneGroupName());
		List<JoySceneGroup> poList = sceneGroupMapper.selectByExample(example);
		
		if(poList != null && !poList.isEmpty()) {
			r.failWithMessage("场景组名称重复");
			return r;
		}
		
		JoySceneGroup po = new JoySceneGroup();
		po.setId(snowFlake.getNextId());
		po.setCreateBy(baseUtilCustom.getUserId());
		po.setSceneGroupName(dto.getSceneGroupName());
		po.setRemark(dto.getRemark());
		
		int insertCount = sceneGroupMapper.insertSelective(po);
		if(insertCount > 0) {
			r.setIsSuccess();
		} else {
			r.failWithMessage("新加场景组异常");
		}
		
		return r;
	}
}
