package demo.article.article.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.article.article.mapper.ArticleChannelsMapper;
import demo.article.article.pojo.bo.GetArticleChannelsBO;
import demo.article.article.pojo.po.ArticleChannels;
import demo.article.article.pojo.po.ArticleChannelsExample;
import demo.article.article.pojo.result.GetArticleChannelsResult;
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
		return articleChannelsMapper.selectByPrimaryKey(channelId);
	}

	private List<ArticleChannelVO> getPublicChannels() {
		ArticleChannelsExample example = new ArticleChannelsExample();
		example.createCriteria().andChannelTypeEqualTo(ArticleChannelType.publicChannel.getCode()).andIsDeleteEqualTo(false);
		List<ArticleChannels> openChannels = articleChannelsMapper.selectByExample(example);
		List<ArticleChannelVO> publicChannelVOList = buildChannelVOByPO(openChannels);
		return publicChannelVOList;
	}

	private List<ArticleChannelVO> getFlashChannels() {
		ArticleChannelsExample example = new ArticleChannelsExample();
		example.createCriteria().andChannelTypeEqualTo(ArticleChannelType.flashChannel.getCode()).andIsDeleteEqualTo(false);
		List<ArticleChannels> flashChannels = articleChannelsMapper.selectByExample(example);
		List<ArticleChannelVO> flashChannelVOList = buildChannelVOByPO(flashChannels);
		return flashChannelVOList;
	}

	private ArticleChannelVO buildChannelVOByPO(ArticleChannels channel) {
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

	private List<ArticleChannelVO> buildChannelVOByPO(List<ArticleChannels> channelPOList) {
		List<ArticleChannelVO> channelVOList = new ArrayList<ArticleChannelVO>();
		if (channelPOList.size() > 0) {
			for (ArticleChannels po : channelPOList) {
				channelVOList.add(buildChannelVOByPO(po));
			}
		}

		return channelVOList;
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

		/*
		 * TODO 2019-12-13 
		 * 频道逻辑将重构.
		 * 重构完成前, 对非管理员只开放公共频道
		 */
		
		ArticleChannelsExample example = new ArticleChannelsExample();
		example.createCriteria().andChannelTypeEqualTo(ArticleChannelType.publicChannel.getCode()).andIsDeleteEqualTo(false);
		List<ArticleChannels> publicChannelsPOList = articleChannelsMapper.selectByExample(example);

		ArrayList<ArticleChannelVO> publicChannelVOList = new ArrayList<ArticleChannelVO>();
		for(ArticleChannels i : publicChannelsPOList) {
			publicChannelVOList.add(buildChannelVOByPO(i));
		}
		
		channelListBO.setPublicChannels(publicChannelVOList);
		channelListBO.setFlashChannels(new ArrayList<ArticleChannelVO>());
		channelListBO.setPrivateChannels(new ArrayList<ArticleChannelVO>());

		channelListBO = removeChannelsByHostName(hostName, channelListBO);

		return channelListBO;
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