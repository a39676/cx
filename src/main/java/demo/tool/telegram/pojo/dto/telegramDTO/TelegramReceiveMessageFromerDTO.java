package demo.tool.telegram.pojo.dto.telegramDTO;

public class TelegramReceiveMessageFromerDTO {

	private Long id;
	private Boolean is_bot;
	private String first_name;
	private String last_name;
	private String username;
	private String language_code;
	private Boolean is_premium;
	private Boolean added_to_attachment_menu;
	private Boolean can_join_groups;
	private Boolean can_read_all_group_messages;
	private Boolean supports_inline_queries;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIs_bot() {
		return is_bot;
	}

	public void setIs_bot(Boolean is_bot) {
		this.is_bot = is_bot;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLanguage_code() {
		return language_code;
	}

	public void setLanguage_code(String language_code) {
		this.language_code = language_code;
	}

	public Boolean getIs_premium() {
		return is_premium;
	}

	public void setIs_premium(Boolean is_premium) {
		this.is_premium = is_premium;
	}

	public Boolean getAdded_to_attachment_menu() {
		return added_to_attachment_menu;
	}

	public void setAdded_to_attachment_menu(Boolean added_to_attachment_menu) {
		this.added_to_attachment_menu = added_to_attachment_menu;
	}

	public Boolean getCan_join_groups() {
		return can_join_groups;
	}

	public void setCan_join_groups(Boolean can_join_groups) {
		this.can_join_groups = can_join_groups;
	}

	public Boolean getCan_read_all_group_messages() {
		return can_read_all_group_messages;
	}

	public void setCan_read_all_group_messages(Boolean can_read_all_group_messages) {
		this.can_read_all_group_messages = can_read_all_group_messages;
	}

	public Boolean getSupports_inline_queries() {
		return supports_inline_queries;
	}

	public void setSupports_inline_queries(Boolean supports_inline_queries) {
		this.supports_inline_queries = supports_inline_queries;
	}

	@Override
	public String toString() {
		return "TelegramReceiveMessageFromerDTO [id=" + id + ", is_bot=" + is_bot + ", first_name=" + first_name
				+ ", last_name=" + last_name + ", username=" + username + ", language_code=" + language_code
				+ ", is_premium=" + is_premium + ", added_to_attachment_menu=" + added_to_attachment_menu
				+ ", can_join_groups=" + can_join_groups + ", can_read_all_group_messages="
				+ can_read_all_group_messages + ", supports_inline_queries=" + supports_inline_queries + "]";
	}

}
