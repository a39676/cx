package demo.tool.wordHelper.pojo.dto;

public class GetRandomWordDTO {

	private Integer wordCount = 10;
	private boolean printEn = true;
	private boolean printCn = false;

	public Integer getWordCount() {
		return wordCount;
	}

	public void setWordCount(Integer wordCount) {
		this.wordCount = wordCount;
	}

	public boolean getPrintEn() {
		return printEn;
	}

	public void setPrintEn(boolean printEn) {
		this.printEn = printEn;
	}

	public boolean getPrintCn() {
		return printCn;
	}

	public void setPrintCn(boolean printCn) {
		this.printCn = printCn;
	}

	@Override
	public String toString() {
		return "GetRandomWordDTO [wordCount=" + wordCount + ", printEn=" + printEn + ", printCn=" + printCn + "]";
	}

}
