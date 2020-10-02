package demo.joy.skill.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.joy.common.service.JoyCommonService;
import demo.joy.skill.mapper.JoySkillStoreMapper;
import demo.joy.skill.mapper.JoySkillUserMapper;
import demo.joy.skill.service.JoySkillService;

@Service
public class JoySkillServiceImpl extends JoyCommonService implements JoySkillService {
	
	@Autowired
	private JoySkillStoreMapper skillStoreMapper;
	
	@Autowired
	private JoySkillUserMapper skillUserMapper;
	
	public void createSkill() {
		/*
		 * 每个技能有各自的特殊执行逻辑
		 * 无法简单插入
		 */
	}
	
	public void setMercenaryDefaultSkill(Long mercenaryStoreId, List<Long> skillIdList) {
		/*
		 * TODO
		 */
	}
}
