package demo.joy.image.icon.service;

import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.image.icon.pojo.dto.BatchUploadIconDTO;

public interface JoyIconManagerService {

	JoyCommonResult batchUploadIcon(BatchUploadIconDTO dto);

}
