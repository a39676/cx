package demo.joy.character.pojo.dto;

import auxiliaryCommon.pojo.type.GenderType;

public class CreateJoyCharacterCharacterDTO {

	private String characterName;
	/**
	 * ({@link GenderType}
	 */
	private Integer gender;

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "CreateJoyCharacterCharacterDTO [characterName=" + characterName + ", gender=" + gender + "]";
	}

}
