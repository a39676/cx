package demo.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.mq.send.AckProducer;

@Service
public class TestService extends CommonService {

	@Autowired
	private AckProducer ackProducer;
	
	public void ackProducer(String content) {
		ackProducer.send(content);
	}
}
