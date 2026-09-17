package demo.tool.other.service;

import org.springframework.web.servlet.ModelAndView;

import demo.tool.other.pojo.dto.MeituanOrderHtmlEditDTO;
import demo.tool.other.pojo.result.MeituanOrderHtmlEditResult;

public interface MeituanHtmlEditService {

	ModelAndView meituanHtmlTool();

	MeituanOrderHtmlEditResult meituanOrderHtmlEdit(MeituanOrderHtmlEditDTO dto);

	MeituanOrderHtmlEditResult meituanMenuHtmlElementCollect(MeituanOrderHtmlEditDTO dto);

}
