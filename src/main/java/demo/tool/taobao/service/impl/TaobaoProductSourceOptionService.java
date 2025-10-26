package demo.tool.taobao.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import demo.common.service.CommonService;
import demo.config.customComponent.OptionFilePathConfigurer;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class TaobaoProductSourceOptionService extends CommonService {

	private List<Long> hotSaleIdList;
	private List<Long> newProductIdList;
	private List<Long> promotProductIdList;
	private Integer minNewProductIdListSize;

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(OptionFilePathConfigurer.TAOBAO_PRODUCT_SOURCE);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.TAOBAO_PRODUCT_SOURCE);
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, localDateTimeAdapter).create();
			TaobaoProductSourceOptionService tmp = gson.fromJson(jsonStr, TaobaoProductSourceOptionService.class);
			BeanUtils.copyProperties(tmp, this);

			log.error("taobao product source option loaded");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("taobao product source option loading error: " + e.getLocalizedMessage());
		}
	}

	public List<Long> getHotSaleIdList() {
		return hotSaleIdList;
	}

	public void setHotSaleIdList(List<Long> hotSaleIdList) {
		this.hotSaleIdList = hotSaleIdList;
	}

	public List<Long> getNewProductIdList() {
		return newProductIdList;
	}

	public void setNewProductIdList(List<Long> newProductIdList) {
		this.newProductIdList = newProductIdList;
	}

	public List<Long> getPromotProductIdList() {
		return promotProductIdList;
	}

	public void setPromotProductIdList(List<Long> promotProductIdList) {
		this.promotProductIdList = promotProductIdList;
	}

	public Integer getMinNewProductIdListSize() {
		return minNewProductIdListSize;
	}

	public void setMinNewProductIdListSize(Integer minNewProductIdListSize) {
		this.minNewProductIdListSize = minNewProductIdListSize;
	}

	@Override
	public String toString() {
		return "TaobaoProductSourceOptionService [hotSaleIdList=" + hotSaleIdList + ", newProductIdList="
				+ newProductIdList + ", promotProductIdList=" + promotProductIdList + ", minNewProductIdListSize="
				+ minNewProductIdListSize + "]";
	}

}
