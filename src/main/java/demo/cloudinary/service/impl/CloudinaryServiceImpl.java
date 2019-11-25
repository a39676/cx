package demo.cloudinary.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;

import cloudinary.pojo.constant.CloudinaryConstant;
import cloudinary.pojo.result.CloudinaryUploadResult;
import cloudinary.service.CloudinaryFunction;
import cloudinary.util.CloudinaryCore;
import demo.base.system.service.impl.SystemConstantService;
import demo.baseCommon.service.CommonService;
import demo.cloudinary.service.CloudinaryService;

@Service
public class CloudinaryServiceImpl extends CommonService implements CloudinaryService {
	
	@Autowired
	private SystemConstantService constantService;
	
	@Autowired
	private CloudinaryFunction cloudinaryFunction;

	protected Cloudinary buildCloudinary() {
		Cloudinary cloudinary = new CloudinaryCore().buildCloudinary(
				constantService.getValByName(CloudinaryConstant.cloudinaryNameStoreKey),
				constantService.getValByName(CloudinaryConstant.cloudinaryApiKeyStoreKey), 
				constantService.getValByName(CloudinaryConstant.cloudinaryApiSecretStoreKey));
		return cloudinary;
	}
	
	@Override
	public CloudinaryUploadResult uploadCore(File f) {
		CloudinaryUploadResult result = null;
		
		if(f.length() > CloudinaryConstant.maxSize) {
			System.out.println(f.getName() + " is too large, size: " + f.length());
			result = new CloudinaryUploadResult();
			return result;
		}
		
		Cloudinary cloudinary = buildCloudinary();
		
		result = cloudinaryFunction.upload(cloudinary, f);
		
		return result;
	}
	
}
