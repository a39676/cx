package demo.cloudinary.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;

import cloudinary.pojo.constant.CloudinaryConstant;
import cloudinary.pojo.result.CloudinaryUploadResult;
import cloudinary.util.CloudinaryCore;
import demo.base.system.service.impl.SystemConstantService;
import demo.baseCommon.service.CommonService;
import demo.cloudinary.service.CloudinaryService;

@Service
public class CloudinaryServiceImpl extends CommonService implements CloudinaryService {
	
	@Autowired
	private SystemConstantService constantService;
	@Autowired
	private CloudinaryCore cloudinaryCore;

	@Override
	public CloudinaryUploadResult uploadCore(File f) {
		CloudinaryUploadResult result = null;
		Cloudinary cloudinary = buildCloudinary();
		
		if(f.length() > CloudinaryConstant.maxSize) {
			System.out.println(f.getName() + " is too large, size: " + f.length());
			result = new CloudinaryUploadResult();
			return result;
		}
		
		result = cloudinaryCore.upload(cloudinary, f);
		
		return result;
	}
	
	private Cloudinary buildCloudinary() {
		return cloudinaryCore.buildCloudinary(
				constantService.getValByName(CloudinaryConstant.cloudinaryNameStoreKey),
				constantService.getValByName(CloudinaryConstant.cloudinaryApiKeyStoreKey), 
				constantService.getValByName(CloudinaryConstant.cloudinaryApiSecretStoreKey));
	}
	
}
