package demo.tool.bookmark.pojo.dto;

public class DeleteBookmarkDTO {

	private String bookmarkPK;

	public String getBookmarkPK() {
		return bookmarkPK;
	}

	public void setBookmarkPK(String bookmarkPK) {
		this.bookmarkPK = bookmarkPK;
	}

	@Override
	public String toString() {
		return "DeleteBookmarkDTO [bookmarkPK=" + bookmarkPK + "]";
	}

}
