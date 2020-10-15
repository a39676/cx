package demo.thirdPartyAPI.cloudinary.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;

import demo.common.service.CommonService;
import demo.thirdPartyAPI.cloudinary.service.CloudinaryService;
import toolPack.cloudinary.pojo.constant.CloudinaryConstant;
import toolPack.cloudinary.pojo.result.CloudinaryDeleteResult;
import toolPack.cloudinary.pojo.result.CloudinaryUploadResult;
import toolPack.cloudinary.service.CloudinaryFunction;
import toolPack.cloudinary.util.CloudinaryCore;

@Service
public class CloudinaryServiceImpl extends CommonService implements CloudinaryService {
	
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
		if(publicIds == null || publicIds.size() < 1 || publicIds.size() > CloudinaryConstant.deleteIdListMaxSize) {
			r = new CloudinaryDeleteResult();
			return r;
		}
		
		Cloudinary cloudinary = buildCloudinary();
		
		r = cloudinaryFunction.delete(cloudinary, publicIds);
		
		return r;
	}
	
}
