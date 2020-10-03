package demo.joy.mercenary.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.joy.mercenary.pojo.dto.CreateNewMercenaryToStoreDTO;

public interface JoyMercenaryService {

	CommonResult createNewMercenaryToStore(CreateNewMercenaryToStoreDTO dto);

}
