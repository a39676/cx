package demo.pmemo.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.article.service.impl.ArticleCommonService;
import demo.pmemo.mapper.PNoteMapper;
import demo.pmemo.pojo.constant.PMemoConstant;
import demo.pmemo.pojo.dto.EditPNoteDTO;
import demo.pmemo.pojo.dto.SetPMemoDTO;
import demo.pmemo.pojo.po.PNote;
import demo.pmemo.pojo.po.PNoteExample;
import demo.pmemo.service.PMemoService;

@Service
public class PMemoServiceImpl extends ArticleCommonService implements PMemoService {

	@Autowired
	private PNoteMapper noteMapper;
	
	@Override
	public String getMemo(String key) {
		if(StringUtils.isBlank(key)) {
			return "";
		}
		
		return constantService.getValByName(PMemoConstant.P_MEMO_REDIS_KEY + key);
	}
	
	@Override
	public CommonResult setMemo(SetPMemoDTO dto) {
		/*
		 * 插入之前, 删除其他memo?
		 */
		CommonResult r = new CommonResult();
		
		if(StringUtils.isAnyBlank(dto.getContent(), dto.getRedisKeyValue())) {
			r.failWithMessage("null param");
			return r;
		}
		
		if(dto.getContent().length() > PMemoConstant.MEMO_MAX_SIZE) {
			r.failWithMessage("too large");
			return r;
		}
		
		Long validSeconds = null;
		try {
			LocalDateTime validDateTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getValidDateStr());
			validSeconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), validDateTime);
			if(validDateTime.isBefore(LocalDateTime.now())) {
				throw new Exception();
			}
		} catch (Exception e) {
			validSeconds = PMemoConstant.DEFAULT_VALID_SECONDS;
		}
		
		constantService.setValByName(PMemoConstant.P_MEMO_REDIS_KEY + dto.getRedisKeyValue(), dto.getContent(), validSeconds, TimeUnit.SECONDS);
		
		r.setIsSuccess();
		return r;
	}
	
	public void cleanMemo() {
		/*
		 *  TODO
		 *  未加入 security url 鉴定权限
		 *  提供删除其他 memo? 
		 */
	}

	public CommonResult editPNote(EditPNoteDTO dto) {
		CommonResult r = new CommonResult();
		
		String pNoteStorePrefixPath = getPNoteStorePrefixPath();
		
		PNote po = findPNote(baseUtilCustom.getUserId());
		
//		TODO
		return r;
	}
	
	private String getPNoteStorePrefixPath() {
		if(isWindows()) {
			return PMemoConstant.P_NOTE_SAVING_FOLDER;
		} else {
			return "d:/" + PMemoConstant.P_NOTE_SAVING_FOLDER;
		}
	}
	
	private PNote findPNote(Long userId) {
		PNoteExample example = new PNoteExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<PNote> poList = noteMapper.selectByExample(example);
		if(poList == null || poList.isEmpty()) {
			return poList.get(0);
		} else {
			PNote po = new PNote();
			po.setId(snowFlake.getNextId());
			po.setUserId(userId);
			return po;
		}
	}
	
}