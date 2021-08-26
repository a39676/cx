package demo.finance.cryptoCoin.tool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.automationTest.mq.producer.TestEventInsertAckProducer;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.tool.service.CryptoCoinDataQueryService;
import finance.cryptoCoin.pojo.dto.CryptoCoinDailyDataQueryDTO;
import net.sf.json.JSONObject;

@Service
public class CryptoCoinDataQueryServiceImpl extends CryptoCoinCommonService implements CryptoCoinDataQueryService {

	@Autowired
	private TestEventInsertAckProducer testEventInsertAckProducer;
	
	@Override
	public void insertSearchingDemoTestEvent(CryptoCoinDailyDataQueryDTO dto) {

		/*
		 * TODO 未解决参数填充问题
		 * 一次发送大量币种? 需要增加队列消息存活时间
		 * bbt 服务新增逻辑: 同一个任务接收多次, 且因异常而执行失败时, 直接返回执行失败, 防止MQ队列拥堵
		 */
		
		try {
			AutomationTestInsertEventDTO insertEventDTO = new AutomationTestInsertEventDTO();
			insertEventDTO.setTestEventId(snowFlake.getNextId());
			insertEventDTO.setTestModuleType(TestModuleType.scheduleClawing.getId());
			insertEventDTO.setFlowType(ScheduleClawingType.CRYPTO_COIN_DAILY_DATA.getId());
			JSONObject paramJson = new JSONObject();
			paramJson.put(dto.getClass().getSimpleName(), JSONObject.fromObject(dto).toString());
			insertEventDTO.setParamStr(paramJson.toString());
			testEventInsertAckProducer.send(insertEventDTO);

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}

	}
}
