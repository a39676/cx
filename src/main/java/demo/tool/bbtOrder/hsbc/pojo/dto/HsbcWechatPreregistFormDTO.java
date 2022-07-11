package demo.tool.bbtOrder.hsbc.pojo.dto;

import java.util.Arrays;
import java.util.List;

public class HsbcWechatPreregistFormDTO {

	private List<String> branchCityList = Arrays.asList("北京", "揭阳", "重庆", "江门", "云浮", "唐山", "阳江", "哈尔滨", "清远", "天津",
			"成都", "太原", "深圳", "惠州", "珠海", "汕尾", "青岛", "佛山", "苏州", "潮州", "河源", "杭州", "郑州", "肇庆", "无锡", "沈阳", "广州", "大连",
			"宁波", "中山", "茂名", "湛江", "上海", "梅州", "韶关", "西安", "东莞", "南京", "武汉", "厦门", "汕头", "合肥", "济南");

	public List<String> getBranchCityList() {
		return branchCityList;
	}

	public void setBranchCityList(List<String> branchCityList) {
		this.branchCityList = branchCityList;
	}

	@Override
	public String toString() {
		return "HsbcWechatPreregistFormDTO [branchCityList=" + branchCityList + "]";
	}

}