package demo.tool.service;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import tool.pojo.bo.IpRecordBO;

public interface VisitDataService {

	/**
	 * 用于每日定时任务, 将访问数量持久化到数据库
	 */
	void visitCountRedisToOrm();

	IpRecordBO getIp(HttpServletRequest request);

	/**
	 * 将访问数据(ip, url 记录)放入到 Redis 缓存
	 * @param request
	 * @param customInfo
	 */
	void insertVisitData(HttpServletRequest request, String customInfo);

	/**
	 * 将访问数据(ip, url 记录)放入到 Redis 缓存
	 * @param request
	 */
	void insertVisitData(HttpServletRequest request);

	/**
	 * 将访问数量(仅记录访问数)放入到 Redis 缓存
	 * 每个 IP 只记录一次
	 * @param request
	 */
	void addVisitCounting(HttpServletRequest request);

	/**
	 * 用于定时任务, 将访问数据持久化到数据库
	 */
	void visitDataRedisToOrm();

	/**
	 * 获取访问数
	 * @return
	 */
	Long getVisitCount();

	/**
	 * 2019-12-06
	 * 因允许未知用户输入
	 * 需要限制时间段内 IP 的输入次数
	 * 此处以redis key 生存时间作为动态保存
	 * @param request
	 */
	void insertFunctionalModuleVisitData(HttpServletRequest request, String redisKeyPrefix);
	void insertFunctionalModuleVisitData(HttpServletRequest request, String redisKeyPrefix, long timeout,
			TimeUnit unit);

	int checkFunctionalModuleVisitData(HttpServletRequest request, String redisKeyPrefix);

}
