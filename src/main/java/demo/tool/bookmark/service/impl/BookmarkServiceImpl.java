package demo.tool.bookmark.service.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.base.system.service.impl.SystemOptionService;
import demo.common.service.CommonService;
import demo.config.costom_component.CustomPasswordEncoder;
import demo.tool.bookmark.mapper.BookmarkMapper;
import demo.tool.bookmark.pojo.dto.BookmarkDTO;
import demo.tool.bookmark.pojo.dto.BookmarkTagDTO;
import demo.tool.bookmark.pojo.dto.BookmarkUrlDTO;
import demo.tool.bookmark.pojo.dto.GetBookmarkWithPwdDTO;
import demo.tool.bookmark.pojo.po.Bookmark;
import demo.tool.bookmark.pojo.po.BookmarkExample;
import demo.tool.bookmark.pojo.result.GetBookmarkResult;
import demo.tool.bookmark.pojo.vo.BookmarkNameVO;
import demo.tool.bookmark.pojo.vo.BookmarkTagVO;
import demo.tool.bookmark.pojo.vo.BookmarkUrlVO;
import demo.tool.bookmark.pojo.vo.BookmarkVO;
import demo.tool.bookmark.service.BookmarkService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class BookmarkServiceImpl extends CommonService implements BookmarkService {

	@Autowired
	private BookmarkMapper mapper;
	@Autowired
	protected SystemOptionService systemOptionService;
	@Autowired
	private FileUtilCustom ioUtil;
	@Autowired
	private CustomPasswordEncoder passwordEncoder;

	private static final String bookmarkSalt = "bookmarkSalt";

	@Override
	public ModelAndView findAllBookmark() {
		ModelAndView v = new ModelAndView("toolJSP/bookmark/bookmarkManager");
		List<BookmarkNameVO> bookmarkVoList = findAllBookmarkName();
		v.addObject("bookmarkList", bookmarkVoList);
		return v;
	}

	private List<BookmarkNameVO> findAllBookmarkName() {
		Long userId = baseUtilCustom.getUserId();
		BookmarkExample example = new BookmarkExample();
		example.createCriteria().andIsDeleteEqualTo(false).andUserIdEqualTo(userId);
		List<Bookmark> poList = mapper.selectByExample(example);
		List<BookmarkNameVO> voList = new ArrayList<>();
		BookmarkNameVO vo = null;
		for (Bookmark po : poList) {
			vo = new BookmarkNameVO();
			vo.setPk(URLEncoder.encode(systemOptionService.encryptId(po.getId()), StandardCharsets.UTF_8));
			vo.setBookmarkName(po.getBookmarkName());
			vo.setNeedPwd(po.getPwd() != null);
			voList.add(vo);
		}

		return voList;
	}

	@Override
	public GetBookmarkResult getBookmark(GetBookmarkWithPwdDTO dto) {
		GetBookmarkResult r = new GetBookmarkResult();
		Long bookId = systemOptionService.decryptPrivateKey(dto.getPk());
		if (bookId == null) {
			return r;
		}

		Bookmark po = mapper.selectByPrimaryKey(bookId);

		if (!baseUtilCustom.hasAdminRole()) {
			Long userId = baseUtilCustom.getUserId();
			if (!userId.equals(po.getUserId())) {
				r.setMessage("It doesn't look like your bookmark");
				return r;
			}
		}

		if (null != po.getPwd()) {
			if (StringUtils.isBlank(dto.getPwd())) {
				r.setMessage("Password wrong");
				return r;
			}
			if (!checkBookmarkPwd(dto.getPwd(), po.getPwd())) {
				r.setMessage("Password wrong");
				return r;
			}
		}

		BookmarkDTO bookmarkDTO = buildBookmarkDtoFromFile(po.getId());
		BookmarkVO vo = buildBookVO(bookmarkDTO);
		r.setBookmark(vo);
		r.setIsSuccess();

		return r;
	}

	@Override
	public ModelAndView getBookmark(String pk) {
		ModelAndView v = new ModelAndView("toolJSP/bookmark/bookmarkDetail");
		Long bookId = systemOptionService.decryptPrivateKey(pk);
		if (bookId == null) {
			return v;
		}

		Bookmark po = mapper.selectByPrimaryKey(bookId);

		if (!baseUtilCustom.hasAdminRole()) {
			Long userId = baseUtilCustom.getUserId();
			if (userId == null || !userId.equals(po.getUserId())) {
				v.addObject("message", "It doesn't look like your bookmark");
				return v;
			}
		}

		if (null != po.getPwd()) {
			v.addObject("needPwd", true);
			return v;
		}

		BookmarkDTO bookmarkDTO = buildBookmarkDtoFromFile(po.getId());
		BookmarkVO vo = buildBookVO(bookmarkDTO);
		v.addObject("bookmarkVO", vo);

		return v;
	}

	private boolean checkBookmarkPwd(String rawPwd, String bookmarkPwd) {
		String pwdEncode = passwordEncoder.encode(bookmarkSalt, rawPwd);
		return pwdEncode.equals(bookmarkPwd);
	}

	private BookmarkDTO buildBookmarkDtoFromFile(Long bookmarkId) {
		try {
			String content = ioUtil.getStringFromFile(getStorePath(bookmarkId));
			BookmarkDTO dto = buildObjFromJsonCustomization(content, BookmarkDTO.class);
			dto.setId(bookmarkId);
			return dto;
		} catch (Exception e) {
			return new BookmarkDTO();
		}
	}

	private String getStorePath(Long id) {
		if (isWindows()) {
			return "d:/" + MAIN_FOLDER_PATH + "/bookmark/" + String.valueOf(id) + ".json";
		} else {
			return MAIN_FOLDER_PATH + "/bookmark/" + String.valueOf(id) + ".json";
		}
	}

	private BookmarkVO buildBookVO(BookmarkDTO dto) {
		BookmarkVO vo = new BookmarkVO();
		vo.setPk(systemOptionService.encryptId(dto.getId()));

		for (BookmarkTagDTO tag : dto.getAllTagList()) {
			vo.getAllTagList().add(buildBookmarkTagVO(tag));
		}

		for (BookmarkUrlDTO url : dto.getUrlList()) {
			vo.getUrlList().add(buildBookmarkUrlVO(url));
		}

		return vo;
	}

	private BookmarkTagVO buildBookmarkTagVO(BookmarkTagDTO dto) {
		BookmarkTagVO vo = new BookmarkTagVO();
		vo.setPk(systemOptionService.encryptId(dto.getId()));
		vo.setTagName(dto.getTagName());
		vo.setWeight(dto.getWeight());
		return vo;
	}

	private BookmarkUrlVO buildBookmarkUrlVO(BookmarkUrlDTO dto) {
		BookmarkUrlVO vo = new BookmarkUrlVO();
		vo.setPk(systemOptionService.encryptId(dto.getId()));
		vo.setName(dto.getName());
		vo.setUrl(dto.getUrl());
		vo.setWeight(dto.getWeight());
		for (BookmarkTagDTO tag : dto.getTagList()) {
			vo.getTagNameList().add(tag.getTagName());
		}

		return vo;
	}
}
