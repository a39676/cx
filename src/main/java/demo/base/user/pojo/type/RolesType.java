package demo.base.user.pojo.type;

public enum RolesType {

	SYS_ROLE("SYS_ROLE", 0),
	ORG_ROLE("ORG_ROLE", 1),
	
	;
	
	private String name;
	private Integer code;
	
	RolesType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	public Integer getCode() {
		return code;
	}
	
	public static RolesType getRole(Integer code) {
		for(RolesType role : RolesType.values()) {
			if(role.getCode() == code) {
				return role;
			}
		}
		return null;
	}
	
	public static RolesType getRole(String name) {
		for(RolesType role : RolesType.values()) {
			if(role.getName().equals(name)) {
				return role;
			}
		}
		return null;
	}
}
