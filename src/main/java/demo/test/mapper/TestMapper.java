package demo.test.mapper;

import java.util.HashMap;
import java.util.List;

import demo.finance.cryptoCoin.data.pojo.dto.FindFirstReasonableDayDTO;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1day;

public interface TestMapper {
	
	Object test1();
	
	Object throwExpection();
	
	List<CryptoCoinPrice1day> findCryptoCoinStartTime(FindFirstReasonableDayDTO dto);
	
	List<HashMap<Object, Object>> findDuplicateDailyData();
	
	List<HashMap<Object, Object>> findDuplicateDailyDataInTheSameDay();
}