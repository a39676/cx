package demo.tool.other.service;

import javax.servlet.http.HttpServletRequest;

import demo.base.user.pojo.po.UserIp;

public interface VisitDataService {

	/**
	 * 用于每日定时任务, 将访问数量持久化到数据库
	 */
	void visitCountRedisToOrm();

	/**
	 * 将访问数据(ip, url 记录)放入到 Redis 缓存
	 * @param request
	 * @param customInfo
	 * @return 
	 */
	UserIp insertVisitData(HttpServletRequest request, String customInfo);

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

}
