package demo.article.article.pojo.dto;

import java.time.LocalDateTime;

/**
 * 应用内部, 图片转存用
 */
public class LocalImageSavingDTO {

	/** 文件名 */
	private String imgName;
	/** 图片保存有效期 */
	private LocalDateTime validTime;
	/** img in base64 */
	private String imgPath;

	private Long imgTagCode;

	public LocalDateTime getValidTime() {
		return validTime;
	}

	public void setValidTime(LocalDateTime validTime) {
		this.validTime = validTime;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public Long getImgTagCode() {
		return imgTagCode;
	}

	public void setImgTagCode(Long imgTagCode) {
		this.imgTagCode = imgTagCode;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	@Override
	public String toString() {
		return "LocalImageSavingDTO [imgName=" + imgName + ", validTime=" + validTime + ", imgPath=" + imgPath
				+ ", imgTagCode=" + imgTagCode + "]";
	}

}
