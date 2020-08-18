package demo.joy.scene.service;

import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.scene.pojo.dto.EditJoySceneGroupRelationDTO;

public interface JoySceneGroupRelationService {

	JoyCommonResult deleteRelationByGroupId(Long groupId);

	JoyCommonResult createSceneGroupRelation(EditJoySceneGroupRelationDTO dto);

	JoyCommonResult deleteSceneGroupRelation(EditJoySceneGroupRelationDTO dto);

}
