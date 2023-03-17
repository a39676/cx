package demo.tool.other.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.google.zxing.NotFoundException;

import demo.common.service.ToolCommonService;
import demo.image.pojo.result.ImgHandleSrcDataResult;
import demo.image.service.ImageService;
import demo.tool.other.service.QrCodeService;
import demo.tool.other.service.TextFilter;
import toolPack.constant.FileSuffixNameConstant;
import toolPack.qrcode.QrCodeDecode;
import toolPack.qrcode.QrCodeGenerator;


@Service
public class QrCodeServiceImpl extends ToolCommonService implements QrCodeService {

	@Autowired
	private QrCodeGenerator generator;

	@Autowired
	private QrCodeDecode decode;

	@Autowired
	private ImageService imageService;

	@Autowired
	private TextFilter textFilter;

	@Override
	public String generatorToBase64(HttpServletResponse response, String content, Integer width, Integer hight) {
		try {
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			BufferedImage bi = generator.generation(content, width, hight);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(bi, FileSuffixNameConstant.PNG, bos);
			byte[] bytes = bos.toByteArray();
			String base64Str = Base64.getEncoder().encodeToString(bytes);
			
			return base64Str;
		} catch (Exception e) {
			log.error("QR code generation error, content: " + content);
			return null;
		}
	}
	
	@Override
	public void generator(HttpServletResponse response, String content, Integer width, Integer hight) {
		try {
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			ImageIO.write(generator.generation(content, width, hight), FileSuffixNameConstant.PNG, response.getOutputStream());
		} catch (Exception e) {
			log.error("QR code generation error, content: " + content);
		}
	}

	@Override
	public List<String> decodeFromContent(String content) {
		List<String> result = new ArrayList<>();

		PolicyFactory filter = textFilter.getArticleFilter();

		String tmpResult = null;
		if (content.startsWith("http")) {
			try {
				tmpResult = decodeByImageUrl(content);
				if (StringUtils.isNotBlank(tmpResult)) {
					result.add(filter.sanitize(tmpResult));
				}
			} catch (IOException e) {
			}
		} else {
			ImgHandleSrcDataResult srcHandleResult = imageService.imgHandleSrcData(content);
			if (srcHandleResult.isFail()) {
			}
			BufferedImage bufferImage = imageService.base64ToBufferedImg(srcHandleResult.getBase64Str());
			try {
				tmpResult = decode.decode(bufferImage);
				result.add(filter.sanitize(tmpResult));
			} catch (NotFoundException e) {
			}
		}

		return result;
	}

	@Override
	public String decodeByImageUrl(String urlStr) throws IOException {
		URL url = new URL(urlStr);
		BufferedImage image = ImageIO.read(url);

		try {
			return decode.decode(image);
		} catch (NotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

}
