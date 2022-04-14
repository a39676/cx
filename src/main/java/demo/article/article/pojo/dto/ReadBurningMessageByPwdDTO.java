package demo.article.article.pojo.dto;

public class ReadBurningMessageByPwdDTO {

	private String readKey;

	private String pwd;

	public String getReadKey() {
		return readKey;
	}

	public void setReadKey(String readKey) {
		this.readKey = readKey;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "ReadBurningMessageByPwdDTO [key=" + readKey + ", pwd=" + pwd + "]";
	}

}
