package demo.tool.calendarNotice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.tool.calendarNotice.pojo.bo.StrongNoticeBO;
import demo.tool.calendarNotice.pojo.constant.CalendarNoticeConstant;
import demo.tool.calendarNotice.pojo.po.CalendarNotice;
import demo.tool.calendarNotice.service.CalendarNoticeStrongNoticeService;
import net.sf.json.JSONObject;

@Service
public class CalendarNoticeStrongNoticeServiceImpl extends CalendarNoticeCommonService implements CalendarNoticeStrongNoticeService {

	@Override
	public List<StrongNoticeBO> getStrongNoticeList() {
		List<String> objStrList = redisHashConnectService.getValsByName(CalendarNoticeConstant.STRONG_NOTICE_REDIS_KEY);
		List<StrongNoticeBO> result = new ArrayList<>();
		StrongNoticeBO obj = null;
		for (String objStr : objStrList) {
			try {
				obj = new Gson().fromJson(objStr, StrongNoticeBO.class);
				result.add(obj);
			} catch (Exception e) {
			}
		}

		return result;
	}
	
	@Override
	public StrongNoticeBO getStrongNotice(Long id) {
		String objStr = redisHashConnectService.getValByName(CalendarNoticeConstant.STRONG_NOTICE_REDIS_KEY, id.toString());
		StrongNoticeBO obj = new Gson().fromJson(objStr, StrongNoticeBO.class);;

		return obj;
	}

	@Override
	public void addStrongNotice(CalendarNotice strongNotice) {
		StrongNoticeBO bo = new StrongNoticeBO();
		BeanUtils.copyProperties(strongNotice, bo);
		JSONObject json = JSONObject.fromObject(bo);
		redisHashConnectService.setValByName(CalendarNoticeConstant.STRONG_NOTICE_REDIS_KEY,
				strongNotice.getId().toString(), json.toString());
	}
	
	@Override
	public void stopStrongNotice(Long id) {
		redisHashConnectService.deleteValByName(CalendarNoticeConstant.STRONG_NOTICE_REDIS_KEY, id.toString());
	}

}
