package demo.joy.character.pojo.vo;

public class JoyCharacterVO {

	private String pk;
	private String name;
	private Integer gender;

	/*
	 * TODO other detail
	 */

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "JoyCharacterVO [pk=" + pk + ", name=" + name + ", gender=" + gender + "]";
	}

}
