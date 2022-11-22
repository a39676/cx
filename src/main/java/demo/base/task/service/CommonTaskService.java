package demo.base.task.service;

import org.springframework.beans.factory.annotation.Autowired;

import demo.base.task.mq.producer.TaskInsertAckProducer;
import demo.common.service.CommonService;

public abstract class CommonTaskService extends CommonService {

	@Autowired
	protected TaskInsertAckProducer taskInsertAckProducer;
}
