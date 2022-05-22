package demo.joy.garden.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.base.user.pojo.po.UsersDetail;
import demo.joy.common.service.JoyCommonService;
import demo.joy.garden.mapper.JoyGardenPlantCatalogMapper;
import demo.joy.garden.pojo.dto.JoyGardenPlantOptionDTO;
import demo.joy.garden.pojo.dto.JoyGardenPlantSearchConditionDTO;
import demo.joy.garden.pojo.po.JoyGardenPlantCatalog;
import demo.joy.garden.pojo.po.JoyGardenPlantCatalogExample;
import demo.joy.garden.pojo.po.JoyGardenPlantCatalogExample.Criteria;
import demo.joy.garden.pojo.type.JoyGardenPlantType;
import demo.joy.garden.pojo.vo.JoyGardenPlantCatalogVO;
import demo.joy.garden.service.JoyGardenPlantCatalogManagerService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class JoyGardenPlantCatalogManagerServiceImpl extends JoyCommonService
		implements JoyGardenPlantCatalogManagerService {

	@Autowired
	private JoyGardenPlantCatalogMapper plantCatalogMapper;

	@Autowired
	private FileUtilCustom ioUtil;
	
	@Override
	public ModelAndView plantCatalogManager() {
		ModelAndView view = new ModelAndView("joyJSP/garden/JoyPlantCatalogManager");
		view.addObject("plantTypeList", JoyGardenPlantType.values());
		return view;
	}

	@Override
	public ModelAndView plantSearch(JoyGardenPlantSearchConditionDTO dto) {
		ModelAndView view = new ModelAndView("joyJSP/garden/JoyPlantCatalogSearchResult");
		view.addObject("plantTypeList", JoyGardenPlantType.values());
		
		JoyGardenPlantCatalogExample example = new JoyGardenPlantCatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andIsDeleteEqualTo(false).andPlantTypeEqualTo(dto.getPlantType());
		if (StringUtils.isNotBlank(dto.getName())) {
			criteria.andPlantNameLike(dto.getName());
		}
		List<JoyGardenPlantCatalog> poList = plantCatalogMapper.selectByExample(example);
		if(poList == null || poList.isEmpty()) {
			return view;
		}
		List<JoyGardenPlantCatalogVO> voList = new ArrayList<>();
		for(JoyGardenPlantCatalog po : poList) {
			voList.add(buildPlantVO(po));
		}
		
		view.addObject("plantList", voList);
		return view;
	}
	
	private JoyGardenPlantCatalogVO buildPlantVO(JoyGardenPlantCatalog po) {
		JoyGardenPlantCatalogVO vo = new JoyGardenPlantCatalogVO();
		vo.setPk(systemOptionService.encryptId(po.getId()));
		vo.setPlantName(po.getPlantName());
		vo.setPlantType(JoyGardenPlantType.getType(po.getPlantType()));
		
		UsersDetail userDetail = userDetailService.findById(po.getCreatorId());
		if(userDetail != null) {
			vo.setCreatorName(userDetail.getNickName());
		}
		
		try {
			String optionFilePath = po.getOptionFilePath();
			String optionStr = ioUtil.getStringFromFile(optionFilePath);
			JoyGardenPlantOptionDTO optionDTO = buildObjFromJsonCustomization(optionStr, JoyGardenPlantOptionDTO.class);
			vo.setOptionDTO(optionDTO);
		} catch (Exception e) {
		}
		
		return vo;
	}
}
