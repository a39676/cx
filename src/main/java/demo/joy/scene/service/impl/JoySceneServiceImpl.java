package demo.joy.scene.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.joy.common.service.JoyCommonService;
import demo.joy.scene.mapper.JoySceneGroupRelationMapper;
import demo.joy.scene.mapper.JoySceneMapper;
import demo.joy.scene.pojo.po.JoyScene;
import demo.joy.scene.pojo.po.JoySceneExample;
import demo.joy.scene.pojo.po.JoySceneGroupRelation;
import demo.joy.scene.pojo.po.JoySceneGroupRelationExample;
import demo.joy.scene.pojo.result.FindSceneVOListBySceneGroupIdResult;
import demo.joy.scene.pojo.vo.JoySceneVO;
import demo.joy.scene.service.JoySceneService;

@Service
public class JoySceneServiceImpl extends JoyCommonService implements JoySceneService {

	@Autowired
	private JoySceneMapper joySceneMapper;
	@Autowired
	private JoySceneGroupRelationMapper sceneGroupRelationMapper;

	@Override
	public FindSceneVOListBySceneGroupIdResult findSceneVOListBySceneGroupId(Long sceneGroupId) {
		FindSceneVOListBySceneGroupIdResult r = new FindSceneVOListBySceneGroupIdResult();

		JoySceneGroupRelationExample sceneGroupRelationExample = new JoySceneGroupRelationExample();
		sceneGroupRelationExample.createCriteria().andSceneGroupIdEqualTo(sceneGroupId);
		List<JoySceneGroupRelation> sceneGroupRelationPOList = sceneGroupRelationMapper.selectByExample(sceneGroupRelationExample);
		
		if(sceneGroupRelationPOList == null || sceneGroupRelationPOList.isEmpty()) {
			r.setIsSuccess();
			return r;
		}

		List<Long> toSceneIdList = sceneGroupRelationPOList.stream().map(po -> po.getSceneId()).collect(Collectors.toList());

		JoySceneExample sceneExample = new JoySceneExample();
		sceneExample.createCriteria().andIsDeleteEqualTo(false).andIdIn(toSceneIdList);
		List<JoyScene> scenePOList = joySceneMapper.selectByExample(sceneExample);

		List<JoySceneVO> voList = scenePOList.stream().map(po -> scenePOToVO(po)).collect(Collectors.toList());
		r.setSceneVOList(voList);
		r.setIsSuccess();

		return r;
	}

	private JoySceneVO scenePOToVO(JoyScene po) {
		JoySceneVO vo = new JoySceneVO();
		vo.setPk(encryptId(po.getId()));
		vo.setSceneName(po.getSceneName());
		return vo;
	}

}
