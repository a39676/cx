package demo.tool.pojo.result;

import demo.baseCommon.pojo.result.CommonResultCX;

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
