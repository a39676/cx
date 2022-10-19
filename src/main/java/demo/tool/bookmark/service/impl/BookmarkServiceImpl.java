package demo.tool.bookmark.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import demo.common.service.CommonService;
import demo.tool.bookmark.mapper.BookmarkMapper;
import demo.tool.bookmark.pojo.dto.BookmarkDTO;
import demo.tool.bookmark.pojo.dto.BookmarkTagDTO;
import demo.tool.bookmark.pojo.dto.BookmarkUrlDTO;
import demo.tool.bookmark.pojo.po.Bookmark;
import demo.tool.bookmark.pojo.po.BookmarkExample;
import demo.tool.bookmark.pojo.vo.BookmarkVO;
import demo.tool.bookmark.service.BookmarkService;
import toolPack.dateTimeHandle.LocalDateTimeAdapter;

@Service
public class BookmarkServiceImpl extends CommonService implements BookmarkService {

	@Autowired
	private BookmarkMapper mapper;

	public void findAllBookmark() {
		Long userId = baseUtilCustom.getUserId();
		BookmarkExample example = new BookmarkExample();
		example.createCriteria().andIsDeleteEqualTo(false).andUserIdEqualTo(userId);
		List<Bookmark> poList = mapper.selectByExample(example);
		
	}
	
	private BookmarkVO buildBookVO(Bookmark po) {
		BookmarkVO vo = new BookmarkVO();
//		TODO
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

		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, localDateTimeAdapter)
				.setPrettyPrinting()
				.create();
		String jsonString = gson.toJson(m);

		System.out.println(jsonString);
	}
}
