package demo.promote.pojo.dto;

public class PromoteImgDTO {

	private Long id;
	private String url;
	private String urlKey;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlKey() {
		return urlKey;
	}

	public void setUrlKey(String urlKey) {
		this.urlKey = urlKey;
	}

	@Override
	public String toString() {
		return "PromoteImgDTO [id=" + id + ", url=" + url + ", urlKey=" + urlKey + "]";
	}

}
