package demo.article.article.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;

import demo.article.article.pojo.result.jsonRespon.ArticleFileSaveResult;
import demo.article.articleComment.mapper.ArticleCommentCountMapper;
import demo.article.articleComment.pojo.po.ArticleCommentCount;
import demo.base.system.service.HostnameService;
import demo.base.system.service.impl.RedisOriginalConnectService;
import demo.base.system.service.impl.SystemOptionService;
import demo.common.service.CommonService;
import demo.image.pojo.result.ImgHandleSrcDataResult;
import demo.image.pojo.type.ImageTagType;
import demo.image.service.ImageService;
import demo.tool.other.service.TextFilter;
import demo.tool.other.service.VisitDataService;
import image.pojo.dto.ImageSavingTransDTO;
import image.pojo.result.ImageSavingResult;
import toolPack.ioHandle.FileUtilCustom;
import toolPack.numericHandel.NumericUtilCustom;

public class ArticleCommonService extends CommonService {

	@Autowired
	private ArticleCommentCountMapper articleCommentCountMapper;

	@Autowired
	private ImageService imgService;
	@Autowired
	private FileUtilCustom ioUtil;
	@Autowired
	private TextFilter textFilter;
	@Autowired
	protected ArticleOptionService articleOptionService;
	@Autowired
	protected NumericUtilCustom numberUtil;
	@Autowired
	protected VisitDataService visitDataService;
	@Autowired
	protected SystemOptionService systemOptionService;
	@Autowired
	protected HostnameService hostnameService;
	@Autowired
	protected RedisOriginalConnectService redisOriginalConnectService;

	protected String sanitize(String content) {
		PolicyFactory filter = textFilter.getArticleFilter();
		return filter.sanitize(content);
	}

	/**
	 * 用于保存新建/编辑的文章, 在编辑情况下, creatorId 是编辑者ID, 不一定是原作者ID, 用于命名文件名
	 * 
	 * @param storePrefixPath
	 * @param creatorId
	 * @param content
	 * @return
	 * @throws IOException
	 */
	protected ArticleFileSaveResult saveArticleFile(String storePrefixPath, String content)
			throws IOException {
		String fileName = snowFlake.getNextId() + ".txt";
		String timeFolder = LocalDate.now().toString();
		File mainFolder = new File(storePrefixPath + File.separator + timeFolder);
		String finalFilePath = storePrefixPath + File.separator + timeFolder + File.separator + fileName;
		ArticleFileSaveResult result = new ArticleFileSaveResult();

		if (!mainFolder.exists()) {
			if (!mainFolder.mkdirs()) {
				result.setMessage("Service error");
				return result;
			}
		}

		Element doc = Jsoup.parse(content);
		Elements imgs = doc.select("img[src]");
		/* 解决如果文章内有本地上传的图片, 转到服务器硬盘保存, 并提供 url 访问, */
		for (Element s : imgs) {
			s.attr("src", imgSrcHandler(s.attr("src")));
		}

		content = doc.toString();

		Long maxArticleLength = articleOptionService.getMaxArticleLength();

		if (content.length() > maxArticleLength) {
			result.setMessage("Article too long");
			return result;
		}

		if (StringUtils.isBlank(content) || content.replaceAll("\\s", "").length() < 6) {
			result.setMessage("Article too short");
			return result;
		}

		StringBuffer sb = new StringBuffer();

		sb.append(content);

		ioUtil.byteToFile(sb.toString().getBytes(StandardCharsets.UTF_8), finalFilePath);

		result.setFilePath(finalFilePath);

		result.setIsSuccess();
		return result;
	}

	protected String imgSrcHandler(String src) {
		if (src == null) {
			return src;
		} else if (src.startsWith("http")) {
			return src;
		} else if (src.startsWith("data")) {
			return imgBase64ToImageStore(src);
		}
		return src;
	}

	private String imgBase64ToImageStore(String src) {
		ImgHandleSrcDataResult srcHandleResult = imgService.imgHandleSrcData(src);
		if (srcHandleResult.isFail()) {
			return "";
		}

		String filename = String.valueOf(snowFlake.getNextId()) + "." + srcHandleResult.getImgFileType();

		ImageSavingTransDTO dto = new ImageSavingTransDTO();
		dto.setImgName(filename);
		dto.setImgBase64Str(srcHandleResult.getBase64Str());
		dto.setImgTagCode(ImageTagType.FROM_ARTICLE.getCode());
		ImageSavingResult result = imgService.imageSaving(dto);
		return result.getImgUrl();
	}

	public void articleCommentCountingUp(Long articleId) {
		ArticleCommentCount po = articleCommentCountMapper.selectByPrimaryKey(articleId);
		if (po == null) {
			po = new ArticleCommentCount();
			po.setArticleId(articleId);
			po.setCounting(1L);
			articleCommentCountMapper.insertSelective(po);
		} else {
			po.setCounting(po.getCounting() + 1);
			articleCommentCountMapper.updateByPrimaryKey(po);
		}
	}

	public void articleCommentCountingDown(Long articleId) {
		ArticleCommentCount po = articleCommentCountMapper.selectByPrimaryKey(articleId);
		if (po == null) {
			return;
		} else {
			if (po.getCounting() > 1) {
				po.setCounting(po.getCounting() - 1);
				articleCommentCountMapper.updateByPrimaryKey(po);
			}
		}
	}
}
