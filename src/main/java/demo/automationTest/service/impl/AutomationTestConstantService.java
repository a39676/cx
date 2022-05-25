package demo.automationTest.service.impl;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;

@Scope("singleton")
@Service
public class AutomationTestConstantService extends CommonService {

	private LocalDateTime lastHeartBeat = LocalDateTime.now();

	public LocalDateTime getLastHeartBeat() {
		return lastHeartBeat;
	}

	public void setLastHeartBeat(LocalDateTime lastHeartBeat) {
		this.lastHeartBeat = lastHeartBeat;
	}

	@Override
	public String toString() {
		return "AutomationTestConstantService [lastHeartBeat=" + lastHeartBeat + "]";
	}

}
