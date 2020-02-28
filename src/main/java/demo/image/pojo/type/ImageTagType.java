package demo.image.pojo.type;

public enum ImageTagType {
	
	fromArticle("fromArticle", 11L),
	autoTestImgToCloudinary("autoTestImgToCloudinary", 12L),
	imageSaving("imageSaving", 13L),
	
	;
	
	private String name;
	private Long code;
	
	ImageTagType(String name, Long code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return this.name;
	}
	
	public Long getCode() {
		return this.code;
	}
}
