package demo.image.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.image.mapper.ImageStoreMapper;
import demo.image.mapper.ImageTagMapper;
import demo.image.pojo.constant.ImageUrl;
import demo.image.pojo.po.ImageStore;
import demo.image.pojo.po.ImageStoreExample;
import demo.image.pojo.po.ImageTag;
import demo.image.pojo.po.ImageTagExample;
import demo.image.pojo.type.ImageTagType;
import demo.image.service.ImageService;
import image.pojo.dto.ImageSavingTransDTO;
import image.pojo.result.ImageSavingResult;

@Service
public class ImageServiceImpl extends CommonService implements ImageService {

	@Autowired
	private ImageStoreMapper imgMapper;
	@Autowired
	private ImageTagMapper imageTagMapper;
	
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
	public ImageSavingResult imageSaving(ImageSavingTransDTO dto) {
		ImageSavingResult r = validImageSavingTransDTO(dto);
		if(r.isFail()) {
			return r;
		}
		
		r = imageStoreSaving(dto);
		
		return r;
	}
	
	private ImageSavingResult validImageSavingTransDTO(ImageSavingTransDTO dto) {
		ImageSavingResult r = new ImageSavingResult();
		if(dto == null 
				|| StringUtils.isAnyBlank(dto.getImgPath(), dto.getImgName()) 
				|| !dto.getImgPath().endsWith(dto.getImgName())
				) {
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

	private ImageSavingResult imageStoreSaving(ImageSavingTransDTO dto) {
		ImageSavingResult r = new ImageSavingResult();
		
		try {
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
		imgTagPO.setTagId(ImageTagType.imageSaving.getCode().longValue());
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
}
