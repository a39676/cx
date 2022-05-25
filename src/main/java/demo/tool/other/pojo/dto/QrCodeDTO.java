package demo.tool.other.pojo.dto;

public class QrCodeDTO {

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "DecodeQrCodeDTO [content=" + content + "]";
	}

}
