package demo.base.user.service;

import java.util.List;

import demo.base.user.pojo.vo.UserIpVO;

public interface UserIpService {

	List<UserIpVO> findIpRecordLastMonth();

}
