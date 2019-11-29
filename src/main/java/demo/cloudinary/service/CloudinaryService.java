package demo.cloudinary.service;

import java.io.File;
import java.util.List;

import cloudinary.pojo.result.CloudinaryDeleteResult;
import cloudinary.pojo.result.CloudinaryUploadResult;

public interface CloudinaryService {

	CloudinaryUploadResult uploadCore(File f);

	CloudinaryDeleteResult delete(String publicId);

	CloudinaryDeleteResult delete(List<String> publicIds);

}
