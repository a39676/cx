package demo.joy.garden.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.joy.garden.mapper.JoyGardenShopGoodsMapper;
import demo.joy.garden.pojo.dto.FindShopGoodsPageDTO;
import demo.joy.garden.pojo.dto.JoyGardenShopSearchDTO;
import demo.joy.garden.pojo.po.JoyGardenShopGoods;
import demo.joy.garden.pojo.vo.JoyGardenShopGoodsVO;
import demo.joy.garden.service.JoyGardenShopService;

@Service
public class JoyGardenShopServiceImpl extends JoyGardenCommonService implements JoyGardenShopService {

	@Autowired
	private JoyGardenShopGoodsMapper shopGoodsMapper;
	
	@Override
	public ModelAndView shop() {
		ModelAndView view = new ModelAndView("joyJSP/garden/shop/JoyGardenShop");
		return view;
	}
	
	@Override
	public ModelAndView shopStoreView(JoyGardenShopSearchDTO dto) {
		ModelAndView view = new ModelAndView("joyJSP/garden/shop/JoyGardenShopSellingDetail");

		if (dto.getIsNextPage() == null) {
			dto.setIsNextPage(true);
		}
		int defaultPageSize = 16;
		Long oldPageEndId = systemOptionService.decryptPrivateKey(dto.getEndPK());
		Long oldPageStartId = systemOptionService.decryptPrivateKey(dto.getStartPK());
		
		FindShopGoodsPageDTO mapperDTO = new FindShopGoodsPageDTO();
		mapperDTO.setGoodsMainType(dto.getGoodsMainType());
		mapperDTO.setGoodsSubType(dto.getGoodsSubType());
		if(dto.getIsNextPage()) {
			mapperDTO.setStartId(oldPageEndId);
		} else {
			mapperDTO.setEndId(oldPageStartId);
		}
		mapperDTO.setPageSize(defaultPageSize);
		List<JoyGardenShopGoods> goodsList = shopGoodsMapper.findShopGoodsPage(mapperDTO);
		if(goodsList == null || goodsList.isEmpty()) {
			return view;
		}
		List<JoyGardenShopGoodsVO> goodsVoList = new ArrayList<>();
		for(JoyGardenShopGoods goods : goodsList) {
			goodsVoList.add(buildJoyGardenShopGoodsVO(goods));
		}
		
		view.addObject("goodsVoList", goodsVoList);
		view.addObject("endPK", goodsVoList.get(goodsVoList.size() - 1).getPk());
		view.addObject("startPK", goodsVoList.get(0).getPk());
		return view;
	}
	
	private JoyGardenShopGoodsVO buildJoyGardenShopGoodsVO(JoyGardenShopGoods po) {
		JoyGardenShopGoodsVO vo = new JoyGardenShopGoodsVO();
		vo.setPk(systemOptionService.encryptId(po.getId()));
		vo.setCounting(po.getCounting());
		vo.setPrice(po.getObjectPrice());
		vo.setGoodsName(po.getObjectName());
		vo.setImgUrlPath(imageService.buildImageUrl(po.getImgId()));
		return vo;
	}
}
