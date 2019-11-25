package demo.image.service;

import java.io.File;
import java.util.List;

import demo.baseCommon.pojo.result.CommonResultCX;
import demo.image.pojo.ImageCache;
import demo.image.pojo.ImageTags;
import demo.image.pojo.result.UploadImageToCloudinaryResult;
import image.pojo.dto.UploadImageToCloudinaryDTO;

public interface ImageService {

	public File getImageFromUrl(String urlStr, String savePath);

	boolean checkImageExists(String md5);

	String getFileNameFromUrl(String urlStr);

	List<ImageTags> getImageTags();

	/**
	 * 保存从来自文章的图片
	 * @param imageUrls
	 * @return
	 */
	int insertImageFromArticle(List<String> imageUrls, Long articleId);

	List<ImageCache> getImageCacheListByArticleId(Long articleId);

	CommonResultCX moveImageCacheToImageStore(Long articleId, Long channelId);

	UploadImageToCloudinaryResult uploadImageToCloudinary(UploadImageToCloudinaryDTO dto);

}
