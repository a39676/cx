package demo.interaction.wechat.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.dto.EncryptDTO;
import demo.interaction.wechat.service.WechatNotifyService;
import toolPack.encryptHandle.EncryptUtil;

@Service
public class WechatNotifyServiceImpl extends WechatCommonService implements WechatNotifyService {

	@Override
	public void forwardNotifyFromWechatSdk(EncryptDTO dto) {
		if (dto == null || StringUtils.isBlank(dto.getEncryptedStr())) {
			return;
		}
		String msg = decryptEncryptDTO(dto, String.class);
		if (msg == null) {
			sendTelegramMessage("收到 Wechat SDK 的通知, 但解码失败: " + dto.getEncryptedStr());
		} else {
			sendTelegramMessage(msg);
		}
	}

	public static void main(String[] args) {
		EncryptUtil encryptUtil = new EncryptUtil();
		try {
			String encryptedStr = "EZuJ6qZNUINPEFyDwSk+EOp0cstIgWafEbhljeuFOBcAmCUZsSAJcp4gNddg9bqmZMAZuoMlca6nFJQ5IA3O8DEKLJdaGYhp2iwxeZFeNjCXJM/sG/q0vgIDPwlwevtJTNiwt6rHuCz0yOF0Iun7wljDNaKV9XkyUZ13cr4kToNkLkhI35kVB5S23rlBoxx6NNNhxn4iktZYzaG+FCeaf/1PgCaQGfoO6tKv23BPfl8kdjAatbjliVH9gqDd/76zMTyavQJKoE4DIfAuYajoKhyMIP6gjJHgO0GFa4ke3ZKJRTketGHEd6W+g/fJ1HdcWrX1h11KAJiNWJOIIelQ1Z3KYJS7Rz/j/tcOq6oD8W8bQNLqRd0CDXeVn/LIErd3ZqE8C89TovE3qUGn0J3h1iD07QazXggj1CtDNzcjWVRh7Z5mWX3AdAqzS5w8rbR0ju0mgdaMUCIYzKtdJj+KWq5Orf87wa3/8e2k6Cx/3+is6YIotadLLvzJNQKIRAoGm5PaiJcRwrraammxSmqeai8st7e50XPvDKaO4gWuGNBXcojmixznY2Jimn7jTuQPAYendir5SyLd8cs8vwgXjWlJNt5gyyCkv+5rPXy+GN/sRpZ3eUyzw5FxZ++9AakmJCSxMce3fyZn4SMMQ+prqA==";
			String decryptedStr = encryptUtil.aesDecrypt("91de13b7b2b44473", "851757f12b5045f0", encryptedStr);
			System.out.println(decryptedStr);
		} catch (Exception e) {
			
		}
	}
}
