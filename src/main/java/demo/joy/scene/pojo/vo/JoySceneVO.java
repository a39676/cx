package demo.joy.scene.pojo.vo;

public class JoySceneVO {

	private String pk;
	private String sceneName;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getSceneName() {
		return sceneName;
	}

	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}

	@Override
	public String toString() {
		return "SceneTransmitVO [pk=" + pk + ", sceneName=" + sceneName + "]";
	}

}
