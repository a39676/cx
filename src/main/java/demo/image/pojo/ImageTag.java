package demo.image.pojo;

import java.util.Date;

public class ImageTag {
	private Long tagId;

	private Long imageId;

	private Date createTime;

	private String md5Mark;

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMd5Mark() {
		return md5Mark;
	}

	public void setMd5Mark(String md5Mark) {
		this.md5Mark = md5Mark == null ? null : md5Mark.trim();
	}

}