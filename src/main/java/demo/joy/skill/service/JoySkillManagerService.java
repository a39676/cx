package demo.joy.skill.service;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;

public interface JoySkillManagerService {

	CommonResult setMercenaryDefaultSkill(Long mercenaryStoreId, List<Long> skillIdList);

}
