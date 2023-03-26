package demo.aiChat.service.impl;

import java.io.File;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.aiChat.pojo.dto.AiChatUserMembershipDetailDTO;
import demo.common.service.CommonService;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class AiChatOptionService extends CommonService {

	@Value("${optionFilePath.aiChat}")
	private String optionFilePath;
	private String chatStorePrefixPath;
	private String payResultStorePrefixPath;
	private Integer chatHistorySaveCountingLimit = 100;
	private Integer dailySignUpBonus;
	private List<AiChatUserMembershipDetailDTO> membershipLDetails;

	public String getChatStorePrefixPath() {
		return chatStorePrefixPath;
	}

	public void setChatStorePrefixPath(String chatStorePrefixPath) {
		this.chatStorePrefixPath = chatStorePrefixPath;
	}

	public final String getPayResultStorePrefixPath() {
		return payResultStorePrefixPath;
	}

	public final void setPayResultStorePrefixPath(String payResultStorePrefixPath) {
		this.payResultStorePrefixPath = payResultStorePrefixPath;
	}

	public Integer getChatHistorySaveCountingLimit() {
		return chatHistorySaveCountingLimit;
	}

	public void setChatHistorySaveCountingLimit(Integer chatHistorySaveCountingLimit) {
		this.chatHistorySaveCountingLimit = chatHistorySaveCountingLimit;
	}

	public Integer getDailySignUpBonus() {
		return dailySignUpBonus;
	}

	public void setDailySignUpBonus(Integer dailySignUpBonus) {
		this.dailySignUpBonus = dailySignUpBonus;
	}

	@Override
	public String toString() {
		return "AiChatOptionService [chatStorePrefixPath=" + chatStorePrefixPath + ", payResultStorePrefixPath="
				+ payResultStorePrefixPath + ", chatHistorySaveCountingLimit=" + chatHistorySaveCountingLimit
				+ ", dailySignUpBonus=" + dailySignUpBonus + ", membershipLDetails=" + membershipLDetails + "]";
	}

	public List<AiChatUserMembershipDetailDTO> getMembershipLDetails() {
		return membershipLDetails;
	}

	public void setMembershipLDetails(List<AiChatUserMembershipDetailDTO> membershipLDetails) {
		this.membershipLDetails = membershipLDetails;
	}

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			AiChatOptionService tmp = new Gson().fromJson(jsonStr, AiChatOptionService.class);
			BeanUtils.copyProperties(tmp, this);
			log.error("AI chat option loaded");
		} catch (Exception e) {
			log.error("AI chat option loading error: " + e.getLocalizedMessage());
		}
	}
}
