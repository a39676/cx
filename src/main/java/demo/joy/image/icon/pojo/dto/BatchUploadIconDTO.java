package demo.joy.image.icon.pojo.dto;

public class BatchUploadIconDTO {

	private String content;
	private String remark;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "BatchUploadIconDTO [content=" + content + ", remark=" + remark + "]";
	}

}
