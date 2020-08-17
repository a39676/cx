package demo.joy.scene.pojo.dto;

import demo.joy.scene.pojo.type.JoySceneOperationType;

public class JoySceneOperationDTO {

	/**
	 * {@link JoySceneOperationType}
	 */
	private Integer operationType;
	private String scenePK;
	private String sceneName;
	private Boolean isOpen = false;
	private Boolean isPrivate = false;

	public Integer getOperationType() {
		return operationType;
	}

	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}

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
		return "EditJoySceneDTO [operationType=" + operationType + ", scenePK=" + scenePK + ", sceneName=" + sceneName
				+ ", isOpen=" + isOpen + ", isPrivate=" + isPrivate + "]";
	}

}
