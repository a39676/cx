package demo.joy.scene.pojo.dto;

public class FindSceneByConditionDTO {

	private String sceneName;

	public String getSceneName() {
		return sceneName;
	}

	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}

	@Override
	public String toString() {
		return "FindSceneByConditionDTO [sceneName=" + sceneName + "]";
	}

}
