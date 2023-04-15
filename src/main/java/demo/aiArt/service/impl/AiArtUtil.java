package demo.aiArt.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiArt.pojo.dto.TextToImageDTO;
import demo.aiArt.pojo.result.TextToImageFromApiResult;
import demo.aiArt.service.AiArtCommonService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import toolPack.httpHandel.HttpUtil;

@Scope("singleton")
@Service
public class AiArtUtil extends AiArtCommonService {

	public TextToImageFromApiResult sendTxtToImgRequest(TextToImageDTO dto) {
		TextToImageFromApiResult r = new TextToImageFromApiResult();
		JSONObject json = JSONObject.fromObject(
				"{\"enable_hr\": false,\"denoising_strength\": 0,\"firstphase_width\": 0,\"firstphase_height\": 0,\"hr_scale\": 2,\"hr_upscaler\": \"string\",\"hr_second_pass_steps\": 0,\"hr_resize_x\": 0,\"hr_resize_y\": 0,\"prompt\": \"\",\"styles\": [],\"seed\": -1,\"subseed\": -1,\"subseed_strength\": 0,\"seed_resize_from_h\": -1,\"seed_resize_from_w\": -1,\"sampler_name\": \"Euler a\",\"batch_size\": 1,\"n_iter\": 1,\"steps\": 20,\"cfg_scale\": 7,\"width\": 512,\"height\": 512,\"restore_faces\": false,\"tiling\": false,\"negative_prompt\": \"\",\"eta\": 0,\"s_churn\": 0,\"s_tmax\": 0,\"s_tmin\": 0,\"s_noise\": 1,\"override_settings\": {},\"override_settings_restore_afterwards\": true,\"script_args\": [],}");
		json.put("prompt", dto.getPrompts());
		json.put("negative_prompt", dto.getNegativePrompts());
		json.put("sampler_name", dto.getSampler());
		if (dto.getWedith() != null && dto.getWedith() > 0) {
			if (dto.getWedith() > aiArtOptionService.getMaxHeight()) {
				dto.setWedith(aiArtOptionService.getMaxHeight());
			}
			json.put("width", dto.getWedith());
		}
		if (dto.getHeight() != null && dto.getHeight() > 0) {
			if (dto.getHeight() > aiArtOptionService.getMaxWidth()) {
				dto.setHeight(aiArtOptionService.getMaxWidth());
			}
			json.put("height", dto.getHeight());
		}
		if (dto.getCfgScale() != null && dto.getCfgScale() > 0) {
			if (dto.getCfgScale() > aiArtOptionService.getMaxBatch()) {
				dto.setCfgScale(aiArtOptionService.getMaxBatch());
			}
			json.put("cfg_scale", dto.getCfgScale());
		}
		if (dto.getSteps() != null && dto.getSteps() > 0) {
			if (dto.getSteps() > aiArtOptionService.getMaxCfgScale()) {
				dto.setSteps(aiArtOptionService.getMaxCfgScale());
			}
			json.put("steps", dto.getSteps());
		}
		if (dto.getBatchSize() != null) {
			if (dto.getBatchSize() > aiArtOptionService.getMaxSteps()) {
				dto.setBatchSize(aiArtOptionService.getMaxSteps());
			}
			if (dto.getBatchSize() < 0) {
				dto.setBatchSize(1);
			}
			json.put("batch_size", dto.getBatchSize());
		}
		if (dto.getSeed() != null) {
			json.put("seed", dto.getSeed());
		}

		String uri = "/sdapi/v1/txt2img";
		HttpUtil h = new HttpUtil();
		String url = aiArtOptionService.getMainUrl() + uri;
		String response = null;
		try {
			response = h.sendPostRestful(url, json.toString());
		} catch (Exception e) {
			log.error("AI art text to image request error: " + e.getLocalizedMessage());
		}
		JSONObject responseJson = JSONObject.fromObject(response);
		JSONArray imageListInBase64 = responseJson.getJSONArray("images");

		r.setIsSuccess();
		List<String> imageList = new ArrayList<>();
		for (int i = 0; i < imageListInBase64.size(); i++) {
			imageList.add(imageListInBase64.getString(i));
			CommonResult saveImgResult = saveImg(imageListInBase64.getString(i));
			if(saveImgResult.isFail()) {
//				TODO
			}
		}
		r.setImageList(imageList);
		r.setIsSuccess();
		return r;

	}

	private CommonResult saveImg(String data) {
		CommonResult r = new CommonResult();
		byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(data);
		BufferedImage img;
		try {
			img = ImageIO.read(new ByteArrayInputStream(imageBytes));
		} catch (IOException e) {
			r.setMessage(e.getLocalizedMessage());
			return r;
		}

		File outputfile = new File(aiArtOptionService.getImageSavingFolder() + "/" + snowFlake.getNextId() + ".jpg");
		try {
			ImageIO.write(img, "jpg", outputfile);
		} catch (IOException e) {
			r.setMessage(e.getLocalizedMessage());
			return r;
		}

		r.setIsSuccess();
		r.setMessage(outputfile.getAbsolutePath());
		return r;
	}
}
