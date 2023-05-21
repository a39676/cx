package demo.image.service.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import demo.article.article.service.impl.ArticleOptionService;
import demo.automationTest.service.impl.AutomationTestOptionService;
import demo.common.service.ToolCommonService;
import demo.image.mapper.ImageStoreMapper;
import demo.image.mapper.ImageTagMapper;
import demo.image.pojo.constant.ImageConstant;
import demo.image.pojo.constant.ImageUrl;
import demo.image.pojo.po.ImageStore;
import demo.image.pojo.po.ImageStoreExample;
import demo.image.pojo.po.ImageTag;
import demo.image.pojo.po.ImageTagExample;
import demo.image.pojo.result.GetImgThirdPartyUrlInBatchResult;
import demo.image.pojo.result.ImgHandleSrcDataResult;
import demo.image.pojo.type.ImageTagType;
import demo.image.service.ImageService;
import demo.tool.telegram.service.TelegramService;
import image.pojo.dto.ImageSavingTransDTO;
import image.pojo.result.ImageSavingResult;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;
import toolPack.constant.FileSuffixNameConstant;

@Service
public class ImageServiceImpl extends ToolCommonService implements ImageService {

	@Autowired
	private ImageStoreMapper imgMapper;
	@Autowired
	private ImageTagMapper imageTagMapper;

	@Autowired
	private AutomationTestOptionService automationTestConstantService;
	@Autowired
	private ArticleOptionService articleOptionService;
	@Autowired
	private TelegramService telegramService;

	private final String DEFAULT_IMG_SAVING_FOLDER = "/home/u2/cx/images";
	private final Integer PHYSICS_DELETE_DELAY_DAYS = 5;
	private final Integer DEFAULT_IMG_LIVING_DAYS = 30;

	@Override
	public void getImage(HttpServletResponse response, String imgPK) {
		if (StringUtils.isBlank(imgPK)) {
			return;
		}

		Long imgId = systemOptionService.decryptPrivateKey(imgPK);
		if (imgId == null) {
			return;
		}

		ImageStore imgPO = imgMapper.selectByPrimaryKey(imgId);
		if (imgPO == null || (imgPO.getValidTime() != null && imgPO.getValidTime().isBefore(LocalDateTime.now()))
				|| StringUtils.isBlank(imgPO.getImageUrl())) {
			return;
		}

		if (imgPO.getImageUrl().startsWith("http")) {
			try {
				PrintWriter out = response.getWriter();
				if (imgPO.getImageUrl() != null && imgPO.getImageUrl().startsWith("http")) {
					out.println("redirect:/" + imgPO.getImageUrl());
				}
				out.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			getImageInBase64(response, imgPO);
		}
	}

	private void getImageInBase64(HttpServletResponse response, ImageStore imgPO) {
		try {
			File f = new File(imgPO.getImageUrl());
			BufferedImage originalImage = ImageIO.read(f);

			InputStream in = new FileInputStream(f);
			IOUtils.copy(in, response.getOutputStream());

			response.setContentType("image/jpeg");
			ImageIO.write(originalImage, "jpeg", response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getThumbnailImage(HttpServletResponse response, String imgPK) {
		getThumbnailImage(response, imgPK, null, null);
	}

	@Override
	public void getThumbnailImage(HttpServletResponse response, String imgPK, Integer width, Integer height) {
		if (StringUtils.isBlank(imgPK)) {
			return;
		}

		Long imgId = systemOptionService.decryptPrivateKey(imgPK);
		if (imgId == null) {
			return;
		}

		ImageStore imgPO = imgMapper.selectByPrimaryKey(imgId);
		if (imgPO == null || (imgPO.getValidTime() != null && imgPO.getValidTime().isBefore(LocalDateTime.now()))
				|| StringUtils.isBlank(imgPO.getImageUrl())) {
			return;
		}

		getThumbnail(response, imgPO, width, height);
	}

	private void getThumbnail(HttpServletResponse response, ImageStore imgPO, Integer widthInput, Integer heightInput) {
		Double maxWidth = 100D;
		if (widthInput != null) {
			maxWidth = widthInput.doubleValue();
		}
		Double maxHeight = 100D;
		if (heightInput != null) {
			maxHeight = heightInput.doubleValue();
		}

		try {
			File f = new File(imgPO.getImageUrl());
			BufferedImage originalImage = ImageIO.read(f);

			Double coe = 0D;

			Integer oWidth = originalImage.getWidth();
			Integer oHeight = originalImage.getHeight();

			Double outputWidth = null;
			Double outputHeight = null;

			if (oWidth < maxWidth && oHeight < maxHeight) {
				outputWidth = oWidth.doubleValue();
				outputHeight = oHeight.doubleValue();
			} else {
				if (oWidth > oHeight) {
					coe = oWidth / maxWidth;
					outputWidth = maxWidth;
					outputHeight = oHeight / coe;
				} else {
					coe = oHeight / maxHeight;
					outputHeight = maxHeight;
					outputWidth = oWidth / coe;
				}
			}

			// 创建缩略图
			int thumbnailWidth = outputWidth.intValue();
			int thumbnailHeight = outputHeight.intValue();
			int thumbnailHint = BufferedImage.TYPE_INT_RGB;
			Image thumbnailImage = originalImage.getScaledInstance(thumbnailWidth, thumbnailHeight, thumbnailHint);
			BufferedImage thumbnailBufferedImage = new BufferedImage(thumbnailWidth, thumbnailHeight, thumbnailHint);
			thumbnailBufferedImage.getGraphics().drawImage(thumbnailImage, 0, 0, null);

			// 将缩略图写入文件或输出到输出流中
			response.setContentType("image/jpeg");
			ImageIO.write(thumbnailBufferedImage, "jpeg", response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void __getImageByPath(HttpServletResponse response, String path) {
		if (StringUtils.isBlank(path)) {
			return;
		}

		try {
			File f = new File(path);
			InputStream in = new FileInputStream(f);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			IOUtils.copy(in, response.getOutputStream());
		} catch (Exception e) {
			return;
		}
	}

	@Override
	public ImageSavingResult imageSaving(ImageSavingTransDTO dto) {
		ImageSavingResult r = validImageSavingTransDTO(dto);
		if (r.isFail()) {
			return r;
		}

		r = saveImgHandler(dto);

		return r;
	}

	@Override
	public ImageSavingResult __saveImgFromBBT(ImageSavingTransDTO dto) {
		dto.setImgTagCode(ImageTagType.IMAGE_SAVING.getCode());
		ImageSavingResult r = validImageSavingTransDTO_forBBT(dto);
		if (r.isFail()) {
			return r;
		}

		r = __saveImgFromBBTHandler(dto);

		return r;
	}

	private ImageSavingResult validImageSavingTransDTO(ImageSavingTransDTO dto) {
		ImageSavingResult r = new ImageSavingResult();
		if (dto == null || StringUtils.isBlank(dto.getImgName()) || dto.getImgTagCode() == null) {
			r.failWithMessage("error data");
			return r;
		}

		ImageTagType tagType = ImageTagType.getType(dto.getImgTagCode());
		if (tagType == null) {
			r.failWithMessage("error data");
			return r;
		}

		if (dto.getValidTime() == null) {
			if (!isBigUser()) {
				dto.setValidTime(LocalDateTime.now().plusDays(DEFAULT_IMG_LIVING_DAYS));
			}
		} else {
			if (dto.getValidTime().isBefore(LocalDateTime.now())) {
				r.failWithMessage("error data");
				return r;
			}
		}

		r.setIsSuccess();
		return r;
	}

	private ImageSavingResult validImageSavingTransDTO_forBBT(ImageSavingTransDTO dto) {
		ImageSavingResult r = new ImageSavingResult();
		if (dto.getValidTime() == null) {
			dto.setValidTime(
					LocalDateTime.now().plusMonths(automationTestConstantService.getTestEventLiveLimitMonth()));
		} else {
			if (dto.getValidTime().isBefore(LocalDateTime.now())) {
				r.failWithMessage("error data");
				return r;
			}
		}
		r = validImageSavingTransDTO(dto);
		return r;
	}

	private ImageSavingResult __saveImgFromBBTHandler(ImageSavingTransDTO dto) {
		return saveImgHandler(dto);
	}

	private ImageSavingResult saveImgHandler(ImageSavingTransDTO dto) {
		ImageSavingResult r = new ImageSavingResult();
		ImageTagType imgTagType = ImageTagType.getType(dto.getImgTagCode());
		String newImgPath = null;
		try {
			if (imgTagType == null) {
				r.failWithMessage("error data");
				return r;
			}

			String imageStorePrefixPath = null;
			if (ImageTagType.IMAGE_SAVING.equals(imgTagType)) {
				imageStorePrefixPath = automationTestConstantService.getImageStorePrefixPath();
			} else if (ImageTagType.FROM_ARTICLE.equals(imgTagType)) {
				imageStorePrefixPath = articleOptionService.getArticleImageSavingFolder();
				dto.setValidTime(LocalDateTime.of(2999, 12, 31, 23, 59, 59));
			} else {
				imageStorePrefixPath = DEFAULT_IMG_SAVING_FOLDER;
			}

			if (StringUtils.isNotBlank(dto.getImgBase64Str())) {
				newImgPath = imageStorePrefixPath + File.separator + dto.getImgName();
				File imgFile = new File(newImgPath);
				if (!imgFile.getParentFile().exists()) {
					if (!imgFile.getParentFile().mkdirs()) {
						r.setMessage("Can NOT create saving folder");
						return r;
					}
				}

				String base64Image = dto.getImgBase64Str(); // .split(",")[1];
				byte[] imageBytes = Base64.getDecoder().decode(base64Image);
				FileUtils.writeByteArrayToFile(imgFile, imageBytes);
			} else {
				newImgPath = dto.getImgUrl();
			}

		} catch (Exception e) {
			r.failWithMessage("error data");
			return r;
		}

		ImageStore imgPO = new ImageStore();
		Long newImgId = snowFlake.getNextId();
		imgPO.setImageId(newImgId);
		imgPO.setImageUrl(newImgPath);
		imgPO.setImageName(dto.getImgName());
		imgPO.setValidTime(dto.getValidTime());
		int insertCount = imgMapper.insertSelective(imgPO);
		if (insertCount < 1) {
			r.failWithMessage("service error");
			return r;
		}

		ImageTag imgTagPO = new ImageTag();
		imgTagPO.setImageId(newImgId);
		imgTagPO.setTagId(imgTagType.getCode().longValue());
		insertCount = imageTagMapper.insertSelective(imgTagPO);
		if (insertCount < 1) {
			r.failWithMessage("service error");
			return r;
		}

		String imgPK = systemOptionService.encryptId(newImgId);
		try {
			String urlEncodeImgPk = URLEncoder.encode(imgPK, StandardCharsets.UTF_8.toString());
			r.setImgUrl(ImageUrl.ROOT + ImageUrl.GET_IMAGE + "/?imgPK=" + urlEncodeImgPk);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		r.setImgPK(imgPK);
		r.setIsSuccess();
		return r;
	}

	@Override
	public void imageCleanAndDeleteFile() {
		log.error("Get in imageCleanAndDeleteFile()");
		ImageStoreExample imgStoreExample = new ImageStoreExample();
		LocalDateTime limitDate = LocalDateTime.now().minusDays(PHYSICS_DELETE_DELAY_DAYS);
		log.error("Image limit date: " + limitDate);
		imgStoreExample.createCriteria().andValidTimeLessThan(limitDate);
		List<ImageStore> targetImgList = imgMapper.selectByExample(imgStoreExample);
		if (targetImgList == null || targetImgList.isEmpty()) {
			log.error("NO image will delete");
			return;
		}

		log.error("May delete " + targetImgList.size() + " images");

		// Collect into map(imgUrl : imgId)
		Map<String, Long> imgPathMap = new HashMap<String, Long>();
		for (ImageStore po : targetImgList) {
			if (!po.getImageUrl().startsWith("http")) {
				imgPathMap.put(po.getImageUrl(), po.getImageId());
				log.error("Will delete image id: " + po.getImageId());
			}
		}

		log.error("Before delete image file, imgPathMap: " + imgPathMap);
		sendTelegramMessage("Before delete image file, imgPathMap: " + imgPathMap);

		// Collect imgId that deleted
		File tmpFile = null;
		List<Long> targetImgIdList = new ArrayList<Long>();
		for (Entry<String, Long> m : imgPathMap.entrySet()) {
			tmpFile = new File(m.getKey());
			if (tmpFile.exists()) {
				if (tmpFile.delete()) {
					targetImgIdList.add(m.getValue());
					log.error("Call imageStoreMapper.deleteByPrimaryKey in ImageServiceImpl.imageCleanAndDeleteFile");
					int deleteCount = imgMapper.deleteByPrimaryKey(m.getValue());
					if (deleteCount != 1) {
						log.error(m.getValue() + ", delete failed");
					}
				}
			} else {
				targetImgIdList.add(m.getValue());
				log.error("Call imageStoreMapper.deleteByPrimaryKey in ImageServiceImpl.imageCleanAndDeleteFile");
				int deleteCount = imgMapper.deleteByPrimaryKey(m.getValue());
				if (deleteCount != 1) {
					log.error(m.getValue() + ", delete failed");
				}
			}
		}

		if (targetImgIdList == null || targetImgIdList.isEmpty()) {
			return;
		}

		// Delete image tag that image deleted
		ImageTagExample imgTagExample = new ImageTagExample();
		imgTagExample.createCriteria().andImageIdIn(targetImgIdList);
		imageTagMapper.deleteByExample(imgTagExample);
	}

	@Override
	public BufferedImage base64ToBufferedImg(String base64) {
		if (base64 == null || base64.length() > ImageConstant.imgBase64MaxSize) {
			return null;
		}

		BufferedImage image = null;
		byte[] imageByte = Base64.getDecoder().decode(base64);
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		try {
			/*
			 * FIXME 2020-04-26 暂时未有合适的 webm 支持库 考虑将此类小体积文件转交给 cloudinary 处理?
			 */
			image = ImageIO.read(bis);
			bis.close();
		} catch (Exception e) {
		}
		return image;
	}

	@Override
	public boolean imgSaveAsFile(String imageBase64Str, String filePath, String fileType) {
		File outputfile = new File(filePath);
		if (!outputfile.exists()) {
			try {
				outputfile.getParentFile().mkdirs();
			} catch (Exception e) {
				return false;
			}
		}
		try {

			byte[] imageBytes = Base64.getDecoder().decode(imageBase64Str);
			FileUtils.writeByteArrayToFile(outputfile, imageBytes);

			return true;
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public ImgHandleSrcDataResult imgHandleSrcData(String src) {
		ImgHandleSrcDataResult r = new ImgHandleSrcDataResult();
		int slashIndex = src.indexOf("/");
		int semicolonIndex = src.indexOf(";");
		int commaIndex = src.indexOf(",");

		if (semicolonIndex < 0 || slashIndex < 0 || commaIndex < 0) {
			return r;
		}
		String fileType = src.substring(slashIndex + 1, semicolonIndex);
		if (!FileSuffixNameConstant.IMAGE_SUFFIX.contains(fileType)) {
			return r;
		}

		r.setImgFileType(fileType);
		r.setBase64Str(src.split(",")[1]);
		r.setIsSuccess();
		return r;
	}

	@Override
	public void setImageInvalidAndWaitingDelete(Long imgId) {
		ImageStore record = new ImageStore();
		record.setImageId(imgId);
		record.setValidTime(LocalDateTime.now().minusMinutes(1));
		imgMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public void shortenImageValidTime(Long imgId, LocalDateTime invalidTime) {
		ImageStore image = imgMapper.selectByPrimaryKey(imgId);
		if (image == null) {
			return;
		}
		if (image.getValidTime().isBefore(invalidTime)) {
			return;
		}
		image.setValidTime(invalidTime);
		imgMapper.updateByPrimaryKeySelective(image);
	}

	@Override
	public void setImageValidTime(Long imgId, LocalDateTime invalidTime) {
		if (!isBigUser()) {
			return;
		}
		ImageStore po = new ImageStore();
		po.setImageId(imgId);
		po.setValidTime(invalidTime);
		imgMapper.updateByPrimaryKeySelective(po);
	}

	private void sendTelegramMessage(String msg) {
		telegramService.sendMessageByChatRecordId(TelegramBotType.CX_MESSAGE, msg, TelegramStaticChatID.MY_ID);
	}

	@Override
	public String getImageBase64(String imgPk) {
		Long imgId = systemOptionService.decryptPrivateKey(imgPk);
		return getImageBase64(imgId);
	}

	private String getImageBase64(Long imgId) {
		if (imgId == null) {
			return null;
		}

		ImageStore imgPO = imgMapper.selectByPrimaryKey(imgId);
		if (imgPO == null || (imgPO.getValidTime() != null && imgPO.getValidTime().isBefore(LocalDateTime.now()))
				|| StringUtils.isBlank(imgPO.getImageUrl())) {
			return null;
		}

		if (imgPO.getImageUrl().startsWith("http")) {
			return null;
		} else {
			byte[] fileContent = null;
			try {
				fileContent = FileUtils.readFileToByteArray(new File(imgPO.getImageUrl()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			String encodedString = Base64.getEncoder().encodeToString(fileContent);
			return encodedString;
		}

	}

	@Override
	public GetImgThirdPartyUrlInBatchResult getImgThirdPartyUrlBatchResult(List<String> imgPkList) {
		GetImgThirdPartyUrlInBatchResult r = new GetImgThirdPartyUrlInBatchResult();

		if (imgPkList == null || imgPkList.isEmpty()) {
			return r;
		}

		List<Long> imgIdList = systemOptionService.decryptPrivateKey(imgPkList);

		Map<String, String> imgPkMatchUrl = new HashMap<>();
		ImageStoreExample example = new ImageStoreExample();
		example.createCriteria().andImageIdIn(imgIdList);
		List<ImageStore> poList = imgMapper.selectByExample(example);

		for (ImageStore po : poList) {
			if (po.getImageUrl().startsWith("http")) {
				imgPkMatchUrl.put(systemOptionService.encryptId(po.getImageId()), po.getImageUrl());
			}
		}

		r.setImgPkMatchUrl(imgPkMatchUrl);
		r.setIsSuccess();
		return r;
	}
}
