package demo.tool.educate.wordHelper.pojo.dto;

public class UpdateOrAppendWordDTO {
	private WordDTO inputWord;
	private boolean update = false;

	public WordDTO getInputWord() {
		return inputWord;
	}

	public void setInputWord(WordDTO inputWord) {
		this.inputWord = inputWord;
	}

	public boolean getUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	@Override
	public String toString() {
		return "UpdateOrAppendWordDTO [inputWord=" + inputWord + ", update=" + update + "]";
	}

}
