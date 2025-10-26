package demo.tool.taobao.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.taobao.pojo.dto.TaobaoProductSourceAddDTO;
import demo.tool.taobao.pojo.dto.TaobaoProductSourceSearchDTO;
import demo.tool.taobao.pojo.po.TaobaoProductSource;

public interface TaobaoProductSourceService {

	CommonResult insert(TaobaoProductSourceAddDTO dto);

	ModelAndView search(TaobaoProductSourceSearchDTO dto);

	ModelAndView taobaoProductSource();

	List<TaobaoProductSource> getHotSaleList();

	List<TaobaoProductSource> getNewProductList();

	List<TaobaoProductSource> getRandomProductList(Integer size);

}
