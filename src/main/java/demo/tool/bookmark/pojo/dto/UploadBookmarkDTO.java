package demo.tool.bookmark.pojo.dto;

public class UploadBookmarkDTO {

	private String bookmarkName;

	private String pwd;

	private String bookmarkHtmlInBase64;

	public String getBookmarkName() {
		return bookmarkName;
	}

	public void setBookmarkName(String bookmarkName) {
		this.bookmarkName = bookmarkName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getBookmarkHtmlInBase64() {
		return bookmarkHtmlInBase64;
	}

	public void setBookmarkHtmlInBase64(String bookmarkHtmlInBase64) {
		this.bookmarkHtmlInBase64 = bookmarkHtmlInBase64;
	}

	@Override
	public String toString() {
		return "UploadBookmarkDTO [bookmarkName=" + bookmarkName + ", pwd=" + pwd + ", bookmarkHtmlInBase64="
				+ bookmarkHtmlInBase64 + "]";
	}

}
