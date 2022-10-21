package demo.tool.bookmark.pojo.dto;

public class CreateBookmarkTagDTO {

	private String bookmarkPK;

	private String tagName;

	public String getBookmarkPK() {
		return bookmarkPK;
	}

	public void setBookmarkPK(String bookmarkPK) {
		this.bookmarkPK = bookmarkPK;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	@Override
	public String toString() {
		return "CreateBookmarkTagDTO [bookmarkPK=" + bookmarkPK + ", tagName=" + tagName + "]";
	}

}
