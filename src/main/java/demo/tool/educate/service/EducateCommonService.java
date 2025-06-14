package demo.tool.educate.service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import demo.base.system.service.impl.SystemOptionService;
import demo.common.service.CommonService;
import demo.tool.educate.mapper.StudentDetailMapper;
import demo.tool.educate.mapper.StudentExerciseHistoryMapper;
import demo.tool.educate.mapper.StudentPointHistoryMapper;
import demo.tool.educate.pojo.dto.MathExerciseDTO;
import demo.tool.educate.pojo.po.StudentExerciseHistory;
import demo.tool.educate.pojo.po.StudentExerciseHistoryExample;
import demo.tool.educate.pojo.result.ExerciseFileSaveResult;
import demo.tool.educate.pojo.type.ExerciseSubjectType;
import demo.tool.educate.pojo.type.GradeType;
import demo.tool.educate.service.impl.EducateOptionService;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;

public abstract class EducateCommonService extends CommonService {

	@Autowired
	protected SystemOptionService systemConstantService;

	@Autowired
	protected EducateOptionService optionService;
	@Autowired
	protected StudentExerciseHistoryMapper exerciseHistoryMapper;
	@Autowired
	protected StudentDetailMapper studentDetailMapper;
	@Autowired
	protected StudentPointHistoryMapper studentPointHistoryMapper;

	@Autowired
	private FileUtilCustom ioUtil;

	protected ExerciseFileSaveResult saveExerciseFile(MathExerciseDTO dto) {
		String storePrefixPath = System.getProperty("user.home") + optionService.getExerciseStorePrefixPath();
		String fileName = dto.getExerciseID() + "L" + ".txt";
		String timeFolder = LocalDate.now().toString();
		File mainFolder = new File(storePrefixPath + "/" + timeFolder);
		String finalFilePath = storePrefixPath + "/" + timeFolder + "/" + fileName;
		ExerciseFileSaveResult result = new ExerciseFileSaveResult();

		if (!mainFolder.exists() || dto == null || dto.getQuestionList() == null || dto.getQuestionList().isEmpty()) {
			if (!mainFolder.mkdirs()) {
				result.setMessage("习题保存目录异常,请联系管理员");
				return result;
			}
		}

		StringBuffer sb = new StringBuffer();

		JSONObject exerciseJson = JSONObject.fromObject(dto);

		sb.append(exerciseJson.toString());

		ioUtil.byteToFile(sb.toString().getBytes(StandardCharsets.UTF_8), finalFilePath);

		result.setFilePath(finalFilePath);

		result.setIsSuccess();
		return result;
	}

	protected <T> List<StudentExerciseHistory> reloadExercise(GradeType gradeType, ExerciseSubjectType subjectType,
			Long userId) {
		StudentExerciseHistoryExample example = new StudentExerciseHistoryExample();
		example.createCriteria().andGradeTypeEqualTo(gradeType.getCode().longValue())
				.andSubjectTypeEqualTo(subjectType.getCode().longValue()).andUserIdEqualTo(userId)
				.andCompeletionTimeIsNull();
		return exerciseHistoryMapper.selectByExample(example);
	}

	protected <T> T buildExerciseFromFile(StudentExerciseHistory exercisePO, Class<T> clazz) {
		String exerciseJsonStr = ioUtil.getStringFromFile(exercisePO.getFilePath());
		return buildObjFromJsonCustomization(exerciseJsonStr, clazz);
	}

}
