package demo.joy.character.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.joy.character.pojo.dto.CreateJoyCharacterCharacterDTO;
import demo.joy.character.pojo.result.GetCharacterDetailResult;

public interface JoyCharacterService {

	CommonResult createJoyCharacterCharacter(CreateJoyCharacterCharacterDTO dto);

	GetCharacterDetailResult getCharacterDetail();

}
