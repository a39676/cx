package demo.aiArt.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.dto.BaseStrDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiArt.service.AiArtCommonService;
import demo.aiArt.service.AiArtManagerService;

@Service
public class AiArtManagerServiceImpl extends AiArtCommonService implements AiArtManagerService {

	@Override
	public ModelAndView getManagerView() {
		ModelAndView v = new ModelAndView("aiArtJSP/aiArtManager");
		return v;
	}

	@Override
	public CommonResult setRunningColab(BaseStrDTO dto) {
		if (dto == null || StringUtils.isBlank(dto.getStr())) {
			return new CommonResult();
		}
		aiArtOptionService.setMainUrl(dto.getStr());
		aiArtOptionService.setIsRunning(true);

		CommonResult r = new CommonResult();
		r.setIsSuccess();
		System.out.println(aiArtOptionService);
		return r;
	}

	@Override
	public CommonResult setStopColab() {
		aiArtOptionService.setMainUrl(null);
		aiArtOptionService.setIsRunning(false);
		CommonResult r = new CommonResult();
		r.setIsSuccess();
		System.out.println(aiArtOptionService);
		return r;
	}
}
