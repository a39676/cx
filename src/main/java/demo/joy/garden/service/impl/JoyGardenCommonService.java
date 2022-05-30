package demo.joy.garden.service.impl;

import demo.base.user.pojo.po.UsersDetail;
import demo.joy.common.service.JoyCommonService;
import demo.joy.garden.pojo.po.JoyGardenInfo;
import demo.joy.garden.pojo.po.JoyGardenLands;
import demo.joy.garden.pojo.po.JoyGardenPlantCatalog;
import demo.joy.garden.pojo.po.JoyGardenPlantGrowingStage;
import demo.joy.garden.pojo.type.JoyGardenLandType;
import demo.joy.garden.pojo.type.JoyGardenPlantType;
import demo.joy.garden.pojo.vo.GardenInfoVO;
import demo.joy.garden.pojo.vo.JoyGardenLandVO;
import demo.joy.garden.pojo.vo.JoyGardenPlantCatalogVO;
import demo.joy.garden.pojo.vo.JoyGardenPlantGrowingStageVO;

public abstract class JoyGardenCommonService extends JoyCommonService {

	protected JoyGardenPlantGrowingStageVO buildJoyGardenPlantGrowingStageVO(JoyGardenPlantGrowingStage po) {
		JoyGardenPlantGrowingStageVO vo = new JoyGardenPlantGrowingStageVO();
		vo.setPk(systemOptionService.encryptId(po.getId()));
		vo.setLivingMinute(po.getLivingMinute());
		vo.setStageName(po.getStageName());
		vo.setImgUrlPath(imageService.buildImageUrl(po.getImgId()));
		return vo;
	}
	
	protected JoyGardenPlantCatalogVO buildPlantVO(JoyGardenPlantCatalog po) {
		JoyGardenPlantCatalogVO vo = new JoyGardenPlantCatalogVO();
		vo.setPk(systemOptionService.encryptId(po.getId()));
		vo.setPlantName(po.getPlantName());
		vo.setPlantType(JoyGardenPlantType.getType(po.getPlantType()));
		vo.setImgUrl(imageService.buildImageUrl(po.getImgId()));
		UsersDetail userDetail = userDetailService.findById(po.getCreatorId());
		if (userDetail != null) {
			vo.setCreatorName(userDetail.getNickName());
		}

		return vo;
	}
	
	protected GardenInfoVO buildGardenInfoVO(JoyGardenInfo po) {
		GardenInfoVO vo = new GardenInfoVO();
		vo.setPk(systemOptionService.encryptId(po.getId()));
		vo.setFieldCount(po.getFieldCount());
		vo.setWetlandCount(po.getWetlandCount());
		vo.setWoodlandCount(po.getWoodlandCount());
		vo.setGardenName(po.getGardenName());
		return vo;
	}
	
	protected JoyGardenLandVO buildLandVO(JoyGardenLands po) {
		JoyGardenLandVO vo = new JoyGardenLandVO();
		vo.setPk(systemOptionService.encryptId(po.getId()));
		vo.setLandLevel(po.getLandLevel());
		vo.setLandType(JoyGardenLandType.getType(po.getLandType()));
		vo.setUserId(po.getUserId());
		return vo;
	}
}
