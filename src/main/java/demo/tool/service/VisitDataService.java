package demo.tool.service;

import javax.servlet.http.HttpServletRequest;

import tool.pojo.bo.IpRecordBO;

public interface VisitDataService {

	/**
	 * 用于每日定时任务, 将访问数量持久化到数据库
	 */
	void visitCountRedisToOrm();

	IpRecordBO getIp(HttpServletRequest request);

	/**
	 * 将访问数据放入到 Redis 缓存
	 * @param request
	 * @param customInfo
	 */
	void insertVisitData(HttpServletRequest request, String customInfo);

	/**
	 * 将访问数据放入到 Redis 缓存
	 * @param request
	 */
	void insertVisitData(HttpServletRequest request);

	/**
	 * 将访问数量放入到 Redis 缓存
	 * 每个 IP 只记录一次
	 * @param request
	 */
	void insertVisitSet(HttpServletRequest request);

	/**
	 * 用于定时任务, 将访问数据持久化到数据库
	 */
	void visitDataRedisToOrm();

	/**
	 * 获取访问数
	 * @return
	 */
	Long getVisitCount();

}
