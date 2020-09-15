package demo.tool.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import demo.tool.pojo.result.UploadResult;

public interface UploadService {

	public Map<String, MultipartFile> getFiles(MultipartHttpServletRequest request);

	public UploadResult saveFiles(Map<String, MultipartFile> fileMap, String storePath);

	UploadResult uploadFakeFTPHandle(MultipartHttpServletRequest request);

	UploadResult saveUploadExcel(Map<String, MultipartFile> fileMap);

	UploadResult uploadV2(MultipartFile file, String storeFolderPath);

	/**
	 * 2020-09-15
	 * 私有上传文件处理逻辑,将返回文件具体存放路径
	 * @param file
	 * @param storeFolderPath
	 * @return
	 */
	UploadResult __privateUploadV2(MultipartFile file, String storeFolderPath);

}
