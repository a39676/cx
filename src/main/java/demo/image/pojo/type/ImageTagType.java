package demo.image.pojo.type;

public enum ImageTagType {
	
	Tag1("1", 1),
	Tag9("9", 9),
	Tag10("10", 10),
	fromArticle("fromArticle", 11)
	
	;
	
	private String tagName;
	private Integer tagId;
	
	ImageTagType(String tagName, Integer tagId) {
		this.tagName = tagName;
		this.tagId = tagId;
	}

	
	public String getTagName() {
		return this.tagName;
	}
	
	public Integer getTagId() {
		return this.tagId;
	}
}
