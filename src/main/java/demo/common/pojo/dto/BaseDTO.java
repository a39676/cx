package demo.common.pojo.dto;

public class BaseDTO {

	private String pk;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	@Override
	public String toString() {
		return "BaseDTO [pk=" + pk + "]";
	}

}
