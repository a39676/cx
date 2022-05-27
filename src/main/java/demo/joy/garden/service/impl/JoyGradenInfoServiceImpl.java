package demo.joy.garden.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.common.service.JoyCommonService;
import demo.joy.garden.mapper.JoyGardenInfoMapper;
import demo.joy.garden.pojo.dto.CreateNewGardenDTO;
import demo.joy.garden.pojo.po.JoyGardenInfo;
import demo.joy.garden.pojo.vo.GardenInfoVO;
import demo.joy.garden.service.JoyGradenInfoService;

@Scope("singleton")
@Service
public class JoyGradenInfoServiceImpl extends JoyCommonService implements JoyGradenInfoService {

	@Autowired
	private JoyGardenInfoMapper infoMapper;

	private Map<Long, JoyGardenInfo> infoMap = new HashMap<>();

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
		
		GardenInfoVO vo = buildVO(po);
		view.addObject("title", vo.getGardenName());
		view.addObject("gardenInfo", vo);
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
	
	private GardenInfoVO buildVO(JoyGardenInfo po) {
		GardenInfoVO vo = new GardenInfoVO();
		vo.setPk(systemOptionService.encryptId(po.getId()));
		vo.setFieldCount(po.getFieldCount());
		vo.setWetlandCount(po.getWetlandCount());
		vo.setWoodlandCount(po.getWoodlandCount());
		vo.setGardenName(po.getGardenName());
		return vo;
	}

}
