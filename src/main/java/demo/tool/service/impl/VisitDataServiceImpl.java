package demo.tool.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dateTimeHandle.DateTimeHandle;
import dateTimeHandle.DateUtilCustom;
import demo.base.system.pojo.constant.SystemRedisKey;
import demo.base.user.mapper.UserIpMapper;
import demo.base.user.pojo.dto.BatchInsertUserIpDTO;
import demo.base.user.pojo.po.UserIp;
import demo.baseCommon.service.CommonService;
import demo.tool.mapper.VisitCountMapper;
import demo.tool.pojo.bo.IpRecordBO;
import demo.tool.pojo.dto.GetVisitCountTotalDTO;
import demo.tool.pojo.po.VisitCount;
import demo.tool.service.VisitDataService;
import demo.util.BaseUtilCustom;
import net.sf.json.JSONObject;
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
		UserIp ui = new UserIp();
		JSONObject j = null;
		IpRecordBO record = getIp(request);
		
		ui.setCreateTime(LocalDateTime.now());
		ui.setIp(numberUtil.ipToLong(record.getRemoteAddr()));
		ui.setForwardIp(numberUtil.ipToLong(record.getForwardAddr()));
		ui.setServerName(request.getServerName());
		if(StringUtils.isNotBlank(customInfo)) {
			ui.setUri(request.getRequestURI() + "/?customInfo=" + customInfo);
		} else {
			ui.setUri(request.getRequestURI());
		}
		ui.setUserId(baseUtilCustom.getUserId());
		
		j = JSONObject.fromObject(ui);
		j.put("createTime", DateTimeHandle.dateToStr(ui.getCreateTime()));
		if(ui.getUserId() == null) {
			j.put("userId", "null");
		}
		
		redisTemplate.opsForList().leftPush(SystemRedisKey.VISIT_DATA_REDIS_KEY, j.toString());
	}
	
	@Override
	public void insertVisitData(HttpServletRequest request) {
		insertVisitData(request, null);
	}
	
	@Override
	public void insertVisitSet(HttpServletRequest request) {
		IpRecordBO record = getIp(request);
		Long l = numberUtil.ipToLong(record.getRemoteAddr());
		if(l == 0) {
			l = numberUtil.ipToLong(record.getForwardAddr());
		}
		redisTemplate.opsForSet().add(String.valueOf(l));
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
	
	@Override
	public void visitDataRedisToOrm() {
		long size = redisTemplate.opsForList().size(SystemRedisKey.VISIT_DATA_REDIS_KEY);
		if(size < 1) {
			return;
		}
		
		long maxSize = 100000;
		if(size > maxSize) {
			size = maxSize;
		}
		
		List<UserIp> l = new ArrayList<UserIp>();
		String str = null;
		JSONObject j = null;
		UserIp ui = null;
		
		for(int i = 0; i < size; i++) {
			str = redisTemplate.opsForList().rightPop(SystemRedisKey.VISIT_DATA_REDIS_KEY);
			j = JSONObject.fromObject(str);
			ui = new UserIp();
			String cdStr = j.getString("createTime");
			Date createDate = DateUtilCustom.stringToDateUnkonwFormat(cdStr);
			ui.setCreateTime(DateTimeHandle.dateToLocalDateTime(createDate));
			if(numberUtil.matchInteger(j.getString("forwardIp"))) {
				ui.setForwardIp(j.getLong("forwardIp"));
			}
			if(numberUtil.matchInteger(j.getString("ip"))) {
				ui.setIp(j.getLong("ip"));
			}
			ui.setServerName(j.getString("serverName"));
			ui.setUri(j.getString("uri"));
			if(numberUtil.matchInteger(j.getString("userId"))) {
				ui.setUserId(j.getLong("userId"));
			}
			l.add(ui);
		}
		
		BatchInsertUserIpDTO dto = new BatchInsertUserIpDTO();
		dto.setPoList(l);
		userIpMapper.batchInsert(dto);
	}
	
	public Long getVisitCount(LocalDateTime startTime) {
		Long visitSetSize = redisTemplate.opsForSet().size(SystemRedisKey.VISIT_SET_REDIS_KEY);
		
		GetVisitCountTotalDTO dto = new GetVisitCountTotalDTO();
		dto.setStartTime(startTime);
		Long ormVisitCount = visitCountMapper.getVisitCountTotal(dto);
		
		return visitSetSize + ormVisitCount;
	}
	
	@Override
	public Long getVisitCount() {
		return getVisitCount(null);
	}
}
