package demo.joy.garden.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.common.service.JoyCommonService;
import demo.joy.garden.mapper.JoyGardenInfoMapper;
import demo.joy.garden.mapper.JoyGardenLandsMapper;
import demo.joy.garden.pojo.dto.CreateNewGardenDTO;
import demo.joy.garden.pojo.po.JoyGardenInfo;
import demo.joy.garden.pojo.po.JoyGardenLands;
import demo.joy.garden.pojo.po.JoyGardenLandsExample;
import demo.joy.garden.pojo.type.JoyGardenLandType;
import demo.joy.garden.pojo.vo.GardenInfoVO;
import demo.joy.garden.pojo.vo.JoyGardenLandVO;
import demo.joy.garden.service.JoyGradenInfoService;

@Scope("singleton")
@Service
public class JoyGradenInfoServiceImpl extends JoyCommonService implements JoyGradenInfoService {

	@Autowired
	private JoyGardenInfoMapper infoMapper;
	@Autowired
	private JoyGardenLandsMapper landsMapper;
	@Autowired
	private JoyGardenOptionService gardenOptionService;

	private Map<Long, JoyGardenInfo> infoMap = new HashMap<>();
	private Map<Long, List<JoyGardenLands>> fieldMap = new HashMap<>();
	private Map<Long, List<JoyGardenLands>> wetlandsMap = new HashMap<>();
	private Map<Long, List<JoyGardenLands>> woodlandsMap = new HashMap<>();

	@Override
	public ModelAndView visitOtherGarden(String userPK) {
		ModelAndView view = new ModelAndView("joyJSP/garden/JoyGardenIndex");

		if (StringUtils.isBlank(userPK)) {
			return view;
		}

		Long userId = systemOptionService.decryptPrivateKey(userPK);
		if (userId == null) {
			return view;
		}

		JoyGardenInfo po = infoMap.get(userId);
		if (po == null) {
			po = infoMapper.selectByPrimaryKey(userId);
			if (po != null) {
				infoMap.put(userId, po);
			} else {
				po = new JoyGardenInfo();
			}
		}

		view.addObject("gardenInfo", po);
		return view;
	}

	@Override
	public ModelAndView index() {
		ModelAndView view = new ModelAndView("joyJSP/garden/JoyGardenIndex");

		Long userId = baseUtilCustom.getUserId();
		JoyGardenInfo po = infoMap.get(userId);
		if (po == null) {
			po = infoMapper.selectByPrimaryKey(userId);
			if (po != null) {
				infoMap.put(userId, po);
			} else {
				return createNewGardenView();
			}
		}

		GardenInfoVO vo = buildGardenInfoVO(po);
		view.addObject("title", vo.getGardenName());
		view.addObject("gardenInfo", vo);

		loadLands(false);
		List<JoyGardenLandVO> fieldLandVoList = new ArrayList<>();
		List<JoyGardenLandVO> wetlandVoList = new ArrayList<>();
		List<JoyGardenLandVO> woodlandVoList = new ArrayList<>();

		List<JoyGardenLands> fieldLandPoList = fieldMap.get(userId);
		List<JoyGardenLands> wetLandPoList = wetlandsMap.get(userId);
		List<JoyGardenLands> woodLandPoList = woodlandsMap.get(userId);

		for (JoyGardenLands land : fieldLandPoList) {
			fieldLandVoList.add(buildLandVO(land));
		}
		for (JoyGardenLands land : wetLandPoList) {
			wetlandVoList.add(buildLandVO(land));
		}
		for (JoyGardenLands land : woodLandPoList) {
			woodlandVoList.add(buildLandVO(land));
		}

		view.addObject("fieldVoList", fieldLandVoList);
		view.addObject("canCreateNewField", gardenOptionService.getFieldMaxSize() > fieldLandVoList.size());
		view.addObject("wetlandVoList", wetlandVoList);
		view.addObject("woodlandVoList", woodlandVoList);

		return view;
	}

	@Override
	public JoyCommonResult createNewGarden(CreateNewGardenDTO dto) {
		JoyCommonResult r = new JoyCommonResult();

		if (StringUtils.isBlank(dto.getGardenName())) {
			r.setMessage("不能使用空白名称");
			return r;
		}

		String gardenName = sanitize(dto.getGardenName());

		JoyGardenInfo po = new JoyGardenInfo();
		po.setId(baseUtilCustom.getUserId());
		po.setGardenName(gardenName);

		infoMapper.insertSelective(po);

		r.setIsSuccess();
		return r;
	}

	private ModelAndView createNewGardenView() {
		return new ModelAndView("joyJSP/garden/JoyGardenCreateNewGarden");
	}

	private GardenInfoVO buildGardenInfoVO(JoyGardenInfo po) {
		GardenInfoVO vo = new GardenInfoVO();
		vo.setPk(systemOptionService.encryptId(po.getId()));
		vo.setFieldCount(po.getFieldCount());
		vo.setWetlandCount(po.getWetlandCount());
		vo.setWoodlandCount(po.getWoodlandCount());
		vo.setGardenName(po.getGardenName());
		return vo;
	}

	private void loadLands(boolean refresh) {
		Long userId = baseUtilCustom.getUserId();
		if (fieldMap.containsKey(userId) && wetlandsMap.containsKey(userId) && woodlandsMap.containsKey(userId)
				&& refresh) {
			return;
		}

		JoyGardenLandsExample example = new JoyGardenLandsExample();
		List<JoyGardenLands> poList = landsMapper.selectByExample(example);

		List<JoyGardenLands> fieldLandList = new ArrayList<>();
		List<JoyGardenLands> wetlandVoList = new ArrayList<>();
		List<JoyGardenLands> woodlandVoList = new ArrayList<>();

		if (poList != null && !poList.isEmpty()) {
			for (JoyGardenLands land : poList) {
				if (JoyGardenLandType.FIELD.getCode().equals(land.getLandType())) {
					fieldLandList.add(land);
				} else if (JoyGardenLandType.WETLAND.getCode().equals(land.getLandType())) {
					wetlandVoList.add(land);
				} else {
					woodlandVoList.add(land);
				}
			}
		}

		fieldMap.put(userId, fieldLandList);
		wetlandsMap.put(userId, wetlandVoList);
		woodlandsMap.put(userId, woodlandVoList);
	}

	private JoyGardenLandVO buildLandVO(JoyGardenLands po) {
		JoyGardenLandVO vo = new JoyGardenLandVO();
		vo.setPk(systemOptionService.encryptId(po.getId()));
		vo.setLandLevel(po.getLandLevel());
		vo.setLandType(JoyGardenLandType.getType(po.getLandType()));
		vo.setUserId(po.getUserId());
		return vo;
	}

	@Override
	public JoyCommonResult createNewFieldLand() {
		JoyCommonResult r = new JoyCommonResult();
		Long userId = baseUtilCustom.getUserId();
		
		List<JoyGardenLands> fieldLandPoList = fieldMap.get(userId);
		if(fieldLandPoList.size() >= gardenOptionService.getFieldMaxSize()) {
			r.setMessage("已经达到最大数量, 无法再扩容了");
			return r;
		}
		
		Integer consumePoint = gardenOptionService.getCreateFieldConsumePointMap().get(fieldLandPoList.size() + 1);
		
		CommonResult consumePointResult = studentService.pointConsume(userId, consumePoint.doubleValue());
		if(consumePointResult.isFail()) {
			r.setMessage(consumePointResult.getMessage());
			return r;
		}
		
		JoyGardenLands newField = new JoyGardenLands();
		newField.setUserId(userId);
		newField.setId(snowFlake.getNextId());
		newField.setCreateTime(LocalDateTime.now());
		newField.setIsDelete(false);
		newField.setLandLevel(1);
		newField.setLandType(JoyGardenLandType.FIELD.getCode());
		
		landsMapper.insert(newField);
		
		fieldLandPoList.add(newField);
		fieldMap.put(userId, fieldLandPoList);
		
		if(gardenOptionService.getFieldMaxSize().equals(fieldLandPoList.size())) {
			r.setMessage("MAX");
		}
		
		r.setIsSuccess();
		return r;
	}
}
