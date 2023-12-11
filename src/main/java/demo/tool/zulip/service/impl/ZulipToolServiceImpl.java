package demo.tool.zulip.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jamesnetherton.zulip.client.Zulip;
import com.github.jamesnetherton.zulip.client.api.message.Message;
import com.github.jamesnetherton.zulip.client.api.message.MessageRecipient;
import com.github.jamesnetherton.zulip.client.api.message.request.DeleteMessageApiRequest;
import com.github.jamesnetherton.zulip.client.api.message.request.GetMessagesApiRequest;
import com.github.jamesnetherton.zulip.client.exception.ZulipClientException;

import demo.common.service.CommonService;
import demo.tool.zulip.service.ZulipToolService;

@Service
public class ZulipToolServiceImpl extends CommonService implements ZulipToolService {

	@Autowired
	private ZulipOptionService zulipOptionService;

	@Override
	public void deleteMessageHistoryAutomation() {
		try {
			Zulip z = new Zulip(zulipOptionService.getEmail(), zulipOptionService.getApiKey(),
					zulipOptionService.getHost());
			GetMessagesApiRequest msgReq = z.messages().getMessages(999, 999, 0L);
			List<Message> history = msgReq.execute();
			Message msg = null;
			MessageRecipient recipient = null;
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime msgTimestamp = null;
			for (int i = 0; i < history.size(); i++) {
				msg = history.get(i);
				List<MessageRecipient> recipients = msg.getRecipients();
				if (recipients != null && !recipients.isEmpty()) {
					for (int j = 0; j < recipients.size(); j++) {
						recipient = recipients.get(j);
						if (zulipOptionService.getTargetUserEmailList().contains(recipient.getEmail())) {
							msgTimestamp = LocalDateTime.ofInstant(msg.getTimestamp(), ZoneId.of("UTC+8"));
							if (msgTimestamp.plusMinutes(zulipOptionService.getMessageLivingMinutes()).isBefore(now)) {
								deleteMsg(z, msg.getId());
							}
						}
					}
				}
			}
			z.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deleteMsg(Zulip z, long msgId) throws ZulipClientException {
		DeleteMessageApiRequest req = z.messages().deleteMessage(msgId);
		req.execute();
	}
}
