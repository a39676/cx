package demo.tool.mail.pojo.result;

import demo.common.pojo.result.CommonResultCX;

public class SendRegistMailResult extends CommonResultCX {

	private String mailKey;

	public String getMailKey() {
		return mailKey;
	}

	public void setMailKey(String mailKey) {
		this.mailKey = mailKey;
	}

	@Override
	public String toString() {
		return "SendRegistMailResult [mailKey=" + mailKey + "]";
	}

}
