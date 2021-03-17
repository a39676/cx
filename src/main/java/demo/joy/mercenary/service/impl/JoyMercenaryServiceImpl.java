package demo.joy.mercenary.service.impl;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.joy.common.pojo.bo.JoyAttributeBO;
import demo.joy.common.service.JoyCommonService;
import demo.joy.mercenary.mapper.JoyMercenaryStoreMapper;
import demo.joy.mercenary.pojo.dto.CreateNewMercenaryToStoreDTO;
import demo.joy.mercenary.pojo.po.JoyMercenaryStore;
import demo.joy.mercenary.service.JoyMercenaryService;
import demo.joy.skill.service.JoySkillManagerService;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class JoyMercenaryServiceImpl extends JoyCommonService implements JoyMercenaryService {
	
	@Autowired
	private JoyMercenaryStoreMapper mercenaryStoreMapper;
	@Autowired
	private JoySkillManagerService skillService;
	
	private String getMercenaryStoreAttributeSavePath() {
		if(isLinux()) {
			return "/home/u2/joy/mercenary/attribute";
		} else {
			return "d:/home/u2/joy/mercenary/attribute";
		}
	}

	@Override
	public CommonResult createNewMercenaryToStore(CreateNewMercenaryToStoreDTO dto) {
		CommonResult r = new CommonResult();
		
		if(StringUtils.isBlank(dto.getName())) {
			r.addMessage("需要指定基础角色名");
			return r;
		}
		
		if(dto.getImgId() == null) {
			r.addMessage("选图异常");
			return r;
		}
		
		long newMercenaryId = snowFlake.getNextId();
		
		if(dto.getSkillIdList() != null && !dto.getSkillIdList().isEmpty()) {
			if(dto.getSkillCount() < dto.getSkillIdList().size()) {
				r.addMessage("技能配置过多");
				return r;
			}
			
			CommonResult setDefaultSkillResult = skillService.setMercenaryDefaultSkill(newMercenaryId, dto.getSkillIdList());
			if(setDefaultSkillResult.isFail()) {
				return setDefaultSkillResult;
			}
		}
		
		CommonResult saveAttributeResult = saveNewAttribute(newMercenaryId, dto);
		if(saveAttributeResult.isFail()) {
			return saveAttributeResult;
		}
		
		JoyMercenaryStore po = new JoyMercenaryStore();
		po.setId(newMercenaryId);
		po.setAttributePath(saveAttributeResult.getMessage());
		po.setDescription(dto.getDescription());
		po.setImgId(dto.getImgId());
		po.setMaxSale(dto.getMaxSale());
		po.setMercenaryName(dto.getName());
		po.setSkillCount(dto.getSkillCount());
		
		mercenaryStoreMapper.insertSelective(po);
		
		r.setIsSuccess();
		return r;
	}
	
	private CommonResult saveNewAttribute(long id, CreateNewMercenaryToStoreDTO dto) {
		CommonResult r = new CommonResult();
		
		String mainFolderPath = getMercenaryStoreAttributeSavePath();
		File mainFolder = new File(mainFolderPath);
		if(!mainFolder.exists()) {
			if(!mainFolder.mkdirs()) {
				r.addMessage("joy system error");
				return r;
			}
		}
		
		try {
			FileUtilCustom ioUtil = new FileUtilCustom();
			String filePath = mainFolderPath + File.separator + id;
			
			JoyAttributeBO attrBO = new JoyAttributeBO();
			BeanUtils.copyProperties(dto, attrBO);
			
			JSONObject json = JSONObject.fromObject(attrBO);
			ioUtil.byteToFile(json.toString().getBytes(StandardCharsets.UTF_8), filePath);
			
			r.successWithMessage(filePath);
		} catch (Exception e) {
			r.addMessage("attribute saving error");
		}
		
		return r;
		
	}
	
	
}
