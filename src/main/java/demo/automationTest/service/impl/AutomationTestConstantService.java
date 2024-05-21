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
	private LocalDateTime worker1LastHeartBeat = LocalDateTime.now();
	private LocalDateTime monitorLastHeartBeat = LocalDateTime.now();

	public LocalDateTime getBbtLastHeartBeat() {
		return bbtLastHeartBeat;
	}

	public void setBbtLastHeartBeat(LocalDateTime bbtLastHeartBeat) {
		this.bbtLastHeartBeat = bbtLastHeartBeat;
	}

	public LocalDateTime getWorker1LastHeartBeat() {
		return worker1LastHeartBeat;
	}

	public void setWorker1LastHeartBeat(LocalDateTime worker1LastHeartBeat) {
		this.worker1LastHeartBeat = worker1LastHeartBeat;
	}

	public LocalDateTime getMonitorLastHeartBeat() {
		return monitorLastHeartBeat;
	}

	public void setMonitorLastHeartBeat(LocalDateTime monitorLastHeartBeat) {
		this.monitorLastHeartBeat = monitorLastHeartBeat;
	}

	public void setBbtLastHeartBeat(BaseStrDTO dto) {
		if (bbtDynamicKey.isCorrectKey(dto.getStr())) {
			setBbtLastHeartBeat(LocalDateTime.now());
		}
	}

	@Override
	public String toString() {
		return "AutomationTestConstantService [lastHeartBeat=" + bbtLastHeartBeat + "]";
	}

}
