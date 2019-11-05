package demo.tool.service.impl;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.system.pojo.constant.SystemRedisKey;
import demo.base.user.mapper.UserIpMapper;
import demo.base.user.pojo.po.UserIp;
import demo.baseCommon.service.CommonService;
import demo.tool.mapper.VisitCountMapper;
import demo.tool.pojo.bo.IpRecordBO;
import demo.tool.pojo.dto.GetVisitCountTotalDTO;
import demo.tool.pojo.po.VisitCount;
import demo.tool.service.VisitDataService;
import demo.util.BaseUtilCustom;
import numericHandel.NumericUtilCustom;

@Service
public class VisitDataServiceImpl extends CommonService implements VisitDataService {

	@Autowired
	private VisitCountMapper visitCountMapper;
	@Autowired
	private UserIpMapper userIpMapper;
	@Autowired
	private BaseUtilCustom baseUtilCustom;
	@Autowired
	private NumericUtilCustom numberUtil;
	
	@Override
	public IpRecordBO getIp(HttpServletRequest request) {
		IpRecordBO record = new IpRecordBO();
        record.setRemoteAddr(request.getRemoteAddr());
        record.setForwardAddr(request.getHeader("X-FORWARDED-FOR"));

        return record;
	}
	
	@Override
	public void insertVisitData(HttpServletRequest request, String customInfo) {
		IpRecordBO record = getIp(request);
		UserIp ui = new UserIp();
		ui.setIp(numberUtil.ipToLong(record.getRemoteAddr()));
		ui.setForwardIp(numberUtil.ipToLong(record.getForwardAddr()));
		ui.setServerName(request.getServerName());
		if(StringUtils.isNotBlank(customInfo)) {
			ui.setUri(request.getRequestURI());
		} else {
			ui.setUri(request.getRequestURI() + "/?customInfo=" + customInfo);
		}
		ui.setUserId(baseUtilCustom.getUserId());
		
		userIpMapper.insertSelective(ui);
	}
	
	@Override
	public void insertVisitData(HttpServletRequest request) {
		insertVisitData(request, null);
	}
	
	@Override
	public void insertVisitSet(HttpServletRequest request) {
		IpRecordBO record = getIp(request);
		redisTemplate.opsForSet().add(SystemRedisKey.VISIT_SET_REDIS_KEY, String.valueOf(numberUtil.ipToLong(record.getRemoteAddr())));
		redisTemplate.opsForSet().add(SystemRedisKey.VISIT_SET_REDIS_KEY, String.valueOf(numberUtil.ipToLong(record.getForwardAddr())));
	}
	
	@Override
	public void visitCountRedisToOrm() {
		Long visitSetSize = redisTemplate.opsForSet().size(SystemRedisKey.VISIT_SET_REDIS_KEY);
		
		VisitCount r = new VisitCount();
		r.setId(snowFlake.getNextId());
		r.setCounting(visitSetSize);
		visitCountMapper.insertSelective(r);
		
		redisTemplate.opsForSet().pop(SystemRedisKey.VISIT_SET_REDIS_KEY, visitSetSize);
		
	}
	
	public Long getVisitCount(LocalDateTime startTime) {
		Long visitSetSize = redisTemplate.opsForSet().size(SystemRedisKey.VISIT_SET_REDIS_KEY);
		
		GetVisitCountTotalDTO dto = new GetVisitCountTotalDTO();
		dto.setStartTime(startTime);
		Long ormVisitCount = visitCountMapper.getVisitCountTotal(dto);
		
		return visitSetSize + ormVisitCount;
	}
	
	public Long getVisitCount() {
		return getVisitCount(null);
	}
}
