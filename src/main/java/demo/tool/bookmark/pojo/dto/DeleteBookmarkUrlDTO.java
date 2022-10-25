package demo.tool.bookmark.pojo.dto;

public class DeleteBookmarkUrlDTO {

	private String bookmarkPK;

	private String bookmarkUrlPK;

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

	@Override
	public String toString() {
		return "DeleteBookmarkUrlDTO [bookmarkPK=" + bookmarkPK + ", bookmarkUrlPK=" + bookmarkUrlPK + "]";
	}

}
