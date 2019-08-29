package demo.image.service;

import java.io.File;
import java.util.List;

import demo.baseCommon.pojo.result.CommonResult;
import demo.image.pojo.ImageCache;
import demo.image.pojo.ImageTags;

public interface ImageService {

	public File getImageFromUrl(String urlStr, String savePath);

	boolean checkImageExists(String md5);

	void saveImage(ImageCache ic);

	String getFileNameFromUrl(String urlStr);

	List<ImageTags> getImageTags();

	/**
	 * 保存从来自文章的图片
	 * @param imageUrls
	 * @return
	 */
	int insertImageFromArticle(List<String> imageUrls, Long articleId);

	List<ImageCache> getImageCacheListByArticleId(Long articleId);

	CommonResult moveImageCacheToImageStore(Long articleId, Long channelId);

}
