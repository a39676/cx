package demo.base.user.service;

import java.util.List;

import demo.base.user.pojo.po.UsersDetail;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface UserDetailService {

	boolean isNicknameExists(String nickname);

	/**
	 * 查找此 email 是否属于已激活用户
	 * @param email
	 * @return
	 */
	CommonResultCX ensureActiveEmail(String email);

	CommonResultCX ensureActiveMobile(Long mobile);
	
	List<UsersDetail> findByEmail(String email);

	UsersDetail findById(Long id);

	int modifyRegistEmail(String email, Long userId);

	int insertSelective(UsersDetail newUserDetail);

}
