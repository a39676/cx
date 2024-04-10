package demo.automationTest.service.impl;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.dto.BaseStrDTO;
import demo.interaction.bbt.service.BbtCommonService;

@Scope("singleton")
@Service
public class AutomationTestConstantService extends BbtCommonService {

	private LocalDateTime bbtLastHeartBeat = LocalDateTime.now();
	private LocalDateTime workerLastHeartBeat = LocalDateTime.now();

	public LocalDateTime getBbtLastHeartBeat() {
		return bbtLastHeartBeat;
	}

	public void setBbtLastHeartBeat(LocalDateTime bbtLastHeartBeat) {
		this.bbtLastHeartBeat = bbtLastHeartBeat;
	}

	public LocalDateTime getWorkerLastHeartBeat() {
		return workerLastHeartBeat;
	}

	public void setWorkerLastHeartBeat(LocalDateTime workerLastHeartBeat) {
		this.workerLastHeartBeat = workerLastHeartBeat;
	}

	public void setBbtLastHeartBeat(BaseStrDTO dto) {
		if (bbtDynamicKey.isCorrectKey(dto.getStr())) {
			setBbtLastHeartBeat(LocalDateTime.now());
		}
	}

	public void setWorkerLastHeartBeat() {
		setWorkerLastHeartBeat(LocalDateTime.now());
	}

	@Override
	public String toString() {
		return "AutomationTestConstantService [lastHeartBeat=" + bbtLastHeartBeat + "]";
	}

}
