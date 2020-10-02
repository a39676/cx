package demo.article.article.pojo.result;

import java.time.LocalDateTime;

import demo.common.pojo.result.CommonResultCX;

public class ArticleBurnResult extends CommonResultCX {

	private String content;
	private Integer readCount;
	private Integer readLimit;
	private LocalDateTime validTime;
	private String readKey;
	private String burnKey;
	private boolean isBurned;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public Integer getReadLimit() {
		return readLimit;
	}

	public void setReadLimit(Integer readLimit) {
		this.readLimit = readLimit;
	}

	public LocalDateTime getValidTime() {
		return validTime;
	}

	public void setValidTime(LocalDateTime validTime) {
		this.validTime = validTime;
	}

	public String getReadKey() {
		return readKey;
	}

	public void setReadKey(String readKey) {
		this.readKey = readKey;
	}

	public String getBurnKey() {
		return burnKey;
	}

	public void setBurnKey(String burnKey) {
		this.burnKey = burnKey;
	}

	public boolean isBurned() {
		return isBurned;
	}

	public void setBurned(boolean isBurned) {
		this.isBurned = isBurned;
	}

	@Override
	public String toString() {
		return "ArticleBurnResult [content=" + content + ", readCount=" + readCount + ", readLimit=" + readLimit
				+ ", validTime=" + validTime + ", readKey=" + readKey + ", burnKey=" + burnKey + ", isBurned="
				+ isBurned + "]";
	}

}