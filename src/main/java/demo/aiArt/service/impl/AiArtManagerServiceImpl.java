package demo.aiArt.service.impl;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import ai.aiArt.pojo.result.AiArtGenerateImageResult;
import ai.aiArt.pojo.result.GetJobResultList;
import ai.aiArt.pojo.type.AiArtJobStatusType;
import ai.aiArt.pojo.vo.AiArtGenerateImageVO;
import auxiliaryCommon.pojo.dto.BasePkDTO;
import auxiliaryCommon.pojo.dto.BaseStrDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiArt.pojo.po.AiArtTextToImageJobRecord;
import demo.aiArt.service.AiArtCommonService;
import demo.aiArt.service.AiArtManagerService;
import demo.aiArt.service.AiArtService;
import demo.image.service.ImageService;

@Service
public class AiArtManagerServiceImpl extends AiArtCommonService implements AiArtManagerService {

	@Autowired
	private AiArtService aiArtService;
	@Autowired
	private ImageService imageService;

	@Override
	public ModelAndView getManagerView() {
		ModelAndView v = new ModelAndView("aiArtJSP/aiArtManager");
		v.addObject("isRunning", aiArtOptionService.getIsRunning());
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
		return r;
	}

	@Override
	public CommonResult setStopColab() {
		aiArtOptionService.setMainUrl(null);
		aiArtOptionService.setIsRunning(false);
		CommonResult r = new CommonResult();
		r.setIsSuccess();
		return r;
	}

	@Override
	public GetJobResultList getAiArtJobList(BasePkDTO dto) {
		GetJobResultList r = new GetJobResultList();
		List<AiArtTextToImageJobRecord> jobPoList = aiArtService.__getJobResultPage(dto.getPk());
		if (jobPoList.isEmpty()) {
			r.setIsSuccess();
			return r;
		}
		AiArtGenerateImageResult jobResult = null;
		List<AiArtGenerateImageVO> voList = new ArrayList<>();
		for (AiArtTextToImageJobRecord po : jobPoList) {
			if (AiArtJobStatusType.SUCCESS.getCode().equals(po.getJobStatus().intValue())) {
				jobResult = getJobResult(po.getId());
			}
			voList.add(buildAiArtGenerateImageVO(po, jobResult, systemOptionService.encryptId(po.getId())));
			jobResult = null;
		}
		r.setJobResultList(voList);
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult setImgInvalid(BasePkDTO dto) {
		if (StringUtils.isBlank(dto.getPk())) {
			return new CommonResult();
		}
		Long imgId = systemOptionService.decryptPrivateKey(URLDecoder.decode(dto.getPk(), StandardCharsets.UTF_8));
		if (imgId == null) {
			return new CommonResult();
		}
		imageService.setImageInvalidAndWaitingDelete(imgId);
		CommonResult r = new CommonResult();
		r.setIsSuccess();
		return r;
	}
}
