package demo.tool.mail.pojo.dto;

public class ResendForgotPasswordMailDTO extends SendForgotPasswordMailDTO {

	private String mailKey;

	public String getMailKey() {
		return mailKey;
	}

	public void setMailKey(String mailKey) {
		this.mailKey = mailKey;
	}

	@Override
	public String toString() {
		return "ResendForgotPasswordMailDTO [mailKey=" + mailKey + ", getUserId()=" + getUserId() + ", getSendTo()="
				+ getSendTo() + ", getHostName()=" + getHostName() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
