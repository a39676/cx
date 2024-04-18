package demo.tool.other.service;

import auxiliaryCommon.pojo.type.HeartBeatType;
import demo.tool.other.pojo.vo.EncryptIdVO;

public interface ComplexToolService {

	EncryptIdVO encryptIDNum(Long id);

	Long decryptPK(String pk);

	void notificationServiceDown(HeartBeatType heartBeatType);

}
