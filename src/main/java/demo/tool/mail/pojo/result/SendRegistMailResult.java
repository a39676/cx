package demo.tool.mail.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class SendRegistMailResult extends CommonResult {

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
