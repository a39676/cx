package demo.tool.bookmark.pojo.type;

public enum BookmarkTaskType {
	
	RE_BALANCE_WEIGHT("reBalanceWeight", 1),
	;
	
	private String name;
	private Integer code;
	
	BookmarkTaskType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static BookmarkTaskType getType(String typeName) {
		for(BookmarkTaskType t : BookmarkTaskType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static BookmarkTaskType getType(Integer typeCode) {
		for(BookmarkTaskType t : BookmarkTaskType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
