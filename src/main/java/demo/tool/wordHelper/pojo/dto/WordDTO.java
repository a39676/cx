package demo.tool.wordHelper.pojo.dto;

public class WordDTO {

	private String en;
	private String cn;

	public WordDTO(String en, String cn) {
		this.en = en;
		this.cn = cn;
	}

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

	@Override
	public String toString() {
		return "WordDTO [en=" + en + ", cn=" + cn + "]";
	}

}
