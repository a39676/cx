package demo.tool.bookmark.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import demo.base.system.service.impl.SystemOptionService;
import demo.common.service.CommonService;
import demo.config.costom_component.CustomPasswordEncoder;
import demo.tool.bookmark.mapper.BookmarkMapper;
import demo.tool.bookmark.pojo.dto.BookmarkDTO;
import demo.tool.bookmark.pojo.dto.BookmarkTagDTO;
import demo.tool.bookmark.pojo.dto.BookmarkUrlDTO;
import demo.tool.bookmark.pojo.po.Bookmark;
import demo.tool.bookmark.pojo.po.BookmarkExample;
import demo.tool.bookmark.pojo.po.BookmarkExample.Criteria;
import demo.tool.bookmark.pojo.vo.BookmarkNameVO;
import demo.tool.bookmark.pojo.vo.BookmarkVO;
import demo.tool.bookmark.service.BookmarkService;
import toolPack.dateTimeHandle.LocalDateTimeAdapter;
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
	
	public ModelAndView findAllBookmark() {
//		TODO
		ModelAndView v = new ModelAndView("");
		
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
			vo.setPk(systemOptionService.encryptId(po.getId()));
			vo.setBookmarkName(po.getBookmarkName());
			vo.setNeedPwd(po.getPwd() == null);
			voList.add(vo);
		}
		
		return voList;
	}
	
	

	/** Use AFTER check PWD */
	private BookmarkVO findBookmarkVoAfterPwdCheck(String bookmarkPK) {
		Long userId = baseUtilCustom.getUserId();
		Long bookmarkId = systemOptionService.decryptPrivateKey(bookmarkPK);
		if(userId == null || bookmarkId == null) {
			return new BookmarkVO();
		}
		
		BookmarkExample example = new BookmarkExample();
		Criteria criterira = example.createCriteria();
		criterira.andIsDeleteEqualTo(false).andIdEqualTo(bookmarkId);
		if(!baseUtilCustom.hasAdminRole()) {
			criterira.andUserIdEqualTo(userId);
		}
		
		List<Bookmark> poList = mapper.selectByExample(example);
		BookmarkDTO dto = null;
		for (Bookmark po : poList) {
			dto = buildBookmarkDtoFromFile(po.getId());
			return buildBookVO(dto);
		}
		
		return new BookmarkVO();
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
		if(isWindows()) {
			return "d:/" + MAIN_FOLDER_PATH + "/bookmark/" + String.valueOf(id) + ".json";
		} else {
			return MAIN_FOLDER_PATH + "/bookmark/" + String.valueOf(id) + ".json";
		}
	}
	
	private BookmarkVO buildBookVO(BookmarkDTO dto) {
		BookmarkVO vo = new BookmarkVO();
		BeanUtils.copyProperties(dto, vo);
		vo.setPk(systemOptionService.encryptId(dto.getId()));
		return vo;
	}

	public static void main(String[] args) {

		LocalDateTimeAdapter localDateTimeAdapter = new LocalDateTimeAdapter();

		BookmarkDTO m = new BookmarkDTO();
		m.setBookmarkName("main");

		BookmarkUrlDTO u = new BookmarkUrlDTO();
		u.setName("u1 in main");
		u.setUrl("u1.com");

		BookmarkTagDTO t = new BookmarkTagDTO();
		t.setTagName("tag1");

		u.addTag(t);
		m.addUrl(u);

		t = new BookmarkTagDTO();
		t.setTagName("tag for main folder");
		m.addTag(t);

		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, localDateTimeAdapter).setPrettyPrinting()
				.create();
		String jsonString = gson.toJson(m);

		System.out.println(jsonString);
	}
}
