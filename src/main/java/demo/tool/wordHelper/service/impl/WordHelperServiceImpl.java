package demo.tool.wordHelper.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

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

	@Override
	public ModelAndView wordHelper() {
		ModelAndView v = new ModelAndView("wordHelperJSP/wordHelper");
		return v;
	}

	private CustomerDictionaryDTO getCustomerDictionaryDTO() {
		Long userId = baseUtilCustom.getUserId();
		FileUtilCustom fileUtil = new FileUtilCustom();
		String content = null;
		try {
			content = fileUtil.getStringFromFile(dictionarySavingFolderPathStr + "/" + userId + ".json");
		} catch (Exception e) {
			return new CustomerDictionaryDTO();
		}

		if (StringUtils.isBlank(content)) {
			return new CustomerDictionaryDTO();
		}

		Gson gson = new GsonBuilder().create();
		return gson.fromJson(content, CustomerDictionaryDTO.class);
	}

	private void save(String content) {
		Long userId = baseUtilCustom.getUserId();
		FileUtilCustom fileUtil = new FileUtilCustom();
		fileUtil.byteToFile(content, dictionarySavingFolderPathStr + "/" + userId + ".json");
	}

	private WordDTO findWordByEnEqual(CustomerDictionaryDTO dictionary, WordDTO word) {
		if (dictionary.getWordDateLineList() == null || dictionary.getWordDateLineList().isEmpty()) {
			return null;
		}

		for (WordDayLineDTO wordDateLine : dictionary.getWordDateLineList()) {
			for (WordDTO wordExists : wordDateLine.getWordList()) {
				if (wordExists.getEn().equals(word.getEn())) {
					return wordExists;
				}
			}
		}

		return null;
	}

	private List<WordDTO> findWordsByContains(CustomerDictionaryDTO dictionary, WordDTO word) {
		List<WordDTO> wordList = new ArrayList<>();
		if (dictionary.getWordDateLineList() == null || dictionary.getWordDateLineList().isEmpty()) {
			return wordList;
		}

		for (WordDayLineDTO wordDateLine : dictionary.getWordDateLineList()) {
			for (WordDTO wordExists : wordDateLine.getWordList()) {
				if ((StringUtils.isNotBlank(word.getEn()) && wordExists.getEn().contains(word.getEn()))
						|| (StringUtils.isNotBlank(word.getCn()) && wordExists.getCn().contains(word.getCn()))) {
					wordList.add(wordExists);
				}
			}
		}

		return wordList;
	}

	@Override
	public CommonResult addNewWord(WordDTO inputWord) {
		CommonResult r = new CommonResult();
		WordDayLineDTO lastLine = null;

		if (StringUtils.isAnyBlank(inputWord.getEn(), inputWord.getCn())) {
			r.setMessage("Please fill EN and CN field");
			return r;
		}

		CustomerDictionaryDTO dictionary = getCustomerDictionaryDTO();

		WordDTO wordExists = findWordByEnEqual(dictionary, inputWord);
		if (wordExists != null) {
			r.setMessage("Contain this word, " + wordExists.getEn() + ", " + wordExists.getCn() + "update or append?");
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
	public GetRandomWordResult findWords(WordDTO inputWord) {
		GetRandomWordResult r = new GetRandomWordResult();

		CustomerDictionaryDTO dictionary = getCustomerDictionaryDTO();

		List<WordDTO> wordList = findWordsByContains(dictionary, inputWord);
		if (wordList.isEmpty()) {
			r.setMessage("Can NOT find words by: " + inputWord.getEn() + ", " + inputWord.getCn());
			r.setIsSuccess();
		} else {
			r.setMessage("Find " + wordList.size() + " words");
			r.setWordList(wordList);
			r.setIsSuccess();
		}
		return r;
	}

	@Override
	public CommonResult updateOrAppendWord(UpdateOrAppendWordDTO dto) {
		CommonResult r = new CommonResult();
		if (StringUtils.isAnyBlank(dto.getInputWord().getEn(), dto.getInputWord().getCn())) {
			r.setMessage("Please fill EN and CN field");
			return r;
		}

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
						word.setCn(word.getCn() + "; " + dto.getInputWord().getCn());
					}

					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					String jsonString = gson.toJson(dictionary);

					save(jsonString);

					r.setMessage("Update: " + word.toString());
					r.setIsSuccess();
					return r;
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
			if (wordRecord.getWordList().isEmpty()) {
				i--;
				continue;
			}
			randomIndex = random.nextInt(wordRecord.getWordList().size());
			WordDTO word = wordRecord.getWordList().get(randomIndex);
			wordList.add(word);
		}
		r.setWordList(wordList);
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult deleteWord(WordDTO dto) {
		CommonResult r = new CommonResult();
		CustomerDictionaryDTO dictionary = getCustomerDictionaryDTO();

		List<WordDayLineDTO> wordRecordList = dictionary.getWordDateLineList();
		for (int lineIndex = 0; lineIndex < wordRecordList.size(); lineIndex++) {
			List<WordDTO> wordList = wordRecordList.get(lineIndex).getWordList();
			for (int wordIndex = 0; wordIndex < wordList.size(); wordIndex++) {
				WordDTO word = wordList.get(wordIndex);
				if (word.getEn().equals(dto.getEn())) {
					wordList.remove(wordIndex);

					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					String jsonString = gson.toJson(dictionary);

					save(jsonString);

					r.setMessage("Delete: " + word.toString());
					r.setIsSuccess();
					return r;
				}
			}
		}

		r.setMessage("Can NOT find: " + dto.getEn());
		return r;
	}
}
