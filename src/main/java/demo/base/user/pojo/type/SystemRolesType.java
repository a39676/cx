package demo.base.user.pojo.type;

public enum SystemRolesType {

	ROLE_ADMIN("ROLE_ADMIN", -4),
	ROLE_DEV("ROLE_DEV", -3),
	ROLE_DBA("ROLE_DBA", -2),
	ROLE_SUPER_ADMIN("ROLE_SUPER_ADMIN", -1),
	
	ROLE_USER("ROLE_USER", 1),
	ROLE_USER_ACTIVE("ROLE_USER_ACTIVE", 2),
	ROLE_POSTER("ROLE_POSTER", 3),
	ROLE_DELAY_POSTER("ROLE_DELAY_POSTER", 4),
	
	ROLE_STUDENT("ROLE_STUDENT", 5),
	;
	
	private String name;
	private Integer code;
	
	SystemRolesType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	public Integer getCode() {
		return code;
	}
	
	public static SystemRolesType getRole(Integer code) {
		for(SystemRolesType role : SystemRolesType.values()) {
			if(role.getCode() == code) {
				return role;
			}
		}
		return null;
	}
	
	public static SystemRolesType getRole(String name) {
		for(SystemRolesType role : SystemRolesType.values()) {
			if(role.getName().equals(name)) {
				return role;
			}
		}
		return null;
	}
}
