package demo.tool.service;

import java.time.LocalDateTime;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.pojo.result.CommonResultCX;
import demo.tool.pojo.dto.CleanTmpFilesDTO;

public interface ComplexToolService {

	CommonResult cleanTmpFiles(CleanTmpFilesDTO dto);

	CommonResultCX cleanTmpFiles(String targetFolder, String extensionName, LocalDateTime oldestCreateTime);

}
