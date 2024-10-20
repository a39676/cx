package demo.interaction.bbt.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope("singleton")
@Service
public class BbtCacheService {

	private boolean isAlive = true;
	private int bbtFailedCounting = 0;
	private int bbtMaxFailed = 3;

	public boolean getIsAlive() {
		return isAlive;
	}

	public void setIsAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public int getBbtFailedCounting() {
		return bbtFailedCounting;
	}

	public void setBbtFailedCounting(int bbtFailedCounting) {
		this.bbtFailedCounting = bbtFailedCounting;
	}

	public int getBbtMaxFailed() {
		return bbtMaxFailed;
	}

	public void setBbtMaxFailed(int bbtMaxFailed) {
		this.bbtMaxFailed = bbtMaxFailed;
	}

	@Override
	public String toString() {
		return "BbtCacheService [isAlive=" + isAlive + ", bbtFailedCounting=" + bbtFailedCounting + ", bbtMaxFailed="
				+ bbtMaxFailed + "]";
	}

}
