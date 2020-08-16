package demo.joy.scene.pojo.dto;

public class CreateJoySceneDTO {

	private String sceneName;
	private Boolean isOpen = false;
	private Boolean isPrivate = false;

	public String getSceneName() {
		return sceneName;
	}

	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
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
		return "CreateJoySceneDTO [sceneName=" + sceneName + ", isOpen=" + isOpen + ", isPrivate=" + isPrivate + "]";
	}

}
