package demo.tool.other.service;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

public interface QrCodeService {

	String generatorToBase64(HttpServletResponse response, String content, Integer width, Integer hight);

	List<String> decodeFromContent(String content);

	String decodeByImageUrl(String urlStr) throws IOException;

	void generator(HttpServletResponse response, String content, Integer width, Integer hight);


}
