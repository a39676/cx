package demo.base.system.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import demo.base.system.service.AuxiliaryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuxiliaryServiceImpl extends SystemCommonService implements AuxiliaryService {

	@Override
	public void robotHandle(HttpServletRequest request, HttpServletResponse response) {
		visitDataService.insertVisitData(request);
		
		Resource resource = new ClassPathResource("/static_resources/txt/robots.txt");
		
		String mimeType= "application/text/plain";
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + resource.getFilename() +"\""));
		try {
			response.setContentLength((int)resource.contentLength());
			InputStream is = resource.getInputStream();
			FileCopyUtils.copy(is, response.getOutputStream());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
