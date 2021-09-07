package demo.article.article.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.article.article.mapper.ArticleChannelKeyHostnameMapper;
import demo.article.article.mapper.ArticleChannelsMapper;
import demo.article.article.pojo.bo.GetArticleChannelsBO;
import demo.article.article.pojo.dto.ArticleChannelKeyHostnameIdDTO;
import demo.article.article.pojo.dto.ArticleChannelManagerDTO;
import demo.article.article.pojo.po.ArticleChannelKeyHostname;
import demo.article.article.pojo.po.ArticleChannelKeyHostnameExample;
import demo.article.article.pojo.po.ArticleChannels;
import demo.article.article.pojo.po.ArticleChannelsExample;
import demo.article.article.pojo.result.GetArticleChannelsResult;
import demo.article.article.pojo.type.ArticleChannelKeyHostnameType;
import demo.article.article.pojo.type.ArticleChannelOperationalType;
import demo.article.article.pojo.type.ArticleChannelType;
import demo.article.article.pojo.vo.ArticleChannelVO;
import demo.article.article.service.ArticleChannelService;
import demo.base.system.pojo.po.Hostname;
import demo.base.system.service.HostnameService;
import demo.common.pojo.result.CommonResultCX;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class ArticleChannelServiceImpl extends ArticleCommonService implements ArticleChannelService {

	@Autowired
	private HostnameService hostnameService;
	@Autowired
	private ArticleChannelsMapper articleChannelsMapper;
	@Autowired
	private ArticleChannelKeyHostnameMapper articleChannelKeyHostnameMapper;
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
	public boolean containThisChannel(HttpServletRequest request, Long channelId) {
		if(channelId == null) {
			return false;
		}
		GetArticleChannelsResult channelResult = getArticleChannelsDynamic(request);
		if(!channelResult.isSuccess()) {
			return false;
		}
		if(channelResult.getChannelList() == null || channelResult.getChannelList().size() < 1) {
			return false;
		}
		List<ArticleChannelVO> channelList = channelResult.getChannelList();
		for(ArticleChannelVO i : channelList) {
			if(channelId.toString().equals(i.getChannelId())) {
				return true;
			}
		}
		return false;
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
		 * TODO 
		 * 2020-02-06
		 * 暂时不开放闪现频道
		 * 考虑废除闪现频道?
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
		String mainFolderPath = articleConstantService.getArticleChannelPrefixStorePath();
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
		} else {
			filterChannelDynamic(channelList, hostName);
		}

		return channelList;
	}

	private GetArticleChannelsBO removeChannelsForUnknow(GetArticleChannelsBO channelList) {
		if ("dev".equals(systemConstantService.getEnvNameRefresh())) {
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

	private GetArticleChannelsBO removeChannels(GetArticleChannelsBO channelList, List<Long> banChannelIds, List<Long> passChannelIds) {
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

	private List<ArticleChannelVO> removeChannels(List<ArticleChannelVO> channelList, List<Long> banChannelIds, List<Long> passChannelIds) {
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
//		TODO
//		未填充域名关联信息
		
		List<Hostname> hostnamePOList = hostnameService.findHonstnames();
		ArticleChannelKeyHostnameExample channelKeyHostnameExample = new ArticleChannelKeyHostnameExample();
		List<ArticleChannelKeyHostname> channelKeyHostnamePOList = articleChannelKeyHostnameMapper.selectByExample(channelKeyHostnameExample);
		
		List<ArticleChannelKeyHostnameIdDTO> channelKeyHostTmpList = null;
		ArticleChannelKeyHostnameIdDTO tmpChannelKeyHostnameDTO = null;
		for(ArticleChannels channel : channelPOList) {
			tmpChannelVO = new ArticleChannelVO();
			tmpChannelVO.setChannelName(channel.getChannelName());
			tmpChannelVO.setChannelId(String.valueOf(channel.getChannelId()));
			tmpChannelVO.setWeights(channel.getWeights());
			tmpChannelVO.setIsDelete(channel.getIsDelete());
			tmpChannelVO.setChannelType(channel.getChannelType());
			channelKeyHostTmpList = new ArrayList<ArticleChannelKeyHostnameIdDTO>();
			for(Hostname i : hostnamePOList) {
				tmpChannelKeyHostnameDTO = new ArticleChannelKeyHostnameIdDTO();
				tmpChannelKeyHostnameDTO.setChannelId(channel.getChannelId());
				tmpChannelKeyHostnameDTO.setHostname(i.getHostname());
				tmpChannelKeyHostnameDTO.setHostnameId(i.getId());
				tmpChannelKeyHostnameDTO.setArticleChannelKeyHostnameType(findChannelKeyHostnameTypeByChannelIdAndHostnameId(channelKeyHostnamePOList, channel.getChannelId(), i.getId()).getCode());
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
		v.addObject("channelList", findArticleChannel());
		return v;
	}
	
	@Override
	public CommonResultCX articleChannelManager(ArticleChannelManagerDTO dto) {
		CommonResultCX result = new CommonResultCX();
		if(dto.getOperationalType() == null) {
			result.failWithMessage("参数为空");
			return result;
		}
		
		ArticleChannelOperationalType operationalType = ArticleChannelOperationalType.getType(dto.getOperationalType());
		if(operationalType == null) {
			result.failWithMessage("参数异常");
			return result;
		}
		
		if(!baseUtilCustom.hasAdminRole()) {
			result.failWithMessage("无操作权限");
			return result;
		}
		
		if(operationalType.equals(ArticleChannelOperationalType.add)) {
			result = addNewChannel(dto);
		} else if(operationalType.equals(ArticleChannelOperationalType.updateDelete)) {
			result = updateDeleteChannel(dto);
		} else if(operationalType.equals(ArticleChannelOperationalType.modify)) {
			result = modifyChannel(dto);
		}
		
		return result;
	}
	
	private CommonResultCX addNewChannel(ArticleChannelManagerDTO dto) {
		CommonResultCX r = new CommonResultCX();
		if(StringUtils.isBlank(dto.getChannelName())) {
			r.failWithMessage("不能设置空白名称");
			return r;
		}
		
		ArticleChannelType channelType = ArticleChannelType.getType(dto.getChannelType());
		if(channelType == null) {
			r.failWithMessage("参数异常");
			return r;
		}
		
		if(dto.getWeights() == null) {
			dto.setWeights(0);
		}
		
		ArticleChannels newChannel = new ArticleChannels();
		newChannel.setChannelId(snowFlake.getNextId());
		newChannel.setChannelName(dto.getChannelName());
		newChannel.setChannelType(dto.getChannelType());
		newChannel.setWeights(dto.getWeights());
		
		int count = articleChannelsMapper.insertSelective(newChannel);
		if(count < 1) {
			r.failWithMessage("新加入异常");
			return r;
		}
		
		r.successWithMessage("新加入成功");
		return r;
	}
	
	private CommonResultCX updateDeleteChannel(ArticleChannelManagerDTO dto) {
		CommonResultCX r = new CommonResultCX();
		if(dto.getChannelId() == null) {
			return r;
		}
		
		ArticleChannels channel = articleChannelsMapper.selectByPrimaryKey(dto.getChannelId());
		if(channel == null) {
			r.failWithMessage("不存在此频道");
			return r;
		}
		
		channel.setIsDelete(dto.getIsDelete());
		int count = articleChannelsMapper.updateByPrimaryKeySelective(channel);
		if(count < 1) {
			r.failWithMessage("更新失败");
			return r;
		}
		
		r.successWithMessage("成功更新");
		return r;
	}
	
	private CommonResultCX modifyChannel(ArticleChannelManagerDTO dto) {
		CommonResultCX r = new CommonResultCX();
		if(dto.getChannelId() == null) {
			r.failWithMessage("参数为空");
			return r;
		}
		
		ArticleChannels channel = articleChannelsMapper.selectByPrimaryKey(dto.getChannelId());
		if(channel == null) {
			r.failWithMessage("参数异常");
			return r;
		}
		if(dto.getChannelType() != null) {
			ArticleChannelType channelType = ArticleChannelType.getType(dto.getChannelType());
			if(channelType == null) {
				r.failWithMessage("参数异常");
				return r;
			}
			channel.setChannelType(channelType.getCode());
		}
		
		if(StringUtils.isNotBlank(dto.getChannelName())) {
			channel.setChannelName(dto.getChannelName());
		}
		if(dto.getWeights() != null) {
			channel.setWeights(dto.getWeights());
		}
		
		int count = articleChannelsMapper.updateByPrimaryKeySelective(channel);
		if(count < 1) {
			r.failWithMessage("更新失败");
			return r;
		}
		
		r.successWithMessage("成功更新");
		return r;
	}

	public GetArticleChannelsBO filterChannelDynamic(GetArticleChannelsBO channelList, String hostname) {
		List<Hostname> hostnameList = hostnameService.findHonstnames();
		Integer hostnameId = null;
		for(Hostname i : hostnameList) {
			if(hostname.equals(i.getHostname())) {
				hostnameId = i.getId();
			}
		}
		
		if(hostnameId == null) {
			return removeChannelsForUnknow(channelList);
		}
		
		ArticleChannelKeyHostnameExample channelKeyHostnameExample = new ArticleChannelKeyHostnameExample();
		channelKeyHostnameExample.createCriteria().andIsDeleteEqualTo(false).andHostIdEqualTo(hostnameId);
		List<ArticleChannelKeyHostname> channelKeyHostnameList = articleChannelKeyHostnameMapper.selectByExample(channelKeyHostnameExample);
		List<Long> channelPassList = new ArrayList<Long>();
		List<Long> channelBanList = new ArrayList<Long>();
		
		for(ArticleChannelKeyHostname i : channelKeyHostnameList) {
			if(ArticleChannelKeyHostnameType.pass.getCode().equals(i.getKeyType())) {
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
		if(dto.getChannelId() == null || dto.getHostnameId() == null || dto.getArticleChannelKeyHostnameType() == null) {
			r.failWithMessage("参数异常");
			return r;
		}
		
		ArticleChannelKeyHostnameType channelKeyHostnameType = ArticleChannelKeyHostnameType.getType(dto.getArticleChannelKeyHostnameType());
		if(channelKeyHostnameType == null) {
			r.failWithMessage("参数异常");
			return r;
		}
		
		ArticleChannelKeyHostname channelKeyHostnamePO = null;
		channelKeyHostnamePO = new ArticleChannelKeyHostname();
		channelKeyHostnamePO.setChannelId(dto.getChannelId());
		channelKeyHostnamePO.setHostId(dto.getHostnameId());
		channelKeyHostnamePO.setKeyType(dto.getArticleChannelKeyHostnameType());
		int count = articleChannelKeyHostnameMapper.insertOrUpdate(channelKeyHostnamePO);
		if(count < 1) {
			r.failWithMessage("修改异常");
			return r;
		}
		
		r.setIsSuccess();
		return r;
	}
	
	private ArticleChannelKeyHostnameType findChannelKeyHostnameTypeByChannelIdAndHostnameId(List<ArticleChannelKeyHostname> channelKeyHostnamePOList, Long channelId, Integer hostnameId) {
		if(channelKeyHostnamePOList == null || channelKeyHostnamePOList.size() < 1 || channelId == null || hostnameId == null) {
			return ArticleChannelKeyHostnameType.ban;
		}
		
		for(ArticleChannelKeyHostname i : channelKeyHostnamePOList) {
			if(channelId.equals(i.getChannelId()) && hostnameId.equals(i.getHostId())) {
				return ArticleChannelKeyHostnameType.getType(i.getKeyType());
			}
		}
		
		return ArticleChannelKeyHostnameType.ban;
	}

}