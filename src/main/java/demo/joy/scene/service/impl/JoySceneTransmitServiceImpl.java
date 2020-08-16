package demo.joy.scene.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.joy.common.service.JoyCommonService;
import demo.joy.scene.mapper.JoySceneTransmitMapper;
import demo.joy.scene.pojo.dto.FindSceneTransmitDTO;
import demo.joy.scene.pojo.po.JoySceneTransmit;
import demo.joy.scene.pojo.po.JoySceneTransmitExample;
import demo.joy.scene.pojo.result.FindSceneTransmitResult;
import demo.joy.scene.service.JoySceneTransmitService;

@Service
public class JoySceneTransmitServiceImpl extends JoyCommonService implements JoySceneTransmitService {

	@Autowired
	private JoySceneTransmitMapper sceneTransmitMapper;
	
	@Override
	public FindSceneTransmitResult findSceneTransmitByFromScenePK(FindSceneTransmitDTO dto) {
		FindSceneTransmitResult r = new FindSceneTransmitResult();

		Long fromSceneId = decryptPrivateKey(dto.getFromScenePK());
		if(fromSceneId == null) {
			r.failWithMessage("数据异常");
			return r;
		}
		
		JoySceneTransmitExample example = new JoySceneTransmitExample();
		example.createCriteria().andIsDeleteEqualTo(false).andFromSceneIdEqualTo(fromSceneId);
		List<JoySceneTransmit> poList = sceneTransmitMapper.selectByExample(example);
		
		List<Long> toSceneIdList = poList.stream().map(po -> po.getId()).collect(Collectors.toList());
		
		r.setToSceneIdList(toSceneIdList);
		r.setIsSuccess();
		
		return r;
	}
}
