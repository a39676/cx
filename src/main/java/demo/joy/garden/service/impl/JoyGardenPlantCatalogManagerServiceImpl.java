package demo.joy.garden.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.common.pojo.type.JoyModuleType;
import demo.joy.garden.mapper.JoyGardenPlantCatalogMapper;
import demo.joy.garden.mapper.JoyGardenPlantGrowingStageMapper;
import demo.joy.garden.pojo.dto.JoyGardenCreatePlantDTO;
import demo.joy.garden.pojo.dto.JoyGardenCreatePlantStageDTO;
import demo.joy.garden.pojo.dto.JoyGardenPlantSearchConditionDTO;
import demo.joy.garden.pojo.dto.JoyGardenPlantStageDeleteDTO;
import demo.joy.garden.pojo.dto.JoyGardenPlantStageUpdateDTO;
import demo.joy.garden.pojo.dto.JoyGardenPlantStageUpdateSortDTO;
import demo.joy.garden.pojo.dto.ShowPlantStageMangerDTO;
import demo.joy.garden.pojo.po.JoyGardenPlantCatalog;
import demo.joy.garden.pojo.po.JoyGardenPlantCatalogExample;
import demo.joy.garden.pojo.po.JoyGardenPlantCatalogExample.Criteria;
import demo.joy.garden.pojo.po.JoyGardenPlantGrowingStage;
import demo.joy.garden.pojo.po.JoyGardenPlantGrowingStageExample;
import demo.joy.garden.pojo.type.JoyGardenPlantType;
import demo.joy.garden.pojo.type.JoyGardenSubModuleType;
import demo.joy.garden.pojo.vo.JoyGardenPlantCatalogVO;
import demo.joy.garden.pojo.vo.JoyGardenPlantGrowingStageVO;
import demo.joy.garden.service.JoyGardenPlantCatalogManagerService;
import demo.joy.image.pojo.dto.JoyImageUploadDTO;
import demo.joy.image.pojo.result.JoyImageUploadResult;

@Service
public class JoyGardenPlantCatalogManagerServiceImpl extends JoyGardenCommonService
		implements JoyGardenPlantCatalogManagerService {

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

	@Override
	public JoyCommonResult createNewPlant(JoyGardenCreatePlantDTO dto) {
		JoyCommonResult r = new JoyCommonResult();
		if (StringUtils.isBlank(dto.getPlantName())) {
			r.setMessage("Can NOT set empty name");
			return r;
		}

		JoyGardenPlantType plantType = JoyGardenPlantType.getType(dto.getPlantTypeCode());
		if (plantType == null) {
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
		if (StringUtils.isBlank(dto.getStageName())) {
			r.setMessage("Can NOT set empty stage name");
			return r;
		}

		if (dto.getLivingMinute() == null || dto.getLivingMinute() < 1) {
			r.setMessage("Please set living minute > 0");
			return r;
		}

		if (StringUtils.isBlank(dto.getStageImgSrc())) {
			r.setMessage("Please upload stage image");
			return r;
		}

		if (StringUtils.isBlank(dto.getPlantPK())) {
			r.setMessage("Please select plant");
			return r;
		}
		Long plantId = systemOptionService.decryptPrivateKey(dto.getPlantPK());
		if (plantId == null) {
			r.setMessage("Please select plant");
			return r;
		}

		JoyGardenPlantCatalog plant = plantCatalogMapper.selectByPrimaryKey(plantId);

		JoyImageUploadDTO uploadImageDTO = new JoyImageUploadDTO();
		uploadImageDTO.setSrc(dto.getStageImgSrc());
		JoyImageUploadResult imgSaveResult = imageService.uploadImage(uploadImageDTO,
				optionService.getGardenImageStorePathPrefix(), JoyModuleType.GARDEN,
				JoyGardenSubModuleType.PLANT_STAGE.getCode());

		if (imgSaveResult.isFail()) {
			r.setMessage(imgSaveResult.getMessage());
			return r;
		}

		updatePlantImgByNewStage(plant, imgSaveResult.getImgId());

		JoyGardenPlantGrowingStageExample stageExample = new JoyGardenPlantGrowingStageExample();
		stageExample.createCriteria().andIsDeleteEqualTo(false).andPlantIdEqualTo(plantId);
		List<JoyGardenPlantGrowingStage> stagePOList = plantGrowingStageMapper.selectByExample(stageExample);
		Integer maxStageSequence = 0;
		if (stagePOList != null && !stagePOList.isEmpty()) {
			maxStageSequence = 1;
			for (JoyGardenPlantGrowingStage stage : stagePOList) {
				if (maxStageSequence < stage.getStageSequence()) {
					maxStageSequence = stage.getStageSequence();
				}
			}
		}

		JoyGardenPlantGrowingStage po = new JoyGardenPlantGrowingStage();
		long newId = snowFlake.getNextId();
		po.setId(newId);
		if (dto.getCycleStage()) {
			po.setIsCycleStage(true);
		}
		po.setImgId(imgSaveResult.getImgId());
		po.setLivingMinute(dto.getLivingMinute());
		po.setPlantId(plantId);
		po.setStageName(dto.getStageName());
		po.setStageSequence(maxStageSequence + 1);
		plantGrowingStageMapper.insertSelective(po);

		r.setIsSuccess();
		return r;
	}

	@Override
	public JoyCommonResult updatePlantStage(JoyGardenPlantStageUpdateDTO dto) {
		JoyCommonResult r = new JoyCommonResult();

		if (StringUtils.isBlank(dto.getStagePK())) {
			r.setMessage("null param");
			return r;
		}

		Long stageId = systemOptionService.decryptPrivateKey(dto.getStagePK());
		if (stageId == null) {
			r.setMessage("null param");
			return r;
		}

		JoyGardenPlantGrowingStage stagePO = plantGrowingStageMapper.selectByPrimaryKey(stageId);
		if (stagePO == null) {
			r.setMessage("error param");
			return r;
		}

		stagePO.setStageName(dto.getStageName());
		stagePO.setLivingMinute(dto.getLivingMinute());
		stagePO.setUpdateTime(LocalDateTime.now());
		plantGrowingStageMapper.updateByPrimaryKeySelective(stagePO);

		r.setIsSuccess();
		return r;
	}

	@Override
	public JoyCommonResult updatePlantStageSort(JoyGardenPlantStageUpdateSortDTO dto) {
		JoyCommonResult r = new JoyCommonResult();

		if (StringUtils.isBlank(dto.getStagePK())) {
			r.setMessage("null param");
			return r;
		}

		Long stageId = systemOptionService.decryptPrivateKey(dto.getStagePK());
		if (stageId == null) {
			r.setMessage("null param");
			return r;
		}

		JoyGardenPlantGrowingStage stagePO = plantGrowingStageMapper.selectByPrimaryKey(stageId);
		if (stagePO == null) {
			r.setMessage("error param");
			return r;
		}

		if (dto.getFlag().equals(1)) {
			ascendingStageSort(stagePO);
		} else {
			descendingStageSort(stagePO);
		}

		r.setIsSuccess();
		return r;
	}

	@Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
	private void ascendingStageSort(JoyGardenPlantGrowingStage po) {
		JoyGardenPlantGrowingStageExample example = new JoyGardenPlantGrowingStageExample();
		example.createCriteria().andIsDeleteEqualTo(false).andPlantIdEqualTo(po.getPlantId())
				.andStageSequenceEqualTo(po.getStageSequence() - 1);
		List<JoyGardenPlantGrowingStage> stagePOList = plantGrowingStageMapper.selectByExample(example);
		if (stagePOList == null || stagePOList.isEmpty()) {
			return;
		}

		Integer oldSequence = po.getStageSequence();

		JoyGardenPlantGrowingStage preStagePO = stagePOList.get(0);
		preStagePO.setStageSequence(oldSequence);
		preStagePO.setUpdateTime(LocalDateTime.now());

		po.setStageSequence(oldSequence - 1);
		po.setUpdateTime(LocalDateTime.now());
		plantGrowingStageMapper.updateByPrimaryKeySelective(po);
		plantGrowingStageMapper.updateByPrimaryKeySelective(preStagePO);
	}

	@Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
	private void descendingStageSort(JoyGardenPlantGrowingStage po) {
		JoyGardenPlantGrowingStageExample example = new JoyGardenPlantGrowingStageExample();
		example.createCriteria().andIsDeleteEqualTo(false).andPlantIdEqualTo(po.getPlantId())
				.andStageSequenceEqualTo(po.getStageSequence() + 1);
		List<JoyGardenPlantGrowingStage> stagePOList = plantGrowingStageMapper.selectByExample(example);
		if (stagePOList == null || stagePOList.isEmpty()) {
			return;
		}

		Integer oldSequence = po.getStageSequence();

		JoyGardenPlantGrowingStage nextStagePO = stagePOList.get(0);
		nextStagePO.setStageSequence(oldSequence);
		nextStagePO.setUpdateTime(LocalDateTime.now());

		po.setStageSequence(oldSequence + 1);
		po.setUpdateTime(LocalDateTime.now());
		plantGrowingStageMapper.updateByPrimaryKeySelective(po);
		plantGrowingStageMapper.updateByPrimaryKeySelective(nextStagePO);

	}

	@Override
	@Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
	public JoyCommonResult deletePlantStage(JoyGardenPlantStageDeleteDTO dto) {
		JoyCommonResult r = new JoyCommonResult();

		if (StringUtils.isBlank(dto.getStagePK())) {
			r.setMessage("null param");
			return r;
		}

		Long stageId = systemOptionService.decryptPrivateKey(dto.getStagePK());
		if (stageId == null) {
			r.setMessage("null param");
			return r;
		}

		JoyGardenPlantGrowingStage stagePO = plantGrowingStageMapper.selectByPrimaryKey(stageId);
		stagePO.setIsDelete(true);
		stagePO.setId(stageId);
		stagePO.setUpdateTime(LocalDateTime.now());
		plantGrowingStageMapper.updateByPrimaryKeySelective(stagePO);

		updatePreStageLinkIdAfterStageDelete(stagePO);

		r.setIsSuccess();
		return r;
	}

	private void updatePlantImgByNewStage(JoyGardenPlantCatalog plant, Long newStageImgId) {
		plant.setImgId(newStageImgId);
		plantCatalogMapper.updateByPrimaryKeySelective(plant);
	}

	private void updatePreStageLinkIdAfterStageDelete(JoyGardenPlantGrowingStage stagePO) {
		JoyGardenPlantGrowingStageExample example = new JoyGardenPlantGrowingStageExample();
		example.createCriteria().andIsDeleteEqualTo(false).andPlantIdEqualTo(stagePO.getPlantId())
				.andStageSequenceGreaterThan(stagePO.getStageSequence());
		List<JoyGardenPlantGrowingStage> stageList = plantGrowingStageMapper.selectByExample(example);
		if (stageList == null || stageList.isEmpty()) {
			return;
		}

		for (JoyGardenPlantGrowingStage stage : stageList) {
			stage.setStageSequence(stage.getStageSequence() - 1);
			stage.setUpdateTime(LocalDateTime.now());
			plantGrowingStageMapper.updateByPrimaryKey(stage);
		}
	}

	@Override
	public ModelAndView plantStageManger(ShowPlantStageMangerDTO dto) {
		ModelAndView view = new ModelAndView("joyJSP/garden/JoyPlantStageManager");

		if (StringUtils.isBlank(dto.getPlantPK())) {
			return view;
		}

		Long plantCatalogId = systemOptionService.decryptPrivateKey(dto.getPlantPK());
		if (plantCatalogId == null) {
			return view;
		}

		JoyGardenPlantCatalog po = plantCatalogMapper.selectByPrimaryKey(plantCatalogId);
		if (po == null) {
			return view;
		}

		view.addObject("plant", po);
		view.addObject("plantPK", dto.getPlantPK());

		JoyGardenPlantGrowingStageExample growingStageExample = new JoyGardenPlantGrowingStageExample();
		growingStageExample.createCriteria().andIsDeleteEqualTo(false).andPlantIdEqualTo(plantCatalogId);
		growingStageExample.setOrderByClause(" stage_sequence ");
		List<JoyGardenPlantGrowingStage> plantStagePOList = plantGrowingStageMapper
				.selectByExample(growingStageExample);
		if (plantStagePOList == null || plantStagePOList.isEmpty()) {
			return view;
		}

		List<JoyGardenPlantGrowingStageVO> stageVOList = new ArrayList<>();
		for (JoyGardenPlantGrowingStage stagePO : plantStagePOList) {
			stageVOList.add(buildJoyGardenPlantGrowingStageVO(stagePO));
		}

		view.addObject("stageVOList", stageVOList);

		return view;
	}

}
