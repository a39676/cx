package demo.ai.aiArt.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ai.aiArt.pojo.result.AiArtImageWallResult;
import demo.common.service.CommonService;

@Scope("singleton")
@Service
public class AiArtCacheService extends CommonService {

	private AiArtImageWallResult imageWall;

	public AiArtImageWallResult getImageWall() {
		return imageWall;
	}

	public void setImageWall(AiArtImageWallResult imageWall) {
		this.imageWall = imageWall;
	}

	@Override
	public String toString() {
		return "AiArtCacheService [imageWall=" + imageWall + "]";
	}

}
