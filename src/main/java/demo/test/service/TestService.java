package demo.test.service;

import java.util.List;

import org.springframework.stereotype.Service;

import demo.common.service.CommonService;

@Service
public class TestService extends CommonService {

	public List<String> getRoles() {
		return baseUtilCustom.getRoles();
	}
	
}
