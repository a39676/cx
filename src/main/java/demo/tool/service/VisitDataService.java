package demo.tool.service;

import javax.servlet.http.HttpServletRequest;

import demo.tool.pojo.bo.IpRecordBO;

public interface VisitDataService {

	/**
	 * 用于每日定时任务, 将访问数量持久化到数据库
	 */
	void visitCountRedisToOrm();

	IpRecordBO getIp(HttpServletRequest request);

	void insertVisitData(HttpServletRequest request, String customInfo);

	void insertVisitData(HttpServletRequest request);

	void insertVisitSet(HttpServletRequest request);

}
