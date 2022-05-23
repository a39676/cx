package demo.joy.garden.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.base.user.pojo.po.UsersDetail;
import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.common.service.JoyCommonService;
import demo.joy.garden.mapper.JoyGardenPlantCatalogMapper;
import demo.joy.garden.mapper.JoyGardenPlantGrowingStageMapper;
import demo.joy.garden.pojo.dto.JoyGardenCreatePlantDTO;
import demo.joy.garden.pojo.dto.JoyGardenCreatePlantStageDTO;
import demo.joy.garden.pojo.dto.JoyGardenPlantSearchConditionDTO;
import demo.joy.garden.pojo.dto.ShowPlantStageMangerDTO;
import demo.joy.garden.pojo.po.JoyGardenPlantCatalog;
import demo.joy.garden.pojo.po.JoyGardenPlantCatalogExample;
import demo.joy.garden.pojo.po.JoyGardenPlantCatalogExample.Criteria;
import demo.joy.garden.pojo.po.JoyGardenPlantGrowingStage;
import demo.joy.garden.pojo.po.JoyGardenPlantGrowingStageExample;
import demo.joy.garden.pojo.type.JoyGardenPlantType;
import demo.joy.garden.pojo.vo.JoyGardenPlantCatalogVO;
import demo.joy.garden.pojo.vo.JoyGardenPlantGrowingStageVO;
import demo.joy.garden.service.JoyGardenPlantCatalogManagerService;
import demo.joy.image.manager.pojo.dto.JoyImageUploadDTO;
import demo.joy.image.manager.pojo.result.JoyImageUploadResult;
import demo.joy.image.manager.service.JoyImageManagerService;

@Service
public class JoyGardenPlantCatalogManagerServiceImpl extends JoyCommonService
		implements JoyGardenPlantCatalogManagerService {
	
	@Autowired
	private JoyImageManagerService imageManagerService;

	@Autowired
	private JoyGardenPlantCatalogMapper plantCatalogMapper;
	@Autowired
	private JoyGardenPlantGrowingStageMapper plantGrowingStageMapper;

	@Override
	public ModelAndView plantCatalogManager() {
		ModelAndView view = new ModelAndView("joyJSP/garden/JoyPlantCatalogManager");
		view.addObject("plantTypeList", JoyGardenPlantType.values());
		return view;
	}

	@Override
	public ModelAndView plantCatalogCreator() {
		ModelAndView view = new ModelAndView("joyJSP/garden/JoyPlantCatalogCreator");
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
			criteria.andPlantNameLike("%" + dto.getName() + "%");
		}
		List<JoyGardenPlantCatalog> poList = plantCatalogMapper.selectByExample(example);
		if (poList == null || poList.isEmpty()) {
			return view;
		}
		List<JoyGardenPlantCatalogVO> voList = new ArrayList<>();
		for (JoyGardenPlantCatalog po : poList) {
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
		if (userDetail != null) {
			vo.setCreatorName(userDetail.getNickName());
		}

		return vo;
	}

	@Override
	public JoyCommonResult createNewPlant(JoyGardenCreatePlantDTO dto) {
		JoyCommonResult r = new JoyCommonResult();
		if(StringUtils.isBlank(dto.getPlantName())) {
			r.setMessage("Can NOT set empty name");
			return r;
		}
		
		JoyGardenPlantType plantType = JoyGardenPlantType.getType(dto.getPlantTypeCode());
		if(plantType == null) {
			r.setMessage("Please set correct plant type");
			return r;
		}
		
		JoyGardenPlantCatalog po = new JoyGardenPlantCatalog();
		po.setId(snowFlake.getNextId());
		po.setCreatorId(baseUtilCustom.getUserId());
		po.setPlantName(dto.getPlantName());
		po.setPlantType(dto.getPlantTypeCode());
		
		plantCatalogMapper.insertSelective(po);

		r.setIsSuccess();
		return r;
	}
	
	@Override
	public JoyCommonResult createNewPlantStage(JoyGardenCreatePlantStageDTO dto) {
		JoyCommonResult r = new JoyCommonResult();
		if(StringUtils.isBlank(dto.getStageName())) {
			r.setMessage("Can NOT set empty stage name");
			return r;
		}
		
		if(dto.getLivingMinute() == null || dto.getLivingMinute() < 1) {
			r.setMessage("Please set living minute > 0");
			return r;
		}
		
		if(StringUtils.isBlank(dto.getStageImgSrc())) {
			r.setMessage("Please upload stage image");
			return r;
		}
		
		if(StringUtils.isBlank(dto.getPlantPK())) {
			r.setMessage("Please select plant");
			return r;
		}
		Long plantId = systemOptionService.decryptPrivateKey(dto.getPlantPK());
		if(plantId == null) {
			r.setMessage("Please select plant");
			return r;
		}
		
		JoyImageUploadDTO uploadImageDTO = new JoyImageUploadDTO();
		uploadImageDTO.setSrc(dto.getStageImgSrc());
		JoyImageUploadResult imgSaveResult = imageManagerService.uploadImage(uploadImageDTO, optionService.getGardenImageStorePathPrefix());
		
		if(imgSaveResult.isFail()) {
			r.setMessage(imgSaveResult.getMessage());
			return r;
		}
		
		JoyGardenPlantGrowingStage po = new JoyGardenPlantGrowingStage();
		long newId = snowFlake.getNextId();
		po.setId(newId);
		if(dto.getCycleStage()) {
			po.setNextStageId(newId);
		}
		po.setImgUrlPath(imgSaveResult.getImgSavePath());
		po.setLivingMinute(dto.getLivingMinute());
		po.setPlantId(plantId);
		po.setStageName(dto.getStageName());

		plantGrowingStageMapper.insertSelective(po);
		
		r.setIsSuccess();
		return r;
	}

	@Override
	public ModelAndView plantStageManger(ShowPlantStageMangerDTO dto) {
		ModelAndView view = new ModelAndView("joyJSP/garden/JoyPlantStageManager");
		
		if(StringUtils.isBlank(dto.getPlantPK())) {
			return view;
		}
		
		Long plantCatalogId = systemOptionService.decryptPrivateKey(dto.getPlantPK());
		if(plantCatalogId == null) {
			return view;
		}
		
		JoyGardenPlantCatalog po = plantCatalogMapper.selectByPrimaryKey(plantCatalogId);
		if(po == null) {
			return view;
		}
		
		view.addObject("plant", po);
		view.addObject("plantPK", dto.getPlantPK());
		
		JoyGardenPlantGrowingStageExample growingStageExample = new JoyGardenPlantGrowingStageExample();
		growingStageExample.createCriteria().andIsDeleteEqualTo(false).andPlantIdEqualTo(plantCatalogId);
		List<JoyGardenPlantGrowingStage> plantStagePOList = plantGrowingStageMapper.selectByExample(growingStageExample);
		if(plantStagePOList == null || plantStagePOList.isEmpty()) {
			return view;
		}
		
		List<JoyGardenPlantGrowingStageVO> stageVOList = new ArrayList<>();
		for(JoyGardenPlantGrowingStage stagePO : plantStagePOList) {
			stageVOList.add(buildJoyGardenPlantGrowingStageVO(stagePO));
		}
		
		view.addObject("stageVOList", stageVOList);
		
		return view;
	}
	
	private JoyGardenPlantGrowingStageVO buildJoyGardenPlantGrowingStageVO(JoyGardenPlantGrowingStage po) {
		JoyGardenPlantGrowingStageVO vo = new JoyGardenPlantGrowingStageVO();
		vo.setPk(systemOptionService.encryptId(po.getId()));
		vo.setLivingMinute(po.getLivingMinute());
		vo.setStageName(po.getStageName());
		
//		TODO url path 
		vo.setImgUrlPath(po.getImgUrlPath());
		return vo;
	}
}
