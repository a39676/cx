package demo.promote.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.common.service.CommonService;
import demo.promote.mapper.PromoteVisitHistoryMapper;
import demo.promote.pojo.dto.PromoteImgDTO;
import demo.promote.pojo.po.PromoteVisitHistory;
import demo.promote.service.PromotePageService;

@Service
public class PromotePageServiceImpl extends CommonService implements PromotePageService {

	@Autowired
	private PromoteOptionService promoteOptionService;
	@Autowired
	private PromoteVisitHistoryMapper promoteVisitHistoryMapper;

	@Override
	public ModelAndView promote(String urlKey) {
		ModelAndView v = new ModelAndView("promoteJSP/promotePage");

		PromoteImgDTO dto = promoteOptionService.getPromoteImgDtoMap().get(urlKey);
		if (dto == null) {
			dto = promoteOptionService.getPromoteImgDtoMap().get("0");
		}

		insertVisitData(dto);

		v.addObject("promoteImgUrl", dto.getUrl());
		return v;
	}

	private void insertVisitData(PromoteImgDTO dto) {
		PromoteVisitHistory row = new PromoteVisitHistory();
		row.setId(snowFlake.getNextId());
		row.setImgId(dto.getId());
		promoteVisitHistoryMapper.insertSelective(row);
	}
}
