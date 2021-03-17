package demo.joy.image.npc.service;

import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.image.npc.pojo.dto.BatchUploadNpcImageDTO;

public interface JoyImageNpcManagerService {

	JoyCommonResult batchUploadImageNpc(BatchUploadNpcImageDTO dto);

	JoyCommonResult delete(Long id);

}
