package demo.joy.skill.service;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.joy.skill.pojo.type.JoySkillType;

public interface JoySkillManagerService {

	CommonResult setMercenaryDefaultSkill(Long mercenaryStoreId, List<Long> skillIdList);

	JoySkillType[] getAllSkill();

}
