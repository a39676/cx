package demo.cloudinary.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;

import cloudinary.pojo.constant.CloudinaryConstant;
import cloudinary.pojo.result.CloudinaryDeleteResult;
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
	
	@Override
	public CloudinaryDeleteResult delete(String publicId) {
		CloudinaryDeleteResult r = null;
		if(StringUtils.isBlank(publicId)) {
			r = new CloudinaryDeleteResult();
			return r;
		}
		
		Cloudinary cloudinary = buildCloudinary();
		
		r = cloudinaryFunction.delete(cloudinary, publicId);
		
		return r;
	}
	
	@Override
	public CloudinaryDeleteResult delete(List<String> publicIds) {
		CloudinaryDeleteResult r = null;
		if(publicIds == null || publicIds.size() < 1) {
			r = new CloudinaryDeleteResult();
			return r;
		}
		
		Cloudinary cloudinary = buildCloudinary();
		
		r = cloudinaryFunction.delete(cloudinary, publicIds);
		
		return r;
	}
	
}
