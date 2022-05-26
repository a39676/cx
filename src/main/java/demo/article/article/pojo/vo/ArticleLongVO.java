package demo.article.article.pojo.vo;

import java.time.LocalDateTime;
import java.util.Date;

public class ArticleLongVO {

	private String articleTitle;

	private String nickName;

	private Long channelId;

	private String headIamgeUrl;

	private String contentLines;

	private String createDateString;

	private String editDateString;

	private String privateKey;

	private String path;

	private boolean isPass;

	private boolean isDelete;

	private boolean isEdited;

	private boolean isReject;

	private Date createTime;

	private Date editTime;

	private Long userId;

	private Integer editCount;

	private boolean iWroteThis = false;

	private String validDateStr;

	private String validTimeStr;

	private LocalDateTime validTime;

	public String getHeadIamgeUrl() {
		return headIamgeUrl;
	}

	public void setHeadIamgeUrl(String headIamgeUrl) {
		this.headIamgeUrl = headIamgeUrl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCreateDateString() {
		return createDateString;
	}

	public void setCreateDateString(String createDateString) {
		this.createDateString = createDateString;
	}

	public String getEditDateString() {
		return editDateString;
	}

	public void setEditDateString(String editDateString) {
		this.editDateString = editDateString;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getContentLines() {
		return contentLines;
	}

	public void setContentLines(String contentLines) {
		this.contentLines = contentLines;
	}

	public boolean getIsPass() {
		return isPass;
	}

	public void setIsPass(boolean isPass) {
		this.isPass = isPass;
	}

	public boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean getIsEdited() {
		return isEdited;
	}

	public void setIsEdited(boolean isEdited) {
		this.isEdited = isEdited;
	}

	public boolean getIsReject() {
		return isReject;
	}

	public void setIsReject(boolean isReject) {
		this.isReject = isReject;
	}

	public boolean getIWroteThis() {
		return iWroteThis;
	}

	public boolean getiWroteThis() {
		return iWroteThis;
	}

	public void setIWroteThis(boolean iWroteThis) {
		this.iWroteThis = iWroteThis;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getEditCount() {
		return editCount;
	}

	public void setEditCount(Integer editCount) {
		this.editCount = editCount;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getValidDateStr() {
		return validDateStr;
	}

	public void setValidDateStr(String validDateStr) {
		this.validDateStr = validDateStr;
	}

	public String getValidTimeStr() {
		return validTimeStr;
	}

	public void setValidTimeStr(String validTimeStr) {
		this.validTimeStr = validTimeStr;
	}

	public LocalDateTime getValidTime() {
		return validTime;
	}

	public void setValidTime(LocalDateTime validTime) {
		this.validTime = validTime;
	}

	@Override
	public String toString() {
		return "ArticleLongVO [articleTitle=" + articleTitle + ", nickName=" + nickName + ", channelId=" + channelId
				+ ", headIamgeUrl=" + headIamgeUrl + ", contentLines=" + contentLines + ", createDateString="
				+ createDateString + ", editDateString=" + editDateString + ", privateKey=" + privateKey + ", path="
				+ path + ", isPass=" + isPass + ", isDelete=" + isDelete + ", isEdited=" + isEdited + ", isReject="
				+ isReject + ", createTime=" + createTime + ", editTime=" + editTime + ", userId=" + userId
				+ ", editCount=" + editCount + ", iWroteThis=" + iWroteThis + ", validDateStr=" + validDateStr
				+ ", validTimeStr=" + validTimeStr + ", validTime=" + validTime + "]";
	}

}
