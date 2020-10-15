package demo.joy.character.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.type.GenderType;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.joy.character.mapper.JoyCharacterMapper;
import demo.joy.character.pojo.constant.JoyCharacterConstant;
import demo.joy.character.pojo.dto.CreateJoyCharacterCharacterDTO;
import demo.joy.character.pojo.po.JoyCharacter;
import demo.joy.character.pojo.po.JoyCharacterExample;
import demo.joy.character.pojo.result.GetCharacterDetailResult;
import demo.joy.character.pojo.vo.JoyCharacterVO;
import demo.joy.character.service.JoyCharacterService;
import demo.joy.common.service.JoyCommonService;

@Service
public class JoyCharacterServiceImpl extends JoyCommonService implements JoyCharacterService {

	@Autowired
	private JoyCharacterMapper joyCharacterMapper;

	@Override
	public CommonResultCX createJoyCharacterCharacter(CreateJoyCharacterCharacterDTO dto) {
		CommonResultCX r = validCreateCharacterDTO(dto);
		if (r.isFail()) {
			return r;
		}

		JoyCharacter newPO = new JoyCharacter();
		newPO.setId(snowFlake.getNextId());
		newPO.setUserId(baseUtilCustom.getUserId());
		newPO.setCharacterName(dto.getCharacterName());
		newPO.setGender(dto.getGender());
		int insertCount = joyCharacterMapper.insertSelective(newPO);
		if (insertCount > 0) {
			r.setIsSuccess();
		} else {
			r.failWithMessage("新加入角色, 服务器异常, 请联系管理员");
		}

		return r;
	}

	private CommonResultCX validCreateCharacterDTO(CreateJoyCharacterCharacterDTO dto) {
		CommonResultCX r = new CommonResultCX();
		if (StringUtils.isBlank(dto.getCharacterName())) {
			r.failWithMessage("角色名不能为空");
			return r;
		} else if (dto.getCharacterName().length() > JoyCharacterConstant.MAX_CHARACTER_NAME_LENGTH) {
			r.failWithMessage("角色名过长");
			return r;
		} else if (dto.getCharacterName().length() < JoyCharacterConstant.MIN_CHARACTER_NAME_LENGTH) {
			r.failWithMessage("角色名过短");
			return r;
		}
		
		/*
		 * TODO
		 * 未校验生僻字,特殊字符
		 */

		GenderType genderType = GenderType.getType(dto.getGender());
		if (genderType == null || genderType.equals(GenderType.unknow)) {
			r.failWithMessage("请选取角色性别");
			return r;
		}

		Long userId = baseUtilCustom.getUserId();
		JoyCharacterExample example = new JoyCharacterExample();
		example.createCriteria().andUserIdEqualTo(userId).andIsDeleteEqualTo(false);
		List<JoyCharacter> characterPOList = joyCharacterMapper.selectByExample(example);
		if (characterPOList != null && !characterPOList.isEmpty()) {
			r.failWithMessage("暂时不允许创建多个角色");
			return r;
		}

		example.createCriteria().andCharacterNameEqualTo(dto.getCharacterName());
		characterPOList = joyCharacterMapper.selectByExample(example);
		if (characterPOList != null && !characterPOList.isEmpty()) {
			r.failWithMessage("角色名已被使用");
			return r;
		}

		r.setIsSuccess();
		return r;
	}

	@Override
	public GetCharacterDetailResult getCharacterDetail() {
		GetCharacterDetailResult r = new GetCharacterDetailResult();
		Long userId = baseUtilCustom.getUserId();
		JoyCharacterExample example = new JoyCharacterExample();
		example.createCriteria().andUserIdEqualTo(userId).andIsDeleteEqualTo(false);
		List<JoyCharacter> characterPOList = joyCharacterMapper.selectByExample(example);
		if (characterPOList == null || characterPOList.isEmpty()) {
			r.failWithMessage("请先新建角色");
			return r;
		}
		
		JoyCharacter po = characterPOList.get(0);
		
		JoyCharacterVO vo = buildCharacterVOByPO(po);
		
		r.setCharacterVO(vo);
		r.setIsSuccess();
		
		return r;
	}
	
	private JoyCharacterVO buildCharacterVOByPO(JoyCharacter po) {
		JoyCharacterVO vo = new JoyCharacterVO();
		vo.setGender(po.getGender());
		vo.setName(po.getCharacterName());
		vo.setPk(encryptId(po.getId()));
		return vo;
	}
}
