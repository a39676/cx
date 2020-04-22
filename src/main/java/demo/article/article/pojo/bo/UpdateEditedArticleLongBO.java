package demo.article.article.pojo.bo;

import demo.article.article.pojo.po.ArticleLong;

public class UpdateEditedArticleLongBO {
	
	private ArticleLong editedArticlePO;
	private Long backupArticleId;
	private Long editorId;
	private String newFilePath;
	private String title;
	private Long channelId;

	
	public ArticleLong getEditedArticlePO() {
		return editedArticlePO;
	}

	public void setEditedArticlePO(ArticleLong editedArticlePO) {
		this.editedArticlePO = editedArticlePO;
	}

	public Long getBackupArticleId() {
		return backupArticleId;
	}

	public void setBackupArticleId(Long backupArticleId) {
		this.backupArticleId = backupArticleId;
	}

	public Long getEditorId() {
		return editorId;
	}

	public void setEditorId(Long editorId) {
		this.editorId = editorId;
	}

	public String getNewFilePath() {
		return newFilePath;
	}

	public void setNewFilePath(String newFilePath) {
		this.newFilePath = newFilePath;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	@Override
	public String toString() {
		return "UpdateEditedArticleLongBO [editedArticlePO=" + editedArticlePO + ", backupArticleId=" + backupArticleId
				+ ", editorId=" + editorId + ", newFilePath=" + newFilePath + ", title=" + title + ", channelId="
				+ channelId + "]";
	}

}
