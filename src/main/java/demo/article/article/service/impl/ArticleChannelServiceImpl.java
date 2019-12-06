package demo.article.article.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.article.article.mapper.ArticleChannelsMapper;
import demo.article.article.mapper.ArticleUserDetailMapper;
import demo.article.article.pojo.bo.ArticleChannelLastLikeOrHateBO;
import demo.article.article.pojo.bo.ArticleChannelUUIDBO;
import demo.article.article.pojo.bo.ArticleUUIDChannelStoreBO;
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
import demo.base.system.service.impl.SystemConstantService;
import demo.config.costom_component.BaseUtilCustom;
import ioHandle.FileUtilCustom;

@Service
public class ArticleChannelServiceImpl extends ArticleCommonService implements ArticleChannelService {
	
	@Autowired
	private SystemConstantService systemConstantService;
	@Autowired
	private ArticleUserDetailMapper articleUserDetailMapper;
	@Autowired
	private ArticleChannelsMapper articleChannelsMapper;
	@Autowired
	private FileUtilCustom ioUtil;
	@Autowired
	private BaseUtilCustom baseUtilCustom;
	
	
	private static ArticleUUIDChannelStoreBO articleUUIDChannelStore = new ArticleUUIDChannelStoreBO();
	private static Long articleChannelRefreshMinute = 0L;
	
	
	@Override
	public boolean loadArticleUUIDChannel(boolean mustRefresh) {
		if (!loadArticleChannelRefreshMinute()) {
			return false;
		}
		String uuidModeValue = systemConstantService.getValByName(SystemConstantStore.articleChannelUUIDMode);
		boolean uuidMode = true;
		if("0".equals(uuidModeValue)) {
			uuidMode = false;
		}
		if(mustRefresh) {
			refreshArticleChannelUUIDChannelStore(uuidMode);
		} else if (articleUUIDChannelStore.getListCreateDate() == null 
				|| (System.currentTimeMillis() - articleUUIDChannelStore.getListCreateDate().getTime() > articleChannelRefreshMinute * 60 * 1000)
				|| articleUUIDChannelStore.getChannelList() == null
				|| articleUUIDChannelStore.getChannelList().size() < 1) {
			refreshArticleChannelUUIDChannelStore(uuidMode);
		}
		return true;
	}
	
	@Override
	public boolean loadArticleUUIDChannel() {
		return loadArticleUUIDChannel(false);
	}
	
	private void refreshArticleChannelUUIDChannelStore(boolean uuidMode) {
		articleUUIDChannelStore.setChannelList(new ArrayList<ArticleChannelUUIDBO>());
		ArticleChannelUUIDBO tmpChannel = null;
		
		FindArticleChannelsParam param = new FindArticleChannelsParam();
		List<ArticleChannels> channelList = articleChannelsMapper.findArticleChannels(param);
		
		for (ArticleChannels channel : channelList) {
			tmpChannel = new ArticleChannelUUIDBO();
			tmpChannel.setChannelId(channel.getChannelId());
			if(uuidMode) {
				tmpChannel.setUuid(UUID.randomUUID().toString().substring(0, 8));
			} else {
				tmpChannel.setUuid(String.valueOf(channel.getChannelId()));
			}
			tmpChannel.setChannelName(channel.getChannelName());
			articleUUIDChannelStore.getChannelList().add(tmpChannel);
		}
		
		articleUUIDChannelStore.setListCreateDate(new Date());
	}
	
	@Override
	public ArticleUUIDChannelStoreBO getArticleUUIDChannelStore() {
		loadArticleUUIDChannel();
		return articleUUIDChannelStore;
	}
	
	@Override
	public Long getChannelIdByUUID(String uuid) {
		if(StringUtils.isBlank(uuid)) {
			return null;
		}
		ArticleUUIDChannelStoreBO articleUUIDChannelStore = getArticleUUIDChannelStore();
		return articleUUIDChannelStore.getChannelId(uuid);
	}
	
	@Override
	public String getChannelUUIDById(Long channelId) {
		if(channelId == null) {
			return null;
		}
		ArticleUUIDChannelStoreBO articleUUIDChannelStore = getArticleUUIDChannelStore();
		return articleUUIDChannelStore.getUUID(channelId);
	}
	
	private boolean loadArticleChannelRefreshMinute() {
		String tmpStr = systemConstantService.getValByName(SystemConstantStore.articleChannelRefreshMinute);
		if (numberUtil.matchInteger(tmpStr)) {
			articleChannelRefreshMinute = Long.parseLong(tmpStr);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public ArticleChannels findArticleChannelById(Long channelId) {
		if(channelId == null) {
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
	
	private ArticleChannelUUIDBO articleChannelPOUUIDProcess(ArticleChannels channel) {
		ArticleChannelUUIDBO resultBO = new ArticleChannelUUIDBO();
		if(channel == null) {
			return resultBO;
		}
		resultBO.setUuid(articleUUIDChannelStore.getUUID(channel.getChannelId()));
		resultBO.setChannelName(channel.getChannelName());
		resultBO.setWeights(channel.getWeights());
		resultBO.setImage(channel.getImage());
		return resultBO;
	}
	
	private List<ArticleChannelVO> buildChannelVOByPO(List<ArticleChannels> channelList) {
		loadArticleUUIDChannel();
		List<ArticleChannelUUIDBO> resultUUIDChannels = new ArrayList<ArticleChannelUUIDBO>();
		if (channelList.size() > 0) {
			for (ArticleChannels channel : channelList) {
				resultUUIDChannels.add(articleChannelPOUUIDProcess(channel));
			}
		}
		
		List<ArticleChannelVO> publicChannelVOList = new ArrayList<ArticleChannelVO>();
		for(ArticleChannelUUIDBO channelBO : resultUUIDChannels) {
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
		
		if(userId == null) {
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
		if(bo.getPrivateChannels() == null) {
			bo.setPrivateChannels(new ArrayList<ArticleChannelVO>());
		}
		if(bo.getPublicChannels() == null) {
			bo.setPublicChannels(new ArrayList<ArticleChannelVO>());
		}
		if(bo.getFlashChannels() == null) {
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

		List<ArticleUserChannelResultBO> articleChannels = articleUserDetailMapper.findArticleUserChannelResultBO(param);
		List<ArticleUserChannelResultBO> channelsRefreshCache = new ArrayList<ArticleUserChannelResultBO>();
		List<ArticleUserChannelResultBO> flashChannels = new ArrayList<ArticleUserChannelResultBO>();
		List<ArticleUserChannelResultBO> privateChannels = new ArrayList<ArticleUserChannelResultBO>();

		if (!loadArticleChannelRefreshMinute()) {
			return channelListBO;
		}

		ArticleUserChannelResultBO tmpArticleChannel = null;
		for (int i = 0; i < articleChannels.size(); i++) {
			tmpArticleChannel = articleChannels.get(i);
			// 闪现频道的处理
			if (ArticleChannelType.flashChannel.getCode().equals(tmpArticleChannel.getChannelType())) {
				// getIsFlashUpdateTime == null  --> 用户在此频道无记录
				// 或者 闪现时间已过期
				// 放置到 channelsRefreshCache 重新处理概率闪现
				if (tmpArticleChannel.getIsFlashUpdateTime() == null
						|| (System.currentTimeMillis() - tmpArticleChannel.getIsFlashUpdateTime().getTime() > articleChannelRefreshMinute * 60 * 1000)) {
					channelsRefreshCache.add(articleChannels.get(i));
				// 未过期，而且在闪现中，给予出现
				} else if(tmpArticleChannel.getFlash().equals(true)) {
					flashChannels.add(tmpArticleChannel);
				}
			// 私人频道的处理
			} else if (ArticleChannelType.privateChannel.getCode().equals(tmpArticleChannel.getChannelType())) {
				privateChannels.add(articleChannels.get(i));
			}
		}
		if (channelsRefreshCache.size() > 0) {
			refreshArticleUserDetail(channelsRefreshCache, param.getUserId());
			flashChannels.addAll(channelsRefreshCache);
		}

		// 使用 articleUUIDChannelStore 前,需要先检查UUID有效期
		// 执行 getArticleUUIDChannel();
		loadArticleUUIDChannel();
		List<ArticleChannelUUIDBO> flashChannelsProcessed = channelBOListUUIDProcess(flashChannels);
		List<ArticleChannelUUIDBO> privateChannelsProcessed = channelBOListUUIDProcess(privateChannels);
		
		List<ArticleChannelVO> flashChannelVOList = new ArrayList<ArticleChannelVO>();
		for(ArticleChannelUUIDBO channelBO : flashChannelsProcessed) {
			flashChannelVOList.add(new ArticleChannelVO().buildByArticleUUIDChannelBO(channelBO));
		}
//		Collections.sort(flashChannelVOList);
		channelListBO.setFlashChannels(flashChannelVOList);
		
		List<ArticleChannelVO> privateChannelVOList = new ArrayList<ArticleChannelVO>();
		for(ArticleChannelUUIDBO channelBO : privateChannelsProcessed) {
			privateChannelVOList.add(new ArticleChannelVO().buildByArticleUUIDChannelBO(channelBO));
		}
//		Collections.sort(privateChannelVOList);
		channelListBO.setPrivateChannels(privateChannelVOList);
		
		channelListBO = removeChannelsByHostName(hostName, channelListBO);
		
		return channelListBO;
	}
	
	private List<ArticleChannelUUIDBO> channelBOListUUIDProcess(List<ArticleUserChannelResultBO> channels) {
		if(channels == null || channels.size() < 1) {
			return new ArrayList<ArticleChannelUUIDBO>();
		}
		
		List<ArticleChannelUUIDBO> resultList = new ArrayList<ArticleChannelUUIDBO>();
		for(ArticleUserChannelResultBO channel : channels) {
			resultList.add(userChannelBOUUIDProcess(channel));
		}
		return resultList;
	}
	
	private ArticleChannelUUIDBO userChannelBOUUIDProcess(ArticleUserChannelResultBO inputBO) {
		ArticleChannelUUIDBO tmpChannelBO = new ArticleChannelUUIDBO();
		tmpChannelBO.setUuid(articleUUIDChannelStore.getUUID(inputBO.getArticleChannelId()));
		tmpChannelBO.setChannelName(inputBO.getChannelName());
		tmpChannelBO.setWeights(inputBO.getWeights());
		tmpChannelBO.setImage(inputBO.getImage());
		return tmpChannelBO;
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
			
			if(articleUserChannelResult.getFlash() && articleUserChannelResult.getUserId() == null) {
				channelWasFlash = true;
			}
			
			if(channelWasFlash || articleUserChannelResult.getChannelFlashPoint() == 0) {
				tmpChannelRemainder = 0L;
			} else {
				tmpChannelRemainder = articleUserChannelResult.getChannelPoint() % articleUserChannelResult.getChannelFlashPoint();
			}

			if (channelWasFlash || tmpChannelRemainder == 0) {
				randomLong = 0L;
			} else {
				randomLong = ThreadLocalRandom.current().nextLong(0, tmpChannelRemainder);
			}
			// 如果用户系数比随机数高,视为随机放行;
			if (articleUserChannelResult.getCoefficient() >= randomLong || channelList.get(i).getChannelFlashPoint() == 0) {
				// 将原本无记录的/不通行的改为通行/新增记录
				if (articleUserChannelResult.getIsFlashUpdateTime() == null || articleUserChannelResult.getFlash().equals(false) || articleUserChannelResult.getChannelPoint() == 0) {
					tmpUpdateParam = new BatchUpdateFlashStatuParam();
					tmpUpdateParam.setFlash(true);
					tmpUpdateParam.setUserId(userId);
					tmpUpdateParam.setArticleChannelId(channelList.get(i).getArticleChannelId());
					if(articleUserChannelResult.getIsFlashUpdateTime() == null) {
						tmpUpdateParam.setDailyChannelPostLimit(ArticleConstant.firstVisitDailyPostLimit);
					} else {
						tmpPostLimit = articleUserChannelResult.getCoefficient()/ArticleConstant.coefficientToPostLimit;
						if(tmpPostLimit < ArticleConstant.firstVisitDailyPostLimit && articleUserChannelResult.getCoefficient() > ArticleConstant.badUserCoefficientLimit) {
							tmpPostLimit = ArticleConstant.firstVisitDailyPostLimit + 0D;
						} 
						tmpUpdateParam.setDailyChannelPostLimit(tmpPostLimit.intValue());
					}
					changeToTrue.add(tmpUpdateParam);
				}
			// 以下为随机禁行
			} else {
				if (articleUserChannelResult.getIsFlashUpdateTime() == null || articleUserChannelResult.getFlash().equals(true)) {
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
		for(ArticleChannels channel : flashChannels) {
			flashPoint = channel.getChannelFlashPoint();
			if(flashPoint == null || flashPoint == 0 || flashPoint == 1) {
				randomPoint = 0L;
			} else {
				channelPoint = channel.getChannelPoint();
				currentPoint = channelPoint % flashPoint;
				randomPoint = ThreadLocalRandom.current().nextLong(0, flashPoint);
			}
			if(randomPoint > currentPoint) {
				channel.setIsFlash(true);
				isFlashParam.addChannelId(channel.getChannelId());
			} else {
				channel.setIsFlash(false);
				notFlashParam.addChannelId(channel.getChannelId());
			}
		}
		
		if(isFlashParam.getChannelIdList().size() > 0) {
			isFlashParam.setFlash(true);
			articleChannelsMapper.updateChannelIsFlash(isFlashParam);
		}
		if(notFlashParam.getChannelIdList().size() > 0) {
			articleChannelsMapper.updateChannelIsFlash(notFlashParam);
		}
	}
	
	@Override
	public boolean showLikeOrHate(Long channelId, Long userId) {
		if(channelId == null || userId == null) {
			return false;
		}
		
		FindArticleChannelsParam findChannelParam = new FindArticleChannelsParam();
		findChannelParam.setChannelId(channelId);
		ArticleChannels channel = articleChannelsMapper.findArticleChannelById(findChannelParam);
		
		if(channel == null) {
			return false;
		}
		
		if(ArticleChannelType.flashChannel.getCode().equals(channel.getChannelType())) {
			FindArticleUserDetailByUserIdChannelIdParam findArticleUserDetailParam = new FindArticleUserDetailByUserIdChannelIdParam();
			findArticleUserDetailParam.setChannelId(channelId);
			findArticleUserDetailParam.setUserId(userId);
			ArticleUserDetail articleUserDetail = articleUserDetailMapper.findArticleUserDetailByUserIdChannelId(findArticleUserDetailParam);
			if(articleUserDetail.getLikeChannelUpdateTime() == null) {
				articleUserDetail.setLikeChannelUpdateTime(new Date(0L));
			}
			if(articleUserDetail.getHateChannelUpdateTime() == null) {
				articleUserDetail.setHateChannelUpdateTime(new Date(0L));
			}
			ArticleChannelLastLikeOrHateBO lastLikeOrHate = new ArticleChannelLastLikeOrHateBO();
			if(articleUserDetail.getLikeChannelUpdateTime().after(articleUserDetail.getHateChannelUpdateTime())) {
				lastLikeOrHate.setLastChooseDate(articleUserDetail.getLikeChannelUpdateTime());
				lastLikeOrHate.setIsLike(true);
				lastLikeOrHate.setLikeOrHateLevel(articleUserDetail.getLikeChannelCount());
			} else if(articleUserDetail.getLikeChannelUpdateTime().before(articleUserDetail.getHateChannelUpdateTime())){
				lastLikeOrHate.setLastChooseDate(articleUserDetail.getHateChannelUpdateTime());
				lastLikeOrHate.setIsLike(false);
				lastLikeOrHate.setLikeOrHateLevel(0 - articleUserDetail.getHateChannelCount());
			}
			if(lastLikeOrHate.getIsLike() == null 
					||new Date().after(dateHandler.dateDiffDays(lastLikeOrHate.getLastChooseDate(), ArticleChannelLikeOrHateType.getType(lastLikeOrHate.getLikeOrHateLevel()).getDelayDays()))) {
				return true;
			}
		} else {
			return false;
		}
		return false;
	}
	
	@Override
	public String loadChannelPrefix(Integer channelId) {
		String mainFolderPath = systemConstantService.getValByName(SystemConstantStore.articleChannelPrefixStorePath);
		String strContent = "";
		if(new File(mainFolderPath + channelId + ".txt").exists()) {
			 strContent = ioUtil.getStringFromFile(mainFolderPath + channelId + ".txt");
		}
		return strContent;
	}

	private GetArticleChannelsBO removeChannelsByHostName(String hostName, GetArticleChannelsBO channelList) {
		if (channelList == null) {
			return channelList;
		}

		ArticleUUIDChannelStoreBO uuidStore = getArticleUUIDChannelStore();

		if (StringUtils.isBlank(hostName) || uuidStore.getChannelList().size() < 1) {
			channelList = removeChannelsForUnknow(channelList, uuidStore);
		} else if (hostName.contains(systemConstantService.getValByName(SystemConstantStore.hostName1))) {
			channelList = removeChannelsFor3310For(channelList, uuidStore);
		} else if (hostName.contains(systemConstantService.getValByName(SystemConstantStore.hostName2))) {
			channelList = removeChannelsForSDW(channelList, uuidStore);
		} else if (hostName.contains(systemConstantService.getValByName(SystemConstantStore.hostName3))) {
			channelList = removeChannelsForER(channelList, uuidStore);
		} else {
			channelList = removeChannelsForUnknow(channelList, uuidStore);
		}

		return channelList;
	}

	private GetArticleChannelsBO removeChannelsFor3310For(GetArticleChannelsBO channelList,
			ArticleUUIDChannelStoreBO uuidStore) {
		if (channelList == null) {
			return channelList;
		}

		GetArticleChannelsBO newChannelList = removeChannels(channelList, uuidStore,
				List.of(2L, 3L, 4L));

		return newChannelList;
	}

	private GetArticleChannelsBO removeChannelsForSDW(GetArticleChannelsBO channelList,
			ArticleUUIDChannelStoreBO uuidStore) {
		if (channelList == null) {
			return channelList;
		}

		GetArticleChannelsBO newChannelList = removeChannels(channelList, uuidStore,
				List.of(4L));

		return newChannelList;
	}

	private GetArticleChannelsBO removeChannelsForER(GetArticleChannelsBO channelList,
			ArticleUUIDChannelStoreBO uuidStore) {
		if (channelList == null) {
			return channelList;
		}

		GetArticleChannelsBO newChannelList = removeChannels(channelList, uuidStore, null);

		return newChannelList;
	}

	private GetArticleChannelsBO removeChannelsForUnknow(
			GetArticleChannelsBO channelList,
			ArticleUUIDChannelStoreBO uuidStore) {
		String envName = systemConstantService.getValByName(SystemConstantStore.envName, true);
		if("dev".equals(envName)) {
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

	private GetArticleChannelsBO removeChannels(GetArticleChannelsBO channelList,
			ArticleUUIDChannelStoreBO uuidStore, List<Long> targetChannelIds) {
		List<ArticleChannelVO> tmpChannelList = null;

		tmpChannelList = removeChannels(channelList.getPublicChannels(), uuidStore, targetChannelIds);
		channelList.setPublicChannels(tmpChannelList);

		tmpChannelList = removeChannels(channelList.getFlashChannels(), uuidStore, targetChannelIds);
		channelList.setFlashChannels(tmpChannelList);

		tmpChannelList = removeChannels(channelList.getPrivateChannels(), uuidStore, targetChannelIds);
		channelList.setPrivateChannels(tmpChannelList);

		return channelList;
	}
	
	private GetArticleChannelsBO removeAllChannels(GetArticleChannelsBO channelList) {
		
		channelList.setPublicChannels(new ArrayList<ArticleChannelVO>());
		channelList.setFlashChannels(new ArrayList<ArticleChannelVO>());
		channelList.setPrivateChannels(new ArrayList<ArticleChannelVO>());

		return channelList;
	}

	private List<ArticleChannelVO> removeChannels(List<ArticleChannelVO> channelList,
			ArticleUUIDChannelStoreBO uuidStore, List<Long> targetChannelIds) {
		if (uuidStore.getChannelList() == null || uuidStore.getChannelList().size() < 0 || channelList == null
				|| channelList.size() < 1) {
			return new ArrayList<ArticleChannelVO>();
		}
		if (targetChannelIds == null || targetChannelIds.size() < 1) {
			return channelList;
		}
		Long tmpChannelId = null;

		List<ArticleChannelVO> newChannelList = new ArrayList<ArticleChannelVO>();
		for (ArticleChannelVO channel : channelList) {
			tmpChannelId = uuidStore.getChannelId(channel.getUuid());
			if (targetChannelIds.contains(tmpChannelId)) {
				continue;
			}
			newChannelList.add(channel);
		}

		return newChannelList;
	}
	
	@Override
	public List<Integer> findChannelIdListByUserId(Long userId) {
		if(userId == null) {
			return new ArrayList<Integer>();
		}
		
		return articleUserDetailMapper.findChannelIdListByUserId(userId);
	}
}