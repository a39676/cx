package demo.article.fakePost.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.article.article.mapper.ArticleLongMapper;
import demo.article.article.mapper.ArticleLongReviewMapper;
import demo.article.article.pojo.param.controllerParam.InsertNewReviewRecordParam;
import demo.article.article.pojo.param.mapperParam.UpdateArticleLongReviewStatuParam;
import demo.article.article.pojo.po.ArticleEvaluationStore;
import demo.article.article.pojo.po.ArticleLong;
import demo.article.article.pojo.type.ArticleEvaluationCodeType;
import demo.article.article.pojo.type.ArticleEvaluationType;
import demo.article.article.pojo.type.ArticleReviewType;
import demo.article.fakePost.mapper.FakePostMapper;
import demo.article.fakePost.pojo.param.mapperParam.FindArticleIdNotPassAndPostByShadowParam;
import demo.article.fakePost.pojo.param.mapperParam.ModifyArticleInfoParam;
import demo.article.fakePost.service.FakePostService;
import demo.base.user.pojo.po.Users;
import demo.base.user.pojo.type.SystemRolesType;
import demo.base.user.service.UsersService;
import demo.common.service.CommonService;

@Service
public class FakePostServiceImpl extends CommonService implements FakePostService {

	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private FakePostMapper fakePostMapper;
	@Autowired
	private ArticleLongMapper articleLongMapper;
	@Autowired
	private ArticleLongReviewMapper articleLongReviewMapper;
	
	@Override
	public int createFakeEvaluationStore() {
		Date startTime = dateHandler.dateDiffDays(-8);
		Date endTime = new Date();
		
		List<ArticleLong> articleList = fakePostMapper.findPassedArticleIdListByPeriod(startTime, endTime);
		
		if(articleList == null || articleList.size() < 1) {
			return 0;
		}
		
		int evaluationUpMax = 6;
		int evaluationDownMax = 3;
//		Long oneDayMilliSecond = 1000L * 60 * 60 * 24;
		long dayDiff = 0;
		
		ArticleEvaluationStore tmpStorePo = null;
		List<ArticleEvaluationStore> storePoList = new ArrayList<ArticleEvaluationStore>();
		
		for(ArticleLong article : articleList) {
			tmpStorePo = new ArticleEvaluationStore();
			tmpStorePo.setPostObjectId(article.getArticleId());
			tmpStorePo.setEvaluationType(ArticleEvaluationType.articleLongEvaluation.getCode());
			tmpStorePo.setEvaluationCode(ArticleEvaluationCodeType.up.getCode());
			dayDiff = ChronoUnit.DAYS.between(article.getCreateTime(), LocalDateTime.now());
			if(dayDiff > 8) {
				tmpStorePo.setEvaluationCount(0);
			} else if(dayDiff >= 5) {
				tmpStorePo.setEvaluationCount(ThreadLocalRandom.current().nextInt(0, evaluationUpMax - 3));
			} else if(dayDiff >= 3) {
				tmpStorePo.setEvaluationCount(ThreadLocalRandom.current().nextInt(0, evaluationUpMax - 2));
			} else {
				tmpStorePo.setEvaluationCount(ThreadLocalRandom.current().nextInt(0, evaluationUpMax));
			}
			
			storePoList.add(tmpStorePo);
			
			tmpStorePo = new ArticleEvaluationStore();
			tmpStorePo.setPostObjectId(article.getArticleId());
			tmpStorePo.setEvaluationType(ArticleEvaluationType.articleLongEvaluation.getCode());
			tmpStorePo.setEvaluationCode(ArticleEvaluationCodeType.down.getCode());
			dayDiff = ChronoUnit.DAYS.between(article.getCreateTime(), LocalDateTime.now());
			if(dayDiff > 8) {
				tmpStorePo.setEvaluationCount(0);
			} else if(dayDiff >= 5) {
				tmpStorePo.setEvaluationCount(ThreadLocalRandom.current().nextInt(0, evaluationDownMax - 2));
			} else if(dayDiff >= 3) {
				tmpStorePo.setEvaluationCount(ThreadLocalRandom.current().nextInt(0, evaluationDownMax - 1));
			} else {
				tmpStorePo.setEvaluationCount(ThreadLocalRandom.current().nextInt(0, evaluationDownMax));
			}
			storePoList.add(tmpStorePo);
		}
		
		return fakePostMapper.batchInserEvaluationStore(storePoList);
	}

	@Override
	@Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
	public void autoPass() throws Exception {
		List<Users> userList = usersService.findUserListByRole(SystemRolesType.ROLE_DELAY_POSTER);
		if(userList == null || userList.isEmpty()) {
			return;
		}
		
		List<Long> userIdList = userList.stream().map(Users::getUserId).collect(Collectors.toList());
		if(userIdList == null || userIdList.isEmpty()) {
			return;
		}
		
		int max = 0;
		FindArticleIdNotPassAndPostByShadowParam findShadowPostParam = new FindArticleIdNotPassAndPostByShadowParam();
		findShadowPostParam.setUserIdList(userIdList);
		List<ArticleLong> articleList = new ArrayList<ArticleLong>();
		
		for(int i = 0; i < 30; i++) {
			max = ThreadLocalRandom.current().nextInt(0, 3);
			findShadowPostParam.setChannelId(i);
			findShadowPostParam.setLimit(max);
			articleList.addAll(fakePostMapper.findArticleIdNotPassAndPostByShadow(findShadowPostParam));
		}
		
		if(articleList == null || articleList.size() < 1) {
			return;
		}
		
		for(ArticleLong a : articleList) {
			passArticle(a);
		}
		
	}
	
	private void passArticle(ArticleLong article) throws Exception {
		UpdateArticleLongReviewStatuParam updateArticleReviewStatuParam = new UpdateArticleLongReviewStatuParam();
		updateArticleReviewStatuParam.setArticleId(article.getArticleId());
		updateArticleReviewStatuParam.setPass(true);
		int updateCount = articleLongMapper.updateArticleLongReviewStatu(updateArticleReviewStatuParam);
		if(updateCount == 0) {
			throw new Exception();
		}
		
		InsertNewReviewRecordParam insertNewReviewRecordParam = new InsertNewReviewRecordParam();
		insertNewReviewRecordParam.setArticleId(article.getArticleId());
		insertNewReviewRecordParam.setArticleReviewerId(-9L);
		insertNewReviewRecordParam.setReviewTypeId(ArticleReviewType.pass.getReviewCode());
		updateCount = articleLongReviewMapper.insertNewReviewRecord(insertNewReviewRecordParam);
		if(updateCount == 0) {
			throw new Exception();
		}
		
		List<Long> shadowUserIdList = fakePostMapper.getShadowUserId();
		
		ModifyArticleInfoParam modifyArticleInfoParam = new ModifyArticleInfoParam();
		modifyArticleInfoParam.setArticleId(article.getArticleId());
		if(shadowUserIdList != null && shadowUserIdList.size() > 0) {
			modifyArticleInfoParam.setUserId(shadowUserIdList.get(ThreadLocalRandom.current().nextInt(0, shadowUserIdList.size() -1)));
		}
		
		fakePostMapper.modifyArticleInfo(modifyArticleInfoParam);
		
	}
}
