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
import demo.baseCommon.service.CommonService;
import numericHandel.NumericUtilCustom;

@Service
public class UserIpServiceImpl extends CommonService implements UserIpService {

	@Autowired
	private UserIpMapper ipMapper;
	
	@Override
	public List<UserIpVO> findIpRecordLastMonth() {
		UserIpExample example = new UserIpExample();
		example.createCriteria().andCreateTimeGreaterThan(LocalDateTime.now().minusMonths(1));
		List<UserIp> ips = ipMapper.selectByExample(example);
		UserIpVO v = null;
		List<UserIpVO> vos = new ArrayList<UserIpVO>();
		for(UserIp po : ips) {
			v = buildUserIpVOFromPO(po);
			vos.add(v);
		}
		return vos;
	}
	
	private UserIpVO buildUserIpVOFromPO(UserIp po) {
		UserIpVO v = new UserIpVO();
		v.setCreateTime(po.getCreateTime());
		v.setUri(po.getUri());
		v.setUserId(po.getUserId());
		v.setIp(NumericUtilCustom.longToIp2(po.getIp()));
		v.setForwardIp(NumericUtilCustom.longToIp2(po.getForwardIp()));
		return v;
	}
	
}
