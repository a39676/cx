package demo.tool.other.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.common.service.CommonService;
import demo.tool.other.pojo.dto.MeituanOrderHtmlEditDTO;
import demo.tool.other.pojo.result.MeituanOrderHtmlEditResult;
import demo.tool.other.service.MeituanHtmlEditService;

@Service
public class MeituanHtmlEditServiceImpl extends CommonService implements MeituanHtmlEditService {

	@Override
	public ModelAndView meituanHtmlTool() {
		ModelAndView view = new ModelAndView("toolJSP/publicTool/meituanHtmlTool");
		return view;
	}

	@Override
	public MeituanOrderHtmlEditResult meituanOrderHtmlEdit(MeituanOrderHtmlEditDTO dto) {
		MeituanOrderHtmlEditResult r = new MeituanOrderHtmlEditResult();
		List<BigDecimal> newPriceList = new ArrayList<>();

		String htmlStr = dto.getHtmlStr();
		BigDecimal total = BigDecimal.ZERO;
		Document doc = Jsoup.parse(htmlStr);
		Double rate = dto.getRate().doubleValue();

		// 1. 查找元素
		Elements prices = doc.select("div.foodPrice_qTQKCd");

		// 2. 修改文本内容
		for (Element priceEle : prices) {
//			System.out.println("修改前: " + priceEle.text());

			// 直接将文字更改为新的价格
			BigDecimal price = new BigDecimal(priceEle.text());
			BigDecimal newPrice = price.multiply(new BigDecimal(rate));
			total = total.add(newPrice);
			newPriceList.add(newPrice);
			priceEle.text(String.valueOf(newPrice.setScale(2, RoundingMode.HALF_UP)));

			// 如果内部需要插入包含标签的内容，可用 price.html("<span>￥35.00</span>");
		}

		// 3. 打印修改后的完整 HTML
//		System.out.println("修改后 HTML:\n" + doc.body().html());
		r.setHtmlStr(doc.body().html());
		r.setTotalPrice(total);
		r.setPriceList(newPriceList);
		r.setIsSuccess();

		return r;
	}

	@Override
	public MeituanOrderHtmlEditResult meituanMenuHtmlElementCollect(MeituanOrderHtmlEditDTO dto) {
		MeituanOrderHtmlEditResult r = new MeituanOrderHtmlEditResult();
		String htmlStr = dto.getHtmlStr();
		Document doc = Jsoup.parse(htmlStr);

		List<String> imgSrcList = getImgSrcList(doc);
		List<String> nameList = getNameList(doc);
		List<String> unitList = getUnitList(doc);
		List<String> priceList = getPriceList(doc);

		for (int i = 0; i < imgSrcList.size(); i++) {
			Element foodContent = generateFoodHtml(imgSrcList.get(i), nameList.get(i), priceList.get(i),
					unitList.get(i), "x1");
			r.setHtmlStr(r.getHtmlStr() + System.lineSeparator() + foodContent.outerHtml());
		}
		r.setIsSuccess();
		return r;
	}

	private List<String> getImgSrcList(Document doc) {
		Elements imgTags = doc.select("img");

		List<String> resultList = new ArrayList<>();

		for (Element img : imgTags) {
			String src = img.attr("src").trim();
			String width = img.attr("width").trim();
			String height = img.attr("height").trim();

			// 判断三个属性是否均不为空
			if (!StringUtils.isAnyEmpty(src, width, height)) {
				resultList.add(src);
			}
		}

		// 输出结果
//		System.out.println(resultList);
		return resultList;
	}

	private List<String> getNameList(Document doc) {
		// 1. 查找所有 class 以 "info_" 开头的 div
		Elements infoDivs = doc.select("div[class^=info_]");
		List<String> resultList = new ArrayList<>();

		for (Element infoDiv : infoDivs) {
			// 2. 在 infoDiv 内部查找 class 以 "name_" 开头的 div
			Element nameEl = infoDiv.selectFirst("div[class^=name_]");
			String name = nameEl != null ? nameEl.text() : "";

			// 输出结果
			resultList.add(name);
		}

		return resultList;
	}

	private List<String> getUnitList(Document doc) {
		// 1. 查找所有 class 以 "info_" 开头的 div
		Elements infoDivs = doc.select("div[class^=info_]");
		List<String> resultList = new ArrayList<>();

		for (Element infoDiv : infoDivs) {
			// 3. 在 infoDiv 内部查找 class 以 "unit_" 开头的 div
			Element unitEl = infoDiv.selectFirst("div[class^=unit_]");
			String unit = unitEl != null ? unitEl.text() : "";

			// 输出结果
			resultList.add(unit);
		}

		return resultList;
	}

	private List<String> getPriceList(Document doc) {
		// 1. 查找所有 class 以 "info_" 开头的 div
		Elements infoDivs = doc.select("div[class^=info_]");
		List<String> resultList = new ArrayList<>();

		for (Element infoDiv : infoDivs) {
			// 4. 在 infoDiv 内部查找 data-tag="price" 的 div
			Element priceEl = infoDiv.selectFirst("div[class^=cprice_]");
			String price = priceEl != null ? priceEl.text() : "";
			price = price.replaceAll("¥", "");

			resultList.add(price);
		}
		return resultList;
	}

	public Element generateFoodHtml(String imgSrc, String name, String price, String description, String count) {
		// 1. 创建根容器元素
		Element root = new Element(Tag.valueOf("div"), "").addClass("food_jk2rgX");

		// 2. 添加图片节点 (动态传入 imgSrc)
		root.appendElement("img").attr("src", imgSrc).addClass("foodPicture_x7oSwL");

		// 3. 构建 foodContent_uDcND5 内容区
		Element foodContent = root.appendElement("div").addClass("foodContent_uDcND5");

		// 3.1 构建 foodLine1_yRjEJT (名称、符号、价格)
		Element foodLine1 = foodContent.appendElement("div").addClass("foodLine1_yRjEJT");

		foodLine1.appendElement("div").addClass("foodName_Es9jW_").text(name);

//		foodLine1.appendElement("div").addClass("foodYen2_A6PSZn").text("¥");

		foodLine1.appendElement("div").addClass("foodPrice_qTQKCd").text(price);

		// 3.2 构建描述信息
		foodContent.appendElement("div").addClass("foodDescription_KTFFlz").text(description);

		// 3.3 构建数量
		foodContent.appendElement("div").addClass("foodCount_QBWTbQ").text(count);

		// 4. 返回格式化/未格式化的 HTML 文本
		// 如果需要与你输入的 HTML 完全一致的排版，开启 outputSettings 调整缩进：
//        root.ownerDocument(new Document("")); // 附加临时 Doc 以使用 Settings
//        root.ownerDocument().outputSettings().prettyPrint(true).indentAmount(1);
//        return root.outerHtml();
//		System.out.println(root.outerHtml());
		return root;
	}
}
