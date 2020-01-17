package demo.toyParts.afk.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.baseCommon.service.CommonService;
import demo.toyParts.afk.service.AFKService;

@Service
public class AFKServiceImpl extends CommonService implements AFKService {

	@Override
	public ModelAndView afk() {
		ModelAndView v = new ModelAndView("toyJSP/toyAFK");
		Double greenProbability = 51.69;
		Double blueProbability = 43.7;
		Double purpleProbability = 4.61;
		// 紫卡 = 蓝卡 * 9
		Double purpleToBlueProbability = purpleProbability * 9;

		Double blueTotalPropability = blueProbability + purpleToBlueProbability;

		v.addObject("greenProbability", greenProbability);
		v.addObject("blueProbability", blueProbability);
		v.addObject("purpleProbability", purpleProbability);
		v.addObject("purpleToBlueProbability", purpleToBlueProbability);
		
		Double tenHit = 27000D;
		
		Double blueCostDiamond = tenHit / blueTotalPropability;
		Double purpleCostDiamond = blueCostDiamond * 9;
		v.addObject("blueCostDiamond", blueCostDiamond);
		v.addObject("purpleCostDiamond", purpleCostDiamond);
		
		Double blueStoneCostDiamond = blueCostDiamond / 60;
		Double purpleStoneCostDiamond = purpleCostDiamond / 60;
		v.addObject("blueStoneCostDiamond", blueStoneCostDiamond);
		v.addObject("purpleStoneCostDiamond", purpleStoneCostDiamond);
		
		Double bigMonCardCostUSD = 15D;
		Double smallMonCardCostUSD = 5D;
		Double doubleMonCardCostUSD = bigMonCardCostUSD + smallMonCardCostUSD;
		v.addObject("bigMonCardCostUSD", bigMonCardCostUSD);
		v.addObject("smallMonCardCostUSD", smallMonCardCostUSD);
		v.addObject("doubleMonCardCostUSD", doubleMonCardCostUSD);
		
		Double bigMonCardTotalDiamond = 12980D;
		Double smallMonCardTotalDiamond = 3300D;
		Double doubleMonCardTotalDiamond = bigMonCardTotalDiamond + smallMonCardTotalDiamond;
		v.addObject("bigMonCardTotalDiamond", bigMonCardTotalDiamond);
		v.addObject("smallMonCardTotalDiamond", smallMonCardTotalDiamond);
		v.addObject("doubleMonCardTotalDiamond", doubleMonCardTotalDiamond);
		
		Double bigMonCardUSDToDiamond = bigMonCardTotalDiamond / bigMonCardCostUSD;
		Double smallMonCardUSDToDiamond = smallMonCardTotalDiamond / smallMonCardCostUSD;
		Double doubleMonCardUSDToDiamond = doubleMonCardTotalDiamond / doubleMonCardCostUSD;
		v.addObject("bigMonCardUSDToDiamond", bigMonCardUSDToDiamond);
		v.addObject("smallMonCardUSDToDiamond", smallMonCardUSDToDiamond);
		v.addObject("doubleMonCardUSDToDiamond", doubleMonCardUSDToDiamond);
		
		// 皇家犒赏令
		Double rewardCardCostUSD = 25D;
		Double rewardCardDiamond = 6100D;
		Double rewardCardPurpleStone = 610D;
		Double rewardTotalDimaond = rewardCardDiamond + rewardCardPurpleStone * purpleStoneCostDiamond;
		v.addObject("rewardCardCostUSD", rewardCardCostUSD);
		v.addObject("rewardCardDiamond", rewardCardDiamond);
		v.addObject("rewardCardPurpleStone", rewardCardPurpleStone);
		v.addObject("rewardTotalDimaond", rewardTotalDimaond);

		

		return v;
	}
}
