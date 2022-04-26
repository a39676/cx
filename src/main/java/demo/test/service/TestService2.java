package demo.test.service;

import java.util.List;

import org.springframework.stereotype.Service;

import demo.common.service.CommonService;

@Service
public class TestService2 extends CommonService {

	public List<String> getRoles() {
		return baseUtilCustom.getRoles();
	}
	
}
