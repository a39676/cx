package demo.joy.character.pojo.bo;

public class JoyBuffBO {

	/**
	 * 被作用角色 ID
	 */
	private Long characterId;

	/**
	 * 作用回合数
	 */
	private int effectCount;

	public Long getCharacterId() {
		return characterId;
	}

	public void setCharacterId(Long characterId) {
		this.characterId = characterId;
	}

	public int getEffectCount() {
		return effectCount;
	}

	public void setEffectCount(int effectCount) {
		this.effectCount = effectCount;
	}

	@Override
	public String toString() {
		return "JoyBuffBO [characterId=" + characterId + ", effectCount=" + effectCount + "]";
	}

}
