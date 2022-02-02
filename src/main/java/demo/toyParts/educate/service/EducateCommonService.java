package demo.toyParts.educate.service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import demo.base.system.service.impl.SystemOptionService;
import demo.common.service.CommonService;
import demo.toyParts.educate.mapper.StudentDetailMapper;
import demo.toyParts.educate.mapper.StudentExerciesHistoryMapper;
import demo.toyParts.educate.pojo.dto.MathExerciesDTO;
import demo.toyParts.educate.pojo.result.ExerciesFileSaveResult;
import demo.toyParts.educate.service.impl.EducateOptionService;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;

public abstract class EducateCommonService extends CommonService {

	@Autowired
	protected SystemOptionService systemConstantService;

	@Autowired
	protected EducateOptionService optionService;
	@Autowired
	protected StudentExerciesHistoryMapper exerciesHistoryMapper;
	@Autowired
	protected StudentDetailMapper studentDetailMapper;
	
	@Autowired
	private FileUtilCustom ioUtil;

	protected ExerciesFileSaveResult saveExerciesFile(MathExerciesDTO dto) {
		String storePrefixPath = optionService.getExerciesStorePrefixPath();
		String fileName = dto.getExerciesID() + "L" + ".txt";
		String timeFolder = LocalDate.now().toString();
		File mainFolder = new File(storePrefixPath + "/" + timeFolder);
		String finalFilePath = storePrefixPath + "/" + timeFolder + "/" + fileName;
		ExerciesFileSaveResult result = new ExerciesFileSaveResult();

		if (!mainFolder.exists() || dto == null || dto.getQuestionList() == null || dto.getQuestionList().isEmpty()) {
			if (!mainFolder.mkdirs()) {
				result.setMessage("习题保存目录异常,请联系管理员");
				return result;
			}
		}

		StringBuffer sb = new StringBuffer();

		JSONObject exerciesJson = JSONObject.fromObject(dto);

		sb.append(exerciesJson.toString());

		ioUtil.byteToFile(sb.toString().getBytes(StandardCharsets.UTF_8), finalFilePath);

		result.setFilePath(finalFilePath);

		result.setIsSuccess();
		return result;
	}


}
