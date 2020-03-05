package demo.test.service;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.base.user.pojo.po.Users;
import demo.base.user.service.AuthService;
import demo.baseCommon.service.CommonService;
import demo.config.costom_component.BaseUtilCustom;
import net.sf.json.JSONObject;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class TestService extends CommonService {

	@Autowired
	private BaseUtilCustom baseUtilCustom;

	private static String testKey = "testKey";
	private static String testValue = "testValue";

	public void redisSet() {
		Users u = new Users();
		u.setUserName(testValue);
		JSONObject j = JSONObject.fromObject(u);
		redisTemplate.opsForValue().set(testKey, j.toString());
	}

	public String redisGet() {
		String testV = redisTemplate.opsForValue().get(testKey);
		testV = redisTemplate.opsForValue().get(testKey);
		return testV;
	}

	public void roleGetTest() {
		System.out.println(baseUtilCustom.getRoles());
		System.out.println(baseUtilCustom.getCurrentUserName());
	}

	public void redisHashTest() {
		String key = "vcode";

		redisTemplate.opsForHash().put(key, "k1", "v1");
		redisTemplate.opsForHash().put(key, "k1", "v2");
		redisTemplate.opsForHash().put(key, "k3", "v3");
		redisTemplate.opsForHash().put(key, "k4", "v4");

		String v = String.valueOf(redisTemplate.opsForHash().get(key, "k1"));
		System.out.println(v);
		List<Object> r = redisTemplate.opsForHash().multiGet(key, Arrays.asList("k1", "k3", "k4"));
		System.out.println(r);
		r = redisTemplate.opsForHash().multiGet(key, Arrays.asList("k1", "k2", "k3", "k4"));
		System.out.println(r);

		redisTemplate.opsForHash().delete(key, "k3");
		r = redisTemplate.opsForHash().multiGet(key, Arrays.asList("k1", "k2", "k3", "k4"));
		System.out.println(r);
	}

	public void tess4jTest() {
		String testImgPath = "D:/auxiliary/tmp/01.gif";
		String dataPath = "D:/auxiliary/tmp/";
		
		File imageFile = new File(testImgPath);
		/**
		 * JNA Interface Mapping
		**/
		ITesseract instance = new Tesseract();
		
		/**
		 * You either set your own tessdata folder with your custom language pack or
		 * use LoadLibs to load the default tessdata folder for you.
		 **/
//		instance.setDatapath(LoadLibs.extractTessResources(dataPath).getParent());
		instance.setDatapath(dataPath);

		try {
			String result = instance.doOCR(imageFile);
			System.out.println(result);
		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public String testMyFindHostNameFromRequst(HttpServletRequest request) {
		return testFindHostNameFromRequst(request);
	}
	
	@Autowired
	private AuthService authService;
	
	public ModelAndView testAuthManagerView() {
		return authService.authManagerView(encryptId(10001L));
	}
	
	public static void main(String[] args) {
		TestService t = new TestService();
		t.tess4jTest();
	}
	
}
