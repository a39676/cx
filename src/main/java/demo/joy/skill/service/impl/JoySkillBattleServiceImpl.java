package demo.joy.skill.service.impl;

import org.springframework.stereotype.Service;

import demo.joy.common.service.JoyCommonService;
import demo.joy.skill.pojo.type.JoySkillType;
import demo.joy.skill.service.JoySkillBattleService;

@Service
public class JoySkillBattleServiceImpl extends JoyCommonService implements JoySkillBattleService {

	/*
	 * TODO
	 * 需要双方所有数据
	 * 双方人物id, 双方buff Map,  
	 */ 
	public void skillHandler(JoySkillType skillType) {
		if(JoySkillType.firstMiracle.equals(skillType)) {
			firstMiracle();
		}
	}
	
	private void firstMiracle() {
//		TODO
	}
}
