package demo.thirdPartyAPI.openAI.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.thirdPartyAPI.openAI.mapper.OpenAiUserAmountHistoryMapper;
import demo.thirdPartyAPI.openAI.mapper.OpenAiUserAssociateWechatUidMapper;
import demo.thirdPartyAPI.openAI.mapper.OpenAiUserChatGptHistoryMapper;
import demo.thirdPartyAPI.openAI.mapper.OpenAiUserDetailMapper;
import demo.thirdPartyAPI.openAI.pojo.dto.SendChatingMessageFromWechatDTO;
import demo.thirdPartyAPI.openAI.pojo.po.OpenAiUserAmountHistory;
import demo.thirdPartyAPI.openAI.pojo.po.OpenAiUserAssociateWechatUidExample;
import demo.thirdPartyAPI.openAI.pojo.po.OpenAiUserAssociateWechatUidKey;
import demo.thirdPartyAPI.openAI.pojo.po.OpenAiUserDetail;
import demo.thirdPartyAPI.openAI.pojo.result.ChatResult;
import demo.thirdPartyAPI.openAI.pojo.type.OpenAiAmountType;
import demo.thirdPartyAPI.openAI.pojo.vo.OpenAiUserDetailVO;
import demo.thirdPartyAPI.openAI.service.OpenAiChatService;
import demo.thirdPartyAPI.wechat.pojo.po.WechatOidUid;
import demo.thirdPartyAPI.wechat.service.WechatUserService;

@Service
public class OpenAiChatServiceImpl extends OpenAiCommonService implements OpenAiChatService {

	@Autowired
	private OpenAiUserAmountHistoryMapper amountHistoryMapper;
	@Autowired
	private OpenAiUserAssociateWechatUidMapper openAiUserAssociateWechatUidMapper;
	@Autowired
	private OpenAiUserChatGptHistoryMapper chatGptHistoryMapper;
	@Autowired
	private OpenAiUserDetailMapper detailMapper;
	@Autowired
	private WechatUserService wechatUserService;
	
	public ChatResult sendNewChatFromWechat(SendChatingMessageFromWechatDTO dto) {
		ChatResult r = new ChatResult();
		
		/*
		 * TODO
		 * find detail
		 * check amount
		 * find history
		 * send msg
		 *   if msg handle success
		 *     debit
		 *     update history
		 *     feedback
		 *   else
		 *     error feedback
		 *     
		 */
		
		OpenAiUserDetail userDetail = findUserDetailByWechatUid(dto.getUid());
	}
	
	private OpenAiUserDetail findUserDetailByWechatUid(String uid) {
		/*
		 * TODO 
		 * 以 wechat uid 查找 openAiUserId 需要做缓存, 放置在 option service
		 * if 无对应用户
		 *   新建用户
		 * else
		 *   查找旧资料
		 * 如何判断前端是不是随便传一个 UID 骗取试用?
		 */
		
		WechatOidUid wechatUser = wechatUserService.findWechatUserByUid(uid);
		if(wechatUser == null) {
			/* 
			 * TODO
			 * create new user
			 * 
			 */
		}
		
		OpenAiUserAssociateWechatUidExample associateExample = new OpenAiUserAssociateWechatUidExample();
		associateExample.createCriteria().andWechatIdEqualTo(wechatUser.getId());
		List<OpenAiUserAssociateWechatUidKey> associateList = openAiUserAssociateWechatUidMapper.selectByExample(associateExample);
		if(associateList == null || associateList.isEmpty()) {
			/*
			 * TODO
			 * create new user ?
			 */
		}
		OpenAiUserAssociateWechatUidKey associate = associateList.get(0);
		
		OpenAiUserDetail openAiUserDetail = detailMapper.selectByPrimaryKey(associate.getOpenAiUserId());
		
		return openAiUserDetail;
	}
	
	@Override
	public OpenAiUserDetailVO findUserDetailVoByWechatUid(String uid) {
		OpenAiUserDetail detail = findUserDetailByWechatUid(uid);
		OpenAiUserDetailVO vo = new OpenAiUserDetailVO();
		vo.setUserPk(systemOptionService.encryptId(detail.getId()));
		vo.setBonusAmount(detail.getBonusAmount().doubleValue());
		vo.setRechargeAmount(detail.getRechargeAmount().doubleValue());
		return vo;
	}
	
	private CommonResult debitAmount(OpenAiUserDetail detail, BigDecimal debitAmount) {
		CommonResult r = new CommonResult();
		if(debitAmount.compareTo(BigDecimal.ZERO) < 1) {
			r.setMessage("输入消费额异常");
			return r;
		}
		
		BigDecimal totalAmount = detail.getBonusAmount().add(detail.getRechargeAmount());
		if(totalAmount.compareTo(debitAmount) < 0) {
			return notEnoughtAmount();
		}
		
		OpenAiUserAmountHistory newAmountHistory = null;
		BigDecimal restDebitAmount = new BigDecimal(debitAmount.doubleValue());

		if(detail.getBonusAmount().compareTo(BigDecimal.ZERO) > 0) {
			if(detail.getBonusAmount().compareTo(debitAmount) > -1) {
				restDebitAmount = debitAmount.subtract(detail.getBonusAmount());
				
				detail.setBonusAmount(detail.getBonusAmount().subtract(debitAmount));
				detailMapper.updateByPrimaryKeySelective(detail);
				
				newAmountHistory = new OpenAiUserAmountHistory();
				newAmountHistory.setId(snowFlake.getNextId());
				newAmountHistory.setAmountType(OpenAiAmountType.BONUS.getCode());
				newAmountHistory.setAmountChange(debitAmount);
				newAmountHistory.setUserId(detail.getId());
				amountHistoryMapper.insertSelective(newAmountHistory);
			}
		}
		
		if(restDebitAmount.doubleValue() <= 0) {
			r.setIsSuccess();
			return r;
		}
		
		detail.setRechargeAmount(detail.getRechargeAmount().subtract(restDebitAmount));
		detailMapper.updateByPrimaryKeySelective(detail);
		
		newAmountHistory = new OpenAiUserAmountHistory();
		newAmountHistory.setId(snowFlake.getNextId());
		newAmountHistory.setAmountType(OpenAiAmountType.RECHARGE.getCode());
		newAmountHistory.setAmountChange(debitAmount.subtract(detail.getBonusAmount()));
		newAmountHistory.setUserId(detail.getId());
		amountHistoryMapper.insertSelective(newAmountHistory);
		r.setIsSuccess();
		return r;
	}
}
