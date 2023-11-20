package demo.pmemo.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.article.service.impl.ArticleCommonService;
import demo.pmemo.pojo.constant.PMemoConstant;
import demo.pmemo.pojo.constant.PMemoView;
import demo.pmemo.pojo.dto.SetPMemoDTO;
import demo.pmemo.service.PMemoService;

@Service
public class PMemoServiceImpl extends ArticleCommonService implements PMemoService {

	@Override
	public ModelAndView setMemoPage() {
		ModelAndView view = new ModelAndView(PMemoView.createPMemo);
		view.addObject("defaultValidDateTime", localDateTimeHandler.dateToStr(LocalDateTime.now().plusMinutes(5)));
		return view;
	}
	
	@Override
	public String getMemo(String key) {
		if(StringUtils.isBlank(key)) {
			return "";
		}
		
		return redisOriginalConnectService.getValByName(PMemoConstant.P_MEMO_REDIS_KEY + key);
	}
	
	@Override
	public CommonResult setMemo(SetPMemoDTO dto) {
		CommonResult r = new CommonResult();
		
		if(StringUtils.isAnyBlank(dto.getContent(), dto.getRedisKeyValue())) {
			r.failWithMessage("null param");
			return r;
		}
		
		if(dto.getContent().length() > PMemoConstant.MEMO_MAX_SIZE) {
			r.failWithMessage("too large");
			return r;
		}
		
		Long validSeconds = null;
		try {
			LocalDateTime validDateTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getValidDateStr());
			validSeconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), validDateTime);
			if(validDateTime.isBefore(LocalDateTime.now())) {
				throw new Exception();
			}
		} catch (Exception e) {
			validSeconds = PMemoConstant.DEFAULT_VALID_SECONDS;
		}
		
		redisOriginalConnectService.setValByName(PMemoConstant.P_MEMO_REDIS_KEY + dto.getRedisKeyValue(), dto.getContent(), validSeconds, TimeUnit.SECONDS);
		
		r.setIsSuccess();
		return r;
	}
	
	public void cleanMemo() {
		/*
		 *  TODO 未加入 security url 鉴定权限 提供删除其他 memo? 
		 */
	}

}