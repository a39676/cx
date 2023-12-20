package demo.tool.textMessageForward.telegram.pojo.bo;

public class TelegramConstantBO {

	private Integer id;

	private String constantname;

	private String constantvalue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConstantname() {
		return constantname;
	}

	public void setConstantname(String constantname) {
		this.constantname = constantname;
	}

	public String getConstantvalue() {
		return constantvalue;
	}

	public void setConstantvalue(String constantvalue) {
		this.constantvalue = constantvalue;
	}

	@Override
	public String toString() {
		return "TelegramConstantBO [id=" + id + ", constantname=" + constantname + ", constantvalue=" + constantvalue
				+ "]";
	}

}
