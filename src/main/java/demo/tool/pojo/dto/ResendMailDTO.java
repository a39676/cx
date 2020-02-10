package demo.tool.pojo.dto;

public class ResendMailDTO extends SendMailDTO {

	private String mailKey;

	public String getMailKey() {
		return mailKey;
	}

	public void setMailKey(String mailKey) {
		this.mailKey = mailKey;
	}

	@Override
	public String toString() {
		return "ResendRegistMailDTO [mailKey=" + mailKey + ", getHostName()=" + getHostName() + ", getUserId()="
				+ getUserId() + ", getSendTo()=" + getSendTo() + ", getNickName()=" + getNickName() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
