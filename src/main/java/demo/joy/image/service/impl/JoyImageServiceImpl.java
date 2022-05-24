package demo.joy.image.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import demo.image.pojo.result.ImgHandleSrcDataResult;
import demo.image.service.ImageService;
import demo.joy.common.pojo.constant.JoyUrl;
import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.common.pojo.type.JoyModuleType;
import demo.joy.image.mapper.JoyImageStoreMapper;
import demo.joy.image.pojo.constant.JoyImageUrl;
import demo.joy.image.pojo.dto.JoyImageUploadDTO;
import demo.joy.image.pojo.po.JoyImageStore;
import demo.joy.image.pojo.result.JoyImageUploadResult;
import demo.joy.image.service.JoyImageService;

@Scope("singleton")
@Service
public class JoyImageServiceImpl extends CommonService implements JoyImageService {

	@Autowired
	private JoyImageStoreMapper imageMapper;

	@Autowired
	private ImageService systemImgService;

	private Map<Long, String> idPathMap = new HashMap<>();

	@Override
	public String imgBase64Saving(String savingFolderPath, String srcStr) {
		ImgHandleSrcDataResult srcHandleResult = systemImgService.imgHandleSrcData(srcStr);
		if (srcHandleResult.isFail()) {
			return null;
		}

		BufferedImage bufferedImage = systemImgService.base64ToBufferedImg(srcHandleResult.getBase64Str());
		if (bufferedImage == null) {
			return null;
		}

		String imgSavingPath = saveBufferedImgAsFile(bufferedImage, savingFolderPath, srcHandleResult.getImgFileType());
		if (imgSavingPath == null) {
			return null;
		}

		return imgSavingPath;
	}

	private String saveBufferedImgAsFile(BufferedImage bufferedImage, String savingFolderPath, String fileType) {
		String filename = String.valueOf(snowFlake.getNextId()) + "." + fileType;

		String imgSavingPath = savingFolderPath + "/" + filename;

		if (systemImgService.imgSaveAsFile(bufferedImage, imgSavingPath, fileType)) {
			return imgSavingPath;
		} else {
			return null;
		}
	}

	@Override
	public void getImageById(HttpServletResponse response, Long id) {
		if (idPathMap.containsKey(id) && idPathMap.get(id) != null) {
			getImageByPath(response, idPathMap.get(id));
			return;
		}

		JoyImageStore po = imageMapper.selectByPrimaryKey(id);
		if (po != null && po.getImagePath() != null) {
			idPathMap.put(id, po.getImagePath());
		}

		getImageByPath(response, po.getImagePath());
	}

	@Override
	public JoyCommonResult cleanIdPathMap() {
		idPathMap.clear();
		JoyCommonResult r = new JoyCommonResult();
		r.setIsSuccess();
		return r;
	}

	private void getImageByPath(HttpServletResponse response, String path) {
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
	public JoyImageUploadResult uploadImage(JoyImageUploadDTO dto, String savingPathPrefix, JoyModuleType joyModuleType,
			Integer subModultTypeCode) {
		JoyImageUploadResult r = new JoyImageUploadResult();
		if (dto == null || StringUtils.isBlank(dto.getSrc())) {
			r.setMessage("Null data: src");
			return r;
		}

		if (joyModuleType == null || subModultTypeCode == null) {
			r.setMessage("Module param error");
			return r;
		}

		ImgHandleSrcDataResult srcHandleResult = systemImgService.imgHandleSrcData(dto.getSrc());
		if (srcHandleResult.isFail()) {
			r.setMessage(srcHandleResult.getMessage());
			return r;
		}

		String filename = String.valueOf(snowFlake.getNextId()) + "." + srcHandleResult.getImgFileType();
		r.setFilename(filename);

		String savingPath = imgBase64Saving(savingPathPrefix, dto.getSrc());
		if (savingPath == null) {
			r.setMessage("Upload saving error");
			return r;
		}

		JoyImageStore po = new JoyImageStore();
		po.setId(snowFlake.getNextId());
		po.setImagePath(savingPath);
		po.setModuleId(joyModuleType.getCode());
		po.setSubModuleId(subModultTypeCode);
		imageMapper.insertSelective(po);

		r.setImgId(po.getId());
		r.setImgSavePath(savingPath);
		r.setIsSuccess();
		return r;
	}

	@Override
	public String buildImageUrl(Long id) {
		return JoyUrl.ROOT + JoyImageUrl.ROOT + JoyImageUrl.GET_IMAGE_BY_ID + "?id=" + id;
	}
}
