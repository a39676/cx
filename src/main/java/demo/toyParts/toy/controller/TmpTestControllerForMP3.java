package demo.toyParts.toy.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/mp3Testing")
public class TmpTestControllerForMP3 {

	@GetMapping(value = "/mp3Testing")
	public void getImage(HttpServletResponse response) {
		
		try {
			File f = new File("/home/u2/tmp/test.mp3");
			InputStream in = new FileInputStream(f);
			response.setContentType("Content-Type: audio/mpeg");
			IOUtils.copy(in, response.getOutputStream());
		} catch (Exception e) {
			return;
		}
	}
}
