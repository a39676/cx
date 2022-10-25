package demo.tool.bookmark.pojo.dto;

import java.util.List;

public class EditBookmarkUrlDTO {

	private boolean createNew = false;

	private String bookmarkPK;

	private String bookmarkUrlPK;

	private String bookmarkUrl;

	private String bookmarkUrlName;

	private List<String> tagNameList;

	private Double weight = 0D;

	public boolean getCreateNew() {
		return createNew;
	}

	public void setCreateNew(boolean createNew) {
		this.createNew = createNew;
	}

	public String getBookmarkPK() {
		return bookmarkPK;
	}

	public void setBookmarkPK(String bookmarkPK) {
		this.bookmarkPK = bookmarkPK;
	}

	public String getBookmarkUrlPK() {
		return bookmarkUrlPK;
	}

	public void setBookmarkUrlPK(String bookmarkUrlPK) {
		this.bookmarkUrlPK = bookmarkUrlPK;
	}

	public String getBookmarkUrl() {
		return bookmarkUrl;
	}

	public void setBookmarkUrl(String bookmarkUrl) {
		this.bookmarkUrl = bookmarkUrl;
	}

	public String getBookmarkUrlName() {
		return bookmarkUrlName;
	}

	public void setBookmarkUrlName(String bookmarkUrlName) {
		this.bookmarkUrlName = bookmarkUrlName;
	}

	public List<String> getTagNameList() {
		return tagNameList;
	}

	public void setTagNameList(List<String> tagNameList) {
		this.tagNameList = tagNameList;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "EditBookmarkUrlDTO [createNew=" + createNew + ", bookmarkPK=" + bookmarkPK + ", bookmarkUrlPK="
				+ bookmarkUrlPK + ", bookmarkUrl=" + bookmarkUrl + ", bookmarkUrlName=" + bookmarkUrlName
				+ ", tagNameList=" + tagNameList + ", weight=" + weight + "]";
	}

}
