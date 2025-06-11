package demo.common.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.dto.BaseStrDTO;
import auxiliaryCommon.pojo.type.HeartBeatType;
import demo.interaction.bbt.service.BbtCommonService;

@Scope("singleton")
@Service
public class HeartBeatService extends BbtCommonService {

	private Map<String, LocalDateTime> heartBeatMap = new HashMap<>();

	public void setBbtLastHeartBeat(BaseStrDTO dto) {
		if (bbtDynamicKey.isCorrectKey(dto.getStr())) {
			heartBeatMap.put(HeartBeatType.BBT.getName(), LocalDateTime.now());
		}
	}

	public Map<String, LocalDateTime> getHeartBeatMap() {
		return heartBeatMap;
	}

	public void setHeartBeatMap(Map<String, LocalDateTime> heartBeatMap) {
		this.heartBeatMap = heartBeatMap;
	}

	@Override
	public String toString() {
		return "HeartBeatService [heartBeatMap=" + heartBeatMap + "]";
	}

}
