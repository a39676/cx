package demo.base.user.pojo.type;

public enum OrganzationRolesType {

	ROLE_ORG_SUPER_ADMIN("ROLE_ORG_SUPER_ADMIN", 5),
	ROLE_SUB_ORG_ADMIN("ROLE_SUB_ORG_ADMIN", 6),
	ROLE_ORG_MEMBER("ROLE_ORG_MEMBER", 7),
	
	;
	
	private String name;
	private Integer code;
	
	OrganzationRolesType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	public Integer getCode() {
		return code;
	}
	
	public static OrganzationRolesType getRole(Integer code) {
		for(OrganzationRolesType role : OrganzationRolesType.values()) {
			if(role.getCode() == code) {
				return role;
			}
		}
		return null;
	}
	
	public static OrganzationRolesType getRole(String name) {
		for(OrganzationRolesType role : OrganzationRolesType.values()) {
			if(role.getName().equals(name)) {
				return role;
			}
		}
		return null;
	}
}
