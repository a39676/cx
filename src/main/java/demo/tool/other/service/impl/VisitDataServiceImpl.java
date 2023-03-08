package demo.tool.other.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import demo.base.system.pojo.constant.SystemRedisKey;
import demo.base.user.mapper.UserIpMapper;
import demo.base.user.pojo.po.UserIp;
import demo.common.service.CommonService;
import demo.tool.other.mapper.VisitCountMapper;
import demo.tool.other.pojo.dto.GetVisitCountTotalDTO;
import demo.tool.other.pojo.po.VisitCount;
import demo.tool.other.service.VisitDataService;
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
	private NumericUtilCustom numberUtil;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	// https://stackoverflow.com/questions/53497/regular-expression-that-matches-valid-ipv6-addresses
	@SuppressWarnings("unused")
	private static final String IPV6_REGEX = "(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))";

	@Override
	public UserIp insertVisitData(HttpServletRequest request, String customInfo) {
		try {
			UserIp ui = new UserIp();
			JSONObject j = null;
			IpRecordBO record = getIp(request);
			
//			if(!record.getForwardAddr().matches(IPV6_REGEX) || !record.getRemoteAddr().matches(IPV6_REGEX)) {
//				log.error("Recive strange IP: " + record.toString());
//			}
			
			String ipStr = record.getForwardAddr();
			if(ipStr.contains(", ")) {
				String[] arr = ipStr.split(", ");
				ui.setIp(arr[0]);
				ui.setForwardIp(arr[1]);
			} else {
				ui.setIp(ipStr);
			}

			ui.setCreateTime(LocalDateTime.now());
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
			
			return ui;
		} catch (Exception e) {
			log.error("Get strange visit data: " + e.getLocalizedMessage());
		}
		return null;
	}

	@Override
	public void insertVisitData(HttpServletRequest request) {
		insertVisitData(request, null);
	}

	@Override
	public void addVisitCounting(HttpServletRequest request) {
		IpRecordBO record = getIp(request);
		String ip = record.getRemoteAddr();
		if(StringUtils.isBlank(ip)) {
			ip = record.getForwardAddr();
		}
		redisTemplate.opsForSet().add(SystemRedisKey.VISIT_COUNTING_REDIS_KEY, ip);
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

		List<UserIp> poList = new ArrayList<UserIp>();
		String str = null;
		JSONObject j = null;
		UserIp ui = null;

		for(int i = 0; i < size; i++) {
			str = (String) redisTemplate.opsForList().rightPop(SystemRedisKey.VISIT_DATA_REDIS_KEY);
			j = JSONObject.fromObject(str);
			ui = new UserIp();
			String cdStr = j.getString("createTime");
			Date createDate = dateHandler.stringToDateUnkonwFormat(cdStr);
			ui.setCreateTime(localDateTimeHandler.dateToLocalDateTime(createDate));
			ui.setForwardIp(j.getString("forwardIp"));
			ui.setIp(j.getString("ip"));
			ui.setServerName(j.getString("serverName"));
			ui.setUri(j.getString("uri"));
			if(numberUtil.matchInteger(j.getString("userId"))) {
				ui.setUserId(j.getLong("userId"));
			}
			poList.add(ui);
		}

		userIpMapper.batchInsert(poList);
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

}
