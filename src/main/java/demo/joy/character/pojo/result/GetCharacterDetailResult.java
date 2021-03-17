package demo.joy.character.pojo.result;

import demo.joy.character.pojo.vo.JoyCharacterVO;
import demo.joy.common.pojo.result.JoyCommonResult;

public class GetCharacterDetailResult extends JoyCommonResult {

	private JoyCharacterVO characterVO;

	public JoyCharacterVO getCharacterVO() {
		return characterVO;
	}

	public void setCharacterVO(JoyCharacterVO characterVO) {
		this.characterVO = characterVO;
	}

	@Override
	public String toString() {
		return "GetCharacterDetailResult [characterVO=" + characterVO + "]";
	}

}
