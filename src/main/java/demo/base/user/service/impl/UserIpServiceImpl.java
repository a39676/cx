package demo.base.user.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.user.mapper.UserIpMapper;
import demo.base.user.pojo.po.UserIp;
import demo.base.user.pojo.po.UserIpExample;
import demo.base.user.pojo.vo.UserIpVO;
import demo.base.user.service.UserIpService;
import demo.common.service.CommonService;

@Service
public class UserIpServiceImpl extends CommonService implements UserIpService {

	@Autowired
	private UserIpMapper ipMapper;
	
	@Override
	public List<UserIpVO> findIpRecordLastMonth() {
		boolean isAdmin = baseUtilCustom.hasAdminRole();
		UserIpExample example = new UserIpExample();
		example.createCriteria().andCreateTimeLessThanOrEqualTo(LocalDateTime.now().minusMonths(1));
		List<UserIp> ips = ipMapper.selectByExample(example);
		UserIpVO v = null;
		List<UserIpVO> vos = new ArrayList<UserIpVO>();
		for(UserIp po : ips) {
			v = buildUserIpVOFromPO(po, isAdmin);
			vos.add(v);
		}
		return vos;
	}
	
	private UserIpVO buildUserIpVOFromPO(UserIp po, boolean isAdmin) {
		UserIpVO v = new UserIpVO();
		v.setVisitTime(localDateTimeHandler.dateToStr(po.getCreateTime()));
		v.setUri(po.getUri());
		v.setUserId(po.getUserId());
		v.setIp(po.getIp());
		v.setForwardIp(po.getForwardIp());
		if(isAdmin) {
			v.setServerName(po.getServerName());
		}
		return v;
	}
	
}
