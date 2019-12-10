package demo.tool.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.system.pojo.constant.SystemRedisKey;
import demo.base.user.mapper.UserIpMapper;
import demo.base.user.pojo.dto.BatchInsertUserIpDTO;
import demo.base.user.pojo.po.UserIp;
import demo.baseCommon.service.CommonService;
import demo.config.costom_component.BaseUtilCustom;
import demo.tool.mapper.VisitCountMapper;
import demo.tool.pojo.dto.GetVisitCountTotalDTO;
import demo.tool.pojo.po.VisitCount;
import demo.tool.service.VisitDataService;
import net.sf.json.JSONObject;
import tool.pojo.bo.IpRecordBO;
import toolPack.numericHandel.NumericUtilCustom;

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
		j.put("createTime", localDateTimeHandler.dateToStr(ui.getCreateTime()));
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
	public void addVisitCounting(HttpServletRequest request) {
		IpRecordBO record = getIp(request);
		Long l = numberUtil.ipToLong(record.getRemoteAddr());
		if(l == 0) {
			l = numberUtil.ipToLong(record.getForwardAddr());
		}
		redisTemplate.opsForSet().add(SystemRedisKey.VISIT_COUNTING_REDIS_KEY, String.valueOf(l));
	}
	
	@Override
	public void visitCountRedisToOrm() {
		Long visitSetSize = redisTemplate.opsForSet().size(SystemRedisKey.VISIT_COUNTING_REDIS_KEY);
		
		VisitCount r = new VisitCount();
		r.setId(snowFlake.getNextId());
		r.setCounting(visitSetSize);
		visitCountMapper.insertSelective(r);
		
		redisTemplate.opsForSet().pop(SystemRedisKey.VISIT_COUNTING_REDIS_KEY, visitSetSize);
		
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
			Date createDate = dateHandler.stringToDateUnkonwFormat(cdStr);
			ui.setCreateTime(localDateTimeHandler.dateToLocalDateTime(createDate));
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
		Long visitSetSize = redisTemplate.opsForSet().size(SystemRedisKey.VISIT_COUNTING_REDIS_KEY);
		
		GetVisitCountTotalDTO dto = new GetVisitCountTotalDTO();
		dto.setStartTime(startTime);
		Long ormVisitCount = visitCountMapper.getVisitCountTotal(dto);
		
		return visitSetSize + ormVisitCount;
	}
	
	@Override
	public Long getVisitCount() {
		return getVisitCount(null);
	}
	
	@Override
	public void insertATDemoVisitData(HttpServletRequest request) {
		IpRecordBO record = getIp(request);
		
		String key = buildBingDemoInsertCountingKeyPrefix(record) + "_" + snowFlake.getNextId();
		redisTemplate.opsForValue().set(key, "", 30, TimeUnit.MINUTES);
	}
	
	@Override
	public int checkATDemoVisitData(HttpServletRequest request) {
		IpRecordBO record = getIp(request);
		
		String keyPrefix = buildBingDemoInsertCountingKeyPrefix(record) + "*";
		Set<String> keys = redisTemplate.keys(keyPrefix);
		
		return keys.size();
	}
	
	private String buildBingDemoInsertCountingKeyPrefix(IpRecordBO record) {
		return SystemRedisKey.bingDemoInsertCountingKeyPrefix + "_" + record.getForwardAddr() + "_" + record.getRemoteAddr();
	}
}
