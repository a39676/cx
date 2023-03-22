package demo.interaction.wechatPay.service.impl;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.dto.EncryptDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.interaction.wechat.service.impl.WechatCommonService;
import demo.interaction.wechatPay.mapper.WechatPayCertificateMapper;
import demo.interaction.wechatPay.mapper.WechatPayJsApiTicketMapper;
import demo.interaction.wechatPay.pojo.po.WechatPayCertificate;
import demo.interaction.wechatPay.pojo.po.WechatPayJsApiTicket;
import demo.interaction.wechatPay.service.WechatPayService;
import wechatPaySdk.jsApi.pojo.dto.SaveJsApiTicketDTO;
import wechatPaySdk.jsApi.pojo.dto.WechatPayOptionDTO;
import wechatPaySdk.jsApi.pojo.result.GetWechatPayJsApiTicketResult;
import wechatPaySdk.pojo.dto.SaveCertificateDTO;
import wechatPaySdk.pojo.result.GetWechatPayCertificateResult;

@Service
public class WechatPayServiceImpl extends WechatCommonService implements WechatPayService {

	@Autowired
	private WechatPayOptionService wechatPayOptionService;
	@Autowired
	private WechatPayJsApiTicketMapper wechatPayJsApiTicketMapper;
	@Autowired
	private WechatPayCertificateMapper wechatPayCertMapper;

	@Override
	public EncryptDTO getWechatPayOptionDTO(EncryptDTO encrypt) {
		String managerCode = decryptEncryptDTO(encrypt, String.class);
		if (!wechatOptionService.getManagerCode().equals(managerCode)) {
			return new EncryptDTO();
		}

		WechatPayOptionDTO dto = new WechatPayOptionDTO();
		dto.setApiV3Key(wechatPayOptionService.getApiV3Key());
		dto.setMerchantId(wechatPayOptionService.getMerchantId());
		dto.setMerchantSerialNumber(wechatPayOptionService.getMerchantSerialNumber());
		dto.setPrivateKey(wechatPayOptionService.getPrivateKey());
		dto.setTicketLivingSecond(wechatPayOptionService.getJsApiTicketLivingSecond());
		dto.setCertificateLivingSecond(wechatPayOptionService.getCertificateLivingSecond());
		return encryptDTO(dto);
	}

	@Override
	public EncryptDTO updateJsApiTicket(EncryptDTO encryptedDTO) {
		CommonResult r = new CommonResult();
		EncryptDTO encryptedResult = null;

		SaveJsApiTicketDTO dto = decryptEncryptDTO(encryptedDTO, SaveJsApiTicketDTO.class);
		if (dto == null) {
			r.setMessage("DTO decrypt fail or parameter error");
			encryptedResult = encryptDTO(r);
			return encryptedResult;
		}

		if (StringUtils.isAnyBlank(dto.getJsApiTicket(), dto.getMerchantId())) {
			r.setMessage("Parameter error");
			encryptedResult = encryptDTO(r);
			return encryptedResult;
		}

		WechatPayJsApiTicket po = wechatPayJsApiTicketMapper.selectByPrimaryKey(dto.getMerchantId());
		int updateCount = 0;
		if (po == null) {
			po = new WechatPayJsApiTicket();
			po.setMerchantId(dto.getMerchantId());
			po.setTicket(dto.getJsApiTicket());
			po.setCreateTime(LocalDateTime.now().minusMinutes(5));
			updateCount = wechatPayJsApiTicketMapper.insert(po);
		} else {
			po.setTicket(dto.getJsApiTicket());
			po.setCreateTime(LocalDateTime.now().minusMinutes(5));
			updateCount = wechatPayJsApiTicketMapper.updateByPrimaryKey(po);
		}

		if (updateCount < 1) {
			r.setMessage("Saving error");
			encryptedResult = encryptDTO(r);
			return encryptedResult;
		}

		r.setIsSuccess();
		encryptedResult = encryptDTO(r);
		return encryptedResult;

	}

	@Override
	public EncryptDTO getJsApiTicket(EncryptDTO encryptDTO) {
		GetWechatPayJsApiTicketResult r = new GetWechatPayJsApiTicketResult();
		EncryptDTO encryptedResult = null;

		String merchantId = decryptEncryptDTO(encryptDTO, String.class);
		if (StringUtils.isBlank(merchantId)) {
			r.setMessage("Ticket NOT exists, please update it by yourself");
			encryptedResult = encryptDTO(r);
			return encryptedResult;
		}

		WechatPayJsApiTicket po = wechatPayJsApiTicketMapper.selectByPrimaryKey(merchantId);
		if (po == null) {
			r.setMessage("Ticket NOT exists, please update it by yourself");
			encryptedResult = encryptDTO(r);
			return encryptedResult;
		}

		LocalDateTime now = LocalDateTime.now();
		if (po.getCreateTime().plusSeconds(wechatPayOptionService.getJsApiTicketLivingSecond()).isBefore(now)) {
			r.setMessage("Ticket expired, please update it by yourself");
			encryptedResult = encryptDTO(r);
			return encryptedResult;
		}

		r.setIsSuccess();
		r.setJsApiTicket(po.getTicket());
		r.setTicketCreateTimeStr(localDateTimeHandler.dateToStr(po.getCreateTime()));
		encryptedResult = encryptDTO(r);
		return encryptedResult;
	}
	
	@Override
	public EncryptDTO updateCertificate(EncryptDTO encryptedDTO) {
		CommonResult r = new CommonResult();
		EncryptDTO encryptedResult = null;

		SaveCertificateDTO dto = decryptEncryptDTO(encryptedDTO, SaveCertificateDTO.class);
		if (dto == null) {
			r.setMessage("DTO decrypt fail or parameter error");
			encryptedResult = encryptDTO(r);
			return encryptedResult;
		}

		if (StringUtils.isAnyBlank(dto.getCertificate(), dto.getMerchantId())) {
			r.setMessage("Parameter error");
			encryptedResult = encryptDTO(r);
			return encryptedResult;
		}

		WechatPayCertificate po = wechatPayCertMapper.selectByPrimaryKey(dto.getMerchantId());
		int updateCount = 0;
		if (po == null) {
			po = new WechatPayCertificate();
			po.setMerchantId(dto.getMerchantId());
			po.setCertificate(dto.getCertificate());
			po.setCreateTime(LocalDateTime.now().minusMinutes(5));
			updateCount = wechatPayCertMapper.insert(po);
		} else {
			po.setCertificate(dto.getCertificate());
			po.setCreateTime(LocalDateTime.now().minusMinutes(5));
			updateCount = wechatPayCertMapper.updateByPrimaryKey(po);
		}

		if (updateCount < 1) {
			r.setMessage("Saving error");
			encryptedResult = encryptDTO(r);
			return encryptedResult;
		}

		r.setIsSuccess();
		encryptedResult = encryptDTO(r);
		return encryptedResult;

	}

	@Override
	public EncryptDTO getCertificate(EncryptDTO encryptDTO) {
		GetWechatPayCertificateResult r = new GetWechatPayCertificateResult();
		EncryptDTO encryptedResult = null;

		String merchantId = decryptEncryptDTO(encryptDTO, String.class);
		if (StringUtils.isBlank(merchantId)) {
			r.setMessage("Certificate NOT exists, please update it by yourself");
			encryptedResult = encryptDTO(r);
			return encryptedResult;
		}

		WechatPayCertificate po = wechatPayCertMapper.selectByPrimaryKey(merchantId);
		if (po == null) {
			r.setMessage("Certificate NOT exists, please update it by yourself");
			encryptedResult = encryptDTO(r);
			return encryptedResult;
		}

		LocalDateTime now = LocalDateTime.now();
		if (po.getCreateTime().plusSeconds(wechatPayOptionService.getCertificateLivingSecond()).isBefore(now)) {
			r.setMessage("Certificate expired, please update it by yourself");
			encryptedResult = encryptDTO(r);
			return encryptedResult;
		}

		r.setIsSuccess();
		r.setCertificate(po.getCertificate());
		r.setCertificateCreateTimeStr(localDateTimeHandler.dateToStr(po.getCreateTime()));
		encryptedResult = encryptDTO(r);
		return encryptedResult;
	}
}
