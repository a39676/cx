package demo.joy.character.service;

import demo.common.pojo.result.CommonResultCX;
import demo.joy.character.pojo.dto.CreateJoyCharacterCharacterDTO;
import demo.joy.character.pojo.result.GetCharacterDetailResult;

public interface JoyCharacterService {

	CommonResultCX createJoyCharacterCharacter(CreateJoyCharacterCharacterDTO dto);

	GetCharacterDetailResult getCharacterDetail();

}
