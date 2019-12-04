package demo.base.task.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.article.article.service.ArticleChannelService;
import demo.article.article.service.ArticleEvaluationService;
import demo.article.article.service.ArticleService;
import demo.article.fakePost.service.FakePostService;
import demo.base.task.service.TaskToolService;
import demo.base.user.mapper.UsersMapper;
import demo.base.user.service.UserRegistService;
import demo.tool.mapper.MailRecordMapper;
import demo.tool.service.VisitDataService;

@Component
public class TaskToolServiceImpl implements TaskToolService {

//	@Autowired
//	private UsersService usersService;
	@Autowired
	private UserRegistService userRegistService;
	@Autowired
	private ArticleEvaluationService articleEvaluationService;
	@Autowired
	private ArticleService articleServcie;
	@Autowired
	private ArticleChannelService articleChannelServcie;
	@Autowired
	private FakePostService fakePostService;
	@Autowired
	private VisitDataService visitDataService;
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private MailRecordMapper mailRecordMapper;
	
//	@Scheduled(cron="0 */30 * * * ?")   //每30分钟执行一次
//	@Scheduled(cron="40 49 23 * * *") // 每天23:49:40执行
	
//	@Scheduled(cron="0 */60 * * * ?")   //每30分钟执行一次
//	public void sendTomcatOut() {
//		mailService.sendTomcatOut();
//	}
	
//	@Scheduled(cron="0 */30 * * * ?")
//	public void sendTomcatLogFolder() {
//		mailService.sendTomcatLogFolder();
//	}
	
//	@Scheduled(cron="0 */32 * * * ?")
//	public void imageShowReload() {
//		imageService.imageShowReload();
//	}
	
	/** 清理无效的错误登录记录. */
	@Scheduled(cron="0 */63 * * * ?")
	public void cleanAttempts() {
		usersMapper.cleanAttempts(new Date(System.currentTimeMillis() - (1000L * 60 * 60 * 24 * 15)));
	}
	
	/** 清理过期或已读的邮件记录. */
	@Scheduled(cron="0 */63 * * * ?")
	public void cleanMailRecord() {
		mailRecordMapper.cleanMailRecord(null);
	}
	
	/** 查看是否有邮件任务未完成(用户注册后/换邮箱后,未发送激活邮件) */
	@Scheduled(cron="0 */10 * * * ?")
	public void hasMailTask() {
		if(mailRecordMapper.hasMailTask() > 0) {
			userRegistService.handleMails();
		}
	}
	
	/**
	 * 将评价缓存转存至历史表,仅记录数字,抹去原记录
	 */
	@Scheduled(cron="40 49 23 * * *") // 每天23:49:40执行
	public void evaluationCacheToStore() {
		articleEvaluationService.evaluationCacheToStore();
	}
	
	/**
	 * 将评价缓存表中的评价, 依据实际情况, 加减对应用户,频道的coefficient
	 */
	@Scheduled(cron="40 01 23 * * *") // 每天03:01:40执行
	public void evaluationCacheStatistics() {
		articleEvaluationService.evaluationCacheStatistics();
	}
	
	/**
	 * 定时任务逻辑, 拉取article_long_review中  缺失作者ID的数据, 补填
	 */
	@Scheduled(cron="12 03 02 * * *") 
	public void refillArticleLongReviewCreatorId() {
		articleServcie.refillArticleLongReviewCreatorId();
	}
	
	/** 评价数生成. */
	@Scheduled(cron="0 */209 * * * ?")
	public void createFakeEvaluationStore() {
		fakePostService.createFakeEvaluationStore();
	}
	
	
	/**
	 * -查找具有DELAY_POSTER权限的用户的N篇随机文章, 处理为通过
	 *  {@link RolesType}
	 * @throws Exception
	 * TODO dev mark
	 */
	@Scheduled(cron="0 */267 * * * ?")
	public void autoPass() throws Exception {
		fakePostService.autoPass();
	}
	
	/** 随机刷新闪现频道是否出现. */
	@Scheduled(cron="0 */68 * * * ?")
	public void refreshArticleChannelIsFlash() {
		articleChannelServcie.refreshArticleChannelIsFlash();
	}
	
	/** 将 redis 内的访问数, 持久化到数据库 */
	@Scheduled(cron="01 02 00 * * *")
	public String visitCountRedisToOrm() {
		visitDataService.visitCountRedisToOrm();
		visitDataService.visitDataRedisToOrm();
		return null;
	}
}
