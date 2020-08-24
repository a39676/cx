package demo.joy.image.icon.pojo.type;

public enum JoyIconEquipmentType {
	
	blade("blade", 1),
	bow("bow", 2),
	bracelet("bracelet", 3),
	clothe("clothe", 4),
	earring("earring", 5),
	glass("glass", 6),
	hair("hair", 7),
	hat("hat", 8),
	headtemp("headtemp", 9),
	heavy("heavy", 10),
	necklace("necklace", 11),
	odd("odd", 12),
	pants("pants", 13),
	ring("ring", 14),
	shield("shield", 15),
	shoe("shoe", 16),
	spear("spear", 17),
	staff("staff", 18),
	stick("stick", 19),
	stone("stone", 20),
	sword("sword", 21),
	;
	
	private String channelTypeName;
	private Integer channelTypeCode;
	
	JoyIconEquipmentType(String channelTypeName, Integer channelTypeCode) {
		this.channelTypeName = channelTypeName;
		this.channelTypeCode = channelTypeCode;
	}
	

	public String getName() {
		return channelTypeName;
	}

	public Integer getCode() {
		return channelTypeCode;
	}

	public static JoyIconEquipmentType getType(String typeName) {
		for(JoyIconEquipmentType t : JoyIconEquipmentType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static JoyIconEquipmentType getType(Integer typeCode) {
		for(JoyIconEquipmentType t : JoyIconEquipmentType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
