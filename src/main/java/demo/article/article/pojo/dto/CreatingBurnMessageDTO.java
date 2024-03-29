package demo.article.article.pojo.dto;

public class CreatingBurnMessageDTO {

	private String content;

	private Integer readLimit;

	private Integer validTime;

	private String pwd;

	public String getContent() {
		return content;
	}

	public CreatingBurnMessageDTO setContent(String content) {
		this.content = content;
		return this;
	}

	public Integer getReadLimit() {
		return readLimit;
	}

	public CreatingBurnMessageDTO setReadLimit(Integer readLimit) {
		this.readLimit = readLimit;
		return this;
	}

	public Integer getValidTime() {
		return validTime;
	}

	public CreatingBurnMessageDTO setValidTime(Integer validTime) {
		this.validTime = validTime;
		return this;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "CreatingBurnMessageDTO [content=" + content + ", readLimit=" + readLimit + ", validTime=" + validTime
				+ ", pwd=" + pwd + "]";
	}

}
