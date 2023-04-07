package demo.thirdPartyAPI.openAI.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.base.system.service.impl.SystemOptionService;
import demo.common.service.CommonService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import openAi.pojo.dto.OpanAiChatCompletionMessageDTO;
import openAi.pojo.dto.OpanAiChatCompletionResponseChoiceDTO;
import openAi.pojo.dto.OpanAiChatCompletionResponseDTO;
import openAi.pojo.dto.OpanAiChatCompletionResponseUsageDTO;
import openAi.pojo.result.OpenAiChatCompletionSendMessageResult;
import openAi.pojo.type.OpenAiChatCompletionFinishType;
import openAi.pojo.type.OpenAiChatCompletionMessageRoleType;
import openAi.pojo.type.OpenAiModelType;

@Service
public class OpenAiUtil extends CommonService {

	@Autowired
	private OpenAiOptionService optionService;
	@SuppressWarnings("unused")
	@Autowired
	private SystemOptionService systemOptionService;

//	private static final String MAIN_URL = "https://api.openai.com/v1";
//	private static final String ENGINES = "https://api.openai.com/v1/engines";
	private static final String COMPLETIONS = "https://api.openai.com/v1/completions";
	private static final String MODELS = "https://api.openai.com/v1/models";
	private static final String CHAT_API = "https://api.openai.com/v1/chat/completions";

	public String NotFinishYet_sendCompletion(String msg) {
		try {
			URL url = new URL(COMPLETIONS);

			JSONObject parameterJson = null;
			parameterJson = buildCompletionsParamJson(msg, optionService.getMaxTokens());

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Authorization", "Bearer " + optionService.getApiKey());
			con.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
			writer.write(parameterJson.toString());
			writer.flush();
			writer.close();
			con.getOutputStream().close();

			int responseCode = con.getResponseCode();

			System.out.println("POST Response Code :: " + responseCode);
			if (responseCode != HttpURLConnection.HTTP_OK) { // success
				System.out.println("POST request did not work.");
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

//		TODO
		return "";
	}

	public OpenAiChatCompletionSendMessageResult sendChatCompletion(List<OpanAiChatCompletionMessageDTO> chatHistory,
			String msg) {
		return sendChatCompletion(chatHistory, msg, optionService.getMaxTokens());
	}

	public OpenAiChatCompletionSendMessageResult sendChatCompletion(String msg) {
		return sendChatCompletion(null, msg, optionService.getMaxTokens());
	}

	public OpenAiChatCompletionSendMessageResult sendChatCompletion(List<OpanAiChatCompletionMessageDTO> chatHistory,
			Integer maxToken) {
		return sendChatCompletion(chatHistory, null, maxToken);
	}

	public OpenAiChatCompletionSendMessageResult sendChatCompletion(List<OpanAiChatCompletionMessageDTO> chatHistory,
			String msg, Integer maxToken) {
		OpenAiChatCompletionSendMessageResult r = new OpenAiChatCompletionSendMessageResult();
		if (chatHistory == null) {
			chatHistory = new ArrayList<>();
		}

//		TODO
//		if (systemOptionService.isDev()) {
//			return createFakeMessageResult(msg);
//		}

		if (maxToken == null) {
			maxToken = optionService.getMaxTokens();
		}

		try {
			URL url = new URL(CHAT_API);

			JSONObject parameterJson = null;
			if (StringUtils.isNotBlank(msg)) {
				OpanAiChatCompletionMessageDTO newChatMsg = new OpanAiChatCompletionMessageDTO();
				newChatMsg.setRole(OpenAiChatCompletionMessageRoleType.USER.getName());
				newChatMsg.setContent(msg);
				chatHistory.add(newChatMsg);
			}

			parameterJson = buildChatCompletionsParamJson(chatHistory, maxToken);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Authorization", "Bearer " + optionService.getApiKey());
			con.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
			writer.write(parameterJson.toString());
			writer.flush();
			writer.close();
			con.getOutputStream().close();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			int responseCode = con.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				log.error("OpenAI request failed: " + response.toString());
				r.setMessage("Http : " + responseCode);
				return r;
			}
			in.close();

			OpanAiChatCompletionResponseDTO dto = new Gson().fromJson(response.toString(),
					OpanAiChatCompletionResponseDTO.class);
			r.setDto(dto);
			r.setIsSuccess();
			return r;

		} catch (Exception e) {
			log.error("Open AI error: " + e.getLocalizedMessage());
			e.printStackTrace();
			r.setMessage("Open AI error: " + e.getLocalizedMessage());
		}

		return r;
	}

	public String notFinishYet_queryModels() {
		try {
			URL url = new URL(MODELS);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
//			con.setRequestProperty("Content-Type", "application/json");
//			con.setRequestProperty("organization", optionService.getOrgId());
			con.setRequestProperty("Authorization", "Bearer " + optionService.getApiKey());
			con.setDoOutput(true);
//			OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
//			writer.write("");
//			writer.flush();
//			writer.close();
			con.getOutputStream().close();

			int responseCode = con.getResponseCode();

			System.out.println("GET Response Code :: " + responseCode);
			if (responseCode != HttpURLConnection.HTTP_OK) { // success
				System.out.println("GET request did not work.");
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			System.out.println(response.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

//		TODO
		return "";
	}

	private JSONObject buildCompletionsParamJson(String msg, Integer maxToken) {
//		{"model": "text-davinci-003", "prompt": "Say this is a test", "temperature": 0, "max_tokens": 7}
		JSONObject json = new JSONObject();
		json.put("model", OpenAiModelType.DAVINCI_003.getName());
		json.put("prompt", msg);
		json.put("max_tokens", maxToken);
		json.put("temperature", 0);
		return json;
	}

	private JSONObject buildChatCompletionsParamJson(List<OpanAiChatCompletionMessageDTO> chatHistory,
			Integer maxToken) {
		/* Reference: https://platform.openai.com/docs/api-reference/chat/create */
		JSONObject parameterJson = new JSONObject();
		parameterJson.put("model", OpenAiModelType.GPT_V_3_5.getName());
		JSONArray messageArray = new JSONArray();
		JSONObject subChatMsg = null;
		for (OpanAiChatCompletionMessageDTO dto : chatHistory) {
			subChatMsg = JSONObject.fromObject(dto);
			messageArray.add(subChatMsg);
		}

		parameterJson.put("messages", messageArray);

		parameterJson.put("max_tokens", maxToken);
		return parameterJson;
	}

	@SuppressWarnings("unused")
	private OpenAiChatCompletionSendMessageResult createFakeMessageResult(String msg) {
		OpenAiChatCompletionSendMessageResult r = new OpenAiChatCompletionSendMessageResult();

		r.setIsSuccess();
		OpanAiChatCompletionResponseDTO dto = new OpanAiChatCompletionResponseDTO();
		OpanAiChatCompletionResponseChoiceDTO c = new OpanAiChatCompletionResponseChoiceDTO();
		c.setFinish_reason(OpenAiChatCompletionFinishType.LENGTH.getName());
		c.setIndex(0);
		OpanAiChatCompletionMessageDTO msgDTO = new OpanAiChatCompletionMessageDTO();
		msgDTO.setContent(msg + ", feedback");
		msgDTO.setRole(OpenAiChatCompletionMessageRoleType.ASSISTANT.getName());
		c.setMessage(msgDTO);
		dto.setChoices(Arrays.asList(c));
		dto.setCreated(1L);
		dto.setId("id");
		dto.setModel(OpenAiModelType.GPT_V_3_5.getName());
		OpanAiChatCompletionResponseUsageDTO usage = new OpanAiChatCompletionResponseUsageDTO();
		usage.setCompletion_tokens(1);
		usage.setPrompt_tokens(2);
		usage.setTotal_tokens(3);
		dto.setUsage(usage);
		r.setDto(dto);
		return r;
	}

}
