package demo.thirdPartyAPI.wechat.pojo.bo;

import java.math.BigDecimal;

public class WechatMessageBO {

	private String ToUserName;
	private String FromUserName;
	private Long CreateTime;
	private String MsgType;
	private String Content;
	private String MsgId;

	// event
	private String Event;
	private String EventKey;

	// image
	private String PicUrl;

	// voice 语音 ; video 视频
	private String MediaId;
	// voice
	private String Format;
	// video 视频缩略图
	private String ThumbMediaId;

	// location 地理位置
	private BigDecimal Location_X;
	private BigDecimal Location_Y;
	// 地图缩放大小
	private BigDecimal Scale;
	// 地理位置信息
	private String Label;
	// link
	private String Title;
	private String Description;
	private String Url;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}

	public BigDecimal getLocation_X() {
		return Location_X;
	}

	public void setLocation_X(BigDecimal location_X) {
		Location_X = location_X;
	}

	public BigDecimal getLocation_Y() {
		return Location_Y;
	}

	public void setLocation_Y(BigDecimal location_Y) {
		Location_Y = location_Y;
	}

	public BigDecimal getScale() {
		return Scale;
	}

	public void setScale(BigDecimal scale) {
		Scale = scale;
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		Label = label;
	}

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public Long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	@Override
	public String toString() {
		return "WXMessageBO [ToUserName=" + ToUserName + ", FromUserName=" + FromUserName + ", CreateTime=" + CreateTime
				+ ", MsgType=" + MsgType + ", Content=" + Content + ", MsgId=" + MsgId + ", Event=" + Event
				+ ", EventKey=" + EventKey + ", PicUrl=" + PicUrl + ", MediaId=" + MediaId + ", Format=" + Format
				+ ", ThumbMediaId=" + ThumbMediaId + ", Location_X=" + Location_X + ", Location_Y=" + Location_Y
				+ ", Scale=" + Scale + ", Label=" + Label + ", Title=" + Title + ", Description=" + Description
				+ ", Url=" + Url + "]";
	}

}
