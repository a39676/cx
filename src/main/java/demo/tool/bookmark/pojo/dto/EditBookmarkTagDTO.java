package demo.tool.bookmark.pojo.dto;

public class EditBookmarkTagDTO {

	private String bookmarkPK;

	private String tagPK;

	private String tagName;

	public String getBookmarkPK() {
		return bookmarkPK;
	}

	public void setBookmarkPK(String bookmarkPK) {
		this.bookmarkPK = bookmarkPK;
	}

	public String getTagPK() {
		return tagPK;
	}

	public void setTagPK(String tagPK) {
		this.tagPK = tagPK;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	@Override
	public String toString() {
		return "CreateBookmarkTagDTO [bookmarkPK=" + bookmarkPK + ", tagPK=" + tagPK + ", tagName=" + tagName + "]";
	}

}
