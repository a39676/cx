package demo.joy.garden.pojo.type;

public enum JoyGardenShopGoodsMainType {
	
	PLANT("PLAN", "植物", 1),
	CONSUMABLES("consumables", "消耗品", 2),
	;
	
	private String name;
	private String cnName;
	private Integer code;
	
	JoyGardenShopGoodsMainType(String name, String cnName, Integer code) {
		this.name = name;
		this.cnName = cnName;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}
	
	public String getCnName() {
		return cnName;
	}

	public Integer getCode() {
		return code;
	}

	public static JoyGardenShopGoodsMainType getType(String typeName) {
		for(JoyGardenShopGoodsMainType t : JoyGardenShopGoodsMainType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static JoyGardenShopGoodsMainType getType(Integer typeCode) {
		for(JoyGardenShopGoodsMainType t : JoyGardenShopGoodsMainType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
