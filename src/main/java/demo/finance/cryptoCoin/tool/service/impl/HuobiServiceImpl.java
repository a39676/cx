//package demo.finance.cryptoCoin.tool.service.impl;
//
//import java.nio.charset.StandardCharsets;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.time.Instant;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.util.Base64;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Service;
//
//import com.huobi.service.huobi.signature.UrlParamsBuilder;
//
//import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
//import demo.finance.cryptoCoin.tool.service.HuobiService;
//
//@Service
//public class HuobiServiceImpl extends CryptoCoinCommonService implements HuobiService {
//
//	private static final String op = "op";
//	private static final String opValue = "auth";
//	private static final String ACCESS_KEY_NAME = "accessKey";
//	private static final String SIGNATURE_METHOD_NAME = "signatureMethod";
//	private static final String SIGNATURE_METHOD_VALUE = "HmacSHA256";
//	private static final String SIGNATURE_VERSION_NAME = "signatureVersion";
//	private static final String SIGNATURE_VERSION_VALUE = "2.1";
//	private static final String TIMESTAMP_NAME = "timestamp";
//	private static final String SIGNATURE_NAME = "signature";
//
//	private static final DateTimeFormatter DT_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss");
//	private static final ZoneId ZONE_GMT = ZoneId.of("Z");
//	
//	public void createSignature(String accessKey, String secretKey, String method, String host, String uri,
//			UrlParamsBuilder builder) {
//		StringBuilder sb = new StringBuilder(1024);
//
//		if (StringUtils.isAnyBlank(accessKey, secretKey)) {
//			throw new Exception("API key and secret key are required");
//		}
//
//		sb.append(method.toUpperCase()).append('\n').append(host.toLowerCase()).append('\n').append(uri).append('\n');
//
//		builder.putToUrl(ACCESS_KEY_NAME, accessKey).putToUrl(SIGNATURE_VERSION_NAME, SIGNATURE_VERSION_VALUE)
//				.putToUrl(SIGNATURE_METHOD_NAME, SIGNATURE_METHOD_VALUE).putToUrl(TIMESTAMP_NAME, gmtNow());
//
//		sb.append(builder.buildSignature());
//		Mac hmacSha256;
//		try {
//			hmacSha256 = Mac.getInstance(SIGNATURE_METHOD_VALUE);
//			SecretKeySpec secKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),
//					SIGNATURE_METHOD_VALUE);
//			hmacSha256.init(secKey);
//		} catch (NoSuchAlgorithmException e) {
//			throw new Exception("[Signature] No such algorithm: " + e.getMessage());
//		} catch (InvalidKeyException e) {
//			throw new Exception("[Signature] Invalid key: " + e.getMessage());
//		}
//		String payload = sb.toString();
//		byte[] hash = hmacSha256.doFinal(payload.getBytes(StandardCharsets.UTF_8));
//
//		String actualSign = Base64.getEncoder().encodeToString(hash);
//
//		builder.putToUrl(SIGNATURE_NAME, actualSign);
//
//	}
//
//	private static long epochNow() {
//		return Instant.now().getEpochSecond();
//	}
//
//	private static String gmtNow() {
//		return Instant.ofEpochSecond(epochNow()).atZone(ZONE_GMT).format(DT_FORMAT);
//	}
//}
/*
所见过最烂, 没有之一的SDK, 迁移难度巨大, 暂搁置
*/