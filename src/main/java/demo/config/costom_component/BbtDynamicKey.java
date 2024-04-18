package demo.config.costom_component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
	private static final int KEY_TOLERANCE_SECONDS = 3;

	public String createKey() {
		return createKey(LocalDateTime.now());
	}

	public String createKey(LocalDateTime time) {
		if (time == null) {
			time = LocalDateTime.now().plusSeconds(KEY_TOLERANCE_SECONDS);
		}
		String nowStr = localDateTimeHandler.dateToStr(time);
		String keyStr = KEY_FORMAT + nowStr;
		String keyEncode = passwordEncoder.encode(SALT, keyStr);
		redisOriginalConnectService.setValByName(keyStr, keyEncode, KEY_TOLERANCE_SECONDS * 2 + 1, TimeUnit.SECONDS);
		return keyEncode;
	}

	public boolean isCorrectKey(String encodeStr) {
		if (StringUtils.isBlank(encodeStr)) {
			log.error("Input incorrect key: " + encodeStr);
			return false;
		}

		LocalDateTime now = LocalDateTime.now();
		String nowStr = localDateTimeHandler.dateToStr(now);
		String keyStr = KEY_FORMAT + nowStr;
		String keyInRedis = redisOriginalConnectService.getValByName(keyStr);

		if (keyInRedis.equals(encodeStr)) {
			return true;
		}

		List<String> redisKeys = new ArrayList<>();
		for (int i = -3; i <= 3; i++) {
			nowStr = localDateTimeHandler.dateToStr(now.plusSeconds(i));
			keyStr = KEY_FORMAT + nowStr;
			redisKeys.add(keyStr);
		}
		List<String> encodeKeysInRedis = redisOriginalConnectService.getValuesByKeys(redisKeys);
		boolean flag = encodeKeysInRedis.contains(encodeStr);

		if (!flag) {
			log.error("Input incorrect key: " + encodeStr);
		}
		return flag;
	}

}
