package demo.tool.textMessageForward.telegram.pojo.dto.telegramDTO;

import java.util.List;

public class TelegramPollDTO {

	private String id;
	private String question;
	private TelegramPollOptionDTO options;
	private Long total_voter_count;
	private Boolean is_closed;
	private Boolean is_anonymous;
	private String type;
	private Boolean allows_multiple_answers;
	private Long correct_option_id;
	private String explanation;
	private List<TelegramMessageEntityDTO> explanation_entities;
	private Long open_period;
	private Long close_date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public TelegramPollOptionDTO getOptions() {
		return options;
	}

	public void setOptions(TelegramPollOptionDTO options) {
		this.options = options;
	}

	public Long getTotal_voter_count() {
		return total_voter_count;
	}

	public void setTotal_voter_count(Long total_voter_count) {
		this.total_voter_count = total_voter_count;
	}

	public Boolean getIs_closed() {
		return is_closed;
	}

	public void setIs_closed(Boolean is_closed) {
		this.is_closed = is_closed;
	}

	public Boolean getIs_anonymous() {
		return is_anonymous;
	}

	public void setIs_anonymous(Boolean is_anonymous) {
		this.is_anonymous = is_anonymous;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getAllows_multiple_answers() {
		return allows_multiple_answers;
	}

	public void setAllows_multiple_answers(Boolean allows_multiple_answers) {
		this.allows_multiple_answers = allows_multiple_answers;
	}

	public Long getCorrect_option_id() {
		return correct_option_id;
	}

	public void setCorrect_option_id(Long correct_option_id) {
		this.correct_option_id = correct_option_id;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public List<TelegramMessageEntityDTO> getExplanation_entities() {
		return explanation_entities;
	}

	public void setExplanation_entities(List<TelegramMessageEntityDTO> explanation_entities) {
		this.explanation_entities = explanation_entities;
	}

	public Long getOpen_period() {
		return open_period;
	}

	public void setOpen_period(Long open_period) {
		this.open_period = open_period;
	}

	public Long getClose_date() {
		return close_date;
	}

	public void setClose_date(Long close_date) {
		this.close_date = close_date;
	}

	@Override
	public String toString() {
		return "TelegramPollDTO [id=" + id + ", question=" + question + ", options=" + options + ", total_voter_count="
				+ total_voter_count + ", is_closed=" + is_closed + ", is_anonymous=" + is_anonymous + ", type=" + type
				+ ", allows_multiple_answers=" + allows_multiple_answers + ", correct_option_id=" + correct_option_id
				+ ", explanation=" + explanation + ", explanation_entities=" + explanation_entities + ", open_period="
				+ open_period + ", close_date=" + close_date + "]";
	}

}
