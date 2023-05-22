package demo.ai.aiArt.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ai.aiArt.pojo.vo.AiArtImageOnWallVO;
import demo.common.service.CommonService;

@Scope("singleton")
@Service
public class AiArtCacheService extends CommonService {

	private List<AiArtImageOnWallVO> imageWall = new ArrayList<>();
	private Boolean isRunning = false;
	private LocalDateTime lastHearBeatTime;
	private LocalTime serviceStartTime;
	private LocalTime serviceEndTime;

	public List<AiArtImageOnWallVO> getImageWall() {
		return imageWall;
	}

	public void setImageWall(List<AiArtImageOnWallVO> imageWall) {
		this.imageWall = imageWall;
	}

	public Boolean getIsRunning() {
		return isRunning;
	}

	public void setIsRunning(Boolean isRunning) {
		this.isRunning = isRunning;
	}

	public LocalDateTime getLastHearBeatTime() {
		return lastHearBeatTime;
	}

	public void setLastHearBeatTime(LocalDateTime lastHearBeatTime) {
		this.lastHearBeatTime = lastHearBeatTime;
	}

	public LocalTime getServiceStartTime() {
		return serviceStartTime;
	}

	public void setServiceStartTime(LocalTime serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}

	public LocalTime getServiceEndTime() {
		return serviceEndTime;
	}

	public void setServiceEndTime(LocalTime serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}

	@Override
	public String toString() {
		return "AiArtCacheService [imageWall=" + imageWall + ", isRunning=" + isRunning + ", lastHearBeatTime="
				+ lastHearBeatTime + ", serviceStartTime=" + serviceStartTime + ", serviceEndTime=" + serviceEndTime
				+ "]";
	}

}
