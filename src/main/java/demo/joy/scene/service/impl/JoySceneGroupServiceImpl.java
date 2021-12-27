package demo.joy.scene.service.impl;

import org.springframework.stereotype.Service;

import demo.joy.common.service.JoyCommonService;
import demo.joy.scene.pojo.po.JoySceneGroup;
import demo.joy.scene.pojo.vo.JoySceneGroupVO;
import demo.joy.scene.service.JoySceneGroupService;

@Service
public class JoySceneGroupServiceImpl extends JoyCommonService implements JoySceneGroupService {

	
	@Override
	public JoySceneGroupVO buildVOByPO(JoySceneGroup po) {
		JoySceneGroupVO vo = new JoySceneGroupVO();
		vo.setName(po.getSceneGroupName());
		vo.setRemark(po.getRemark());
		vo.setPk(systemConstantService.encryptId(po.getId()));
		return vo;
	}
	
}
