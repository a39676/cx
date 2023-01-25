package demo.tool.openAI.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

@Service
public class OpenAiUtil {

	@Autowired
	private OpenAiOptionService optionService;

	private static final String MAIN_URL = "https://api.openai.com/v1";
	@SuppressWarnings("unused")
	private static final String ENGINES = "/engines";
	private static final String COMPLETIONS = "/completions ";

	public String sendMsg(String msg) {
//		TODO delete it
		optionService = buildOption();

		try {
			URL url = new URL(MAIN_URL 
//					+ ENGINES
//					+ "/text-davinci-003" 
					+ COMPLETIONS
					);

			JSONObject parameterJson = null;
			parameterJson = buildCompletionsParamJson(msg);

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

	private JSONObject buildCompletionsParamJson(String msg) {
//		{"model": "text-davinci-003", "prompt": "Say this is a test", "temperature": 0, "max_tokens": 7}
		JSONObject json = new JSONObject();
		json.put("model", "text-davinci-003");
		json.put("prompt", msg);
		json.put("temperature", 0);
		json.put("max_tokens", 7);
		return json;
	}

//	TODO delete it
	public static void main(String[] args) {

		String proxyHost = "127.0.0.1";
		String proxyPort = "10809";

		System.setProperty("http.proxyHost", proxyHost);
		System.setProperty("http.proxyPort", proxyPort);

		System.setProperty("https.proxyHost", proxyHost);
		System.setProperty("https.proxyPort", proxyPort);

		OpenAiUtil o = new OpenAiUtil();
		o.sendMsg("请翻译以下内容: This is a testing message");
	}

//	TODO for dev
	private OpenAiOptionService buildOption() {
		OpenAiOptionService o = new OpenAiOptionService();
		o.setApiKey("");
		return o;
	}
}
