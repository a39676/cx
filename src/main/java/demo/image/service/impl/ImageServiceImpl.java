package demo.image.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
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

import demo.article.article.pojo.dto.LocalImageSavingDTO;
import demo.automationTest.service.impl.AutomationTestConstantService;
import demo.common.service.CommonService;
import demo.image.mapper.ImageStoreMapper;
import demo.image.mapper.ImageTagMapper;
import demo.image.pojo.constant.ImageConstant;
import demo.image.pojo.constant.ImageUrl;
import demo.image.pojo.po.ImageStore;
import demo.image.pojo.po.ImageStoreExample;
import demo.image.pojo.po.ImageTag;
import demo.image.pojo.po.ImageTagExample;
import demo.image.pojo.result.ImgHandleSrcDataResult;
import demo.image.pojo.type.ImageTagType;
import demo.image.service.ImageService;
import image.pojo.dto.ImageSavingTransDTO;
import image.pojo.result.ImageSavingResult;
import toolPack.constant.FileSuffixNameConstant;

@Service
public class ImageServiceImpl extends CommonService implements ImageService {

	@Autowired
	private ImageStoreMapper imgMapper;
	@Autowired
	private ImageTagMapper imageTagMapper;
	
	@Autowired
	private AutomationTestConstantService automationTestConstantService;
	
	@Override
	public void getImage(HttpServletResponse response, String imgPK) {
		if(StringUtils.isBlank(imgPK)) {
			return;
		}
		
		Long imgId = decryptPrivateKey(imgPK);
		if(imgId == null) {
			return;
		}
		
		ImageStore imgPO = imgMapper.selectByPrimaryKey(imgId);
		if(imgPO == null || StringUtils.isBlank(imgPO.getImageUrl())) {
			return;
		}
		
		try {
			File f = new File(imgPO.getImageUrl());
			InputStream in = new FileInputStream(f);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			IOUtils.copy(in, response.getOutputStream());
		} catch (Exception e) {
			return;
		}
	}
	
	@Override
	public void getImageByPath(HttpServletResponse response, String path) {
		if(StringUtils.isBlank(path)) {
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
		if(r.isFail()) {
			return r;
		}
		
		r = saveImgHandler(dto);
		
		return r;
	}
	
	@Override
	public ImageSavingResult imageSaving(LocalImageSavingDTO dto) {
		ImageSavingResult r = validImageSavingTransDTO(dto);
		if(r.isFail()) {
			return r;
		}
		
		r = saveImgHandler(dto);
		
		return r;
	}
	
	private ImageSavingResult saveImgHandler(LocalImageSavingDTO dto) {
		ImageSavingResult r = new ImageSavingResult();
		ImageTagType imgTagType = ImageTagType.getType(dto.getImgTagCode());
		try {
			if(imgTagType == null) {
				r.failWithMessage("error data");
				return r;
			}
			
			Paths.get(dto.getImgPath());
			File imgFile = new File(dto.getImgPath());
			if(!imgFile.exists()) {
				r.failWithMessage("error data");
				return r;
			}
		} catch (Exception e) {
			r.failWithMessage("error data");
			return r;
		}
		
		ImageStore imgPO = new ImageStore();
		Long newImgId = snowFlake.getNextId();
		imgPO.setImageId(newImgId);
		imgPO.setImageUrl(dto.getImgPath());
		imgPO.setImageName(dto.getImgName());
		imgPO.setValidTime(dto.getValidTime());
		int insertCount = imgMapper.insertSelective(imgPO);
		if(insertCount < 1) {
			r.failWithMessage("service error");
			return r;
		}
		
		ImageTag imgTagPO = new ImageTag();
		imgTagPO.setImageId(newImgId);
		imgTagPO.setTagId(imgTagType.getCode().longValue());
		insertCount = imageTagMapper.insertSelective(imgTagPO);
		if(insertCount < 1) {
			r.failWithMessage("service error");
			return r;
		}
		
		String imgPK = encryptId(newImgId);
		try {
			String urlEncodeImgPk = URLEncoder.encode(imgPK, StandardCharsets.UTF_8.toString());
			r.setImgUrl(ImageUrl.root + ImageUrl.getImage + "/?imgPK=" + urlEncodeImgPk);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		r.setImgPK(imgPK);
		r.setIsSuccess();
		return r;
	}

	private ImageSavingResult validImageSavingTransDTO(LocalImageSavingDTO dto) {
		ImageSavingResult r = new ImageSavingResult();
		if(dto == null 
				|| StringUtils.isBlank(dto.getImgName()) 
				|| dto.getImgTagCode() == null
				) {
			r.failWithMessage("error data");
			return r;
		}
		
		ImageTagType tagType = ImageTagType.getType(dto.getImgTagCode());
		if(tagType == null) {
			r.failWithMessage("error data");
			return r;
		}
		
		if(dto.getValidTime() == null) {
			if(!isBigUser()) {
				dto.setValidTime(LocalDateTime.now().plusMonths(1));
			}
		} else {
			if(dto.getValidTime().isBefore(LocalDateTime.now())) {
				r.failWithMessage("error data");
				return r;
			}
		}
		
		r.setIsSuccess();
		return r;
	}
	

	@Override
	public ImageSavingResult __saveImgFromBBT(ImageSavingTransDTO dto) {
		dto.setImgTagCode(ImageTagType.imageSaving.getCode());
		ImageSavingResult r = validImageSavingTransDTO_forBBT(dto);
		if(r.isFail()) {
			return r;
		}
		
		r = __saveImgFromBBTHandler(dto);
		
		return r;
	}
	
	private ImageSavingResult validImageSavingTransDTO(ImageSavingTransDTO dto) {
		ImageSavingResult r = new ImageSavingResult();
		if(dto == null 
				|| StringUtils.isBlank(dto.getImgName()) 
				|| dto.getImgTagCode() == null
				) {
			r.failWithMessage("error data");
			return r;
		}
		
		ImageTagType tagType = ImageTagType.getType(dto.getImgTagCode());
		if(tagType == null) {
			r.failWithMessage("error data");
			return r;
		}
		
		if(dto.getValidTime() == null) {
			if(!isBigUser()) {
				dto.setValidTime(LocalDateTime.now().plusMonths(1));
			}
		} else {
			if(dto.getValidTime().isBefore(LocalDateTime.now())) {
				r.failWithMessage("error data");
				return r;
			}
		}
		
		r.setIsSuccess();
		return r;
	}
	
	private ImageSavingResult validImageSavingTransDTO_forBBT(ImageSavingTransDTO dto) {
		ImageSavingResult r = new ImageSavingResult();
		if(dto.getValidTime() == null) {
			dto.setValidTime(LocalDateTime.now().plusMonths(automationTestConstantService.getTestEventLiveLimitMonth()));
		} else {
			if(dto.getValidTime().isBefore(LocalDateTime.now())) {
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
		String newImgFilePath = null;
		try {
			if(imgTagType == null) {
				r.failWithMessage("error data");
				return r;
			}
			
			String imageStorePrefixPath = automationTestConstantService.getImageStorePrefixPath();
			newImgFilePath = imageStorePrefixPath + File.separator + dto.getImgName();
			File imgFile = new File(newImgFilePath);
			
			String base64Image = dto.getImgBase64Str(); // .split(",")[1];
			byte[] imageBytes = Base64.getDecoder().decode(base64Image);
			FileUtils.writeByteArrayToFile(imgFile, imageBytes);
			
		} catch (Exception e) {
			r.failWithMessage("error data");
			return r;
		}
		
		ImageStore imgPO = new ImageStore();
		Long newImgId = snowFlake.getNextId();
		imgPO.setImageId(newImgId);
		imgPO.setImageUrl(newImgFilePath);
		imgPO.setImageName(dto.getImgName());
		imgPO.setValidTime(dto.getValidTime());
		int insertCount = imgMapper.insertSelective(imgPO);
		if(insertCount < 1) {
			r.failWithMessage("service error");
			return r;
		}
		
		ImageTag imgTagPO = new ImageTag();
		imgTagPO.setImageId(newImgId);
		imgTagPO.setTagId(imgTagType.getCode().longValue());
		insertCount = imageTagMapper.insertSelective(imgTagPO);
		if(insertCount < 1) {
			r.failWithMessage("service error");
			return r;
		}
		
		String imgPK = encryptId(newImgId);
		try {
			String urlEncodeImgPk = URLEncoder.encode(imgPK, StandardCharsets.UTF_8.toString());
			r.setImgUrl(ImageUrl.root + ImageUrl.getImage + "/?imgPK=" + urlEncodeImgPk);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		r.setImgPK(imgPK);
		r.setIsSuccess();
		return r;
	}

	@Override
	public void imageClean() {
		ImageStoreExample imgStoreExample = new ImageStoreExample();
		imgStoreExample.createCriteria().andValidTimeLessThan(LocalDateTime.now());
		List<ImageStore> targetImgList = imgMapper.selectByExample(imgStoreExample);
		if(targetImgList == null || targetImgList.isEmpty()) {
			return;
		}
		
		Map<String, Long> imgPathMap = new HashMap<String, Long>();
		for(ImageStore po : targetImgList) {
			if(!po.getImageUrl().startsWith("http")) {
				imgPathMap.put(po.getImageUrl(), po.getImageId());
			}
		}
		
		File tmpFile = null;
		List<Long> targetImgIdList = new ArrayList<Long>();
		for(Entry<String, Long> m : imgPathMap.entrySet()) {
			tmpFile = new File(m.getKey());
			if(tmpFile.exists()) {
				if(tmpFile.delete()) {
					targetImgIdList.add(m.getValue());
					imgMapper.deleteByPrimaryKey(m.getValue());
				}
			}
		}
		
		ImageTagExample imgTagExample = new ImageTagExample();
		imgTagExample.createCriteria().andImageIdIn(targetImgIdList);
		imageTagMapper.deleteByExample(imgTagExample);
		
	}

	@Override
	public BufferedImage base64ToBufferedImg(String base64) {
		if(base64 == null || base64.length() > ImageConstant.imgBase64MaxSize) {
			return null;
		}
		
		BufferedImage image = null;
		byte[] imageByte = Base64.getDecoder().decode(base64);
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		try {
			/*
			 *  FIXME 2020-04-26 暂时未有合适的 webp webm 支持库
			 *  考虑将此类小体积文件转交给 cloudinary 处理?
			 */
			image = ImageIO.read(bis);
			bis.close();
		} catch (Exception e) {
		}
		return image;
	}
	
	@Override
	public boolean imgSaveAsFile(BufferedImage image, String filePath, String fileType) {
		File outputfile = new File(filePath);
		if(!outputfile.exists()) {
			try {
				outputfile.getParentFile().mkdirs();
			} catch (Exception e) {
				return false;
			}
		}
		try {
			ImageIO.write(image, fileType, outputfile);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	@Override
	public boolean imgSaveAsFileDirect(String base64Str, String filePath, String fileType) {
		File outputfile = new File(filePath);
		if(!outputfile.exists()) {
			try {
				outputfile.getParentFile().mkdirs();
				outputfile.createNewFile();
			} catch (Exception e) {
				return false;
			}
		}
		
		byte[] imageByte = Base64.getDecoder().decode(base64Str);
		try {
			OutputStream stream = new FileOutputStream(filePath, false);
		    stream.write(imageByte);
		    stream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@Override
	public ImgHandleSrcDataResult imgHandleSrcData(String src) {
		ImgHandleSrcDataResult r = new ImgHandleSrcDataResult();
		int slashIndex = src.indexOf("/");
		int semicolonIndex = src.indexOf(";");
		int commaIndex = src.indexOf(",");
		
		if(semicolonIndex < 0 || slashIndex < 0 || commaIndex < 0) {
			return r;
		}
		String fileType = src.substring(slashIndex + 1, semicolonIndex);
		if(!FileSuffixNameConstant.IMAGE_SUFFIX.contains(fileType)) {
			return r;
		}
		
		r.setImgFileType(fileType);
		r.setBase64Str(src.split(",")[1]);
		r.setIsSuccess();
		return r;
	}

}
