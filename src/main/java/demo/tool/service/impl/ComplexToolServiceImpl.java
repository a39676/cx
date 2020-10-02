package demo.tool.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.pojo.result.CommonResultCX;
import demo.common.pojo.type.ResultTypeCX;
import demo.common.service.CommonService;
import demo.tool.pojo.constant.ToolPathConstant;
import demo.tool.pojo.dto.CleanTmpFilesDTO;
import demo.tool.service.ComplexToolService;

@Service
public class ComplexToolServiceImpl extends CommonService implements ComplexToolService {
	
	@Override
	public CommonResult cleanTmpFiles(CleanTmpFilesDTO dto) {
		Integer passTime = 30; // minute
		CommonResult result = new CommonResult();
		
		if(dto.getPassTime() == null || dto.getPassTime() > passTime) {
			passTime = dto.getPassTime();
		}
		
		File mainFolder = new File(ToolPathConstant.getTmpStorePath());
		File[] files = mainFolder.listFiles();
		BasicFileAttributes attr;
		List<String> successFile = new ArrayList<String>();
		List<String> failFile = new ArrayList<String>();
		
		StringBuffer messageBuffer = new StringBuffer();
		
		for(File file : files) {
			try {
				attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
				if((System.currentTimeMillis() - attr.creationTime().toMillis()) >= (passTime * 1000L * 60)) {
					file.delete();
				}
				successFile.add(file.getName());
			} catch (IOException e) {
				failFile.add(file.getName());
				e.printStackTrace();
			}
		}
		
		if(failFile.size() > 0) {
			result.setResult(ResultTypeCX.fail.getCode());
			messageBuffer.append("fail on :" + failFile.toString());
			messageBuffer.append("\n");
		} else {
			result.setResult(ResultTypeCX.success.getCode());
		}
		messageBuffer.append("deleted :" + successFile.toString());
		result.setMessage(messageBuffer.toString());
		
		return result;
	}
	
	@Override
	public CommonResultCX cleanTmpFiles(String targetFolder, String extensionName, LocalDateTime oldestCreateTime) {
		CommonResultCX r = new CommonResultCX();

		File folder = new File(targetFolder);
		if(!folder.exists()) {
			return r;
		}
		
		File[] files = folder.listFiles();
		BasicFileAttributes attrs;
		Path p;
		Date fileTime;
		LocalDateTime fileLocalDateTime;
		for(File f : files) {
			if(f.isDirectory()) {
				cleanTmpFiles(f.getAbsolutePath(), extensionName, oldestCreateTime);
			}
			if(f.isFile()) {
				p = f.toPath();
				try {
					attrs = Files.readAttributes(p, BasicFileAttributes.class);
					fileTime = new Date(attrs.creationTime().toMillis());
					fileLocalDateTime = localDateTimeHandler.dateToLocalDateTime(fileTime);
					if(fileLocalDateTime.isBefore(oldestCreateTime)) {
						if(StringUtils.isBlank(extensionName)) {
							f.delete();
						} else {
							if(f.getName().endsWith(extensionName)) {
								f.delete();
							}
						}
					}
				} catch (IOException e) {
				}
			}
		}
		
		r.setIsSuccess();
		return r;
	}

	
}
