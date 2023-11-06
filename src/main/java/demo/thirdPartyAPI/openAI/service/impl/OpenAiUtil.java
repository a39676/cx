package demo.thirdPartyAPI.openAI.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import ai.aiChat.pojo.dto.AiChatSendNewMsgFromApiDTO;
import demo.base.system.service.impl.SystemOptionService;
import demo.common.service.CommonService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import openAi.pojo.dto.OpenAiChatCompletionMessageDTO;
import openAi.pojo.dto.OpenAiChatCompletionResponseChoiceDTO;
import openAi.pojo.dto.OpenAiChatCompletionResponseDTO;
import openAi.pojo.dto.OpenAiChatCompletionResponseUsageDTO;
import openAi.pojo.result.OpenAiChatCompletionSendMessageResult;
import openAi.pojo.type.OpenAiChatCompletionFinishType;
import openAi.pojo.type.OpenAiChatCompletionMessageRoleType;
import openAi.pojo.type.OpenAiModelType;

@Service
public class OpenAiUtil extends CommonService {

	@Autowired
	private OpenAiOptionService optionService;
	@Autowired
	private SystemOptionService systemOptionService;

//	private static final String MAIN_URL = "https://api.openai.com/v1";
//	private static final String ENGINES = "https://api.openai.com/v1/engines";
	private static final String COMPLETIONS = "https://api.openai.com/v1/completions";
	private static final String MODELS = "https://api.openai.com/v1/models";
	private static final String CHAT_API = "https://api.openai.com/v1/chat/completions";

	public String NotFinishYet_sendCompletion(String msg) {
		try {
			URL url = new URI(COMPLETIONS).toURL();

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

	public OpenAiChatCompletionSendMessageResult sendChatCompletionFromUI(
			List<OpenAiChatCompletionMessageDTO> chatHistory) {
		return sendChatCompletionFromUI(chatHistory, null);
	}

	public OpenAiChatCompletionSendMessageResult sendChatCompletionFromUI(
			List<OpenAiChatCompletionMessageDTO> chatHistory, String msg) {
		OpenAiChatCompletionSendMessageResult r = new OpenAiChatCompletionSendMessageResult();
		if (chatHistory == null) {
			chatHistory = new ArrayList<>();
		}

		if (systemOptionService.isDev()) {
			return createFakeMessageResult(msg);
		}

		try {
			URL url = new URI(CHAT_API).toURL();

			JSONObject parameterJson = null;
			if (StringUtils.isNotBlank(msg)) {
				OpenAiChatCompletionMessageDTO newChatMsg = new OpenAiChatCompletionMessageDTO();
				newChatMsg.setRole(OpenAiChatCompletionMessageRoleType.USER.getName());
				newChatMsg.setContent(msg);
				chatHistory.add(newChatMsg);
			}

			parameterJson = buildChatCompletionsParamJson(chatHistory);

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

			OpenAiChatCompletionResponseDTO dto = new Gson().fromJson(response.toString(),
					OpenAiChatCompletionResponseDTO.class);
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

	public JSONObject sendChatCompletionFromApi(AiChatSendNewMsgFromApiDTO inputDTO) {
		JSONObject j = new JSONObject();

		if (systemOptionService.isDev()) {
			OpenAiChatCompletionSendMessageResult mockResult = createFakeMessageResult(
					"demo msg at: " + LocalDateTime.now());
			OpenAiChatCompletionResponseDTO mockMsg = mockResult.getDto();
			j = JSONObject.fromObject(mockMsg);
			return j;
		}

		AiChatSendNewMsgFromApiDTO newDTO = new AiChatSendNewMsgFromApiDTO();
		newDTO.setModel(OpenAiModelType.GPT_V_3_5.getName());
		newDTO.setMessages(inputDTO.getMessages());
		newDTO.setTemperature(inputDTO.getTemperature());
		newDTO.setTop_p(inputDTO.getTop_p());
		newDTO.setN(inputDTO.getN());
		newDTO.setStop(inputDTO.getStop());
		newDTO.setMax_tokens(inputDTO.getMax_tokens());
		newDTO.setPresence_penalty(inputDTO.getPresence_penalty());
		newDTO.setFrequency_penalty(inputDTO.getFrequency_penalty());
		newDTO.setLogit_bias(inputDTO.getLogit_bias());

		try {
			URL url = new URI(CHAT_API).toURL();

			JSONObject parameterJson = JSONObject.fromObject(newDTO);
			parameterJson.remove("apiKey");
			if (inputDTO.getTemperature() == null) {
				parameterJson.remove("temperature");
			}
			if (inputDTO.getTop_p() == null) {
				parameterJson.remove("top_p");
			}
			if (inputDTO.getN() == null) {
				parameterJson.remove("n");
			}
			if (inputDTO.getStop() == null) {
				parameterJson.remove("stop");
			}
			if (inputDTO.getMax_tokens() == null) {
				parameterJson.remove("max_tokens");
			}
			if (inputDTO.getPresence_penalty() == null) {
				parameterJson.remove("presence_penalty");
			}
			if (inputDTO.getFrequency_penalty() == null) {
				parameterJson.remove("frequency_penalty");
			}
			if (inputDTO.getLogit_bias() == null) {
				parameterJson.remove("logit_bias");
			}
			log.error("parameter json: " + parameterJson);

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
				log.error("OpenAI message from API request failed: " + response.toString());
			}
			in.close();
			j = JSONObject.fromObject(response.toString());

			return j;

		} catch (Exception e) {
			log.error("Open AI message from API error: " + e.getLocalizedMessage());
			e.printStackTrace();
			j.put("error", "Source API error error");
			return j;
		}
	}

	public String notFinishYet_queryModels() {
		try {
			URL url = new URI(MODELS).toURL();

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

	private JSONObject buildChatCompletionsParamJson(List<OpenAiChatCompletionMessageDTO> chatHistory) {
		/* Reference: https://platform.openai.com/docs/api-reference/chat/create */
		JSONObject parameterJson = new JSONObject();
		parameterJson.put("model", OpenAiModelType.GPT_V_3_5.getName());
		JSONArray messageArray = new JSONArray();
		JSONObject subChatMsg = null;
		for (OpenAiChatCompletionMessageDTO dto : chatHistory) {
			subChatMsg = JSONObject.fromObject(dto);
			messageArray.add(subChatMsg);
		}

		parameterJson.put("messages", messageArray);

		return parameterJson;
	}

	private OpenAiChatCompletionSendMessageResult createFakeMessageResult(String msg) {
		OpenAiChatCompletionSendMessageResult r = new OpenAiChatCompletionSendMessageResult();

		r.setIsSuccess();
		OpenAiChatCompletionResponseDTO dto = new OpenAiChatCompletionResponseDTO();
		OpenAiChatCompletionResponseChoiceDTO c = new OpenAiChatCompletionResponseChoiceDTO();
		c.setFinish_reason(OpenAiChatCompletionFinishType.LENGTH.getName());
		c.setIndex(0);
		OpenAiChatCompletionMessageDTO msgDTO = new OpenAiChatCompletionMessageDTO();
		msgDTO.setContent(msg + ", feedback");
		msgDTO.setRole(OpenAiChatCompletionMessageRoleType.ASSISTANT.getName());
		c.setMessage(msgDTO);
		dto.setChoices(Arrays.asList(c));
		dto.setCreated(1L);
		dto.setId("id");
		dto.setModel(OpenAiModelType.GPT_V_3_5.getName());
		OpenAiChatCompletionResponseUsageDTO usage = new OpenAiChatCompletionResponseUsageDTO();
		usage.setCompletion_tokens(1);
		usage.setPrompt_tokens(2);
		usage.setTotal_tokens(3);
		dto.setUsage(usage);
		dto.setCreated(new Date().getTime() / 1000);
		r.setDto(dto);
		return r;
	}

	public OpenAiChatCompletionSendMessageResult sendChatCompletionFromUIWithSdk(
			List<OpenAiChatCompletionMessageDTO> chatHistory, String msg, Integer maxToken) {
		OpenAiChatCompletionSendMessageResult r = new OpenAiChatCompletionSendMessageResult();
		if (chatHistory == null) {
			chatHistory = new ArrayList<>();
		}

		if (systemOptionService.isDev()) {
			return createFakeMessageResult(msg);
		}

		if (maxToken == null) {
			maxToken = optionService.getMaxTokens();
		}

		try {
			OpenAiService openAiService = new OpenAiService(optionService.getApiKey(), Duration.ofSeconds(10));

			List<ChatMessage> sdkMsgList = new ArrayList<>();
			for (OpenAiChatCompletionMessageDTO chat : chatHistory) {
				ChatMessage sdkDto = new ChatMessage();
				sdkDto.setContent(chat.getContent());
				sdkDto.setRole(chat.getRole());
				sdkMsgList.add(sdkDto);
			}

			if (StringUtils.isNotBlank(msg)) {
				ChatMessage sdkDto = new ChatMessage();
				sdkDto.setContent(msg);
				sdkDto.setRole(OpenAiChatCompletionMessageRoleType.USER.getName());
				sdkMsgList.add(sdkDto);
			}

			ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
					.model(OpenAiModelType.GPT_V_3_5.getName()).temperature(0.8).messages(sdkMsgList).n(1).build();

			ChatCompletionResult sdkResult = openAiService.createChatCompletion(chatCompletionRequest);

			OpenAiChatCompletionResponseDTO resultDto = new OpenAiChatCompletionResponseDTO();
			List<ChatCompletionChoice> sdkChoices = sdkResult.getChoices();
			ChatCompletionChoice sdkChoice = sdkChoices.get(0);
			OpenAiChatCompletionResponseChoiceDTO choice = new OpenAiChatCompletionResponseChoiceDTO();
			choice.setFinish_reason(sdkChoice.getFinishReason());
			choice.setIndex(sdkChoice.getIndex());
			ChatMessage sdkMsg = sdkChoice.getMessage();
			OpenAiChatCompletionMessageDTO dtoMsg = new OpenAiChatCompletionMessageDTO();
			dtoMsg.setContent(sdkMsg.getContent());
			dtoMsg.setRole(sdkMsg.getRole());
			choice.setMessage(dtoMsg);
			resultDto.addChoices(choice);
			resultDto.setCreated(resultDto.getCreated());
			resultDto.setId(resultDto.getId());
			resultDto.setModel(resultDto.getModel());
			resultDto.setObject(resultDto.getObject());
			OpenAiChatCompletionResponseUsageDTO sdkUsage = resultDto.getUsage();
			OpenAiChatCompletionResponseUsageDTO dtoUsage = new OpenAiChatCompletionResponseUsageDTO();
			dtoUsage.setCompletion_tokens(sdkUsage.getCompletion_tokens());
			dtoUsage.setPrompt_tokens(sdkUsage.getPrompt_tokens());
			dtoUsage.setTotal_tokens(sdkUsage.getTotal_tokens());
			resultDto.setUsage(dtoUsage);

			r.setDto(resultDto);
			r.setIsSuccess();
			return r;

		} catch (Exception e) {
			log.error("Open AI error: " + e.getLocalizedMessage());
			e.printStackTrace();
			r.setMessage("Open AI error: " + e.getLocalizedMessage());
		}

		return r;
	}

	public void sendChatCompletionFromUIWithSdk(AiChatSendNewMsgFromApiDTO inputDTO) {
		try {
			OpenAiService openAiService = new OpenAiService(optionService.getApiKey(), Duration.ofSeconds(10));

			List<ChatMessage> sdkMsgList = new ArrayList<>();
			for (OpenAiChatCompletionMessageDTO chat : inputDTO.getMessages()) {
				ChatMessage sdkDto = new ChatMessage();
				sdkDto.setContent(chat.getContent());
				sdkDto.setRole(chat.getRole());
				sdkMsgList.add(sdkDto);
			}

			ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
					.model(OpenAiModelType.GPT_V_3_5.getName()).temperature(inputDTO.getTemperature())
					.messages(sdkMsgList).topP(inputDTO.getTop_p()).frequencyPenalty(inputDTO.getFrequency_penalty())
					.presencePenalty(inputDTO.getPresence_penalty()).logitBias(inputDTO.getLogit_bias()).n(1).build();

			ChatCompletionResult sdkResult = openAiService.createChatCompletion(chatCompletionRequest);
			log.error(sdkResult.toString());

			OpenAiChatCompletionResponseDTO resultDto = new OpenAiChatCompletionResponseDTO();
			List<ChatCompletionChoice> sdkChoices = sdkResult.getChoices();
			ChatCompletionChoice sdkChoice = sdkChoices.get(0);
			OpenAiChatCompletionResponseChoiceDTO choice = new OpenAiChatCompletionResponseChoiceDTO();
			choice.setFinish_reason(sdkChoice.getFinishReason());
			choice.setIndex(sdkChoice.getIndex());
			ChatMessage sdkMsg = sdkChoice.getMessage();
			OpenAiChatCompletionMessageDTO dtoMsg = new OpenAiChatCompletionMessageDTO();
			dtoMsg.setContent(sdkMsg.getContent());
			dtoMsg.setRole(sdkMsg.getRole());
			choice.setMessage(dtoMsg);
			resultDto.addChoices(choice);
			resultDto.setCreated(resultDto.getCreated());
			resultDto.setId(resultDto.getId());
			resultDto.setModel(resultDto.getModel());
			resultDto.setObject(resultDto.getObject());
			OpenAiChatCompletionResponseUsageDTO sdkUsage = resultDto.getUsage();
			OpenAiChatCompletionResponseUsageDTO dtoUsage = new OpenAiChatCompletionResponseUsageDTO();
			dtoUsage.setCompletion_tokens(sdkUsage.getCompletion_tokens());
			dtoUsage.setPrompt_tokens(sdkUsage.getPrompt_tokens());
			dtoUsage.setTotal_tokens(sdkUsage.getTotal_tokens());
			resultDto.setUsage(dtoUsage);

		} catch (Exception e) {
			log.error("Open AI error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}

	}
}
