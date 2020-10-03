package demo.joy.skill.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.joy.common.service.JoyCommonService;
import demo.joy.skill.mapper.JoySkillStoreMapper;
import demo.joy.skill.mapper.JoySkillUserMapper;
import demo.joy.skill.pojo.po.JoySkillStore;
import demo.joy.skill.pojo.po.JoySkillStoreExample;
import demo.joy.skill.pojo.po.JoySkillUser;
import demo.joy.skill.service.JoySkillManagerService;

@Service
public class JoySkillManagerServiceImpl extends JoyCommonService implements JoySkillManagerService {
	
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
	
	@Override
	public CommonResult setMercenaryDefaultSkill(Long mercenaryStoreId, List<Long> skillIdList) {
		CommonResult r = new CommonResult();
		
		JoySkillStoreExample example = new JoySkillStoreExample();
		example.createCriteria().andIdIn(skillIdList);
		List<JoySkillStore> skillStorePOList = skillStoreMapper.selectByExample(example);
		
		if(skillStorePOList == null || skillStorePOList.isEmpty()) {
			return r;
		}
		
		JoySkillUser skillUserPO = null;
		int insertCount = 0;
		for(JoySkillStore skillStorePO : skillStorePOList) {
			skillUserPO = new JoySkillUser();
			skillUserPO.setId(snowFlake.getNextId());
			skillUserPO.setDescription(skillStorePO.getDescription());
			skillUserPO.setImgId(skillStorePO.getImgId());
			skillUserPO.setMercenaryId(mercenaryStoreId);
			skillUserPO.setUserId(0L);
			
			insertCount += skillUserMapper.insertSelective(skillUserPO);
		}
		
		r.setIsSuccess();
		r.setMessage(String.valueOf(insertCount));
		
		return r;
	}
}
