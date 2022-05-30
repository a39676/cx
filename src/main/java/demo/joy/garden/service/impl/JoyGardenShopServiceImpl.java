package demo.joy.garden.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.joy.garden.mapper.JoyGardenPlantGrowingStageMapper;
import demo.joy.garden.pojo.dto.JoyGardenShopSeedSearchDTO;
import demo.joy.garden.pojo.po.JoyGardenPlantGrowingStage;
import demo.joy.garden.pojo.po.JoyGardenPlantGrowingStageExample;
import demo.joy.garden.pojo.vo.JoyGardenPlantGrowingStageVO;
import demo.joy.garden.service.JoyGardenShopManagerService;

@Service
public class JoyGardenShopServiceImpl extends JoyGardenCommonService implements JoyGardenShopManagerService {

	@Autowired
	private JoyGardenPlantGrowingStageMapper stageMapper;
	
	public ModelAndView seedSearchView(JoyGardenShopSeedSearchDTO dto) {
//		TODO
		ModelAndView view = new ModelAndView();
		
		JoyGardenPlantGrowingStageExample example = new JoyGardenPlantGrowingStageExample();
		example.createCriteria().andIsDeleteEqualTo(false).andStageSequenceEqualTo(1);
		
		List<JoyGardenPlantGrowingStage> seedList = stageMapper.selectByExample(example);
		List<JoyGardenPlantGrowingStageVO> stageVoList = new ArrayList<>();
		if(seedList != null && !seedList.isEmpty()) {
			for(JoyGardenPlantGrowingStage stage : seedList) {
				stageVoList.add(buildJoyGardenPlantGrowingStageVO(stage));
			}
		}
		
//		TODO
		
		
		return null;
	}
}
