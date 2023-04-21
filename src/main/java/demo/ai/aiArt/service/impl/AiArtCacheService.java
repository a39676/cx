package demo.ai.aiArt.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ai.aiArt.pojo.dto.AiArtImageWallDTO;
import demo.common.service.CommonService;

@Scope("singleton")
@Service
public class AiArtCacheService extends CommonService {

	private AiArtImageWallDTO imageWall;

	public AiArtImageWallDTO getImageWall() {
		return imageWall;
	}

	public void setImageWall(AiArtImageWallDTO imageWall) {
		this.imageWall = imageWall;
	}

	@Override
	public String toString() {
		return "AiArtCacheService [imageWall=" + imageWall + "]";
	}

}
