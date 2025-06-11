package demo.test.service;

import org.springframework.stereotype.Service;

import demo.common.service.CommonService;

@Service
public class TestService extends CommonService {

	public String test() {
		return "Test";
	}
}
