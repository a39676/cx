package demo.joy.character.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.joy.character.mapper.JoyCharacterDetailMapper;
import demo.joy.character.pojo.po.JoyCharacterDetail;
import demo.joy.character.service.JoyCharacterDetailService;
import demo.joy.common.service.JoyCommonService;

@Service
public class JoyCharacterDetailServiceImpl extends JoyCommonService implements JoyCharacterDetailService {

	@Autowired
	private JoyCharacterDetailMapper characterDetailMapper;
	
	public void createCharacterDetail(Long newCharacterId, String newCharacterImgPath) {
		/*
		 * TODO
		 * newCharacterImgPath?  file???
		 */
		JoyCharacterDetail po = new JoyCharacterDetail();
		po.setId(newCharacterId);
		po.setCharacterImgPath(newCharacterImgPath);
		
		characterDetailMapper.insertSelective(po);
	}
}
