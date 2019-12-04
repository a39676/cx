package demo.toyParts.weixin.pojo.type;

/** 微信接收消息类型 */
public enum WXMsgType {
	
	/** 文本 */
	text("text", 1),
	/** 图片 */
	image("image", 2),
	/** 语音 */
	voice("voice", 3),
	/** 视频 */
	video("video", 4),
	/** 短视频 */
	shortvideo("shortvideo", 5),
	/** 地理位置 */
	location("location", 6),
	/** 链接 */
	link("link", 7),
	/** 事件 */
	event("event", 8),
	;
	
	private String msgType;
	private Integer code;
	
	WXMsgType(String channelName, Integer channelCode) {
		this.msgType = channelName;
		this.code = channelCode;
	}
	
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}



	public static WXMsgType getType(String typeName) {
		for(WXMsgType t : WXMsgType.values()) {
			if(t.getMsgType().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static WXMsgType getType(Integer typeCode) {
		for(WXMsgType t : WXMsgType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
