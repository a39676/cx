package demo.tool.educate.wordHelper.pojo.dto;

public class WordDTO {

	private String en;
	private String cn;
	private String dateStr;
	private String markCode;

	public String getEn() {
		return en;
	}

	public void setEn(String en) {
		this.en = en;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getMarkCode() {
		return markCode;
	}

	public void setMarkCode(String markCode) {
		this.markCode = markCode;
	}

	@Override
	public String toString() {
		return "WordDTO [en=" + en + ", cn=" + cn + ", dateStr=" + dateStr + ", markCode=" + markCode + "]";
	}

}
