package demo.tool.other.pojo.vo;

public class EncryptIdVO {

	private String pk;
	private String urlEncodePk;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getUrlEncodePk() {
		return urlEncodePk;
	}

	public void setUrlEncodePk(String urlEncodePk) {
		this.urlEncodePk = urlEncodePk;
	}

	@Override
	public String toString() {
		return "EncryptIdVO [pk=" + pk + ", urlEncodePk=" + urlEncodePk + "]";
	}

}
