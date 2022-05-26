package demo.article.article.pojo.result;

import java.time.LocalDateTime;

import auxiliaryCommon.pojo.result.CommonResult;

public class ArticleBurnResult extends CommonResult {

	private String content;
	private Integer readCount;
	private Integer readLimit;
	private LocalDateTime validTime;
	private String readKey;
	private String burnKey;
	private String burnUri;
	private boolean isBurned;
	private boolean needPwd = false;

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

	public boolean getNeedPwd() {
		return needPwd;
	}

	public void setNeedPwd(boolean needPwd) {
		this.needPwd = needPwd;
	}

	public boolean getIsBurned() {
		return isBurned;
	}

	public void setIsBurned(boolean isBurned) {
		this.isBurned = isBurned;
	}

	public String getBurnUri() {
		return burnUri;
	}

	public void setBurnUri(String burnUri) {
		this.burnUri = burnUri;
	}

	@Override
	public String toString() {
		return "ArticleBurnResult [content=" + content + ", readCount=" + readCount + ", readLimit=" + readLimit
				+ ", validTime=" + validTime + ", readKey=" + readKey + ", burnKey=" + burnKey + ", burnUri=" + burnUri
				+ ", isBurned=" + isBurned + ", needPwd=" + needPwd + "]";
	}

}