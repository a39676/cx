package demo.tool.taobao.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.taobao.pojo.dto.AddTaobaoProductSourceDTO;
import demo.tool.taobao.pojo.po.TaobaoProductSource;

public interface TaobaoProductSourceService {

	CommonResult insert(AddTaobaoProductSourceDTO dto);

	ModelAndView search(AddTaobaoProductSourceDTO dto);

	ModelAndView taobaoProductSource();

	List<TaobaoProductSource> getHotSaleList();

	List<TaobaoProductSource> getNewProductList();

}
