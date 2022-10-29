package demo.tool.bookmark.service.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.owasp.html.PolicyFactory;
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
import demo.tool.bookmark.pojo.dto.BookmarkTagWeightChangeSubDataDTO;
import demo.tool.bookmark.pojo.dto.BookmarkUrlDTO;
import demo.tool.bookmark.pojo.dto.BookmarkUrlWeightChangeSubDataDTO;
import demo.tool.bookmark.pojo.dto.BookmarkWeightChangeMainDTO;
import demo.tool.bookmark.pojo.dto.CreateBookmarkTagDTO;
import demo.tool.bookmark.pojo.dto.CreateNewBookmarkDTO;
import demo.tool.bookmark.pojo.dto.DeleteBookmarkTagDTO;
import demo.tool.bookmark.pojo.dto.DeleteBookmarkUrlDTO;
import demo.tool.bookmark.pojo.dto.EditBookmarkTagDTO;
import demo.tool.bookmark.pojo.dto.EditBookmarkUrlDTO;
import demo.tool.bookmark.pojo.dto.GetBookmarkWithPwdDTO;
import demo.tool.bookmark.pojo.dto.RemoveEmptyTagDTO;
import demo.tool.bookmark.pojo.po.Bookmark;
import demo.tool.bookmark.pojo.po.BookmarkExample;
import demo.tool.bookmark.pojo.result.CreateBookmarkTagResult;
import demo.tool.bookmark.pojo.result.EditBookmarkUrlResult;
import demo.tool.bookmark.pojo.result.GetBookmarkResult;
import demo.tool.bookmark.pojo.result.RemoveEmptyTagResult;
import demo.tool.bookmark.pojo.vo.BookmarkNameVO;
import demo.tool.bookmark.pojo.vo.BookmarkTagVO;
import demo.tool.bookmark.pojo.vo.BookmarkUrlVO;
import demo.tool.bookmark.pojo.vo.BookmarkVO;
import demo.tool.bookmark.service.BookmarkService;
import demo.tool.other.service.TextFilter;
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
	@Autowired
	private TextFilter textFilter;

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

		Collections.sort(vo.getAllTagList());
		Collections.reverse(vo.getAllTagList());
		
		for (BookmarkUrlDTO url : dto.getUrlList()) {
			vo.getUrlList().add(buildBookmarkUrlVO(url, tagVoMap));
		}
		
		Collections.sort(vo.getUrlList());
		Collections.reverse(vo.getUrlList());

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
			if (tagVO != null) {
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
		newBookmarkDTO.setBookmarkName(sanitize(dto.getBookmarkName()));
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
		if (StringUtils.isBlank(bookmarkPK)) {
			return null;
		}

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

	@Override
	public CreateBookmarkTagResult createBookmarkTag(CreateBookmarkTagDTO dto) {
		CreateBookmarkTagResult r = new CreateBookmarkTagResult();

		Bookmark po = matchBookmarkAndUser(dto.getBookmarkPK());
		if (po == null) {
			r.setMessage("It doesn't look like your bookmark");
			return r;
		}

		if (StringUtils.isBlank(dto.getTagName())) {
			r.setMessage("Can not use empty tag name");
			return r;
		}

		if (dto.getTagName().length() > 18) {
			r.setMessage("Tag name can NOT longer than 18");
			return r;
		}

		dto.setTagName(sanitize(dto.getTagName()));

		BookmarkDTO bookmarkDTO = buildBookmarkDtoFromFile(po.getId());
		if (hadDuplicateTagName(bookmarkDTO, dto.getTagName())) {
			r.setMessage("Can not use duplicate tag name");
			return r;
		}

		BookmarkTagDTO tagDTO = new BookmarkTagDTO();
		tagDTO.setId(snowFlake.getNextId());
		tagDTO.setTagName(dto.getTagName());
		tagDTO.setWeight(0D);

		bookmarkDTO.addTag(tagDTO);

		rewiriteBookmarkFile(bookmarkDTO);

		r.setPk(systemOptionService.encryptId(tagDTO.getId()));
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult editBookmarkTag(EditBookmarkTagDTO dto) {
		CommonResult r = new CommonResult();

		Bookmark po = matchBookmarkAndUser(dto.getBookmarkPK());
		if (po == null) {
			r.setMessage("It doesn't look like your bookmark");
			return r;
		}

		if (StringUtils.isBlank(dto.getTagName())) {
			r.setMessage("Can not use empty tag name");
			return r;
		}

		if (dto.getTagName().length() > 18) {
			r.setMessage("Tag name can NOT longer than 18");
			return r;
		}

		Long tagId = systemOptionService.decryptPrivateKey(dto.getTagPK());
		if (tagId == null) {
			r.setMessage("Old tan NOT found");
			return r;
		}

		dto.setTagName(sanitize(dto.getTagName()));

		BookmarkDTO bookmarkDTO = buildBookmarkDtoFromFile(po.getId());
		if (hadDuplicateTagName(bookmarkDTO, dto.getTagName())) {
			r.setMessage("Can not use duplicate tag name");
			return r;
		}

		BookmarkTagDTO tagDTO = null;
		List<BookmarkTagDTO> tagList = bookmarkDTO.getAllTagList();
		for (int i = 0; tagDTO == null && i < tagList.size(); i++) {
			if (tagList.get(i).getId().equals(tagId)) {
				tagDTO = tagList.remove(i);
			}
		}

		if (tagDTO == null) {
			r.setMessage("Can not use duplicate tag name");
			return r;
		}

		String oldTagName = tagDTO.getTagName();
		tagDTO.setTagName(dto.getTagName());

		bookmarkDTO.addTag(tagDTO);

		rewiriteBookmarkFile(bookmarkDTO);

		r.setMessage("Edit success, rename " + oldTagName + " to " + dto.getTagName());
		r.setIsSuccess();
		return r;
	}

	private boolean hadDuplicateTagName(BookmarkDTO dto, String tagName) {
		List<BookmarkTagDTO> tagList = dto.getAllTagList();
		boolean flag = false;
		for (BookmarkTagDTO tag : tagList) {
			if (tag.getTagName().equals(tagName)) {
				flag = true;
				return flag;
			}
		}
		return flag;
	}

	private String sanitize(String content) {
		PolicyFactory filter = textFilter.getArticleFilter();
		return filter.sanitize(content);
	}

	@Override
	public EditBookmarkUrlResult editBookmarkUrl(EditBookmarkUrlDTO dto) {
		EditBookmarkUrlResult r = new EditBookmarkUrlResult();

		dto.setBookmarkUrlName(sanitize(dto.getBookmarkUrlName()));
		if (StringUtils.isBlank(dto.getBookmarkUrlName())) {
			r.setMessage("Can NOT use empty url name");
			return r;
		}
		if (dto.getBookmarkUrlName().length() > 30) {
			r.setMessage("Url name should NOT longer than 30");
			return r;
		}

		Bookmark po = matchBookmarkAndUser(dto.getBookmarkPK());
		if (po == null) {
			r.setMessage("It doesn't look like your bookmark");
			return r;
		}

		BookmarkDTO bookmarkDTO = buildBookmarkDtoFromFile(po.getId());

		Map<String, BookmarkTagDTO> tagNameMap = new HashMap<>();
		for (BookmarkTagDTO tag : bookmarkDTO.getAllTagList()) {
			tagNameMap.put(tag.getTagName(), tag);
		}

		BookmarkUrlDTO urlDTO = null;
		if (dto.getCreateNew()) {
			urlDTO = new BookmarkUrlDTO();
			Long newUrlId = snowFlake.getNextId();
			urlDTO.setId(newUrlId);
			urlDTO.setName(dto.getBookmarkUrlName());
			urlDTO.setUrl(dto.getBookmarkUrl());
			for (String editTagName : dto.getTagNameList()) {
				BookmarkTagDTO tmpTag = tagNameMap.get(editTagName);
				if (tmpTag != null) {
					urlDTO.addTag(tmpTag);
				}
			}
			bookmarkDTO.addUrl(urlDTO);
			rewiriteBookmarkFile(bookmarkDTO);

			r.setBookmarkUrlPk(systemOptionService.encryptId(newUrlId));
			r.setMessage("Insert new bookmark: " + dto.getBookmarkUrlName());
			r.setIsSuccess();
			return r;

		} else {

			Long urlId = systemOptionService.decryptPrivateKey(dto.getBookmarkUrlPK());
			if (urlId == null) {
				r.setMessage("Can NOT find this url. Would you create a new one ?");
				return r;
			}
			for (int i = 0; i < bookmarkDTO.getUrlList().size() && urlDTO == null; i++) {
				if (bookmarkDTO.getUrlList().get(i).getId().equals(urlId)) {
					urlDTO = bookmarkDTO.getUrlList().remove(i);
				}
			}
			if (urlDTO == null) {
				r.setMessage("Can NOT find this url. Would you create a new one ?");
				return r;
			}

			urlDTO.setName(dto.getBookmarkUrlName());
			urlDTO.setUrl(dto.getBookmarkUrl());
			List<BookmarkTagDTO> newTagList = new ArrayList<>();
			for (String tmpTagName : dto.getTagNameList()) {
				BookmarkTagDTO tmpTag = tagNameMap.get(tmpTagName);
				if (tmpTag != null) {
					newTagList.add(tmpTag);
				}
			}
			urlDTO.setTagList(newTagList);

			bookmarkDTO.addUrl(urlDTO);
			rewiriteBookmarkFile(bookmarkDTO);
			r.setMessage("Edited bookmark: " + dto.getBookmarkUrlName());
			r.setIsSuccess();
			return r;
		}
	}

	@Override
	public CommonResult deleteBookmarkUrl(DeleteBookmarkUrlDTO dto) {
		CommonResult r = new CommonResult();

		Bookmark po = matchBookmarkAndUser(dto.getBookmarkPK());
		if (po == null) {
			r.setMessage("It doesn't look like your bookmark");
			return r;
		}

		Long urlId = systemOptionService.decryptPrivateKey(dto.getBookmarkUrlPK());
		if (urlId == null) {
			return r;
		}

		BookmarkDTO bookmarkDTO = buildBookmarkDtoFromFile(po.getId());

		BookmarkUrlDTO deleteBookmark = null;
		for (int i = 0; i < bookmarkDTO.getUrlList().size() && deleteBookmark == null; i++) {
			if (urlId.equals(bookmarkDTO.getUrlList().get(i).getId())) {
				deleteBookmark = bookmarkDTO.getUrlList().remove(i);
			}
		}

		if (deleteBookmark != null) {
			rewiriteBookmarkFile(bookmarkDTO);
			r.setMessage("\"" + deleteBookmark.getName() + "\", " + deleteBookmark.getUrl() + ", " + "deleted");
			r.setIsSuccess();
		}
		return r;
	}

	@Override
	public CommonResult bookmarkWeightChange(BookmarkWeightChangeMainDTO dto) {
		CommonResult r = new CommonResult();

		if ((dto.getTagSubDataList() == null || dto.getTagSubDataList().isEmpty())
				&& (dto.getUrlSubDataList() == null || dto.getUrlSubDataList().isEmpty())) {
			return r;
		}

		Bookmark po = matchBookmarkAndUser(dto.getBookmarkPK());
		if (po == null) {
			r.setMessage("It doesn't look like your bookmark");
			return r;
		}

		BookmarkDTO bookmarkDTO = buildBookmarkDtoFromFile(po.getId());
		boolean hadUpdate = false;
		
		bookmarkTagIf: if (dto.getTagSubDataList() != null && !dto.getTagSubDataList().isEmpty()) {
			List<BookmarkTagDTO> tagList = bookmarkDTO.getAllTagList();
			Map<Long, Double> idMap = new HashMap<Long, Double>();
			for (BookmarkTagWeightChangeSubDataDTO tagData : dto.getTagSubDataList()) {
				Long tagId = null;
				if ((tagId = systemOptionService.decryptPrivateKey(tagData.getTagPK())) != null) {
					idMap.put(tagId, tagData.getWeight());
				}
			}

			if (idMap.isEmpty()) {
				break bookmarkTagIf;
			}
			BookmarkTagDTO tag = null;
			for (int i = 0; i < tagList.size(); i++) {
				tag = tagList.get(i);
				if (idMap.get(tag.getId()) != null) {
					tag.setWeight(tag.getWeight() + idMap.get(tag.getId()));
					tagList.set(i, tag);
					hadUpdate = true;
				}
			}
			
			if(hadUpdate) {
				bookmarkDTO.setAllTagList(tagList);
			}
		}
		
		bookmarkUrlIf: if (dto.getUrlSubDataList() != null && !dto.getUrlSubDataList().isEmpty()) {
			List<BookmarkUrlDTO> urlList = bookmarkDTO.getUrlList();
			Map<Long, Double> idMap = new HashMap<Long, Double>();
			for (BookmarkUrlWeightChangeSubDataDTO urlData : dto.getUrlSubDataList()) {
				Long urlId = null;
				if ((urlId = systemOptionService.decryptPrivateKey(urlData.getUrlPK())) != null) {
					idMap.put(urlId, urlData.getWeight());
				}
			}

			if (idMap.isEmpty()) {
				break bookmarkUrlIf;
			}
			BookmarkUrlDTO url = null;
			for (int i = 0; i < urlList.size(); i++) {
				url = urlList.get(i);
				if (idMap.get(url.getId()) != null) {
					url.setWeight(url.getWeight() + idMap.get(url.getId()));
					urlList.set(i, url);
					hadUpdate = true;
				}
			}
			
			if(hadUpdate) {
				bookmarkDTO.setUrlList(urlList);
			}
		}
		
		if(hadUpdate) {
			rewiriteBookmarkFile(bookmarkDTO);
			r.setIsSuccess();
		}
		
		return r;
	}
	
	@Override
	public RemoveEmptyTagResult removeEmptyTags(RemoveEmptyTagDTO dto) {
		RemoveEmptyTagResult r = new RemoveEmptyTagResult();
		
		Bookmark po = matchBookmarkAndUser(dto.getBookmarkPK());
		if (po == null) {
			r.setMessage("It doesn't look like your bookmark");
			return r;
		}

		BookmarkDTO bookmarkDTO = buildBookmarkDtoFromFile(po.getId());
		
		if(bookmarkDTO.getAllTagList().isEmpty() || bookmarkDTO.getUrlList().isEmpty()) {
			return r;
		}
		
		List<BookmarkTagDTO> allTagList = bookmarkDTO.getAllTagList();
		List<BookmarkUrlDTO> urlList = bookmarkDTO.getUrlList();
		Set<BookmarkTagDTO> allTagSet = new HashSet<>();
		allTagSet.addAll(allTagList);
		
		for(int i = 0; i < urlList.size() && !allTagSet.isEmpty(); i++) {
			BookmarkUrlDTO url = urlList.get(i);
			for(int j = 0; j < url.getTagList().size() && !allTagSet.isEmpty(); j++) {
				BookmarkTagDTO tmpTag = url.getTagList().get(j);
				allTagSet.remove(tmpTag);
			}
		}
		
		if(allTagSet.isEmpty()) {
			return r;
		}
		
		List<String> tagRemoved = new ArrayList<>();
		for(BookmarkTagDTO tagNeedRemove : allTagSet) {
			allTagList.remove(tagNeedRemove);
			tagRemoved.add(tagNeedRemove.getTagName());
		}
		
		if(!allTagList.isEmpty()) {
			rewiriteBookmarkFile(bookmarkDTO);
		}
		
		r.setMessage("Removed: " + tagRemoved);
		r.setIsSuccess();
		return r;
	}

	@Override
	public void reBalanceWeight() {
		BookmarkExample example = new BookmarkExample();
		example.createCriteria().andIsDeleteEqualTo(false);
		List<Bookmark> poList = mapper.selectByExample(example);
		for(Bookmark po : poList) {
			BookmarkDTO dto = buildBookmarkDtoFromFile(po.getId());
			reBalanceWeight(dto);
		}
	}
	
	private void reBalanceWeight(BookmarkDTO dto) {
		if(dto == null || (dto.getAllTagList().isEmpty() && dto.getUrlList().isEmpty())) {
			return;
		}
		
		Double limitWeight = 9999D;
		boolean needUpdate = false;
		
		if(!dto.getAllTagList().isEmpty()) {
			Double tagMaxWeight = 0D;
			for(BookmarkTagDTO tag : dto.getAllTagList()) {
				if(tag.getWeight() > tagMaxWeight) {
					tagMaxWeight = tag.getWeight();
				}
			}
			
			if(tagMaxWeight > limitWeight) {
				needUpdate = true;
				Double rate = limitWeight / tagMaxWeight;
				for(BookmarkTagDTO tag : dto.getAllTagList()) {
					tag.setWeight(tag.getWeight() * rate);
				}
			}
		}
		
		
		if(!dto.getUrlList().isEmpty()) {
			Double tagUrlWeight = 0D;
			for(BookmarkUrlDTO url : dto.getUrlList()) {
				if(url.getWeight() > tagUrlWeight) {
					tagUrlWeight = url.getWeight();
				}
			}
			
			if(tagUrlWeight > limitWeight) {
				needUpdate = true;
				Double rate = limitWeight / tagUrlWeight;
				for(BookmarkUrlDTO url : dto.getUrlList()) {
					url.setWeight(url.getWeight() * rate);
				}
			}
		}
		
		if(needUpdate) {
			rewiriteBookmarkFile(dto);
		}
	}
}
