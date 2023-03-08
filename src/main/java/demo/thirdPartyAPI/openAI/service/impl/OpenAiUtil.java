package demo.thirdPartyAPI.openAI.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.thirdPartyAPI.openAI.pojo.dto.OpanAiChatCompletionMessageDTO;
import demo.thirdPartyAPI.openAI.pojo.dto.OpanAiChatCompletionResponseDTO;
import demo.thirdPartyAPI.openAI.pojo.result.OpenAiChatCompletionSendMessageResult;
import demo.thirdPartyAPI.openAI.pojo.type.OpenAiChatCompletionMessageRoleType;
import demo.thirdPartyAPI.openAI.pojo.type.OpenAiModelType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class OpenAiUtil {

	@Autowired
	private OpenAiOptionService optionService;

	private static final String MAIN_URL = "https://api.openai.com/v1";
	@SuppressWarnings("unused")
	private static final String ENGINES = "/engines";
	private static final String CHAT = "/chat";
	private static final String COMPLETIONS = "/completions";
	private static final String MODELS = "/models";

	public String NotFinishYet_sendCompletion(String msg) {
		try {
			URL url = new URL(MAIN_URL + COMPLETIONS);

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

	private OpenAiChatCompletionSendMessageResult sendChatCompletion(List<OpanAiChatCompletionMessageDTO> chatHistory,
			String msg, Integer maxToken) {
		OpenAiChatCompletionSendMessageResult r = new OpenAiChatCompletionSendMessageResult();
		if (chatHistory == null) {
			chatHistory = new ArrayList<>();
		}

		try {
			URL url = new URL(MAIN_URL + CHAT + COMPLETIONS);

			JSONObject parameterJson = null;
			OpanAiChatCompletionMessageDTO newChatMsg = new OpanAiChatCompletionMessageDTO();
			newChatMsg.setRole(OpenAiChatCompletionMessageRoleType.USER.getName());
			newChatMsg.setContent(msg);

			parameterJson = buildChatCompletionsParamJson(chatHistory, newChatMsg, maxToken);

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

			if (responseCode != HttpURLConnection.HTTP_OK) {
				r.setMessage("Http : " + responseCode);
				return r;
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			OpanAiChatCompletionResponseDTO dto = new Gson().fromJson(response.toString(),
					OpanAiChatCompletionResponseDTO.class);
			r.setDto(dto);
			r.setIsSuccess();
			return r;

		} catch (Exception e) {
			r.setMessage("Open AI error: " + e.getLocalizedMessage());
		}

		return r;
	}

	public String notFinishYet_queryModels() {
		try {
			URL url = new URL(MAIN_URL + MODELS);

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
			OpanAiChatCompletionMessageDTO newChatMessage, Integer maxToken) {
		/* Reference: https://platform.openai.com/docs/api-reference/chat/create */
		JSONObject parameterJson = new JSONObject();
		parameterJson.put("model", OpenAiModelType.GPT_V_3_5.getName());
		JSONArray messageArray = new JSONArray();
		JSONObject subChatMsg = null;
		for (OpanAiChatCompletionMessageDTO dto : chatHistory) {
			subChatMsg = new JSONObject();
			subChatMsg.put("role", dto.getRole());
			subChatMsg.put("content", dto.getContent());
			messageArray.add(subChatMsg);
		}

		subChatMsg = new JSONObject();
		subChatMsg.put("role", newChatMessage.getRole());
		subChatMsg.put("content", newChatMessage.getContent());
		messageArray.add(subChatMsg);

		parameterJson.put("messages", messageArray);

		parameterJson.put("max_tokens", maxToken);
		return parameterJson;
	}

}
