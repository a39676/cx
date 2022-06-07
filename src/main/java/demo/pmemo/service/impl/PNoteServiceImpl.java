package demo.pmemo.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.article.pojo.result.jsonRespon.ArticleFileSaveResult;
import demo.article.article.service.impl.ArticleCommonService;
import demo.pmemo.mapper.PNoteMapper;
import demo.pmemo.pojo.constant.PMemoConstant;
import demo.pmemo.pojo.dto.EditPNoteDTO;
import demo.pmemo.pojo.po.PNote;
import demo.pmemo.pojo.po.PNoteExample;
import demo.pmemo.pojo.vo.PNoteVO;
import demo.pmemo.service.PNoteService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class PNoteServiceImpl extends ArticleCommonService implements PNoteService {

	@Autowired
	private PNoteMapper noteMapper;
	@Autowired
	private FileUtilCustom ioUtil;

	@Override
	public CommonResult editPNote(EditPNoteDTO dto) {
		CommonResult r = new CommonResult();

		if (StringUtils.isNotBlank(dto.getContent()) && !isBigUser()) {
			dto.setContent(sanitize(dto.getContent()));
		}

		Long userId = baseUtilCustom.getUserId();
		PNote po = findPNote(userId);

		boolean createNewFlag = (po.getPath() == null);
		if (createNewFlag) {
			po.setPath(getPNoteStorePrefixPath() + "/" + snowFlake.getNextId());
		}

		try {
			ArticleFileSaveResult pNoteSaveResult = editPNote(po.getPath(), sanitize(dto.getContent()));
			if (pNoteSaveResult.isFail()) {
				r.addMessage(pNoteSaveResult.getMessage());
				return r;
			}

		} catch (IOException e) {
			r.addMessage("pNote save error");
			return r;
		}

		if (createNewFlag) {
			po.setEditCount(0);
			noteMapper.insertSelective(po);
		} else {
			po.setEditCount(po.getEditCount() + 1);
			po.setEditTime(LocalDateTime.now());
			noteMapper.updateByPrimaryKeySelective(po);
		}
		
		r.setMessage("note updated");
		r.setIsSuccess();
		return r;
	}

	private ArticleFileSaveResult editPNote(String finalFilePath, String content) throws IOException {
		content = sanitize(content);
		String storePrefixPath = getPNoteStorePrefixPath();
		ArticleFileSaveResult result = new ArticleFileSaveResult();

		File mainFolder = new File(storePrefixPath);
		if (!mainFolder.exists()) {
			if (!mainFolder.mkdirs()) {
				result.setMessage("Service error");
				return result;
			}
		}

		Element doc = Jsoup.parse(content);
		Elements imgs = doc.select("img[src]");
		/* 解决如果文章内有本地上传的图片, 转到服务器硬盘保存, 并提供 url 访问, */
		for (Element s : imgs) {
			s.attr("src", imgSrcHandler(s.attr("src")));
		}

		content = doc.toString();

		Long maxNoteLength = PMemoConstant.MEMO_MAX_SIZE;

		if (content.length() > maxNoteLength) {
			result.failWithMessage("note too long");
			return result;
		}

		StringBuffer sb = new StringBuffer();

		sb.append(content);

		ioUtil.byteToFile(sb.toString().getBytes(StandardCharsets.UTF_8), finalFilePath);

		result.setFilePath(finalFilePath);

		result.setIsSuccess();
		return result;
	}

	private String getPNoteStorePrefixPath() {
		if (isWindows()) {
			return "d:" + PMemoConstant.P_NOTE_SAVING_FOLDER;
		} else {
			return PMemoConstant.P_NOTE_SAVING_FOLDER;
		}
	}

	private PNote findPNote(Long userId) {
		PNoteExample example = new PNoteExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<PNote> poList = noteMapper.selectByExample(example);
		if (poList == null || poList.isEmpty()) {
			PNote po = new PNote();
			po.setId(snowFlake.getNextId());
			po.setUserId(userId);
			
			return po;
		} else {
			return poList.get(0);
		}
	}

	@Override
	public ModelAndView readNote() {
		ModelAndView v = new ModelAndView("pNoteJSP/createPNote");
		PNoteVO vo = new PNoteVO();

		Long userId = baseUtilCustom.getUserId();
		PNote po = findPNote(userId);
		
		if(!StringUtils.isBlank(po.getPath())) {
			String content = ioUtil.getStringFromFile(po.getPath());
			vo.setContent(content);
		}
		
		v.addObject("noteVO", vo);
		
		return v;

	}
}