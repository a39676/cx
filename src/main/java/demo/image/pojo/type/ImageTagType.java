package demo.image.pojo.type;

public enum ImageTagType {
	
	fromArticle("fromArticle", 11),
	autoTestImgToCloudinary("autoTestImgToCloudinary", 12),
	
	;
	
	private String name;
	private Integer code;
	
	ImageTagType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return this.name;
	}
	
	public Integer getCode() {
		return this.code;
	}
}
