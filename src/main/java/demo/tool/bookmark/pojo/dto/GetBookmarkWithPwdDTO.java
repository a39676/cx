package demo.tool.bookmark.pojo.dto;

public class GetBookmarkWithPwdDTO {

	private String pk;

	private String pwd;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "GetBookmarkWithPwdDTO [pk=" + pk + ", pwd=" + pwd + "]";
	}

}
