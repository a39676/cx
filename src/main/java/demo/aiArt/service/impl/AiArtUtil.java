package demo.aiArt.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.aiArt.pojo.dto.TextToImageFromDTO;
import demo.aiArt.service.AiArtCommonService;
import net.sf.json.JSONObject;
import toolPack.httpHandel.HttpUtil;

@Scope("singleton")
@Service
public class AiArtUtil extends AiArtCommonService {

	public JSONObject sendTxtToImgRequest(TextToImageFromDTO dto) {
		JSONObject json = JSONObject.fromObject(
				"{\"enable_hr\": false,\"denoising_strength\": 0,\"firstphase_width\": 0,\"firstphase_height\": 0,\"hr_scale\": 2,\"hr_upscaler\": \"string\",\"hr_second_pass_steps\": 0,\"hr_resize_x\": 0,\"hr_resize_y\": 0,\"prompt\": \"\",\"styles\": [],\"seed\": -1,\"subseed\": -1,\"subseed_strength\": 0,\"seed_resize_from_h\": -1,\"seed_resize_from_w\": -1,\"sampler_name\": \"Euler a\",\"batch_size\": 1,\"n_iter\": 1,\"steps\": 20,\"cfg_scale\": 7,\"width\": 512,\"height\": 512,\"restore_faces\": false,\"tiling\": false,\"negative_prompt\": \"\",\"eta\": 0,\"s_churn\": 0,\"s_tmax\": 0,\"s_tmin\": 0,\"s_noise\": 1,\"override_settings\": {},\"override_settings_restore_afterwards\": true,\"script_args\": [],}");
		json.put("prompt", dto.getPrompts());
		json.put("negative_prompt", dto.getNegativePrompts());
		json.put("sampler_name", dto.getSampler());
		json.put("width", dto.getWedith());
		json.put("height", dto.getHeight());
		json.put("cfg_scale", dto.getCfgScale());
		json.put("steps", dto.getSteps());
		json.put("batch_size", dto.getBatchSize());
		json.put("seed", dto.getSeed());

		String uri = "/sdapi/v1/txt2img";
		HttpUtil h = new HttpUtil();
		String url = aiArtOptionService.getMainUrl() + uri;
		String response = null;
		try {
			response = h.sendPostRestful(url, json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("AI art text to image request error: " + e.getLocalizedMessage());
		}
		JSONObject responseJson = JSONObject.fromObject(response);
		return responseJson;
	}

}
