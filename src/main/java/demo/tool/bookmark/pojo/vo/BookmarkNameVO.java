package demo.tool.bookmark.pojo.vo;

public class BookmarkNameVO {

	private String pk;

	private String pkUrlEncoded;

	private String bookmarkName;

	private boolean needPwd = true;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getPkUrlEncoded() {
		return pkUrlEncoded;
	}

	public void setPkUrlEncoded(String pkUrlEncoded) {
		this.pkUrlEncoded = pkUrlEncoded;
	}

	public String getBookmarkName() {
		return bookmarkName;
	}

	public void setBookmarkName(String bookmarkName) {
		this.bookmarkName = bookmarkName;
	}

	public boolean isNeedPwd() {
		return needPwd;
	}

	public void setNeedPwd(boolean needPwd) {
		this.needPwd = needPwd;
	}

	@Override
	public String toString() {
		return "BookmarkNameVO [pk=" + pk + ", pkUrlEncoded=" + pkUrlEncoded + ", bookmarkName=" + bookmarkName
				+ ", needPwd=" + needPwd + "]";
	}

}
