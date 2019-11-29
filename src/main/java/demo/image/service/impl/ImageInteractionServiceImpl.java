package demo.image.service.impl;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cloudinary.pojo.result.CloudinaryUploadResult;
import demo.baseCommon.service.CommonService;
import demo.cloudinary.service.CloudinaryService;
import demo.image.mapper.ImageCloudinaryMapper;
import demo.image.mapper.ImageStoreMapper;
import demo.image.mapper.ImageTagMapper;
import demo.image.pojo.ImageTag;
import demo.image.pojo.po.ImageCloudinary;
import demo.image.pojo.po.ImageStore;
import demo.image.pojo.type.ImageTagType;
import demo.image.service.ImageInteractionService;
import image.pojo.dto.UploadImageToCloudinaryDTO;
import image.pojo.result.UploadImageToCloudinaryResult;

@Service
public class ImageInteractionServiceImpl extends CommonService implements ImageInteractionService {

	@Autowired
	private ImageStoreMapper imageStoreMapper;
	@Autowired
	private ImageTagMapper imageTagMapper;
	@Autowired
	private ImageCloudinaryMapper imageCloudinaryMapper;
	@Autowired
	private CloudinaryService cloudinaryService;
	

	@Override
	public UploadImageToCloudinaryResult uploadImageToCloudinary(UploadImageToCloudinaryDTO dto) {
		UploadImageToCloudinaryResult r = new UploadImageToCloudinaryResult();
		if(StringUtils.isBlank(dto.getFilePath())) {
			r.failWithMessage("null param");
			return r;
		}
		
		File imgFile = new File(dto.getFilePath());
		if(!imgFile.exists()) {
			r.failWithMessage("file not exists");
			return r;
		}
		
		CloudinaryUploadResult uploadResult = cloudinaryService.uploadCore(imgFile);
		if(!uploadResult.isSuccess()) {
			r.failWithMessage("cloudinary upload fail");
			return r;
		}
		
		ImageStore imgPO = new ImageStore();
		Long imgId = snowFlake.getNextId();
		imgPO.setImageId(imgId);
		imgPO.setImageName(imgFile.getName());
		imgPO.setImageUrl(uploadResult.getSecureUrl());
		imageStoreMapper.insertSelective(imgPO);
		
		ImageCloudinary imgCloudPO = new ImageCloudinary();
		imgCloudPO.setImageId(imgId);
		imgCloudPO.setCloudinaryPublicId(uploadResult.getPublicId());
		imageCloudinaryMapper.insertSelective(imgCloudPO);
		
		ImageTag imgTagPO = new ImageTag();
		imgTagPO.setImageId(imgId);
		imgTagPO.setTagId(ImageTagType.autoTestImgToCloudinary.getCode().longValue());
		imageTagMapper.insertSelective(imgTagPO);
		
		r.setImgId(imgId);
		r.setImgUrl(uploadResult.getSecureUrl());
		r.setIsSuccess();
		
		return r;
	}
	
	public void cleanOldAutoTestUploadImage() {
//		TODO
	}
}
