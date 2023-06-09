package demo.thirdPartyAPI.imgbb.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import demo.thirdPartyAPI.imgbb.dto.result.ImgbbUploadResult;
import demo.tool.service.impl.ToolOptionService;
import toolPack.imgbb.pojo.dto.ImgbbUploadResponseDTO;
import toolPack.imgbb.service.ImgbbUtil;

@Service
public class ImgbbUploadService extends CommonService {

	@Autowired
	private ToolOptionService toolOptionService;
	@Autowired
	private ImgbbUtil imgbbUtil;

	public ImgbbUploadResult uploadImg(String imgInBase64Str, boolean saveForLongTime) {
		ImgbbUploadResult r = new ImgbbUploadResult();

		List<String> apiKeyList = toolOptionService.getImgbbApiKeyList();
		Random random = new Random();
		int randomIndex = random.nextInt(apiKeyList.size());
		String imgbbApiKey = apiKeyList.get(randomIndex);
		log.error("Will use " + randomIndex + " of " + apiKeyList.size() + " in key list");

		String filename = String.valueOf(snowFlake.getNextId());

		ImgbbUploadResponseDTO uploadResponse = imgbbUtil.uploadImg(imgbbApiKey, filename, imgInBase64Str,
				saveForLongTime);

		log.error("Upload image result: " + uploadResponse.toString());

		r.setResponseData(uploadResponse);
		r.setIsSuccess();

		return r;
	}

}
