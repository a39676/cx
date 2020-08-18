package demo.joy.scene.pojo.dto;

public class EditJoySceneGroupRelationDTO {

	private String sceneGroupPK;
	private String scenePK;

	public String getSceneGroupPK() {
		return sceneGroupPK;
	}

	public void setSceneGroupPK(String sceneGroupPK) {
		this.sceneGroupPK = sceneGroupPK;
	}

	public String getScenePK() {
		return scenePK;
	}

	public void setScenePK(String scenePK) {
		this.scenePK = scenePK;
	}

	@Override
	public String toString() {
		return "CreateJoySceneGroupRelationDTO [sceneGroupPK=" + sceneGroupPK + ", scenePK=" + scenePK + "]";
	}

}
