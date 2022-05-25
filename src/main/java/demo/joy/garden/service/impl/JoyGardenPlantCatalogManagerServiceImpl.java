package demo.joy.garden.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.base.user.pojo.po.UsersDetail;
import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.common.pojo.type.JoyModuleType;
import demo.joy.common.service.JoyCommonService;
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
public class JoyGardenPlantCatalogManagerServiceImpl extends JoyCommonService
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

	private JoyGardenPlantCatalogVO buildPlantVO(JoyGardenPlantCatalog po) {
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

		JoyGardenPlantGrowingStage po = new JoyGardenPlantGrowingStage();
		long newId = snowFlake.getNextId();
		po.setId(newId);
		if (dto.getCycleStage()) {
			po.setNextStageId(newId);
		}
		po.setImgId(imgSaveResult.getImgId());
		po.setLivingMinute(dto.getLivingMinute());
		po.setPlantId(plantId);
		po.setStageName(dto.getStageName());

		updatePreStageLinkIdBeforeNewRecordInsert(plantId, newId);

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

	private void ascendingStageSort(JoyGardenPlantGrowingStage po) {
		JoyGardenPlantGrowingStageExample example = new JoyGardenPlantGrowingStageExample();
		example.createCriteria().andIsDeleteEqualTo(false).andNextStageIdEqualTo(po.getId());
		List<JoyGardenPlantGrowingStage> stagePOList = plantGrowingStageMapper.selectByExample(example);
		if (stagePOList == null || stagePOList.isEmpty()) {
			return;
		}

		JoyGardenPlantGrowingStage preStage = stagePOList.get(0);

		example = new JoyGardenPlantGrowingStageExample();
		example.createCriteria().andIsDeleteEqualTo(false).andNextStageIdEqualTo(preStage.getId());
		stagePOList = plantGrowingStageMapper.selectByExample(example);
		if (stagePOList != null && !stagePOList.isEmpty()) {
			JoyGardenPlantGrowingStage prePreStage = stagePOList.get(0);
			prePreStage.setNextStageId(po.getId());
			plantGrowingStageMapper.updateByPrimaryKeySelective(prePreStage);
		}

		if (po.getId().equals(po.getNextStageId())) {
			preStage.setNextStageId(preStage.getId());
			plantGrowingStageMapper.updateByPrimaryKey(preStage);
		} else if (po.getId() == null) {
			preStage.setNextStageId(null);
			plantGrowingStageMapper.updateByPrimaryKey(preStage);
		} else {
			preStage.setNextStageId(po.getNextStageId());
			plantGrowingStageMapper.updateByPrimaryKey(preStage);
		}

		po.setNextStageId(preStage.getId());
		plantGrowingStageMapper.updateByPrimaryKeySelective(po);

	}

	private void descendingStageSort(JoyGardenPlantGrowingStage po) {
		if (po.getNextStageId() == null || po.getId().equals(po.getNextStageId())) {
			return;
		}

		JoyGardenPlantGrowingStageExample example = new JoyGardenPlantGrowingStageExample();
		example.createCriteria().andIsDeleteEqualTo(false).andIdEqualTo(po.getNextStageId());
		List<JoyGardenPlantGrowingStage> stagePOList = plantGrowingStageMapper.selectByExample(example);
		if (stagePOList == null || stagePOList.isEmpty()) {
			return;
		}

		JoyGardenPlantGrowingStage nextStage = stagePOList.get(0);

		example = new JoyGardenPlantGrowingStageExample();
		example.createCriteria().andIsDeleteEqualTo(false).andNextStageIdEqualTo(po.getId());
		stagePOList = plantGrowingStageMapper.selectByExample(example);
		if (stagePOList != null && !stagePOList.isEmpty()) {
			JoyGardenPlantGrowingStage preStage = stagePOList.get(0);
			preStage.setNextStageId(nextStage.getId());
			plantGrowingStageMapper.updateByPrimaryKeySelective(preStage);
		}

		if (nextStage.getNextStageId() == null) {
			po.setNextStageId(null);
			plantGrowingStageMapper.updateByPrimaryKey(po);
		} else if (nextStage.getId().equals(nextStage.getNextStageId())) {
			po.setNextStageId(po.getId());
			plantGrowingStageMapper.updateByPrimaryKeySelective(po);
		} else {
			po.setNextStageId(nextStage.getNextStageId());
			plantGrowingStageMapper.updateByPrimaryKeySelective(po);
		}

		nextStage.setNextStageId(po.getId());
		plantGrowingStageMapper.updateByPrimaryKeySelective(nextStage);

	}

	@Override
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

		JoyGardenPlantGrowingStage stagePO = new JoyGardenPlantGrowingStage();
		stagePO.setIsDelete(true);
		stagePO.setId(stageId);
		plantGrowingStageMapper.updateByPrimaryKeySelective(stagePO);

		updatePreStageLinkIdAfterStageDelete(stageId);

		r.setIsSuccess();
		return r;
	}

	private void updatePlantImgByNewStage(JoyGardenPlantCatalog plant, Long newStageImgId) {
		plant.setImgId(newStageImgId);
		plantCatalogMapper.updateByPrimaryKeySelective(plant);
	}

	private void updatePreStageLinkIdBeforeNewRecordInsert(Long plantId, Long newStageId) {
		JoyGardenPlantGrowingStageExample example = new JoyGardenPlantGrowingStageExample();
		example.createCriteria().andIsDeleteEqualTo(false).andPlantIdEqualTo(plantId);
		List<JoyGardenPlantGrowingStage> stagePOList = plantGrowingStageMapper.selectByExample(example);
		if (stagePOList == null || stagePOList.isEmpty()) {
			return;
		}

		stagePOList = sortStagePOList(stagePOList);

		JoyGardenPlantGrowingStage preStagePO = stagePOList.get(stagePOList.size() - 1);
		preStagePO.setNextStageId(newStageId);

		plantGrowingStageMapper.updateByPrimaryKeySelective(preStagePO);
	}

	private void updatePreStageLinkIdAfterStageDelete(Long deletedStageId) {
		JoyGardenPlantGrowingStageExample example = new JoyGardenPlantGrowingStageExample();
		example.createCriteria().andIsDeleteEqualTo(false).andNextStageIdEqualTo(deletedStageId);
		List<JoyGardenPlantGrowingStage> stageList = plantGrowingStageMapper.selectByExample(example);
		if (stageList == null || stageList.isEmpty()) {
			return;
		}

		JoyGardenPlantGrowingStage deletedPO = plantGrowingStageMapper.selectByPrimaryKey(deletedStageId);

		JoyGardenPlantGrowingStage lastStage = stageList.get(0);
		lastStage.setNextStageId(deletedPO.getNextStageId());

		plantGrowingStageMapper.updateByPrimaryKey(lastStage);
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
		List<JoyGardenPlantGrowingStage> plantStagePOList = plantGrowingStageMapper
				.selectByExample(growingStageExample);
		if (plantStagePOList == null || plantStagePOList.isEmpty()) {
			return view;
		}

		plantStagePOList = sortStagePOList(plantStagePOList);

		List<JoyGardenPlantGrowingStageVO> stageVOList = new ArrayList<>();
		for (JoyGardenPlantGrowingStage stagePO : plantStagePOList) {
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
		vo.setImgUrlPath(imageService.buildImageUrl(po.getImgId()));
		return vo;
	}

	private List<JoyGardenPlantGrowingStage> sortStagePOList(List<JoyGardenPlantGrowingStage> stagePOList) {
		if (stagePOList.size() == 1) {
			return stagePOList;
		}

		List<JoyGardenPlantGrowingStage> result = new ArrayList<>();

		Map<Long, JoyGardenPlantGrowingStage> idMap = new HashMap<>();
		for (JoyGardenPlantGrowingStage po : stagePOList) {
			idMap.put(po.getId(), po);
		}

		result.add(stagePOList.get(0));
		JoyGardenPlantGrowingStage nextStagePO = stagePOList.get(0);
		firstTag: while (idMap.get(nextStagePO.getNextStageId()) != null) {
			result.add(idMap.get(nextStagePO.getNextStageId()));
			nextStagePO = idMap.get(nextStagePO.getNextStageId());
			if (nextStagePO.getId().equals(nextStagePO.getNextStageId())) {
				break firstTag;
			}
		}

		if (result.size() == stagePOList.size()) {
			return result;
		}

		idMap.clear();
		for (JoyGardenPlantGrowingStage po : stagePOList) {
			idMap.put(po.getNextStageId(), po);
		}

		JoyGardenPlantGrowingStage preStagePO = null;
		secondTag: while ((preStagePO = idMap.get(result.get(0).getId())) != null) {
			result.add(0, preStagePO);
			if (preStagePO.getId().equals(preStagePO.getNextStageId())) {
				break secondTag;
			}
		}

		return result;
	}
}
