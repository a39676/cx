package demo.joy.scene.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.joy.common.service.JoyCommonService;
import demo.joy.scene.pojo.dto.FindSceneGroupByConditionDTO;
import demo.joy.scene.pojo.po.JoySceneGroup;
import demo.joy.scene.pojo.result.FindSceneGroupVOResult;
import demo.joy.scene.pojo.vo.JoySceneGroupVO;
import demo.joy.scene.service.JoySceneGroupManagerService;
import demo.joy.scene.service.JoySceneGroupService;

@Service
public class JoySceneGroupServiceImpl extends JoyCommonService implements JoySceneGroupService {

	@Autowired
	private JoySceneGroupManagerService sceneGroupOperationService;
	
	@Override
	public JoySceneGroupVO buildVOByPO(JoySceneGroup po) {
		JoySceneGroupVO vo = new JoySceneGroupVO();
		vo.setName(po.getSceneGroupName());
		vo.setRemark(po.getRemark());
		vo.setPk(systemConstantService.encryptId(po.getId()));
		return vo;
	}
	
	@Override
	public FindSceneGroupVOResult findAllSceneGroupVOList() {
		FindSceneGroupByConditionDTO dto = new FindSceneGroupByConditionDTO();
		return sceneGroupOperationService.findSceneGroupVOListByCondition(dto);
	}

}
