package demo.tool.calendarNotice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.tool.calendarNotice.pojo.constant.CalendarNoticeConstant;
import demo.tool.calendarNotice.pojo.po.CalendarNotice;
import demo.tool.calendarNotice.service.CalendarNoticeStrongNoticeService;
import net.sf.json.JSONObject;

@Service
public class CalendarNoticeStrongNoticeServiceImpl extends CalendarNoticeCommonService implements CalendarNoticeStrongNoticeService {

	@Override
	public List<CalendarNotice> getStrongNoticeList() {
		log.error("get in");
		List<String> objStrList = redisHashConnectService.getValsByName(CalendarNoticeConstant.STRONG_NOTICE_REDIS_KEY);
		log.error("objStrList: " + objStrList);
		List<CalendarNotice> result = new ArrayList<>();
		CalendarNotice obj = null;
		for (String objStr : objStrList) {
			try {
				obj = new Gson().fromJson(objStr, CalendarNotice.class);
				result.add(obj);
			} catch (Exception e) {
				log.error(e.toString());
				log.error(e.getLocalizedMessage());
				log.error(e.getMessage());
				log.error(e.getStackTrace().toString());
			}
		}

		log.error(result.toString());
		
		log.error("end");
		return result;
	}

	@Override
	public void addStrongNotice(CalendarNotice strongNotice) {
		CalendarNotice tmp = new CalendarNotice();
		BeanUtils.copyProperties(strongNotice, tmp);
		tmp.setCreateTime(null);
		tmp.setNoticeTime(null);
		tmp.setNoticeTime(null);
		tmp.setValidTime(null);
		JSONObject json = JSONObject.fromObject(tmp);
		redisHashConnectService.setValByName(CalendarNoticeConstant.STRONG_NOTICE_REDIS_KEY,
				strongNotice.getId().toString(), json.toString());
	}
	
	@Override
	public void stopStrongNotice(Long id) {
		redisHashConnectService.deleteValByName(CalendarNoticeConstant.STRONG_NOTICE_REDIS_KEY, id.toString());
	}

}
