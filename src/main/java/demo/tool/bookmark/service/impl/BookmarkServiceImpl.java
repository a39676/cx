package demo.tool.bookmark.service.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.system.service.impl.SystemOptionService;
import demo.common.service.CommonService;
import demo.config.costom_component.CustomPasswordEncoder;
import demo.tool.bookmark.mapper.BookmarkMapper;
import demo.tool.bookmark.pojo.dto.BookmarkDTO;
import demo.tool.bookmark.pojo.dto.BookmarkTagDTO;
import demo.tool.bookmark.pojo.dto.BookmarkUrlDTO;
import demo.tool.bookmark.pojo.dto.CreateNewBookmarkDTO;
import demo.tool.bookmark.pojo.dto.DeleteBookmarkTagDTO;
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

		Bookmark po = matchBookmarkAndUser(dto.getPk());
		if (po == null) {
			r.setMessage("It doesn't look like your bookmark");
			return r;
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

		Bookmark po = matchBookmarkAndUser(pk);

		if (po == null) {
			v.addObject("message", "It doesn't look like your bookmark");
			return v;
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
		
		Map<Long, BookmarkTagVO> tagVoMap = new HashMap<>();

		for (BookmarkTagDTO tag : dto.getAllTagList()) {
			BookmarkTagVO tagVO = buildBookmarkTagVO(tag);
			vo.getAllTagList().add(tagVO);
			tagVoMap.put(tag.getId(), tagVO);
		}

		for (BookmarkUrlDTO url : dto.getUrlList()) {
			vo.getUrlList().add(buildBookmarkUrlVO(url, tagVoMap));
		}
		
		vo.setBookmarkName(dto.getBookmarkName());

		return vo;
	}

	private BookmarkTagVO buildBookmarkTagVO(BookmarkTagDTO dto) {
		BookmarkTagVO vo = new BookmarkTagVO();
		vo.setPk(systemOptionService.encryptId(dto.getId()));
		vo.setTagName(dto.getTagName());
		vo.setWeight(dto.getWeight());
		return vo;
	}

	private BookmarkUrlVO buildBookmarkUrlVO(BookmarkUrlDTO dto, Map<Long, BookmarkTagVO> tagVoMap) {
		BookmarkUrlVO vo = new BookmarkUrlVO();
		vo.setPk(systemOptionService.encryptId(dto.getId()));
		vo.setName(dto.getName());
		vo.setUrl(dto.getUrl());
		vo.setWeight(dto.getWeight());
		for (BookmarkTagDTO tag : dto.getTagList()) {
			BookmarkTagVO tagVO = tagVoMap.get(tag.getId());
			if(tagVO != null) {
				vo.getTagVoList().add(tagVO);
				vo.getTagNameList().add(tagVO.getTagName());
			}
		}

		return vo;
	}

	@Override
	public CommonResult deleteBookmarkUrl(String bookmarkPk, String bookmarkUrlPk) {
		CommonResult r = new CommonResult();
		Bookmark bookmarkPO = matchBookmarkAndUser(bookmarkPk);
		if (bookmarkPO == null) {
			r.setMessage("Can NOT find correct bookmark");
			return r;
		}

		Long bookmarkUrlId = systemOptionService.decryptPrivateKey(bookmarkUrlPk);
		if (bookmarkUrlId == null) {
			r.setMessage("Can NOT find correct url");
			return r;
		}

		BookmarkDTO bookmark = buildBookmarkDtoFromFile(bookmarkPO.getId());
		List<BookmarkUrlDTO> urlList = bookmark.getUrlList();
		boolean found = false;
		for (int i = 0; i < urlList.size() && !found; i++) {
			if (bookmarkUrlId.equals(urlList.get(i).getId())) {
				urlList.remove(i);
				found = true;
			}
		}

		if (found) {
			rewiriteBookmarkFile(bookmark);
			r.setIsSuccess();
			return r;
		}

		r.setMessage("Can NOT find correct url");
		return r;
	}

	@Override
	public CommonResult createNewBookmark(CreateNewBookmarkDTO dto) {
		CommonResult r = new CommonResult();

		Long userId = baseUtilCustom.getUserId();
		Bookmark po = new Bookmark();
		Long newBookmarkId = snowFlake.getNextId();
		po.setId(newBookmarkId);
		po.setUserId(userId);
		po.setBookmarkName(dto.getBookmarkName());
		if (dto.getPwd() != null) {
			String pwd = passwordEncoder.encode(bookmarkSalt, dto.getPwd());
			po.setPwd(pwd);
		}

		mapper.insertSelective(po);

		BookmarkDTO newBookmarkDTO = new BookmarkDTO();
		newBookmarkDTO.setBookmarkName(dto.getBookmarkName());
		newBookmarkDTO.setId(newBookmarkId);

		rewiriteBookmarkFile(newBookmarkDTO);

		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult deleteBookmarkTag(DeleteBookmarkTagDTO dto) {
		CommonResult r = new CommonResult();

		List<Long> tagIdList = systemOptionService.decryptPrivateKey(dto.getTagPkList());
		if (tagIdList.isEmpty()) {
			r.setMessage("Deleted");
			r.setIsSuccess();
			return r;
		}

		Bookmark po = matchBookmarkAndUser(dto.getBookmarkPK());
		if (po == null) {
			r.setMessage("It doesn't look like your bookmark");
			return r;
		}

		BookmarkDTO bookmarkDTO = buildBookmarkDtoFromFile(po.getId());
		List<BookmarkTagDTO> tagList = bookmarkDTO.getAllTagList();

		List<String> removedTagNameList = new ArrayList<>();
		for (int tagIndex = 0; tagIndex < tagList.size(); tagIndex++) {
			if (tagIdList.contains(tagList.get(tagIndex).getId())) {
				BookmarkTagDTO tag = tagList.remove(tagIndex);
				removedTagNameList.add(tag.getTagName());
				tagIndex--;
			}
		}

		for (int urlIndex = 0; urlIndex < bookmarkDTO.getUrlList().size(); urlIndex++) {
			List<BookmarkTagDTO> urlTagList = bookmarkDTO.getUrlList().get(urlIndex).getTagList();
			for (int tagIndex = 0; tagIndex < urlTagList.size(); tagIndex++) {
				if (tagIdList.contains(urlTagList.get(tagIndex).getId())) {
					urlTagList.remove(tagIndex);
					tagIndex--;
				}
			}
		}
		
		rewiriteBookmarkFile(bookmarkDTO);

		r.setIsSuccess();
		if (!removedTagNameList.isEmpty()) {
			r.setMessage("Tag :\"" + removedTagNameList + "\" deleted");
		} else {
			r.setMessage("Tag deleted");
		}
		return r;
	}

	private Bookmark matchBookmarkAndUser(String bookmarkPK) {
		Long bookId = systemOptionService.decryptPrivateKey(bookmarkPK);
		if (bookId == null) {
			return null;
		}

		Long userId = baseUtilCustom.getUserId();
		if (userId == null) {
			return null;
		}

		Bookmark po = mapper.selectByPrimaryKey(bookId);
		if (po == null) {
			return null;
		}

		if (!baseUtilCustom.hasAdminRole()) {
			if (!userId.equals(po.getUserId())) {
				return null;
			}
		}

		return po;
	}

	private void rewiriteBookmarkFile(BookmarkDTO dto) {
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, localDateTimeAdapter).setPrettyPrinting()
				.create();
		String jsonString = gson.toJson(dto);
		ioUtil.byteToFile(jsonString.toString().getBytes(StandardCharsets.UTF_8), getStorePath(dto.getId()));
	}
}
