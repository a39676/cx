package demo.joy.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import demo.joy.garden.pojo.po.JoyGardenInfo;
import demo.joy.garden.pojo.po.JoyGardenLands;

@Scope("singleton")
@Service
public class JoyCacheService extends CommonService {

	private Map<Long, JoyGardenInfo> gardenInfoMap = new HashMap<>();
	private Map<Long, List<JoyGardenLands>> fieldlandMap = new HashMap<>();
	private Map<Long, List<JoyGardenLands>> wetlandsMap = new HashMap<>();
	private Map<Long, List<JoyGardenLands>> woodlandsMap = new HashMap<>();

	public Map<Long, JoyGardenInfo> getGardenInfoMap() {
		return gardenInfoMap;
	}

	public void setGardenInfoMap(Map<Long, JoyGardenInfo> infoMap) {
		this.gardenInfoMap = infoMap;
	}

	public Map<Long, List<JoyGardenLands>> getFieldlandMap() {
		return fieldlandMap;
	}

	public void setFieldlandMap(Map<Long, List<JoyGardenLands>> fieldMap) {
		this.fieldlandMap = fieldMap;
	}

	public Map<Long, List<JoyGardenLands>> getWetlandsMap() {
		return wetlandsMap;
	}

	public void setWetlandsMap(Map<Long, List<JoyGardenLands>> wetlandsMap) {
		this.wetlandsMap = wetlandsMap;
	}

	public Map<Long, List<JoyGardenLands>> getWoodlandsMap() {
		return woodlandsMap;
	}

	public void setWoodlandsMap(Map<Long, List<JoyGardenLands>> woodlandsMap) {
		this.woodlandsMap = woodlandsMap;
	}

	@Override
	public String toString() {
		return "JoyCacheService [infoMap=" + gardenInfoMap + ", fieldMap=" + fieldlandMap + ", wetlandsMap=" + wetlandsMap
				+ ", woodlandsMap=" + woodlandsMap + "]";
	}

}
