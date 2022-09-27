package demo.tool.telegram.pojo.dto.telegramDTO;

import java.util.List;

public class TelegramMessageDTO {

	private Long message_id;
	private TelegramUserDTO from;
	private TelegramChatDTO sender_chat;
	private Long date;
	private TelegramChatDTO chat;
	private TelegramUserDTO forward_from;
	private TelegramChatDTO forward_from_chat;
	private Long forward_from_message_id;
	private String forward_signature;
	private String forward_sender_name;
	private Long forward_date;
	private Boolean is_automatic_forward;
	private TelegramMessageDTO reply_to_message;
	private TelegramUserDTO via_bot;
	private Long edit_date;
	private Boolean has_protected_content;
	private String media_group_id;
	private String author_signature;
	private String text;
	private List<TelegramMessageEntityDTO> entities;
	private TelegramAnimationDTO animation;
	private TelegramAudioDTO audio;
	private TelegramDocumentDTO document;
	private List<TelegramPhotoSizeDTO> photo;
	private TelegramStickersDTO sticker;
	private TelegramVideoDTO video;
	private TelegramVideoNoteDTO video_note;
	private TelegramVoiceDTO voice;
	private String caption;
	private List<TelegramMessageEntityDTO> caption_entities;
	private TelegramContactDTO contact;
	private TelegramDiceDTO dice;
	private TelegramGameDTO game;
	private TelegramPollDTO poll;
	private TelegramVenueDTO venue;
	private TelegramLocationDTO location;
	private List<TelegramUserDTO> new_chat_members;
	private TelegramUserDTO left_chat_member;
	private String new_chat_title;
	private List<TelegramPhotoSizeDTO> new_chat_photo;
	private Boolean delete_chat_photo;
	private Boolean group_chat_created;
	private Boolean supergroup_chat_created;
	private Boolean channel_chat_created;
	private TelegramMessageAutoDeleteTimerChangedDTO message_auto_delete_timer_changed;
	private Long migrate_to_chat_id;
	private Long migrate_from_chat_id;
	private TelegramMessageDTO pinned_message;
	private TelegramInvoiceDTO invoice;
	private TelegramSuccessfulPaymentDTO successful_payment;
	private String connected_website;
	private TelegramPassportDataDTO passport_data;
	private TelegramProximityAlertTriggeredDTO proximity_alert_triggered;
	private TelegramVideoChatScheduledDTO video_chat_scheduled;
	private TelegramWebAppDataDTO web_app_data;
	private TelegramInlineKeyboardMarkupDTO reply_markup;

	public Long getMessage_id() {
		return message_id;
	}

	public void setMessage_id(Long message_id) {
		this.message_id = message_id;
	}

	public TelegramUserDTO getFrom() {
		return from;
	}

	public void setFrom(TelegramUserDTO from) {
		this.from = from;
	}

	public TelegramChatDTO getSender_chat() {
		return sender_chat;
	}

	public void setSender_chat(TelegramChatDTO sender_chat) {
		this.sender_chat = sender_chat;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public TelegramChatDTO getChat() {
		return chat;
	}

	public void setChat(TelegramChatDTO chat) {
		this.chat = chat;
	}

	public TelegramUserDTO getForward_from() {
		return forward_from;
	}

	public void setForward_from(TelegramUserDTO forward_from) {
		this.forward_from = forward_from;
	}

	public TelegramChatDTO getForward_from_chat() {
		return forward_from_chat;
	}

	public void setForward_from_chat(TelegramChatDTO forward_from_chat) {
		this.forward_from_chat = forward_from_chat;
	}

	public Long getForward_from_message_id() {
		return forward_from_message_id;
	}

	public void setForward_from_message_id(Long forward_from_message_id) {
		this.forward_from_message_id = forward_from_message_id;
	}

	public String getForward_signature() {
		return forward_signature;
	}

	public void setForward_signature(String forward_signature) {
		this.forward_signature = forward_signature;
	}

	public String getForward_sender_name() {
		return forward_sender_name;
	}

	public void setForward_sender_name(String forward_sender_name) {
		this.forward_sender_name = forward_sender_name;
	}

	public Long getForward_date() {
		return forward_date;
	}

	public void setForward_date(Long forward_date) {
		this.forward_date = forward_date;
	}

	public Boolean getIs_automatic_forward() {
		return is_automatic_forward;
	}

	public void setIs_automatic_forward(Boolean is_automatic_forward) {
		this.is_automatic_forward = is_automatic_forward;
	}

	public TelegramMessageDTO getReply_to_message() {
		return reply_to_message;
	}

	public void setReply_to_message(TelegramMessageDTO reply_to_message) {
		this.reply_to_message = reply_to_message;
	}

	public TelegramUserDTO getVia_bot() {
		return via_bot;
	}

	public void setVia_bot(TelegramUserDTO via_bot) {
		this.via_bot = via_bot;
	}

	public Long getEdit_date() {
		return edit_date;
	}

	public void setEdit_date(Long edit_date) {
		this.edit_date = edit_date;
	}

	public Boolean getHas_protected_content() {
		return has_protected_content;
	}

	public void setHas_protected_content(Boolean has_protected_content) {
		this.has_protected_content = has_protected_content;
	}

	public String getMedia_group_id() {
		return media_group_id;
	}

	public void setMedia_group_id(String media_group_id) {
		this.media_group_id = media_group_id;
	}

	public String getAuthor_signature() {
		return author_signature;
	}

	public void setAuthor_signature(String author_signature) {
		this.author_signature = author_signature;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<TelegramMessageEntityDTO> getEntities() {
		return entities;
	}

	public void setEntities(List<TelegramMessageEntityDTO> entities) {
		this.entities = entities;
	}

	public TelegramAnimationDTO getAnimation() {
		return animation;
	}

	public void setAnimation(TelegramAnimationDTO animation) {
		this.animation = animation;
	}

	public TelegramAudioDTO getAudio() {
		return audio;
	}

	public void setAudio(TelegramAudioDTO audio) {
		this.audio = audio;
	}

	public TelegramDocumentDTO getDocument() {
		return document;
	}

	public void setDocument(TelegramDocumentDTO document) {
		this.document = document;
	}

	public List<TelegramPhotoSizeDTO> getPhoto() {
		return photo;
	}

	public void setPhoto(List<TelegramPhotoSizeDTO> photo) {
		this.photo = photo;
	}

	public TelegramStickersDTO getSticker() {
		return sticker;
	}

	public void setSticker(TelegramStickersDTO sticker) {
		this.sticker = sticker;
	}

	public TelegramVideoDTO getVideo() {
		return video;
	}

	public void setVideo(TelegramVideoDTO video) {
		this.video = video;
	}

	public TelegramVideoNoteDTO getVideo_note() {
		return video_note;
	}

	public void setVideo_note(TelegramVideoNoteDTO video_note) {
		this.video_note = video_note;
	}

	public TelegramVoiceDTO getVoice() {
		return voice;
	}

	public void setVoice(TelegramVoiceDTO voice) {
		this.voice = voice;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public List<TelegramMessageEntityDTO> getCaption_entities() {
		return caption_entities;
	}

	public void setCaption_entities(List<TelegramMessageEntityDTO> caption_entities) {
		this.caption_entities = caption_entities;
	}

	public TelegramContactDTO getContact() {
		return contact;
	}

	public void setContact(TelegramContactDTO contact) {
		this.contact = contact;
	}

	public TelegramDiceDTO getDice() {
		return dice;
	}

	public void setDice(TelegramDiceDTO dice) {
		this.dice = dice;
	}

	public TelegramGameDTO getGame() {
		return game;
	}

	public void setGame(TelegramGameDTO game) {
		this.game = game;
	}

	public TelegramPollDTO getPoll() {
		return poll;
	}

	public void setPoll(TelegramPollDTO poll) {
		this.poll = poll;
	}

	public TelegramVenueDTO getVenue() {
		return venue;
	}

	public void setVenue(TelegramVenueDTO venue) {
		this.venue = venue;
	}

	public TelegramLocationDTO getLocation() {
		return location;
	}

	public void setLocation(TelegramLocationDTO location) {
		this.location = location;
	}

	public List<TelegramUserDTO> getNew_chat_members() {
		return new_chat_members;
	}

	public void setNew_chat_members(List<TelegramUserDTO> new_chat_members) {
		this.new_chat_members = new_chat_members;
	}

	public TelegramUserDTO getLeft_chat_member() {
		return left_chat_member;
	}

	public void setLeft_chat_member(TelegramUserDTO left_chat_member) {
		this.left_chat_member = left_chat_member;
	}

	public String getNew_chat_title() {
		return new_chat_title;
	}

	public void setNew_chat_title(String new_chat_title) {
		this.new_chat_title = new_chat_title;
	}

	public List<TelegramPhotoSizeDTO> getNew_chat_photo() {
		return new_chat_photo;
	}

	public void setNew_chat_photo(List<TelegramPhotoSizeDTO> new_chat_photo) {
		this.new_chat_photo = new_chat_photo;
	}

	public Boolean getDelete_chat_photo() {
		return delete_chat_photo;
	}

	public void setDelete_chat_photo(Boolean delete_chat_photo) {
		this.delete_chat_photo = delete_chat_photo;
	}

	public Boolean getGroup_chat_created() {
		return group_chat_created;
	}

	public void setGroup_chat_created(Boolean group_chat_created) {
		this.group_chat_created = group_chat_created;
	}

	public Boolean getSupergroup_chat_created() {
		return supergroup_chat_created;
	}

	public void setSupergroup_chat_created(Boolean supergroup_chat_created) {
		this.supergroup_chat_created = supergroup_chat_created;
	}

	public Boolean getChannel_chat_created() {
		return channel_chat_created;
	}

	public void setChannel_chat_created(Boolean channel_chat_created) {
		this.channel_chat_created = channel_chat_created;
	}

	public TelegramMessageAutoDeleteTimerChangedDTO getMessage_auto_delete_timer_changed() {
		return message_auto_delete_timer_changed;
	}

	public void setMessage_auto_delete_timer_changed(
			TelegramMessageAutoDeleteTimerChangedDTO message_auto_delete_timer_changed) {
		this.message_auto_delete_timer_changed = message_auto_delete_timer_changed;
	}

	public Long getMigrate_to_chat_id() {
		return migrate_to_chat_id;
	}

	public void setMigrate_to_chat_id(Long migrate_to_chat_id) {
		this.migrate_to_chat_id = migrate_to_chat_id;
	}

	public Long getMigrate_from_chat_id() {
		return migrate_from_chat_id;
	}

	public void setMigrate_from_chat_id(Long migrate_from_chat_id) {
		this.migrate_from_chat_id = migrate_from_chat_id;
	}

	public TelegramMessageDTO getPinned_message() {
		return pinned_message;
	}

	public void setPinned_message(TelegramMessageDTO pinned_message) {
		this.pinned_message = pinned_message;
	}

	public TelegramInvoiceDTO getInvoice() {
		return invoice;
	}

	public void setInvoice(TelegramInvoiceDTO invoice) {
		this.invoice = invoice;
	}

	public TelegramSuccessfulPaymentDTO getSuccessful_payment() {
		return successful_payment;
	}

	public void setSuccessful_payment(TelegramSuccessfulPaymentDTO successful_payment) {
		this.successful_payment = successful_payment;
	}

	public String getConnected_website() {
		return connected_website;
	}

	public void setConnected_website(String connected_website) {
		this.connected_website = connected_website;
	}

	public TelegramPassportDataDTO getPassport_data() {
		return passport_data;
	}

	public void setPassport_data(TelegramPassportDataDTO passport_data) {
		this.passport_data = passport_data;
	}

	public TelegramProximityAlertTriggeredDTO getProximity_alert_triggered() {
		return proximity_alert_triggered;
	}

	public void setProximity_alert_triggered(TelegramProximityAlertTriggeredDTO proximity_alert_triggered) {
		this.proximity_alert_triggered = proximity_alert_triggered;
	}

	public TelegramVideoChatScheduledDTO getVideo_chat_scheduled() {
		return video_chat_scheduled;
	}

	public void setVideo_chat_scheduled(TelegramVideoChatScheduledDTO video_chat_scheduled) {
		this.video_chat_scheduled = video_chat_scheduled;
	}

	public TelegramWebAppDataDTO getWeb_app_data() {
		return web_app_data;
	}

	public void setWeb_app_data(TelegramWebAppDataDTO web_app_data) {
		this.web_app_data = web_app_data;
	}

	public TelegramInlineKeyboardMarkupDTO getReply_markup() {
		return reply_markup;
	}

	public void setReply_markup(TelegramInlineKeyboardMarkupDTO reply_markup) {
		this.reply_markup = reply_markup;
	}

	@Override
	public String toString() {
		return "TelegramMessageDTO [message_id=" + message_id + ", from=" + from + ", sender_chat=" + sender_chat
				+ ", date=" + date + ", chat=" + chat + ", forward_from=" + forward_from + ", forward_from_chat="
				+ forward_from_chat + ", forward_from_message_id=" + forward_from_message_id + ", forward_signature="
				+ forward_signature + ", forward_sender_name=" + forward_sender_name + ", forward_date=" + forward_date
				+ ", is_automatic_forward=" + is_automatic_forward + ", reply_to_message=" + reply_to_message
				+ ", via_bot=" + via_bot + ", edit_date=" + edit_date + ", has_protected_content="
				+ has_protected_content + ", media_group_id=" + media_group_id + ", author_signature="
				+ author_signature + ", text=" + text + ", entities=" + entities + ", animation=" + animation
				+ ", audio=" + audio + ", document=" + document + ", photo=" + photo + ", sticker=" + sticker
				+ ", video=" + video + ", video_note=" + video_note + ", voice=" + voice + ", caption=" + caption
				+ ", caption_entities=" + caption_entities + ", contact=" + contact + ", dice=" + dice + ", game="
				+ game + ", poll=" + poll + ", venue=" + venue + ", location=" + location + ", new_chat_members="
				+ new_chat_members + ", left_chat_member=" + left_chat_member + ", new_chat_title=" + new_chat_title
				+ ", new_chat_photo=" + new_chat_photo + ", delete_chat_photo=" + delete_chat_photo
				+ ", group_chat_created=" + group_chat_created + ", supergroup_chat_created=" + supergroup_chat_created
				+ ", channel_chat_created=" + channel_chat_created + ", message_auto_delete_timer_changed="
				+ message_auto_delete_timer_changed + ", migrate_to_chat_id=" + migrate_to_chat_id
				+ ", migrate_from_chat_id=" + migrate_from_chat_id + ", pinned_message=" + pinned_message + ", invoice="
				+ invoice + ", successful_payment=" + successful_payment + ", connected_website=" + connected_website
				+ ", passport_data=" + passport_data + ", proximity_alert_triggered=" + proximity_alert_triggered
				+ ", video_chat_scheduled=" + video_chat_scheduled + ", web_app_data=" + web_app_data
				+ ", reply_markup=" + reply_markup + "]";
	}

}
