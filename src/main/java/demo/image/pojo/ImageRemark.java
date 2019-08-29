package demo.image.pojo;

import java.util.Date;

public class ImageRemark {
    private Integer imageId;

	private String remark;

	private Date createTime;

	private String md5Mark;

	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
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