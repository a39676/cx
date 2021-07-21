package demo.article.article.pojo.param.controllerParam;

public class ChangeChannelParam {

	private String pk;
	private Long channelId;

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	@Override
	public String toString() {
		return "ChangeChannelParam [pk=" + pk + ", channelId=" + channelId + "]";
	}

}