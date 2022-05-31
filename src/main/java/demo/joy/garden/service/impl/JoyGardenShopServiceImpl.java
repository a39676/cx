package demo.joy.garden.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.joy.garden.pojo.dto.FindSeedPageDTO;
import demo.joy.garden.pojo.dto.JoyGardenShopSeedSearchDTO;
import demo.joy.garden.pojo.po.JoyGardenPlantCatalog;
import demo.joy.garden.pojo.po.JoyGardenPlantCatalogExample;
import demo.joy.garden.pojo.po.JoyGardenPlantGrowingStage;
import demo.joy.garden.pojo.vo.JoyGardenPlantGrowingStageVO;
import demo.joy.garden.pojo.vo.JoyGardenPlantSeedVO;
import demo.joy.garden.service.JoyGardenShopService;

@Service
public class JoyGardenShopServiceImpl extends JoyGardenCommonService implements JoyGardenShopService {

	@Override
	public ModelAndView shop() {
		ModelAndView view = new ModelAndView("joyJSP/garden/JoyGardenShop");
		return view;
	}
	
	@Override
	public ModelAndView seedSearchView(JoyGardenShopSeedSearchDTO dto) {
		ModelAndView view = new ModelAndView("joyJSP/garden/JoyGardenShopDetail");
		
		if(dto.getIsNextPage() == null) {
			dto.setIsNextPage(true);
		}
		int defaultPageSize = 16;
		Long oldPageEndId = systemOptionService.decryptPrivateKey(dto.getEndPK());
		Long oldPageStartId = systemOptionService.decryptPrivateKey(dto.getStartPK());
		
		FindSeedPageDTO mapperDTO = new FindSeedPageDTO();
		mapperDTO.setPageSize(defaultPageSize);
		if(dto.getIsNextPage()) {
			mapperDTO.setStartId(oldPageEndId);
		} else {
			mapperDTO.setEndId(oldPageStartId);
		}
		List<JoyGardenPlantGrowingStage> seedList = plantStageMapper.findSeedPage(mapperDTO);
		if(seedList == null || seedList.isEmpty()) {
			if(dto.getIsNextPage() && oldPageEndId != null) {
				mapperDTO.setStartId(null);
				seedList = plantStageMapper.findSeedPage(mapperDTO);
			} else if (!dto.getIsNextPage() && oldPageStartId != null) {
				mapperDTO.setEndId(null);
				seedList = plantStageMapper.findSeedPage(mapperDTO);
			} else {
				return view;
			}
		}
		
		List<Long> plantIdList = new ArrayList<>();
		for(JoyGardenPlantGrowingStage stage : seedList) {
			plantIdList.add(stage.getPlantId());
		}
		
		JoyGardenPlantCatalogExample plantCatalogExample = new JoyGardenPlantCatalogExample();
		plantCatalogExample.createCriteria().andIdIn(plantIdList);
		List<JoyGardenPlantCatalog> plantCatalogList = plantCatalogMapper.selectByExample(plantCatalogExample);
		Map<Long, JoyGardenPlantCatalog> catalogMap = new HashMap<>();
		for(JoyGardenPlantCatalog plantCatalog : plantCatalogList) {
			catalogMap.put(plantCatalog.getId(), plantCatalog);
		}
		
		List<JoyGardenPlantSeedVO> seedVoList = new ArrayList<>();
		JoyGardenPlantGrowingStageVO tmpStageVO = null;
		JoyGardenPlantSeedVO tmpSeedVO = null;
		JoyGardenPlantCatalog tmpPlant = null;
		for(JoyGardenPlantGrowingStage stage : seedList) {
			tmpStageVO = buildJoyGardenPlantGrowingStageVO(stage);
			tmpSeedVO = new JoyGardenPlantSeedVO();
			BeanUtils.copyProperties(tmpStageVO, tmpSeedVO);
			tmpPlant = catalogMap.get(stage.getPlantId());
			if(tmpPlant != null) {
				tmpSeedVO.setPlantName(tmpPlant.getPlantName());
			}
			seedVoList.add(tmpSeedVO);
		}
		
		view.addObject("seedVoList", seedVoList);
		view.addObject("endPK", seedVoList.get(seedVoList.size() - 1).getPk());
		view.addObject("startPK", seedVoList.get(0).getPk());
//		TODO startPK & endPK 兼顾上下翻页
		return view;
	}
}
