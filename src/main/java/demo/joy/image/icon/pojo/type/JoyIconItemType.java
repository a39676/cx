package demo.joy.image.icon.pojo.type;

public enum JoyIconItemType {
	
	books("books", 1),
	consumable("consumable", 2),
	eggs("eggs", 3),
	face("face", 4),
	keys("keys", 5),
	material("material", 6),
	monsterDrops("monsterDrops", 7),
	mooncake("mooncake", 8),
	mount("mount", 9),
	musicNote("musicNote", 10),
	other("other", 11),
	petCard("petCard", 12),
	props("props", 13),
	quest("quest", 14),
	secretStone("secretStone", 15),
	shopingItem("shopingItem", 16),
	spell("spell", 17),
	strengthen("strengthen", 18),
	tokens("tokens", 19),
	;
	
	private String channelTypeName;
	private Integer channelTypeCode;
	
	JoyIconItemType(String channelTypeName, Integer channelTypeCode) {
		this.channelTypeName = channelTypeName;
		this.channelTypeCode = channelTypeCode;
	}
	

	public String getName() {
		return channelTypeName;
	}

	public Integer getCode() {
		return channelTypeCode;
	}

	public static JoyIconItemType getType(String typeName) {
		for(JoyIconItemType t : JoyIconItemType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static JoyIconItemType getType(Integer typeCode) {
		for(JoyIconItemType t : JoyIconItemType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
