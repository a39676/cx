package demo.tool.taobao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.service.CommonService;
import demo.tool.taobao.mapper.TaobaoUpstreamSupplierMapper;
import demo.tool.taobao.pojo.po.TaobaoUpstreamSupplier;
import demo.tool.taobao.pojo.po.TaobaoUpstreamSupplierExample;
import demo.tool.taobao.service.TaobaoUpstreamSupplierService;

@Service
public class TaobaoUpstreamSupplierServiceImpl extends CommonService implements TaobaoUpstreamSupplierService {

	@Autowired
	private TaobaoUpstreamSupplierMapper mapper;

	@Override
	public CommonResult supplierHeatReduct() {
		TaobaoUpstreamSupplierExample example = new TaobaoUpstreamSupplierExample();
		example.createCriteria().andConnectIdIsNotNull();
		List<TaobaoUpstreamSupplier> supplierList = mapper.selectByExample(example);
		int minHeat = Integer.MAX_VALUE;
		TaobaoUpstreamSupplier supplier = null;
		for (int i = 0; i < supplierList.size(); i++) {
			supplier = supplierList.get(i);
			if (supplier.getHeat() == null) {
				supplier.setHeat(0);
				mapper.updateByPrimaryKeySelective(supplier);
			} else if (supplier.getHeat() > 0 && minHeat > supplier.getHeat()) {
				minHeat = supplier.getHeat();
			}
		}
		// 削减时, 保证不将最低热度削至0, 至少为1
		minHeat = minHeat - 1;
		for (int i = 0; i < supplierList.size(); i++) {
			supplier = supplierList.get(i);
			if (supplier.getHeat() > minHeat) {
				supplier.setHeat(supplier.getHeat() - minHeat);
				mapper.updateByPrimaryKeySelective(supplier);
			}
		}
		CommonResult r = new CommonResult();
		r.setIsSuccess();
		return r;
	}
}
