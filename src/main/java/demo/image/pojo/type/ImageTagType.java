package demo.image.pojo.type;

public enum ImageTagType {
	
	FROM_ARTICLE("fromArticle", 11L),
	AUTO_TEST_IMG_TO_CLOUDINARY("autoTestImgToCloudinary", 12L),
	IMAGE_SAVING("imageSaving", 13L),
	JOY_IMAGE_SAVING("joyImageSaving", 14L),
	
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
	
	public static ImageTagType getType(String typeName) {
		for(ImageTagType t : ImageTagType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static ImageTagType getType(Long typeCode) {
		for(ImageTagType t : ImageTagType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
