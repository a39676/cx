package demo.thirdPartyAPI.cloudinary.service;

import java.io.File;
import java.util.List;

import cloudinary.pojo.constant.CloudinaryConstant;
import cloudinary.pojo.result.CloudinaryDeleteResult;
import cloudinary.pojo.result.CloudinaryUploadResult;

public interface CloudinaryService {

	CloudinaryUploadResult uploadCore(File f);

	CloudinaryDeleteResult delete(String publicId);

	/**
	 * 
	 * @param publicIds size max 100
	 * {@link CloudinaryConstant} deleteIdListMaxSize
	 * @return
	 */
	CloudinaryDeleteResult delete(List<String> publicIds);

}
