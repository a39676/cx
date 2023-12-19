package demo.config.costom_component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.base.system.service.impl.RedisOriginalConnectService;
import demo.common.service.CommonService;

@Component
public class BbtDynamicKey extends CommonService {

	@Autowired
	private CustomPasswordEncoder passwordEncoder;
	@Autowired
	private RedisOriginalConnectService redisOriginalConnectService;

	private static final String SALT = "BBT";
	private static final String KEY_FORMAT = SALT + "_";

	public String createKey(LocalDateTime time) {
		if (time == null) {
			time = LocalDateTime.now().plusSeconds(3);
		}
		String nowStr = localDateTimeHandler.dateToStr(time);
		String keyStr = KEY_FORMAT + nowStr;
		String keyEncode = passwordEncoder.encode(SALT, keyStr);
		redisOriginalConnectService.setValByName(keyStr, keyEncode, 3, TimeUnit.SECONDS);
		return keyEncode;
	}

	public boolean isCorrectKey(String encodeStr) {
		if (StringUtils.isBlank(encodeStr)) {
			return false;
		}

		LocalDateTime now = LocalDateTime.now();
		String nowStr = localDateTimeHandler.dateToStr(now);
		String keyStr = KEY_FORMAT + nowStr;
		String keyInRedis = redisOriginalConnectService.getValByName(keyStr);

		if (keyInRedis.equals(encodeStr)) {
			return true;
		}

		for (int i = -3; i <= 3; i++) {
			nowStr = localDateTimeHandler.dateToStr(now.plusSeconds(i));
			keyStr = KEY_FORMAT + nowStr;
			keyInRedis = redisOriginalConnectService.getValByName(keyStr);
			if (keyInRedis.equals(encodeStr)) {
				return true;
			}
		}

		return false;
	}

}
