package demo.toyParts.weixin.pojo.type;

/** 微信key */
public enum WXAppSwitchType {

	/** 1 */
	app1("1", 1),
	/** 2(云浮?) */
	app2("2", 2),
	/** 3(39676) */
	app3("3", 3),

	;

	private String type;
	private Integer code;

	WXAppSwitchType(String type, Integer code) {
		this.type = type;
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public static WXAppSwitchType getType(String typeName) {
		for (WXAppSwitchType t : WXAppSwitchType.values()) {
			if (t.getType().equals(typeName)) {
				return t;
			}
		}
		return null;
	}

	public static WXAppSwitchType getType(Integer typeCode) {
		for (WXAppSwitchType t : WXAppSwitchType.values()) {
			if (t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
