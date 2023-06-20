package demo.tool.wordHelper.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.service.CommonService;
import demo.tool.wordHelper.pojo.dto.CustomerDictionaryDTO;
import demo.tool.wordHelper.pojo.dto.GetRandomWordDTO;
import demo.tool.wordHelper.pojo.dto.UpdateOrAppendWordDTO;
import demo.tool.wordHelper.pojo.dto.WordDTO;
import demo.tool.wordHelper.pojo.dto.WordDayLineDTO;
import demo.tool.wordHelper.pojo.result.GetRandomWordResult;
import demo.tool.wordHelper.service.WordHelperService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class WordHelperServiceImpl extends CommonService implements WordHelperService {

	private String dictionarySavingFolderPathStr = "/home/u2/cx/wordHelper";

	private CustomerDictionaryDTO getCustomerDictionaryDTO() {
		Long userId = baseUtilCustom.getUserId();
		FileUtilCustom fileUtil = new FileUtilCustom();
		String content = null;
		try {
			content = fileUtil.getStringFromFile(dictionarySavingFolderPathStr + "/" + userId + ".json");
		} catch (Exception e) {
			content = "{}";
		}
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(content, CustomerDictionaryDTO.class);
	}

	private void save(String content) {
		Long userId = baseUtilCustom.getUserId();
		FileUtilCustom fileUtil = new FileUtilCustom();
		fileUtil.byteToFile(content, dictionarySavingFolderPathStr + "/" + userId + ".json");
	}

	private boolean containWord(CustomerDictionaryDTO dictionary, WordDTO word) {
		if (dictionary.getWordDateLineList() == null || dictionary.getWordDateLineList().isEmpty()) {
			return false;
		}

		for (WordDayLineDTO wordDateLine : dictionary.getWordDateLineList()) {
			for (WordDTO wordExists : wordDateLine.getWordList()) {
				if (wordExists.getEn().equals(word.getEn())) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public CommonResult addNewWord(WordDTO inputWord) {
		CommonResult r = new CommonResult();
		WordDayLineDTO lastLine = null;

		CustomerDictionaryDTO dictionary = getCustomerDictionaryDTO();

		if (containWord(dictionary, inputWord)) {
			r.setMessage("Contain this word, update or append?");
			return r;
		}

		LocalDate today = LocalDate.now();
		boolean newLineFlag = false;

		List<WordDayLineDTO> wordRecordList = dictionary.getWordDateLineList();
		if (wordRecordList == null || wordRecordList.isEmpty()) {
			wordRecordList = new ArrayList<>();
			lastLine = new WordDayLineDTO();
			newLineFlag = true;
			lastLine.setDateStr(today.getYear() + "-" + today.getMonthValue() + "-" + today.getDayOfMonth());
		} else {
			lastLine = wordRecordList.get(wordRecordList.size() - 1);
			String firstLineDateStr = lastLine.getDateStr();
			String todayStr = today.getYear() + "-" + today.getMonthValue() + "-" + today.getDayOfMonth();
			if (!todayStr.equals(firstLineDateStr)) {
				lastLine = new WordDayLineDTO();
				newLineFlag = true;
				lastLine.setDateStr(today.getYear() + "-" + today.getMonthValue() + "-" + today.getDayOfMonth());
			}
		}

		if (lastLine.getWordList() == null) {
			lastLine.setWordList(new ArrayList<>());
		}

		lastLine.getWordList().add(inputWord);

		if (newLineFlag) {
			wordRecordList.add(lastLine);
		} else {
			wordRecordList.set(wordRecordList.size() - 1, lastLine);
		}

		dictionary.setWordDateLineList(wordRecordList);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonString = gson.toJson(dictionary);

		save(jsonString);

		r.setMessage("Input: " + inputWord.getEn());
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult updateOrAppendWord(UpdateOrAppendWordDTO dto) {
		CommonResult r = new CommonResult();
		CustomerDictionaryDTO dictionary = getCustomerDictionaryDTO();

		List<WordDayLineDTO> wordRecordList = dictionary.getWordDateLineList();
		for (int lineIndex = 0; lineIndex < wordRecordList.size(); lineIndex++) {
			List<WordDTO> wordList = wordRecordList.get(lineIndex).getWordList();
			for (int wordIndex = 0; wordIndex < wordList.size(); wordIndex++) {
				WordDTO word = wordList.get(wordIndex);
				if (word.getEn().equals(dto.getInputWord().getEn())) {
					if (dto.getUpdate()) {
						word.setCn(dto.getInputWord().getCn());
					} else {
						word.setCn(word.getCn() + dto.getInputWord().getCn());
					}

					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					String jsonString = gson.toJson(dictionary);

					save(jsonString);

					r.setMessage("Update: " + word.toString());
					r.setIsSuccess();
					return r;
//					wordList.set(wordIndex, word);
//					String dateStr = wordRecordList.get(lineIndex).getDateStr();
//					WordDayLineDTO newWordDayLine = new WordDayLineDTO();
//					newWordDayLine.setDateStr(dateStr);
//					newWordDayLine.setWordList(wordList);
//					wordRecordList.set(lineIndex, newWordDayLine);
				}
			}
		}

		return addNewWord(dto.getInputWord());
	}

	@Override
	public GetRandomWordResult printRandomWords(GetRandomWordDTO dto) {
		GetRandomWordResult r = new GetRandomWordResult();

		Random random = new Random();
		int randomIndex = 0;

		CustomerDictionaryDTO dictionary = getCustomerDictionaryDTO();
		if (dictionary == null || dictionary.getWordDateLineList() == null
				|| dictionary.getWordDateLineList().isEmpty()) {
			return r;
		}

		List<WordDTO> wordList = new ArrayList<>();

		for (int i = 0; i < dto.getWordCount(); i++) {
			randomIndex = random.nextInt(dictionary.getWordDateLineList().size());
			WordDayLineDTO wordRecord = dictionary.getWordDateLineList().get(randomIndex);
			randomIndex = random.nextInt(wordRecord.getWordList().size());
			WordDTO word = wordRecord.getWordList().get(randomIndex);
			if (!dto.getPrintEn()) {
				word.setEn("");
			}
			if (!dto.getPrintCn()) {
				word.setCn("");
			}
			wordList.add(word);
		}
		r.setWordList(wordList);
		r.setIsSuccess();
		return r;
	}
}
