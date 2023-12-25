package demo.automationTest.service.impl;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.dto.BaseStrDTO;
import demo.interaction.bbt.service.BbtCommonService;

@Scope("singleton")
@Service
public class AutomationTestConstantService extends BbtCommonService {

	private LocalDateTime lastHeartBeat = LocalDateTime.now();

	public LocalDateTime getLastHeartBeat() {
		return lastHeartBeat;
	}

	public void setLastHeartBeat(LocalDateTime lastHeartBeat) {
		this.lastHeartBeat = lastHeartBeat;
	}

	public void setLastHeartBeat(BaseStrDTO dto) {
		if (bbtDynamicKey.isCorrectKey(dto.getStr())) {
			setLastHeartBeat(LocalDateTime.now());
		}
	}

	@Override
	public String toString() {
		return "AutomationTestConstantService [lastHeartBeat=" + lastHeartBeat + "]";
	}

}
