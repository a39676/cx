package demo.joy.image.icon.pojo.type;

public enum JoyIconLevel_1_Type {
	
	equipment("equipment", 1),
	item("item", 2),
	npcMonster("npcMonster", 3),
	other("other", 4),
	skill("skill", 5),
	status("status", 6),

	;
	
	private String channelTypeName;
	private Integer channelTypeCode;
	
	JoyIconLevel_1_Type(String channelTypeName, Integer channelTypeCode) {
		this.channelTypeName = channelTypeName;
		this.channelTypeCode = channelTypeCode;
	}
	

	public String getName() {
		return channelTypeName;
	}

	public Integer getCode() {
		return channelTypeCode;
	}

	public static JoyIconLevel_1_Type getType(String typeName) {
		for(JoyIconLevel_1_Type t : JoyIconLevel_1_Type.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static JoyIconLevel_1_Type getType(Integer typeCode) {
		for(JoyIconLevel_1_Type t : JoyIconLevel_1_Type.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
