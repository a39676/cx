package demo.interaction.bbt.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope("singleton")
@Service
public class BbtCacheService {

	private boolean isAlive = true;

	public boolean getIsAlive() {
		return isAlive;
	}

	public void setIsAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	@Override
	public String toString() {
		return "BbtCacheService [isAlive=" + isAlive + "]";
	}

}
