package demo.tool.taobao.pojo.dto;

public class TaobaoProductSourceUpdateDTO {

	private String idStr;
	private Boolean includePostage;
	private Boolean isAvailable;

	public String getIdStr() {
		return idStr;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

	public Boolean getIncludePostage() {
		return includePostage;
	}

	public void setIncludePostage(Boolean includePostage) {
		this.includePostage = includePostage;
	}

	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Override
	public String toString() {
		return "TaobaoProductSourceUpdateDTO [idStr=" + idStr + ", includePostage=" + includePostage + ", isAvailable="
				+ isAvailable + "]";
	}

}
