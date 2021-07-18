package demo.thirdPartyAPI.cloudinary.service.impl;

import org.springframework.stereotype.Service;

import demo.common.service.CommonService;

@Service
public class CloudinaryConstantService extends CommonService {

	private String cloudinaryNameStoreKey;
	private String cloudinaryApiKeyStoreKey;
	private String cloudinaryApiSecretStoreKey;
	
	public String getCloudinaryNameStoreKey() {
//		TODO building cloudinary constant service
//		CloudinaryConstant.cloudinaryApiKeyStoreKey
		return cloudinaryNameStoreKey;
	}
	
	public String getCloudinaryApiKeyStoreKey() {
//		TODO building cloudinary constant service
		return cloudinaryApiKeyStoreKey;
	}
	
	public String getCloudinaryApiSecretStoreKey() {
//		TODO building cloudinary constant service
		return cloudinaryApiSecretStoreKey;
	}
	
}
