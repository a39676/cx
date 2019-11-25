package demo.cloudinary.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;

import cloudinary.pojo.constant.CloudinaryConstant;
import cloudinary.pojo.result.CloudinaryUploadResult;
import cloudinary.service.CloudinaryFunction;
import demo.baseCommon.service.CommonService;
import demo.cloudinary.service.CloudinaryService;

@Service
public class CloudinaryServiceImpl extends CommonService implements CloudinaryService {
	
	@Autowired
	private Cloudinary cloudinary;
	@Autowired
	private CloudinaryFunction cloudinaryFunction;

	@Override
	public CloudinaryUploadResult uploadCore(File f) {
		CloudinaryUploadResult result = null;
		
		if(f.length() > CloudinaryConstant.maxSize) {
			System.out.println(f.getName() + " is too large, size: " + f.length());
			result = new CloudinaryUploadResult();
			return result;
		}
		
		result = cloudinaryFunction.upload(cloudinary, f);
		
		return result;
	}
	
	
}
