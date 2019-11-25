package demo.image.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cloudinary.pojo.result.CloudinaryUploadResult;
import dateTimeHandle.DateTimeHandle;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.baseCommon.pojo.type.ResultTypeCX;
import demo.baseCommon.service.CommonService;
import demo.cloudinary.service.CloudinaryService;
import demo.image.mapper.ImageCacheMapper;
import demo.image.mapper.ImageCloudinaryMapper;
import demo.image.mapper.ImageStoreMapper;
import demo.image.mapper.ImageTagMapper;
import demo.image.mapper.ImageTagsMapper;
import demo.image.pojo.ImageCache;
import demo.image.pojo.ImageTag;
import demo.image.pojo.ImageTags;
import demo.image.pojo.dto.UploadImageToCloudinaryDTO;
import demo.image.pojo.po.ImageCloudinary;
import demo.image.pojo.po.ImageStore;
import demo.image.pojo.result.UploadImageToCloudinaryResult;
import demo.image.service.ImageService;
import demo.tool.pojo.constant.ToolPathConstant;
import encodeHandle.EncodeUtil;

@Service
public class ImageServiceImpl extends CommonService implements ImageService {

	@Autowired
	private ImageCacheMapper imageCacheMapper;
	@Autowired
	private ImageStoreMapper imageStoreMapper;
	@Autowired
	private ImageTagMapper imageTagMapper;
	@Autowired
	private ImageTagsMapper imageTagsMapper;
	private ImageCloudinaryMapper imageCloudinaryMapper;
	
	@Autowired
	private CloudinaryService cloudinaryService;
	
	private static List<ImageTags> imageTags = new ArrayList<ImageTags>();

	private String imageName = "fileNameForMD5";

	@Override
	public File getImageFromUrl(String urlStr, String savePath) {
		File f = new File(savePath);
		URL url;

		try {
			url = new URL(urlStr);
			ReadableByteChannel rbc = Channels.newChannel(url.openStream());
			FileOutputStream fos = new FileOutputStream(f.getAbsolutePath());
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
			return f;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		f = null;
		return f;
	}

	@Override
	public String getFileNameFromUrl(String urlStr) {
		Pattern pattern = Pattern.compile("^https?://(?:.*)(/\\S+\\.\\w{1,4})(?:\\?.*)?$");
		Matcher matcher = pattern.matcher(urlStr);
		if (matcher.find()) {
			return matcher.group(1).replaceAll("/", "");
		}
		return null;
	}

	@Override
	public boolean checkImageExists(String md5) {
		Integer result = imageStoreMapper.checkImageExistsByMD5(md5);
		if (result == 1) {
			return true;
		} else {
			return false;
		}
	}

	public void imageCacheDownload() {
		List<ImageCache> caches = imageCacheMapper.getImageCaches(5);
		List<Long> downLoadImageIds = new ArrayList<Long>();

		if (caches != null && caches.size() > 0) {
			for (ImageCache image : caches) {
				getImageFromUrl(image.getImageUrl(), ToolPathConstant.getTmpImagePath());
				downLoadImageIds.add(image.getImageId());
			}
		}

		imageCacheMapper.updateDownloadMark(downLoadImageIds);
	}

	protected String getImageMD5(String filePath) {
		File oldFile = new File(filePath);

		String newPath = oldFile.getAbsolutePath().replace(oldFile.getName(), imageName);
		File newFile = new File(newPath);

		oldFile.renameTo(newFile);
		EncodeUtil eu = new EncodeUtil();

		String imageMD5 = new String(eu.fileMD5(newPath));

		newFile.renameTo(oldFile);
		return imageMD5;
	}
	
	@Override
	public List<ImageTags> getImageTags() {
		if(imageTags.size() <= 0) {
			imageTags = imageTagsMapper.getTagList();
		}
		return imageTags;
	}

	@Override
	public int insertImageFromArticle(List<String> imageUrls, Long articleId) {
		if(imageUrls == null || imageUrls.size() < 1) {
			return 0;
		}
		String tmpFileName = "";
		ImageCache tmpImage;
		List<ImageCache> ics = new ArrayList<ImageCache>();
		
		for(String url : imageUrls) {
			tmpImage = new ImageCache();
			tmpImage.setImageUrl(url);
			tmpFileName = getFileNameFromUrl(url);
			tmpImage.setImageName(tmpFileName);
			tmpImage.setArticleId(articleId);
			ics.add(tmpImage);
		}
		
		return imageCacheMapper.batchInsert(ics);
	}

	@Override
	public List<ImageCache> getImageCacheListByArticleId(Long articleId) {
		if(articleId == null) {
			return new ArrayList<ImageCache>();
		}
		
		return imageCacheMapper.findCacheByArticleId(articleId);
	}
	
	@Override
	public CommonResultCX moveImageCacheToImageStore(Long articleId, Long channelId) {
		CommonResultCX result = new CommonResultCX();
		List<ImageCache> icList = getImageCacheListByArticleId(articleId);
		if(icList == null || icList.size() < 1) {
			result.fillWithResult(ResultTypeCX.success);
			return result;
		}
		ImageStore tmpIS = null;
		ImageTag tmpIT = null;
		List<ImageStore> isList = new ArrayList<ImageStore>();
		List<ImageTag> itList = new ArrayList<ImageTag>();
		List<Long> icIdList = new ArrayList<Long>();
		for(ImageCache ic : icList) {
			tmpIS = new ImageStore();
			tmpIT = new ImageTag();
			tmpIS.setImageId(ic.getImageId());
			tmpIS.setCreateTime(DateTimeHandle.dateToLocalDateTime(ic.getCreateTime()));
			tmpIS.setImageName(ic.getImageName());
			tmpIS.setImageUrl(ic.getImageUrl());
			tmpIT.setImageId(ic.getImageId());
			tmpIT.setTagId(channelId);
			isList.add(tmpIS);
			itList.add(tmpIT);
			icIdList.add(ic.getImageId());
		}
		imageStoreMapper.batchInsert(isList);
		
		imageCacheMapper.updateDownloadMark(icIdList);
		
		imageTagMapper.batchInsert(itList);
		
		imageCacheMapper.deleteDownloadedCache(icIdList);
		
		result.fillWithResult(ResultTypeCX.success);
		return result;
	}

	@Override
	public UploadImageToCloudinaryResult uploadImageToCloudinary(UploadImageToCloudinaryDTO dto) {
		UploadImageToCloudinaryResult r = new UploadImageToCloudinaryResult();
		if(StringUtils.isBlank(dto.getFilePath())) {
			r.fillWithResult(ResultTypeCX.nullParam);
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
		
		/*
		 * TODO
		 * 2019-11-25
		 * 是否需要加入  image_tag 数据
		 */
		
		r.setImgId(imgId);
		r.setImgUrl(uploadResult.getSecureUrl());
		r.setIsSuccess();
		
		return r;
	}
	
}
