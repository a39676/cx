package demo.image.pojo;

import java.util.Date;

public class ImageCache {
	private Long imageId;

	private String imageUrl;

	private String imageName;

	private Long articleId;

	private String remark;

	private Boolean isDownload;

	private Date createTime;

	private Date downloadTime;

	private String md5Mark;

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getIsDownload() {
		return isDownload;
	}

	public void setIsDownload(Boolean isDownload) {
		this.isDownload = isDownload;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDownloadTime() {
		return downloadTime;
	}

	public void setDownloadTime(Date downloadTime) {
		this.downloadTime = downloadTime;
	}

	public String getMd5Mark() {
		return md5Mark;
	}

	public void setMd5Mark(String md5Mark) {
		this.md5Mark = md5Mark;
	}


	@Override
	public String toString() {
		return "ImageCache [imageId=" + imageId + ", imageUrl=" + imageUrl + ", imageName=" + imageName + ", articleId="
				+ articleId + ", remark=" + remark + ", isDownload=" + isDownload + ", createTime=" + createTime
				+ ", downloadTime=" + downloadTime + ", md5Mark=" + md5Mark + "]";
	}

}