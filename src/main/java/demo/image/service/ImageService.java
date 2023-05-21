package demo.image.service;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import demo.image.pojo.result.GetImgUrlInBatchResult;
import demo.image.pojo.result.ImgHandleSrcDataResult;
import image.pojo.dto.ImageSavingTransDTO;
import image.pojo.result.ImageSavingResult;

public interface ImageService {

	ImageSavingResult imageSaving(ImageSavingTransDTO dto);

	ImageSavingResult __saveImgFromBBT(ImageSavingTransDTO dto);

	void imageCleanAndDeleteFile();

	BufferedImage base64ToBufferedImg(String base64);

	/**
	 * 可能会有其他模块的图片服务会使用此功能 如{@link JoyImageNpcService }
	 * 
	 * @param image
	 * @param filePath
	 * @param fileType
	 * @return
	 */
	boolean imgSaveAsFile(String imageBase64Str, String filePath, String fileType);

	/**
	 * 处理用src字段, base64字符 上传的图片
	 * 
	 * @param src
	 * @return
	 */
	ImgHandleSrcDataResult imgHandleSrcData(String src);

	void __getImageByPath(HttpServletResponse response, String path);

	void getImage(HttpServletResponse response, String imgPK);

	void getThumbnailImage(HttpServletResponse response, String imgPK);

	/** 修改 valid_time 字段, 等待定时任务执行删除 */
	void setImageInvalidAndWaitingDelete(Long imgId);

	void shortenImageValidTime(Long imgId, LocalDateTime invalidTime);

	void setImageValidTime(Long imgId, LocalDateTime invalidTime);

	void getThumbnailImage(HttpServletResponse response, String imgPK, Integer width, Integer height);

	String getImageBase64(String imgPk);

	GetImgUrlInBatchResult transImgUrlBatchResult(List<String> imgPkList);

}
