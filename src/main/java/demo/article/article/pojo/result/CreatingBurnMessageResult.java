package demo.article.article.pojo.result;

import demo.baseCommon.pojo.result.CommonResultCX;

public class CreatingBurnMessageResult extends CommonResultCX {
	
	private String readKey;

	private String burnKey;
	
	private String readUri;

	private String burnUri;

	public String getReadKey() {
		return readKey;
	}

	public CreatingBurnMessageResult setReadKey(String readKey) {
		this.readKey = readKey;
		return this;
	}

	public String getBurnKey() {
		return burnKey;
	}

	public CreatingBurnMessageResult setBurnKey(String burnKey) {
		this.burnKey = burnKey;
		return this;
	}

	public String getReadUri() {
		return readUri;
	}

	public CreatingBurnMessageResult setReadUri(String readUri) {
		this.readUri = readUri;
		return this;
	}

	public String getBurnUri() {
		return burnUri;
	}

	public CreatingBurnMessageResult setBurnUri(String burnUri) {
		this.burnUri = burnUri;
		return this;
	}

	@Override
	public String toString() {
		return "CreatingBurnMessageResult [readKey=" + readKey + ", burnKey=" + burnKey + ", readUri=" + readUri
				+ ", burnUri=" + burnUri + "]";
	}


}
