package demo.tool.other.service;

import demo.tool.other.pojo.vo.EncryptIdVO;

public interface ComplexToolService {

	void notificationBbtDown();

	EncryptIdVO encryptIDNum(Long id);

	Long decryptPK(String pk);

}
