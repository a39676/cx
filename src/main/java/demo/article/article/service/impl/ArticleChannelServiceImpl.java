package demo.article.article.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.article.article.mapper.ArticleChannelKeyHostnameMapper;
import demo.article.article.mapper.ArticleChannelsMapper;
import demo.article.article.mapper.ArticleUserDetailMapper;
import demo.article.article.pojo.bo.GetArticleChannelsBO;
import demo.article.article.pojo.dto.ArticleChannelKeyHostnameIdDTO;
import demo.article.article.pojo.dto.ArticleChannelManagerDTO;
import demo.article.article.pojo.po.ArticleChannelKeyHostname;
import demo.article.article.pojo.po.ArticleChannelKeyHostnameExample;
import demo.article.article.pojo.po.ArticleChannels;
import demo.article.article.pojo.po.ArticleChannelsExample;
import demo.article.article.pojo.po.ArticleUserDetail;
import demo.article.article.pojo.po.ArticleUserDetailExample;
import demo.article.article.pojo.result.GetArticleChannelsResult;
import demo.article.article.pojo.type.ArticleChannelKeyHostnameType;
import demo.article.article.pojo.type.ArticleChannelOperationalType;
import demo.article.article.pojo.type.ArticleChannelType;
import demo.article.article.pojo.vo.ArticleChannelVO;
import demo.article.article.service.ArticleChannelService;
import demo.base.system.pojo.po.Hostname;
import demo.base.system.pojo.result.HostnameType;
import demo.common.pojo.result.CommonResultCX;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class ArticleChannelServiceImpl extends ArticleCommonService implements ArticleChannelService {

	@Autowired
	private ArticleChannelsMapper articleChannelsMapper;
	@Autowired
	private ArticleUserDetailMapper articleUserDetailMapper;
	@Autowired
	private ArticleChannelKeyHostnameMapper articleChannelKeyHostnameMapper;
	@Autowired
	private FileUtilCustom ioUtil;

	@PostConstruct
	@Override
	public void loadPublicChannels() {
		if (articleOptionService.getPublicChannels() == null) {
			articleOptionService.setPublicChannels(new HashMap<HostnameType, List<ArticleChannelVO>>());
		}

		List<Hostname> hostnameList = hostnameService.findHostnames();
		List<ArticleChannelVO> allPublicChannelVoList = getPublicChannels();
		HostnameType hostnameType = null;
		
		for (Hostname hostname : hostnameList) {
			ArticleChannelKeyHostnameExample channelKeyHostnameExample = new ArticleChannelKeyHostnameExample();
			channelKeyHostnameExample.createCriteria().andIsDeleteEqualTo(false).andHostIdEqualTo(hostname.getId());
			hostnameType = HostnameType.getTypeCustom(hostname.getHostname());
			List<ArticleChannelKeyHostname> channelKeyHostnameList = articleChannelKeyHostnameMapper
					.selectByExample(channelKeyHostnameExample);
			List<Long> channelPassList = new ArrayList<Long>();
			List<Long> channelBanList = new ArrayList<Long>();

			for (ArticleChannelKeyHostname i : channelKeyHostnameList) {
				if (ArticleChannelKeyHostnameType.pass.getCode().equals(i.getKeyType())) {
					channelPassList.add(i.getChannelId());
				} else {
					channelBanList.add(i.getChannelId());
				}
			}

			List<ArticleChannelVO> tmpChannelList = new ArrayList<ArticleChannelVO>();
			tmpChannelList.add(createTheAllChannel());
			tmpChannelList.addAll(removeChannels(allPublicChannelVoList, channelBanList, channelPassList));

			articleOptionService.getPublicChannels().put(hostnameType, tmpChannelList);
		}
	}

	@Override
	public ArticleChannels findArticleChannelById(Long channelId) {
		if (channelId == null) {
			return null;
		}
		return articleChannelsMapper.selectByPrimaryKey(channelId);
	}

	private List<ArticleChannelVO> getPublicChannels() {
		ArticleChannelsExample example = new ArticleChannelsExample();
		example.createCriteria().andChannelTypeEqualTo(ArticleChannelType.publicChannel.getCode())
				.andIsDeleteEqualTo(false);
		List<ArticleChannels> openChannels = articleChannelsMapper.selectByExample(example);
		List<ArticleChannelVO> publicChannelVOList = buildChannelVOByPO(openChannels);
		return publicChannelVOList;
	}

	@Override
	public List<ArticleChannelVO> getPrivateChannels(Long userId) {
		if(userId == null) {
			return new ArrayList<ArticleChannelVO>();
		}
		ArticleUserDetailExample privateChannelConnectExample = new ArticleUserDetailExample();
		privateChannelConnectExample.createCriteria().andIsDeleteEqualTo(false).andUserIdEqualTo(userId)
				.andConnectTypeEqualTo(true);
		List<ArticleUserDetail> privateChannelConnectList = articleUserDetailMapper
				.selectByExample(privateChannelConnectExample);
		if (privateChannelConnectList == null || privateChannelConnectList.isEmpty()) {
			return new ArrayList<>();
		}
		List<Long> privateChannelIdList = privateChannelConnectList.stream().map(po -> po.getArticleChannelId())
				.collect(Collectors.toList());

		ArticleChannelsExample example = new ArticleChannelsExample();
		example.createCriteria().andChannelTypeEqualTo(ArticleChannelType.privateChannel.getCode())
				.andChannelIdIn(privateChannelIdList).andIsDeleteEqualTo(false);
		List<ArticleChannels> privateChannels = articleChannelsMapper.selectByExample(example);
		if (privateChannels == null || privateChannels.isEmpty()) {
			return new ArrayList<>();
		}
		List<ArticleChannelVO> privateChannelVOList = buildChannelVOByPO(privateChannels);

		return privateChannelVOList;
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
		
		result.setIsSuccess();
		return result;
	}

	@Override
	public boolean containThisChannel(HttpServletRequest request, Long channelId) {
		if (channelId == null) {
			return false;
		}
		GetArticleChannelsResult channelResult = getArticleChannelsDynamic(request);
		if (!channelResult.isSuccess()) {
			return false;
		}
		if (channelResult.getChannelList() == null || channelResult.getChannelList().size() < 1) {
			return false;
		}
		List<ArticleChannelVO> channelList = channelResult.getChannelList();
		for (ArticleChannelVO i : channelList) {
			if (channelId.toString().equals(i.getChannelId())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public GetArticleChannelsResult getArticleChannelsDynamic(HttpServletRequest request) {
		GetArticleChannelsBO bo = null;
		Long userId = baseUtilCustom.getUserId();

		HostnameType hostnameType = hostnameService.findHostnameType(request);
		if (userId == null) {
			bo = getArticleChannelsForNotLogin(hostnameType);
		} else {
			bo = getArticleChannelsByUserId(hostnameType, userId);
		}

		GetArticleChannelsResult result = buildGetArticleChannelsResult(bo);
		result.setIsSuccess();
		return result;
	}

	private ArticleChannelVO createTheAllChannel() {
		ArticleChannelVO vo = new ArticleChannelVO();
		vo.setChannelName("New");
		vo.setChannelType(ArticleChannelType.publicChannel.getCode());
		vo.setWeights(Integer.MAX_VALUE);
		return vo;
	}

	private GetArticleChannelsBO getArticleChannelsForNotLogin(HostnameType hostnameType) {
		GetArticleChannelsBO channelListBO = new GetArticleChannelsBO();

		channelListBO.setPublicChannels(articleOptionService.getPublicChannels().get(hostnameType));
		fillChannels(channelListBO);

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

	private GetArticleChannelsBO getArticleChannelsByUserId(HostnameType hostnameType, Long userId) {
		GetArticleChannelsBO channelListBO = new GetArticleChannelsBO();

		channelListBO.setPublicChannels(articleOptionService.getPublicChannels().get(hostnameType));

		if (userId == null) {
			fillChannels(channelListBO);
			return channelListBO;
		}

		List<ArticleChannelVO> privateChannelVOList = getPrivateChannels(userId);

		channelListBO.setFlashChannels(new ArrayList<ArticleChannelVO>());
		channelListBO.setPrivateChannels(privateChannelVOList);

		channelListBO = removeChannelsByHostName(hostnameType, channelListBO);

		channelListBO.getPublicChannels().add(0, createTheAllChannel());

		return channelListBO;
	}

	@Override
	public String loadChannelPrefix(Integer channelId) {
		String mainFolderPath = articleOptionService.getArticleChannelPrefixStorePath();
		String strContent = "";
		if (new File(mainFolderPath + channelId + ".txt").exists()) {
			strContent = ioUtil.getStringFromFile(mainFolderPath + channelId + ".txt");
		}
		return strContent;
	}

	private GetArticleChannelsBO removeChannelsByHostName(HostnameType hostnameType, GetArticleChannelsBO channelList) {
		if (channelList == null) {
			return channelList;
		}

		if (hostnameType == null) {
			channelList = removeChannelsForUnknow(channelList);
		} else {
			filterChannelDynamic(channelList, hostnameType);
		}

		return channelList;
	}

	private GetArticleChannelsBO removeChannelsForUnknow(GetArticleChannelsBO channelList) {
		if (systemOptionService.isDev()) {
			return channelList;
		}

		if (channelList == null) {
			return channelList;
		}

		GetArticleChannelsBO newChannelList = removeAllChannels(channelList);

		return newChannelList;
	}

	private GetArticleChannelsBO removeChannels(GetArticleChannelsBO channelList, List<Long> banChannelIds,
			List<Long> passChannelIds) {
		List<ArticleChannelVO> tmpChannelList = null;

		tmpChannelList = removeChannels(channelList.getPublicChannels(), banChannelIds, passChannelIds);
		channelList.setPublicChannels(tmpChannelList);

		tmpChannelList = removeChannels(channelList.getFlashChannels(), banChannelIds, passChannelIds);
		channelList.setFlashChannels(tmpChannelList);

		tmpChannelList = removeChannels(channelList.getPrivateChannels(), banChannelIds, passChannelIds);
		channelList.setPrivateChannels(tmpChannelList);

		return channelList;
	}

	private GetArticleChannelsBO removeAllChannels(GetArticleChannelsBO channelList) {

		channelList.setPublicChannels(new ArrayList<ArticleChannelVO>());
		channelList.setFlashChannels(new ArrayList<ArticleChannelVO>());
		channelList.setPrivateChannels(new ArrayList<ArticleChannelVO>());

		return channelList;
	}

	private List<ArticleChannelVO> removeChannels(List<ArticleChannelVO> channelList, List<Long> banChannelIds,
			List<Long> passChannelIds) {
		if (channelList == null || channelList.size() < 1 || passChannelIds == null || passChannelIds.size() < 1) {
			return new ArrayList<ArticleChannelVO>();
		}
		if (banChannelIds == null || banChannelIds.size() < 1) {
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

			if (banChannelIds.contains(tmpChannelId)) {
				continue;
			}

			if (passChannelIds.contains(tmpChannelId)) {
				newChannelList.add(channel);
			}
		}

		return newChannelList;
	}

	@Override
	public List<ArticleChannelVO> findArticleChannel() {
		List<ArticleChannelVO> channelVOList = new ArrayList<ArticleChannelVO>();
		ArticleChannelsExample channelExample = new ArticleChannelsExample();
//		example.createCriteria().andIsDeleteEqualTo(false);
		List<ArticleChannels> channelPOList = articleChannelsMapper.selectByExample(channelExample);
		ArticleChannelVO tmpChannelVO = null;

		List<Hostname> hostnamePOList = hostnameService.findHostnames();
		ArticleChannelKeyHostnameExample channelKeyHostnameExample = new ArticleChannelKeyHostnameExample();
		List<ArticleChannelKeyHostname> channelKeyHostnamePOList = articleChannelKeyHostnameMapper
				.selectByExample(channelKeyHostnameExample);

		List<ArticleChannelKeyHostnameIdDTO> channelKeyHostTmpList = null;
		ArticleChannelKeyHostnameIdDTO tmpChannelKeyHostnameDTO = null;
		ArticleChannelType channelType = null;
		for (ArticleChannels channel : channelPOList) {
			channelType = ArticleChannelType.getType(channel.getChannelType());
			tmpChannelVO = new ArticleChannelVO();
			tmpChannelVO.setChannelName(channel.getChannelName());
			tmpChannelVO.setChannelId(String.valueOf(channel.getChannelId()));
			tmpChannelVO.setWeights(channel.getWeights());
			tmpChannelVO.setIsDelete(channel.getIsDelete());
			tmpChannelVO.setChannelType(channel.getChannelType());
			tmpChannelVO.setChannelTypeName(channelType.getName());
			channelKeyHostTmpList = new ArrayList<ArticleChannelKeyHostnameIdDTO>();
			for (Hostname i : hostnamePOList) {
				tmpChannelKeyHostnameDTO = new ArticleChannelKeyHostnameIdDTO();
				tmpChannelKeyHostnameDTO.setChannelId(channel.getChannelId());
				tmpChannelKeyHostnameDTO.setHostname(i.getHostname());
				tmpChannelKeyHostnameDTO.setHostnameId(i.getId());
				tmpChannelKeyHostnameDTO.setArticleChannelKeyHostnameType(
						findChannelKeyHostnameTypeByChannelIdAndHostnameId(channelKeyHostnamePOList,
								channel.getChannelId(), i.getId()).getCode());
				channelKeyHostTmpList.add(tmpChannelKeyHostnameDTO);
			}
			tmpChannelVO.setChannelIdKeyHostnameId(channelKeyHostTmpList);
			channelVOList.add(tmpChannelVO);
		}
		Collections.sort(channelVOList);
		return channelVOList;
	}

	@Override
	public ModelAndView articleChannelManagerView() {
		ModelAndView v = new ModelAndView();
		v.setViewName("articleJSP/articleChannelManager");
		v.addObject("channelTypeList", ArticleChannelType.values());
		v.addObject("channelList", findArticleChannel());
		return v;
	}

	@Override
	public CommonResultCX articleChannelManager(ArticleChannelManagerDTO dto) {
		CommonResultCX result = new CommonResultCX();
		if (dto.getOperationalType() == null) {
			result.failWithMessage("参数为空");
			return result;
		}

		ArticleChannelOperationalType operationalType = ArticleChannelOperationalType.getType(dto.getOperationalType());
		if (operationalType == null) {
			result.failWithMessage("参数异常");
			return result;
		}

		if (!baseUtilCustom.hasAdminRole()) {
			result.failWithMessage("无操作权限");
			return result;
		}

		if (operationalType.equals(ArticleChannelOperationalType.add)) {
			result = addNewChannel(dto);
		} else if (operationalType.equals(ArticleChannelOperationalType.updateDelete)) {
			result = updateDeleteChannel(dto);
		} else if (operationalType.equals(ArticleChannelOperationalType.modify)) {
			result = modifyChannel(dto);
		}

		return result;
	}

	private CommonResultCX addNewChannel(ArticleChannelManagerDTO dto) {
		CommonResultCX r = new CommonResultCX();
		if (StringUtils.isBlank(dto.getChannelName())) {
			r.failWithMessage("不能设置空白名称");
			return r;
		}

		ArticleChannelType channelType = ArticleChannelType.getType(dto.getChannelType());
		if (channelType == null) {
			r.failWithMessage("参数异常");
			return r;
		}

		if (dto.getWeights() == null) {
			dto.setWeights(0);
		}

		ArticleChannels newChannel = new ArticleChannels();
		newChannel.setChannelId(snowFlake.getNextId());
		newChannel.setChannelName(dto.getChannelName());
		newChannel.setChannelType(dto.getChannelType());
		newChannel.setWeights(dto.getWeights());

		int count = articleChannelsMapper.insertSelective(newChannel);
		if (count < 1) {
			r.failWithMessage("新加入异常");
			return r;
		}

		r.successWithMessage("新加入成功");
		return r;
	}

	private CommonResultCX updateDeleteChannel(ArticleChannelManagerDTO dto) {
		CommonResultCX r = new CommonResultCX();
		if (dto.getChannelId() == null) {
			return r;
		}

		ArticleChannels channel = articleChannelsMapper.selectByPrimaryKey(dto.getChannelId());
		if (channel == null) {
			r.failWithMessage("不存在此频道");
			return r;
		}

		channel.setIsDelete(dto.getIsDelete());
		int count = articleChannelsMapper.updateByPrimaryKeySelective(channel);
		if (count < 1) {
			r.failWithMessage("更新失败");
			return r;
		}

		r.successWithMessage("成功更新");
		return r;
	}

	private CommonResultCX modifyChannel(ArticleChannelManagerDTO dto) {
		CommonResultCX r = new CommonResultCX();
		if (dto.getChannelId() == null) {
			r.failWithMessage("参数为空");
			return r;
		}

		ArticleChannels channel = articleChannelsMapper.selectByPrimaryKey(dto.getChannelId());
		if (channel == null) {
			r.failWithMessage("参数异常");
			return r;
		}
		if (dto.getChannelType() != null) {
			ArticleChannelType channelType = ArticleChannelType.getType(dto.getChannelType());
			if (channelType == null) {
				r.failWithMessage("参数异常");
				return r;
			}
			channel.setChannelType(channelType.getCode());
		}

		if (StringUtils.isNotBlank(dto.getChannelName())) {
			channel.setChannelName(dto.getChannelName());
		}
		if (dto.getWeights() != null) {
			channel.setWeights(dto.getWeights());
		}

		int count = articleChannelsMapper.updateByPrimaryKeySelective(channel);
		if (count < 1) {
			r.failWithMessage("更新失败");
			return r;
		}
		
		loadPublicChannels();

		r.successWithMessage("成功更新");
		return r;
	}

	private GetArticleChannelsBO filterChannelDynamic(GetArticleChannelsBO channelList, HostnameType hostnameType) {
		List<Hostname> hostnameList = hostnameService.findHostnames();
		Integer hostnameId = null;
		for (Hostname hostname : hostnameList) {
			if (hostnameType.equals(HostnameType.getTypeCustom(hostname.getHostname()))) {
				hostnameId = hostname.getId();
			}
		}

		if (hostnameId == null) {
			return removeChannelsForUnknow(channelList);
		}

		ArticleChannelKeyHostnameExample channelKeyHostnameExample = new ArticleChannelKeyHostnameExample();
		channelKeyHostnameExample.createCriteria().andIsDeleteEqualTo(false).andHostIdEqualTo(hostnameId);
		List<ArticleChannelKeyHostname> channelKeyHostnameList = articleChannelKeyHostnameMapper
				.selectByExample(channelKeyHostnameExample);
		List<Long> channelPassList = new ArrayList<Long>();
		List<Long> channelBanList = new ArrayList<Long>();

		for (ArticleChannelKeyHostname i : channelKeyHostnameList) {
			if (ArticleChannelKeyHostnameType.pass.getCode().equals(i.getKeyType())) {
				channelPassList.add(i.getChannelId());
			} else {
				channelBanList.add(i.getChannelId());
			}
		}

		GetArticleChannelsBO newChannelList = removeChannels(channelList, channelBanList, channelPassList);

		return newChannelList;
	}

	@Override
	public CommonResultCX editChannelKeyHostname(ArticleChannelKeyHostnameIdDTO dto) {
		CommonResultCX r = new CommonResultCX();
		if (dto.getChannelId() == null || dto.getHostnameId() == null
				|| dto.getArticleChannelKeyHostnameType() == null) {
			r.failWithMessage("参数异常");
			return r;
		}

		ArticleChannelKeyHostnameType channelKeyHostnameType = ArticleChannelKeyHostnameType
				.getType(dto.getArticleChannelKeyHostnameType());
		if (channelKeyHostnameType == null) {
			r.failWithMessage("参数异常");
			return r;
		}

		ArticleChannelKeyHostname channelKeyHostnamePO = null;
		channelKeyHostnamePO = new ArticleChannelKeyHostname();
		channelKeyHostnamePO.setChannelId(dto.getChannelId());
		channelKeyHostnamePO.setHostId(dto.getHostnameId());
		channelKeyHostnamePO.setKeyType(dto.getArticleChannelKeyHostnameType());
		int count = articleChannelKeyHostnameMapper.insertOrUpdate(channelKeyHostnamePO);
		if (count < 1) {
			r.failWithMessage("修改异常");
			return r;
		}

		loadPublicChannels();

		r.setIsSuccess();
		return r;
	}

	private ArticleChannelKeyHostnameType findChannelKeyHostnameTypeByChannelIdAndHostnameId(
			List<ArticleChannelKeyHostname> channelKeyHostnamePOList, Long channelId, Integer hostnameId) {
		if (channelKeyHostnamePOList == null || channelKeyHostnamePOList.size() < 1 || channelId == null
				|| hostnameId == null) {
			return ArticleChannelKeyHostnameType.ban;
		}

		for (ArticleChannelKeyHostname i : channelKeyHostnamePOList) {
			if (channelId.equals(i.getChannelId()) && hostnameId.equals(i.getHostId())) {
				return ArticleChannelKeyHostnameType.getType(i.getKeyType());
			}
		}

		return ArticleChannelKeyHostnameType.ban;
	}

	@Override
	public boolean canVisitThisChannel(Long userId, Long channelId) {
		if (userId == null || channelId == null) {
			return false;
		}
		ArticleUserDetailExample example = new ArticleUserDetailExample();
		example.createCriteria().andIsDeleteEqualTo(false).andConnectTypeEqualTo(true).andUserIdEqualTo(userId)
				.andArticleChannelIdEqualTo(channelId);
		List<ArticleUserDetail> poList = articleUserDetailMapper.selectByExample(example);
		return poList != null && !poList.isEmpty();
	}
}