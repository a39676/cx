package demo.thirdPartyAPI.cloudFlare.service.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import demo.thirdPartyAPI.cloudFlare.service.CloudFlareService;
import net.sf.json.JSONObject;

@Service
public class CloudFlareServiceImpl extends CommonService implements CloudFlareService {

	@Autowired
	private CloudFlareOptionService optionService;

	@Override
	public boolean verify(String token) {
		String urlStr = "https://challenges.cloudflare.com/turnstile/v0/siteverify";

		Map<String, String> requestPropertyMap = new HashMap<>();
		requestPropertyMap.put("Content-Type", "application/json; charset=UTF-8");
		requestPropertyMap.put("Data-Type", "json; charset=UTF-8");

		HttpURLConnection con = null;
		StringBuilder response = new StringBuilder();

		JSONObject parameterJson = new JSONObject();
		parameterJson.put("secret", optionService.getServerKey());
		parameterJson.put("response", token);

		JSONObject responseJson = null;

		byte[] postData = parameterJson.toString().getBytes(StandardCharsets.UTF_8);

		try {

			URL myurl = new URI(urlStr).toURL();
			con = (HttpURLConnection) myurl.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");

			for (Entry<String, String> entry : requestPropertyMap.entrySet()) {
				con.setRequestProperty(entry.getKey(), entry.getValue());
			}

			if (postData != null) {
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.write(postData);
				wr.flush();
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;

			while ((line = in.readLine()) != null) {
				response.append(line);
				response.append(System.lineSeparator());
			}

			responseJson = JSONObject.fromObject(response.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (con != null) {
			con.disconnect();
		}

		return responseJson.containsKey("success") && "true".equals(String.valueOf(responseJson.get("success")));
	}
}
