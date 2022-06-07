package demo.joy.garden.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.garden.mapper.JoyGardenShopGoodsMapper;
import demo.joy.garden.pojo.dto.FindSeedPageDTO;
import demo.joy.garden.pojo.dto.JoyGardenShopAddShopStoreDTO;
import demo.joy.garden.pojo.dto.JoyGardenShopSearchDTO;
import demo.joy.garden.pojo.po.JoyGardenPlantCatalog;
import demo.joy.garden.pojo.po.JoyGardenPlantCatalogExample;
import demo.joy.garden.pojo.po.JoyGardenPlantGrowingStage;
import demo.joy.garden.pojo.po.JoyGardenShopGoods;
import demo.joy.garden.pojo.po.JoyGardenShopGoodsExample;
import demo.joy.garden.pojo.type.JoyGardenShopGoodsMainType;
import demo.joy.garden.pojo.vo.JoyGardenShopPlantVO;
import demo.joy.garden.service.JoyGardenShopManagerService;

@Service
public class JoyGardenShopManagerServiceImpl extends JoyGardenCommonService implements JoyGardenShopManagerService {

	@Autowired
	private JoyGardenShopGoodsMapper shopGoodsMapper;

	@Override
	public ModelAndView shopManagerView() {
		ModelAndView view = new ModelAndView("joyJSP/garden/shop/JoyGardenShopManager");
		return view;
	}

	@Override
	public ModelAndView plantShopSearchView(JoyGardenShopSearchDTO dto) {
		ModelAndView view = new ModelAndView("joyJSP/garden/shop/JoyGardenShopManagerDetail");

		if (dto.getIsNextPage() == null) {
			dto.setIsNextPage(true);
		}
		int defaultPageSize = 16;
		Long oldPageEndId = systemOptionService.decryptPrivateKey(dto.getEndPK());
		Long oldPageStartId = systemOptionService.decryptPrivateKey(dto.getStartPK());

		FindSeedPageDTO mapperDTO = new FindSeedPageDTO();
		mapperDTO.setPageSize(defaultPageSize);
		if (dto.getIsNextPage()) {
			mapperDTO.setStartId(oldPageEndId);
		} else {
			mapperDTO.setEndId(oldPageStartId);
		}
		List<JoyGardenPlantGrowingStage> seedList = plantStageMapper.findSeedPage(mapperDTO);
		if (seedList == null || seedList.isEmpty()) {
			if (dto.getIsNextPage() && oldPageEndId != null) {
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
		for (JoyGardenPlantGrowingStage stage : seedList) {
			plantIdList.add(stage.getPlantId());
		}

		JoyGardenPlantCatalogExample plantCatalogExample = new JoyGardenPlantCatalogExample();
		plantCatalogExample.createCriteria().andIdIn(plantIdList);
		List<JoyGardenPlantCatalog> plantCatalogList = plantCatalogMapper.selectByExample(plantCatalogExample);
		Map<Long, JoyGardenPlantCatalog> catalogMap = new HashMap<>();
		for (JoyGardenPlantCatalog plantCatalog : plantCatalogList) {
			catalogMap.put(plantCatalog.getId(), plantCatalog);
		}

		List<JoyGardenShopPlantVO> goodsVoList = new ArrayList<>();
		JoyGardenShopPlantVO tmpGoodsVO = null;
		JoyGardenPlantCatalog tmpPlant = null;
		for (JoyGardenPlantGrowingStage stage : seedList) {
			tmpGoodsVO = new JoyGardenShopPlantVO();
			tmpGoodsVO.setPk(systemOptionService.encryptId(stage.getId()));
			tmpPlant = catalogMap.get(stage.getPlantId());
			if (tmpPlant != null) {
				tmpGoodsVO.setGoodsName(tmpPlant.getPlantName() + ", " + stage.getStageName());
			} else {
				tmpGoodsVO.setGoodsName(stage.getStageName());
			}
			tmpGoodsVO.setImgUrlPath(imageService.buildImageUrl(stage.getImgId()));
			goodsVoList.add(tmpGoodsVO);
		}

		view.addObject("goodsVoList", goodsVoList);
		view.addObject("endPK", goodsVoList.get(goodsVoList.size() - 1).getPk());
		view.addObject("startPK", goodsVoList.get(0).getPk());
		return view;
	}

	@Override
	public JoyCommonResult addShopStore(JoyGardenShopAddShopStoreDTO dto) {
		JoyCommonResult r = new JoyCommonResult();
		JoyGardenShopGoodsMainType goodsMainType = JoyGardenShopGoodsMainType.getType(dto.getGoodsMainType());
		if (goodsMainType == null) {
			r.setMessage("大类参数异常");
			return r;
		}

		if (JoyGardenShopGoodsMainType.PLANT.equals(goodsMainType)) {
			return addPlantStore(dto);
		}

		return r;
	}

	private JoyCommonResult addPlantStore(JoyGardenShopAddShopStoreDTO dto) {
		JoyCommonResult r = new JoyCommonResult();

		if (dto.getAddCounting() < 1) {
			r.setMessage("请至少添加1个");
			return r;
		}

		if (dto.getPrice().doubleValue() < 0) {
			r.setMessage("价格设定异常");
			return r;
		}

		Long goodsId = systemOptionService.decryptPrivateKey(dto.getGoodsPK());
		if (goodsId == null) {
			r.setMessage("错误 private key");
			return r;
		}

		JoyGardenPlantGrowingStage stage = plantGrowingStageMapper.selectByPrimaryKey(goodsId);
		if (stage == null) {
			r.setMessage("商品不存在");
			return r;
		}

		JoyGardenShopGoodsMainType goodsMainType = JoyGardenShopGoodsMainType.getType(dto.getGoodsMainType());

		JoyGardenShopGoods shopStore = null;
		JoyGardenShopGoodsExample shopStoreExample = new JoyGardenShopGoodsExample();
		shopStoreExample.createCriteria().andObjectTypeEqualTo(goodsMainType.getCode())
				.andObjectSubTypeEqualTo(goodsMainType.getCode()).andObjectIdEqualTo(stage.getId())
				.andObjectPriceEqualTo(dto.getPrice());
		List<JoyGardenShopGoods> goodsStoreList = shopGoodsMapper.selectByExample(shopStoreExample);
		if (goodsStoreList == null || goodsStoreList.isEmpty()) {
			shopStore = new JoyGardenShopGoods();
			shopStore.setId(snowFlake.getNextId());
			shopStore.setObjectId(stage.getId());
			shopStore.setCounting(dto.getAddCounting());
			JoyGardenPlantCatalog plant = plantCatalogMapper.selectByPrimaryKey(stage.getPlantId());
			if (plant != null) {
				shopStore.setObjectName(plant.getPlantName() + ", " + stage.getStageName());
			} else {
				shopStore.setObjectName(stage.getStageName());
			}
			shopStore.setImgId(stage.getImgId());
			shopStore.setObjectPrice(dto.getPrice());
			shopStore.setObjectType(goodsMainType.getCode());
			shopStore.setObjectSubType(goodsMainType.getCode());
			shopGoodsMapper.insertSelective(shopStore);
		} else {
			shopStore = goodsStoreList.get(0);
			shopStore.setCounting(shopStore.getCounting() + dto.getAddCounting());
			shopStore.setUpdateTime(LocalDateTime.now());
			shopGoodsMapper.updateByPrimaryKeySelective(shopStore);
		}

		r.setIsSuccess();

		return r;
	}

}
