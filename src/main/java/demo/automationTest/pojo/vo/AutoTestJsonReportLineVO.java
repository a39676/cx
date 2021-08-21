package demo.automationTest.pojo.vo;

public class AutoTestJsonReportLineVO {

	private String lineArrtibute;
	
	private String content;

	public String getLineArrtibute() {
		return lineArrtibute;
	}

	public void setLineArrtibute(String lineArrtibute) {
		this.lineArrtibute = lineArrtibute;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "AutoTestJsonReportLineVO [lineArrtibute=" + lineArrtibute + ", content=" + content
				+ ", getLineArrtibute()=" + getLineArrtibute() + ", getContent()=" + getContent() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
}
