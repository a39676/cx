package demo.article.pojo.param.controllerParam;

public class SetArticleHotParam {

	private String pk;

	private Integer hotLevel = 0;

	private Long hotMinutes;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public Integer getHotLevel() {
		return hotLevel;
	}

	public void setHotLevel(Integer hotLevel) {
		this.hotLevel = hotLevel;
	}

	public Long getHotMinutes() {
		return hotMinutes;
	}

	public void setHotMinutes(Long hotMinutes) {
		this.hotMinutes = hotMinutes;
	}

	@Override
	public String toString() {
		return "SetArticleHotParam [pk=" + pk + "]";
	}

}
