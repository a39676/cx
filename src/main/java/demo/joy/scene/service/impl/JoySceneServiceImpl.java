package demo.joy.scene.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.joy.common.service.JoyCommonService;
import demo.joy.scene.mapper.JoySceneGroupRelationMapper;
import demo.joy.scene.mapper.JoySceneMapper;
import demo.joy.scene.pojo.dto.FindSceneVOListBySceneGroupPKDTO;
import demo.joy.scene.pojo.po.JoyScene;
import demo.joy.scene.pojo.po.JoySceneExample;
import demo.joy.scene.pojo.po.JoySceneGroupRelation;
import demo.joy.scene.pojo.po.JoySceneGroupRelationExample;
import demo.joy.scene.pojo.result.FindSceneVOListResult;
import demo.joy.scene.pojo.vo.JoySceneVO;
import demo.joy.scene.service.JoySceneService;

@Service
public class JoySceneServiceImpl extends JoyCommonService implements JoySceneService {

	@Autowired
	private JoySceneMapper joySceneMapper;
	@Autowired
	private JoySceneGroupRelationMapper sceneGroupRelationMapper;

	@Override
	public FindSceneVOListResult findSceneVOListBySceneGroupPK(FindSceneVOListBySceneGroupPKDTO dto) {
		FindSceneVOListResult r = new FindSceneVOListResult();
		
		Long sceneGroupId = decryptPrivateKey(dto.getSceneGroupPK());
		if(sceneGroupId == null) {
			r.addMessage("error param");
			return r;
		}

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
		sceneExample.setOrderByClause(" weight desc ");
		List<JoyScene> scenePOList = joySceneMapper.selectByExample(sceneExample);

		List<JoySceneVO> voList = scenePOList.stream().map(po -> scenePOToVO(po)).collect(Collectors.toList());
		r.setSceneVOList(voList);
		r.setIsSuccess();

		return r;
	}

	@Override
	public JoySceneVO scenePOToVO(JoyScene po) {
		JoySceneVO vo = new JoySceneVO();
		vo.setPk(encryptId(po.getId()));
		vo.setSceneName(po.getSceneName());
		return vo;
	}
	
	@Override
	public JoyScene findByPk(String pk) {
		Long id = decryptPrivateKey(pk);
		return findById(id);
	}
	
	@Override
	public JoyScene findById(Long id) {
		return joySceneMapper.selectByPrimaryKey(id);
	}
}
