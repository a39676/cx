package demo.tool.downoad.service;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;

public interface DownloadService {

	void downloadFileWithZip(HttpServletResponse response, String filePath) throws IOException;

}
