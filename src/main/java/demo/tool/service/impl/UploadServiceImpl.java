package demo.tool.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import demo.common.pojo.type.ResultTypeCX;
import demo.common.service.CommonService;
import demo.tool.pojo.constant.ToolPathConstant;
import demo.tool.pojo.result.UploadResult;
import demo.tool.service.UploadService;
import toolPack.ioHandle.FileUtilCustom;
import toolPack.stringHandle.StringUtilCustom;

@Service
public class UploadServiceImpl extends CommonService implements UploadService {

	@Autowired
	private FileUtilCustom ioUtil;

	@Override
	public Map<String, MultipartFile> getFiles(MultipartHttpServletRequest request) {
		return request.getFileMap();
	}

	@Override
	public UploadResult saveFiles(Map<String, MultipartFile> fileMap, String storePath) {
		MultipartFile tmpFile = null;
		UploadResult result = new UploadResult();
		List<String> uploadSuccessFileNameList = new ArrayList<String>();
		List<String> uploadFailFileNameList = new ArrayList<String>();

		boolean flag = true;

		for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
			flag = true;
			tmpFile = entry.getValue();

			try {
				ioUtil.byteToFile(tmpFile.getBytes(), storePath + tmpFile.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
				flag = false;
				uploadFailFileNameList.add(tmpFile.getOriginalFilename());
			}

			if (flag) {
				uploadSuccessFileNameList.add(tmpFile.getOriginalFilename());
			}
		}
		result.setUploadSuccessFileNameList(uploadSuccessFileNameList);
		result.setUploadFailFileNameList(uploadFailFileNameList);
		result.setUploadTime(new Date());
		result.setIsSuccess();
		return result;
	}

	@Override
	public UploadResult saveUploadExcel(Map<String, MultipartFile> fileMap) {
		MultipartFile tmpFile = null;
		String fileName = null;

		Map<String, MultipartFile> targetFileMap = new HashMap<String, MultipartFile>();

		for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
			tmpFile = entry.getValue();
			fileName = tmpFile.getOriginalFilename();
			if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
				targetFileMap.put(entry.getKey(), entry.getValue());
			}
		}
		return saveFiles(targetFileMap, ToolPathConstant.getExcelTmpStorePath());
	}

	@Override
	public UploadResult uploadFakeFTPHandle(MultipartHttpServletRequest request) {
		UploadResult result = new UploadResult();
		Map<String, MultipartFile> fileMap = getFiles(request);
		String savePath = request.getParameter("savePath");
		if (StringUtils.isBlank(savePath) || fileMap.size() < 1) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}

		result = saveFiles(fileMap, savePath);

		return result;
	}

	@Override
	public UploadResult uploadV2(MultipartFile file, String storeFolderPath) {
		UploadResult r = new UploadResult();

		File storeFolder = new File(storeFolderPath);
		if (!storeFolder.exists() || storeFolder.isFile()) {
			if (!storeFolder.mkdirs()) {
				r.addMessage("store folder error");
				return r;
			}
		}

		StringUtilCustom stringUtil = new StringUtilCustom();
		if (stringUtil.filenameHasIllegalCharacter(file.getOriginalFilename())) {
			r.addMessage("filename error");
			return r;
		}

		try {
			Path copyLocation = Paths.get(storeFolder + File.separator + file.getOriginalFilename());
			Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
			r.addUploadSuccessFileName(file.getOriginalFilename());
			r.setIsSuccess();
			return r;
		} catch (Exception e) {
			r.addMessage("saving error");
			return r;
		}
	}
	
	@Override
	public UploadResult __privateUploadV2(MultipartFile file, String storeFolderPath) {
		UploadResult r = new UploadResult();

		File storeFolder = new File(storeFolderPath);
		if (!storeFolder.exists() || storeFolder.isFile()) {
			if (!storeFolder.mkdirs()) {
				r.addMessage("store folder error");
				return r;
			}
		}

		StringUtilCustom stringUtil = new StringUtilCustom();
		if (stringUtil.filenameHasIllegalCharacter(file.getOriginalFilename())) {
			r.addMessage("filename error");
			return r;
		}

		try {
			String fileSuffixName = stringUtil.getFileSuffixName(file.getOriginalFilename());
			Path copyLocation = Paths.get(storeFolder + File.separator + String.valueOf(snowFlake.getNextId()) + "." + fileSuffixName);
			Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
			r.addUploadSuccessFileName(copyLocation.toString());
			r.setIsSuccess();
			return r;
		} catch (Exception e) {
			r.addMessage("saving error");
			return r;
		}
	}
}
