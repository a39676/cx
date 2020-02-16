package demo.base.organizations.pojo.type;

public enum OrganizationType {

	topOrg("topOrg", 0L),
	subOrg("subOrg", 1L),
	;
	
	private String name;
	private Long code;
	
	OrganizationType(String name, Long roleId) {
		this.name = name;
		this.code = roleId;
	}
	
	public String getName() {
		return name;
	}
	public Long getCode() {
		return code;
	}
	
	public static OrganizationType getRole(Long id) {
		for(OrganizationType role : OrganizationType.values()) {
			if(role.getCode() == id) {
				return role;
			}
		}
		return null;
	}
	
	public static OrganizationType getRole(String name) {
		for(OrganizationType role : OrganizationType.values()) {
			if(role.getName().equals(name)) {
				return role;
			}
		}
		return null;
	}
}
