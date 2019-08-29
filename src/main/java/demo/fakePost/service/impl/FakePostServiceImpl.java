package demo.fakePost.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dateTimeHandle.DateUtilCustom;
import demo.article.mapper.ArticleChannelsMapper;
import demo.article.mapper.ArticleLongMapper;
import demo.article.mapper.ArticleLongReviewMapper;
import demo.article.mapper.ArticleUserDetailMapper;
import demo.article.pojo.constant.ArticleConstant;
import demo.article.pojo.param.controllerParam.InsertNewReviewRecordParam;
import demo.article.pojo.param.mapperParam.UpdateArticleLongReviewStatuParam;
import demo.article.pojo.param.mapperParam.UpdateArticleUserCoefficientParam;
import demo.article.pojo.param.mapperParam.UpdateChannelPointByArticleIdParam;
import demo.article.pojo.po.ArticleEvaluationStore;
import demo.article.pojo.po.ArticleLong;
import demo.article.pojo.type.ArticleEvaluationCodeType;
import demo.article.pojo.type.ArticleEvaluationType;
import demo.article.pojo.type.ArticleReviewType;
import demo.base.user.pojo.type.AuthType;
import demo.base.user.service.UsersService;
import demo.baseCommon.service.CommonService;
import demo.fakePost.mapper.FakePostMapper;
import demo.fakePost.pojo.param.mapperParam.FindArticleIdNotPassAndPostByShadowParam;
import demo.fakePost.pojo.param.mapperParam.ModifyArticleInfoParam;
import demo.fakePost.service.FakePostService;
import demo.image.service.ImageService;

@Service
public class FakePostServiceImpl extends CommonService implements FakePostService {

	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private FakePostMapper fakePostMapper;
	@Autowired
	private ArticleLongMapper articleLongMapper;
	@Autowired
	private ArticleUserDetailMapper articleUserDetailMapper;
	@Autowired
	private ArticleLongReviewMapper articleLongReviewMapper;
	@Autowired
	private ArticleChannelsMapper articleChannelsMapper;
	
	@Autowired
	private ImageService imageService;
	
	@Override
	public int createFakeEvaluationStore() {
		Date startTime = DateUtilCustom.dateDiffDays(-8);
		Date endTime = new Date();
		
		List<ArticleLong> articleList = fakePostMapper.findPassedArticleIdListByPeriod(startTime, endTime);
		
		if(articleList == null || articleList.size() < 1) {
			return 0;
		}
		
		int evaluationUpMax = 6;
		int evaluationDownMax = 3;
		Long oneDayMilliSecond = 1000L * 60 * 60 * 24;
		long dayDiff = 0;
		
		ArticleEvaluationStore tmpStorePo = null;
		List<ArticleEvaluationStore> storePoList = new ArrayList<ArticleEvaluationStore>();
		
		for(ArticleLong article : articleList) {
			tmpStorePo = new ArticleEvaluationStore();
			tmpStorePo.setPostObjectId(article.getArticleId());
			tmpStorePo.setEvaluationType(ArticleEvaluationType.articleLongEvaluation.getCode());
			tmpStorePo.setEvaluationCode(ArticleEvaluationCodeType.up.getCode());
			dayDiff =  (System.currentTimeMillis() - article.getCreateTime().getTime()) / oneDayMilliSecond;
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
			dayDiff =  (System.currentTimeMillis() - article.getCreateTime().getTime()) / oneDayMilliSecond;
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
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void autoPass() throws Exception {
		List<Long> userIdList = usersService.findUserIdListByAuthId(AuthType.DELAY_POSTER.getCode());
		if(userIdList == null || userIdList.size() < 1) {
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
		
		UpdateArticleUserCoefficientParam updateUserCoefficientParam = new UpdateArticleUserCoefficientParam();
		updateUserCoefficientParam.setChannelId(article.getChannelId());
		updateUserCoefficientParam.setCoefficient(ArticleConstant.passArticleCoefficient);
		updateUserCoefficientParam.setUserId(article.getUserId());
		updateCount = articleUserDetailMapper.updateArticleUserCoefficient(updateUserCoefficientParam);
		if(updateCount != 1) {
			throw new Exception();
		}
		
		UpdateChannelPointByArticleIdParam updateChannelPointParam = new UpdateChannelPointByArticleIdParam();
		updateChannelPointParam.setArticleId(article.getArticleId());
		updateChannelPointParam.setChannelPoint(ArticleConstant.passArticleCoefficient);
		updateCount = articleChannelsMapper.updateChannelPointByArticleId(updateChannelPointParam);
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
		
		imageService.moveImageCacheToImageStore(article.getArticleId(), article.getChannelId());
	}
}
