package demo.joy.scene.pojo.dto;

public class JoySceneOperationDTO {

	private String scenePK;
	private String sceneName;
	private Integer weight = 0;
	private Boolean isOpen = false;
	private Boolean isPrivate = false;

	public String getScenePK() {
		return scenePK;
	}

	public void setScenePK(String scenePK) {
		this.scenePK = scenePK;
	}

	public String getSceneName() {
		return sceneName;
	}

	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	public Boolean getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	@Override
	public String toString() {
		return "JoySceneOperationDTO [scenePK=" + scenePK + ", sceneName=" + sceneName + ", weight=" + weight
				+ ", isOpen=" + isOpen + ", isPrivate=" + isPrivate + "]";
	}

}
