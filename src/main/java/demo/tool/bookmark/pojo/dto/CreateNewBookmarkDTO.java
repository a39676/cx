package demo.tool.bookmark.pojo.dto;

public class CreateNewBookmarkDTO {

	private String bookmarkName;

	private String pwd;

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

	@Override
	public String toString() {
		return "CreateNewBookmarkDTO [bookmarkName=" + bookmarkName + ", pwd=" + pwd + "]";
	}

}
