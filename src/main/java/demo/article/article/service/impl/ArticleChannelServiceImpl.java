package demo.article.article.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.article.article.mapper.ArticleChannelsMapper;
import demo.article.article.pojo.bo.ArticleChannelLastLikeOrHateBO;
import demo.article.article.pojo.bo.ArticleUserChannelResultBO;
import demo.article.article.pojo.bo.GetArticleChannelsBO;
import demo.article.article.pojo.constant.ArticleConstant;
import demo.article.article.pojo.param.controllerParam.FindArticleUserDetailParam;
import demo.article.article.pojo.param.mapperParam.BatchUpdateFlashStatuParam;
import demo.article.article.pojo.param.mapperParam.FindArticleChannelsParam;
import demo.article.article.pojo.param.mapperParam.FindArticleUserDetailByUserIdChannelIdParam;
import demo.article.article.pojo.param.mapperParam.UpdateChannelIsFlashParam;
import demo.article.article.pojo.po.ArticleChannels;
import demo.article.article.pojo.po.ArticleUserDetail;
import demo.article.article.pojo.result.GetArticleChannelsResult;
import demo.article.article.pojo.type.ArticleChannelLikeOrHateType;
import demo.article.article.pojo.type.ArticleChannelType;
import demo.article.article.pojo.vo.ArticleChannelVO;
import demo.article.article.service.ArticleChannelService;
import demo.base.system.pojo.bo.SystemConstantStore;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class ArticleChannelServiceImpl extends ArticleCommonService implements ArticleChannelService {

	@Autowired
	private ArticleChannelsMapper articleChannelsMapper;
	@Autowired
	private FileUtilCustom ioUtil;

	@Override
	public ArticleChannels findArticleChannelById(Long channelId) {
		if (channelId == null) {
			return null;
		}
		FindArticleChannelsParam findChannelParam = new FindArticleChannelsParam();
		findChannelParam.setChannelId(channelId);
		return articleChannelsMapper.findArticleChannelById(findChannelParam);
	}

	private List<ArticleChannelVO> getPublicChannels() {
		List<ArticleChannels> openChannels = articleChannelsMapper.findPublicChannels();
		List<ArticleChannelVO> publicChannelVOList = buildChannelVOByPO(openChannels);
//		Collections.sort(publicChannelVOList);
		return publicChannelVOList;
	}

	private List<ArticleChannelVO> getFlashChannels() {
		List<ArticleChannels> flashChannels = articleChannelsMapper.findIsFlashChannels();
		List<ArticleChannelVO> flashChannelVOList = buildChannelVOByPO(flashChannels);
//		Collections.sort(flashChannelVOList);
		return flashChannelVOList;
	}

	private ArticleChannelVO articleChannelPOToVO(ArticleChannels channel) {
		ArticleChannelVO vo = new ArticleChannelVO();
		if (channel == null) {
			return vo;
		}
		vo.setChannelId(channel.getChannelId().toString());
		vo.setChannelName(channel.getChannelName());
		vo.setWeights(channel.getWeights());
		vo.setChannelImage(channel.getImage());
		return vo;
	}

	private List<ArticleChannelVO> buildChannelVOByPO(List<ArticleChannels> channelList) {
		List<ArticleChannelVO> resultUUIDChannels = new ArrayList<ArticleChannelVO>();
		if (channelList.size() > 0) {
			for (ArticleChannels channel : channelList) {
				resultUUIDChannels.add(articleChannelPOToVO(channel));
			}
		}

		List<ArticleChannelVO> publicChannelVOList = new ArrayList<ArticleChannelVO>();
		for (ArticleChannelVO channelBO : resultUUIDChannels) {
			publicChannelVOList.add(new ArticleChannelVO().buildByArticleUUIDChannelBO(channelBO));
		}
		return publicChannelVOList;
	}

	private GetArticleChannelsResult buildGetArticleChannelsResult(GetArticleChannelsBO bo) {
		GetArticleChannelsResult result = new GetArticleChannelsResult();
		result.setChannelList(new ArrayList<ArticleChannelVO>());
		result.getChannelList().addAll(bo.getPublicChannels());
		result.getChannelList().addAll(bo.getPrivateChannels());
		result.getChannelList().addAll(bo.getFlashChannels());
		Collections.sort(result.getChannelList());
		return result;
	}

	@Override
	public GetArticleChannelsResult getArticleChannelsDynamic(HttpServletRequest request) {
		GetArticleChannelsBO bo = null;
		Long userId = baseUtilCustom.getUserId();

		if (userId == null) {
			bo = getArticleChannelsForNotLogin(findHostNameFromRequst(request));
		} else {
			bo = getArticleChannelsByUserId(findHostNameFromRequst(request), userId);
		}

		GetArticleChannelsResult result = buildGetArticleChannelsResult(bo);
		result.setIsSuccess();
		return result;
	}

	private GetArticleChannelsBO getArticleChannelsForNotLogin(String hostName) {
		GetArticleChannelsBO channelListBO = new GetArticleChannelsBO();
		channelListBO.setPublicChannels(getPublicChannels());
		channelListBO.setFlashChannels(getFlashChannels());
		fillChannels(channelListBO);

		channelListBO = removeChannelsByHostName(hostName, channelListBO);

		return channelListBO;
	}

	private void fillChannels(GetArticleChannelsBO bo) {
		if (bo.getPrivateChannels() == null) {
			bo.setPrivateChannels(new ArrayList<ArticleChannelVO>());
		}
		if (bo.getPublicChannels() == null) {
			bo.setPublicChannels(new ArrayList<ArticleChannelVO>());
		}
		if (bo.getFlashChannels() == null) {
			bo.setFlashChannels(new ArrayList<ArticleChannelVO>());
		}
	}

	private GetArticleChannelsBO getArticleChannelsByUserId(String hostName, Long userId) {
		GetArticleChannelsBO channelListBO = new GetArticleChannelsBO();
		channelListBO.setPublicChannels(getPublicChannels());
		if (userId == null) {
			return channelListBO;
		}

		FindArticleUserDetailParam param = new FindArticleUserDetailParam();
		param.setUserId(userId);

		List<ArticleUserChannelResultBO> articleChannels = articleUserDetailMapper
				.findArticleUserChannelResultBO(param);
		List<ArticleUserChannelResultBO> channelsRefreshCache = new ArrayList<ArticleUserChannelResultBO>();
		List<ArticleUserChannelResultBO> flashChannels = new ArrayList<ArticleUserChannelResultBO>();
		List<ArticleUserChannelResultBO> privateChannels = new ArrayList<ArticleUserChannelResultBO>();

		ArticleUserChannelResultBO tmpArticleChannel = null;
		for (int i = 0; i < articleChannels.size(); i++) {
			tmpArticleChannel = articleChannels.get(i);
			/*
			 * TODO 2019-12-13 将重新构造闪现频道的实现 重构之前, 闪现频道将一律隐藏
			 */
			// 闪现频道的处理
			if (ArticleChannelType.flashChannel.getCode().equals(tmpArticleChannel.getChannelType())) {
//				// getIsFlashUpdateTime == null  --> 用户在此频道无记录
//				// 或者 闪现时间已过期
//				// 放置到 channelsRefreshCache 重新处理概率闪现
//				if (tmpArticleChannel.getIsFlashUpdateTime() == null
//						|| (System.currentTimeMillis() - tmpArticleChannel.getIsFlashUpdateTime().getTime() > articleChannelRefreshMinute * 60 * 1000)) {
//					channelsRefreshCache.add(articleChannels.get(i));
//				// 未过期，而且在闪现中，给予出现
//				} else if(tmpArticleChannel.getFlash().equals(true)) {
//					flashChannels.add(tmpArticleChannel);
//				}
				// 私人频道的处理
			} else if (ArticleChannelType.privateChannel.getCode().equals(tmpArticleChannel.getChannelType())) {
				privateChannels.add(articleChannels.get(i));
			}
		}
		if (channelsRefreshCache.size() > 0) {
			refreshArticleUserDetail(channelsRefreshCache, param.getUserId());
			flashChannels.addAll(channelsRefreshCache);
		}

		List<ArticleChannelVO> flashChannelsProcessed = channelBOListUUIDProcess(flashChannels);
		List<ArticleChannelVO> privateChannelsProcessed = channelBOListUUIDProcess(privateChannels);

		List<ArticleChannelVO> flashChannelVOList = new ArrayList<ArticleChannelVO>();
		for (ArticleChannelVO channelBO : flashChannelsProcessed) {
			flashChannelVOList.add(new ArticleChannelVO().buildByArticleUUIDChannelBO(channelBO));
		}
//		Collections.sort(flashChannelVOList);
		channelListBO.setFlashChannels(flashChannelVOList);

		List<ArticleChannelVO> privateChannelVOList = new ArrayList<ArticleChannelVO>();
		for (ArticleChannelVO channelBO : privateChannelsProcessed) {
			privateChannelVOList.add(new ArticleChannelVO().buildByArticleUUIDChannelBO(channelBO));
		}
//		Collections.sort(privateChannelVOList);
		channelListBO.setPrivateChannels(privateChannelVOList);

		channelListBO = removeChannelsByHostName(hostName, channelListBO);

		return channelListBO;
	}

	private List<ArticleChannelVO> channelBOListUUIDProcess(List<ArticleUserChannelResultBO> channels) {
		if (channels == null || channels.size() < 1) {
			return new ArrayList<ArticleChannelVO>();
		}

		List<ArticleChannelVO> resultList = new ArrayList<ArticleChannelVO>();
		for (ArticleUserChannelResultBO channel : channels) {
			resultList.add(userChannelBOToVO(channel));
		}
		return resultList;
	}

	private ArticleChannelVO userChannelBOToVO(ArticleUserChannelResultBO inputBO) {
		ArticleChannelVO vo = new ArticleChannelVO();
		vo.setChannelId(String.valueOf(inputBO.getArticleChannelId()));
		vo.setChannelName(inputBO.getChannelName());
		vo.setWeights(inputBO.getWeights());
		vo.setChannelImage(inputBO.getImage());
		return vo;
	}

	/**
	 * 将输入的channelList,依条件,随机判定是否通行. 并将不通行的剔除. 只留下通行部分.
	 * 
	 * @param channelList
	 */
	private void refreshArticleUserDetail(List<ArticleUserChannelResultBO> channelList, Long userId) {
		if (channelList == null || channelList.size() < 1 || userId == null) {
			return;
		}

		List<BatchUpdateFlashStatuParam> changeToTrue = new ArrayList<BatchUpdateFlashStatuParam>();
		List<BatchUpdateFlashStatuParam> changeToFalse = new ArrayList<BatchUpdateFlashStatuParam>();
		BatchUpdateFlashStatuParam tmpUpdateParam;

		Long tmpChannelRemainder = 0L;
		Long randomLong = 0L;
		Double tmpPostLimit = 0D;

		ArticleUserChannelResultBO articleUserChannelResult = null;
		boolean channelWasFlash = false;
		for (int i = 0; i < channelList.size(); i++) {
			channelWasFlash = false;
			articleUserChannelResult = channelList.get(i);
			if (articleUserChannelResult.getCoefficient() == null) {
				articleUserChannelResult.setCoefficient(0);
			}

			if (articleUserChannelResult.getFlash() && articleUserChannelResult.getUserId() == null) {
				channelWasFlash = true;
			}

			if (channelWasFlash || articleUserChannelResult.getChannelFlashPoint() == 0) {
				tmpChannelRemainder = 0L;
			} else {
				tmpChannelRemainder = articleUserChannelResult.getChannelPoint()
						% articleUserChannelResult.getChannelFlashPoint();
			}

			if (channelWasFlash || tmpChannelRemainder == 0) {
				randomLong = 0L;
			} else {
				randomLong = ThreadLocalRandom.current().nextLong(0, tmpChannelRemainder);
			}
			// 如果用户系数比随机数高,视为随机放行;
			if (articleUserChannelResult.getCoefficient() >= randomLong
					|| channelList.get(i).getChannelFlashPoint() == 0) {
				// 将原本无记录的/不通行的改为通行/新增记录
				if (articleUserChannelResult.getIsFlashUpdateTime() == null
						|| articleUserChannelResult.getFlash().equals(false)
						|| articleUserChannelResult.getChannelPoint() == 0) {
					tmpUpdateParam = new BatchUpdateFlashStatuParam();
					tmpUpdateParam.setFlash(true);
					tmpUpdateParam.setUserId(userId);
					tmpUpdateParam.setArticleChannelId(channelList.get(i).getArticleChannelId());
					if (articleUserChannelResult.getIsFlashUpdateTime() == null) {
						tmpUpdateParam.setDailyChannelPostLimit(ArticleConstant.firstVisitDailyPostLimit);
					} else {
						tmpPostLimit = articleUserChannelResult.getCoefficient()
								/ ArticleConstant.coefficientToPostLimit;
						if (tmpPostLimit < ArticleConstant.firstVisitDailyPostLimit && articleUserChannelResult
								.getCoefficient() > ArticleConstant.badUserCoefficientLimit) {
							tmpPostLimit = ArticleConstant.firstVisitDailyPostLimit + 0D;
						}
						tmpUpdateParam.setDailyChannelPostLimit(tmpPostLimit.intValue());
					}
					changeToTrue.add(tmpUpdateParam);
				}
				// 以下为随机禁行
			} else {
				if (articleUserChannelResult.getIsFlashUpdateTime() == null
						|| articleUserChannelResult.getFlash().equals(true)) {
					tmpUpdateParam = new BatchUpdateFlashStatuParam();
					tmpUpdateParam.setFlash(false);
					tmpUpdateParam.setUserId(userId);
					tmpUpdateParam.setArticleChannelId(channelList.get(i).getArticleChannelId());
					changeToFalse.add(tmpUpdateParam);
				}
				channelList.remove(i);
				i--;
			}
		}

		if (changeToTrue.size() > 0) {
			articleUserDetailMapper.batchUpdateFlashStatu(changeToTrue);
		}

		if (changeToFalse.size() > 0) {
			articleUserDetailMapper.batchUpdateFlashStatu(changeToFalse);
		}

	}

	@Override
	public void refreshArticleChannelIsFlash() {
		List<ArticleChannels> flashChannels = articleChannelsMapper.findFlashChannels();
		UpdateChannelIsFlashParam isFlashParam = new UpdateChannelIsFlashParam();
		UpdateChannelIsFlashParam notFlashParam = new UpdateChannelIsFlashParam();
		isFlashParam.setFlash(true);

		Integer flashPoint = 0;
		Long channelPoint = 0L;
		Long currentPoint = 0L;
		Long randomPoint = 0L;
		for (ArticleChannels channel : flashChannels) {
			flashPoint = channel.getChannelFlashPoint();
			if (flashPoint == null || flashPoint == 0 || flashPoint == 1) {
				randomPoint = 0L;
			} else {
				channelPoint = channel.getChannelPoint();
				currentPoint = channelPoint % flashPoint;
				randomPoint = ThreadLocalRandom.current().nextLong(0, flashPoint);
			}
			if (randomPoint > currentPoint) {
				channel.setIsFlash(true);
				isFlashParam.addChannelId(channel.getChannelId());
			} else {
				channel.setIsFlash(false);
				notFlashParam.addChannelId(channel.getChannelId());
			}
		}

		if (isFlashParam.getChannelIdList().size() > 0) {
			isFlashParam.setFlash(true);
			articleChannelsMapper.updateChannelIsFlash(isFlashParam);
		}
		if (notFlashParam.getChannelIdList().size() > 0) {
			articleChannelsMapper.updateChannelIsFlash(notFlashParam);
		}
	}

	@Override
	public boolean showLikeOrHate(Long channelId, Long userId) {
		if (channelId == null || userId == null) {
			return false;
		}

		FindArticleChannelsParam findChannelParam = new FindArticleChannelsParam();
		findChannelParam.setChannelId(channelId);
		ArticleChannels channel = articleChannelsMapper.findArticleChannelById(findChannelParam);

		if (channel == null) {
			return false;
		}

		if (ArticleChannelType.flashChannel.getCode().equals(channel.getChannelType())) {
			FindArticleUserDetailByUserIdChannelIdParam findArticleUserDetailParam = new FindArticleUserDetailByUserIdChannelIdParam();
			findArticleUserDetailParam.setChannelId(channelId);
			findArticleUserDetailParam.setUserId(userId);
			ArticleUserDetail articleUserDetail = articleUserDetailMapper
					.findArticleUserDetailByUserIdChannelId(findArticleUserDetailParam);
			if (articleUserDetail.getLikeChannelUpdateTime() == null) {
				articleUserDetail.setLikeChannelUpdateTime(new Date(0L));
			}
			if (articleUserDetail.getHateChannelUpdateTime() == null) {
				articleUserDetail.setHateChannelUpdateTime(new Date(0L));
			}
			ArticleChannelLastLikeOrHateBO lastLikeOrHate = new ArticleChannelLastLikeOrHateBO();
			if (articleUserDetail.getLikeChannelUpdateTime().after(articleUserDetail.getHateChannelUpdateTime())) {
				lastLikeOrHate.setLastChooseDate(articleUserDetail.getLikeChannelUpdateTime());
				lastLikeOrHate.setIsLike(true);
				lastLikeOrHate.setLikeOrHateLevel(articleUserDetail.getLikeChannelCount());
			} else if (articleUserDetail.getLikeChannelUpdateTime()
					.before(articleUserDetail.getHateChannelUpdateTime())) {
				lastLikeOrHate.setLastChooseDate(articleUserDetail.getHateChannelUpdateTime());
				lastLikeOrHate.setIsLike(false);
				lastLikeOrHate.setLikeOrHateLevel(0 - articleUserDetail.getHateChannelCount());
			}
			if (lastLikeOrHate.getIsLike() == null || new Date().after(dateHandler.dateDiffDays(
					lastLikeOrHate.getLastChooseDate(),
					ArticleChannelLikeOrHateType.getType(lastLikeOrHate.getLikeOrHateLevel()).getDelayDays()))) {
				return true;
			}
		} else {
			return false;
		}
		return false;
	}

	@Override
	public String loadChannelPrefix(Integer channelId) {
		String mainFolderPath = constantService.getValByName(SystemConstantStore.articleChannelPrefixStorePath);
		String strContent = "";
		if (new File(mainFolderPath + channelId + ".txt").exists()) {
			strContent = ioUtil.getStringFromFile(mainFolderPath + channelId + ".txt");
		}
		return strContent;
	}

	private GetArticleChannelsBO removeChannelsByHostName(String hostName, GetArticleChannelsBO channelList) {
		if (channelList == null) {
			return channelList;
		}

		if (StringUtils.isBlank(hostName)) {
			channelList = removeChannelsForUnknow(channelList);
		} else if (hostName.contains(constantService.getValByName(SystemConstantStore.hostName1))) {
			channelList = removeChannelsFor3310For(channelList);
		} else if (hostName.contains(constantService.getValByName(SystemConstantStore.hostName2))) {
			channelList = removeChannelsForSDW(channelList);
		} else if (hostName.contains(constantService.getValByName(SystemConstantStore.hostName3))) {
			channelList = removeChannelsForER(channelList);
		} else {
			channelList = removeChannelsForUnknow(channelList);
		}

		return channelList;
	}

	private GetArticleChannelsBO removeChannelsFor3310For(GetArticleChannelsBO channelList) {
		if (channelList == null) {
			return channelList;
		}

		GetArticleChannelsBO newChannelList = removeChannels(channelList, List.of(2L, 3L, 4L));

		return newChannelList;
	}

	private GetArticleChannelsBO removeChannelsForSDW(GetArticleChannelsBO channelList) {
		if (channelList == null) {
			return channelList;
		}

		GetArticleChannelsBO newChannelList = removeChannels(channelList, List.of(4L));

		return newChannelList;
	}

	private GetArticleChannelsBO removeChannelsForER(GetArticleChannelsBO channelList) {
		if (channelList == null) {
			return channelList;
		}

		GetArticleChannelsBO newChannelList = removeChannels(channelList, null);

		return newChannelList;
	}

	private GetArticleChannelsBO removeChannelsForUnknow(GetArticleChannelsBO channelList) {
		String envName = constantService.getValByName(SystemConstantStore.envName, true);
		if ("dev".equals(envName)) {
			return channelList;
		}

		if (channelList == null) {
			return channelList;
		}

		channelList.getFlashChannels().clear();
		channelList.getPrivateChannels().clear();

		GetArticleChannelsBO newChannelList = removeAllChannels(channelList);

		return newChannelList;
	}

	private GetArticleChannelsBO removeChannels(GetArticleChannelsBO channelList, List<Long> targetChannelIds) {
		List<ArticleChannelVO> tmpChannelList = null;

		tmpChannelList = removeChannels(channelList.getPublicChannels(), targetChannelIds);
		channelList.setPublicChannels(tmpChannelList);

		tmpChannelList = removeChannels(channelList.getFlashChannels(), targetChannelIds);
		channelList.setFlashChannels(tmpChannelList);

		tmpChannelList = removeChannels(channelList.getPrivateChannels(), targetChannelIds);
		channelList.setPrivateChannels(tmpChannelList);

		return channelList;
	}

	private GetArticleChannelsBO removeAllChannels(GetArticleChannelsBO channelList) {

		channelList.setPublicChannels(new ArrayList<ArticleChannelVO>());
		channelList.setFlashChannels(new ArrayList<ArticleChannelVO>());
		channelList.setPrivateChannels(new ArrayList<ArticleChannelVO>());

		return channelList;
	}

	private List<ArticleChannelVO> removeChannels(List<ArticleChannelVO> channelList, List<Long> targetChannelIds) {
		if (channelList == null || channelList.size() < 1) {
			return new ArrayList<ArticleChannelVO>();
		}
		if (targetChannelIds == null || targetChannelIds.size() < 1) {
			return channelList;
		}
		Long tmpChannelId = null;

		List<ArticleChannelVO> newChannelList = new ArrayList<ArticleChannelVO>();
		for (ArticleChannelVO channel : channelList) {
			if (numberUtil.matchInteger(channel.getChannelId())) {
				tmpChannelId = Long.parseLong(channel.getChannelId());
			} else {
				tmpChannelId = null;
				continue;
			}
			if (targetChannelIds.contains(tmpChannelId)) {
				continue;
			}
			newChannelList.add(channel);
		}

		return newChannelList;
	}

}