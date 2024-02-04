package demo.tool.wordHelper.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.service.CommonService;
import demo.tool.wordHelper.pojo.dto.CustomerDictionaryV2DTO;
import demo.tool.wordHelper.pojo.dto.GetRandomWordDTO;
import demo.tool.wordHelper.pojo.dto.UpdateOrAppendWordDTO;
import demo.tool.wordHelper.pojo.dto.WordDTO;
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

	private CustomerDictionaryV2DTO getCustomerDictionaryV2DTO() {
		Long userId = baseUtilCustom.getUserId();
		FileUtilCustom fileUtil = new FileUtilCustom();
		String content = null;
		try {
			content = fileUtil.getStringFromFile(dictionarySavingFolderPathStr + "/" + userId + ".json");
		} catch (Exception e) {
			return new CustomerDictionaryV2DTO();
		}

		if (StringUtils.isBlank(content)) {
			return new CustomerDictionaryV2DTO();
		}

		Gson gson = new GsonBuilder().create();
		return gson.fromJson(content, CustomerDictionaryV2DTO.class);
	}

	private void save(String content) {
		Long userId = baseUtilCustom.getUserId();
		FileUtilCustom fileUtil = new FileUtilCustom();
		fileUtil.byteToFile(content, dictionarySavingFolderPathStr + "/" + userId + ".json");
	}

	private WordDTO findWordByEnEqual(CustomerDictionaryV2DTO dictionary, WordDTO word) {
		if (dictionary.getWordList() == null || dictionary.getWordList().isEmpty()) {
			return null;
		}

		for (WordDTO wordExists : dictionary.getWordList()) {
			if (wordExists.getEn().equals(word.getEn())) {
				return wordExists;
			}
		}

		return null;
	}

	private List<WordDTO> findWordsByContains(CustomerDictionaryV2DTO dictionary, WordDTO word) {
		List<WordDTO> wordList = new ArrayList<>();
		if (dictionary.getWordList() == null || dictionary.getWordList().isEmpty()) {
			return wordList;
		}

		for (WordDTO wordExists : dictionary.getWordList()) {
			if ((StringUtils.isNotBlank(word.getEn()) && wordExists.getEn().contains(word.getEn()))
					|| (StringUtils.isNotBlank(word.getCn()) && wordExists.getCn().contains(word.getCn()))) {
				wordList.add(wordExists);
			}
		}

		return wordList;
	}

	@Override
	public CommonResult addNewWord(WordDTO inputWord) {
		CommonResult r = new CommonResult();

		if (StringUtils.isAnyBlank(inputWord.getEn(), inputWord.getCn())) {
			r.setMessage("Please fill EN and CN field");
			return r;
		}

		CustomerDictionaryV2DTO dictionary = getCustomerDictionaryV2DTO();

		WordDTO wordExists = findWordByEnEqual(dictionary, inputWord);
		if (wordExists != null) {
			r.setMessage("Contain this word, " + wordExists.getEn() + ", " + wordExists.getCn() + "update or append?");
			return r;
		}

		LocalDate today = LocalDate.now();
		String todayStr = today.getYear() + "-" + today.getMonthValue() + "-" + today.getDayOfMonth();

		List<WordDTO> wordList = dictionary.getWordList();
		if (wordList == null || wordList.isEmpty()) {
			wordList = new ArrayList<>();
			dictionary.setWordList(wordList);
			inputWord.setDateStr(todayStr);
		} else {
			String lastDateStr = null;
			for (int i = wordList.size() - 1; i >= 0 && lastDateStr == null; i--) {
				wordExists = wordList.get(i);
				if (wordExists.getDateStr() != null) {
					lastDateStr = wordExists.getDateStr();
				}
			}
			if (lastDateStr == null || !lastDateStr.equals(todayStr)) {
				inputWord.setDateStr(todayStr);
			}
		}

		wordList.add(inputWord);

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

		CustomerDictionaryV2DTO dictionary = getCustomerDictionaryV2DTO();

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

		CustomerDictionaryV2DTO dictionary = getCustomerDictionaryV2DTO();

		List<WordDTO> wordList = dictionary.getWordList();
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
		return addNewWord(dto.getInputWord());
	}

	@Override
	public GetRandomWordResult printRandomWords(GetRandomWordDTO dto) {
		GetRandomWordResult r = new GetRandomWordResult();

		Random random = new Random();
		int randomIndex = 0;

		CustomerDictionaryV2DTO dictionary = getCustomerDictionaryV2DTO();
		if (dictionary == null || dictionary.getWordList() == null || dictionary.getWordList().isEmpty()) {
			return r;
		}

		List<WordDTO> wordList = new ArrayList<>();

		int wordCounting = 0;
		if (dictionary.getWordList() != null) {
			wordCounting = dictionary.getWordList().size();
		}
		boolean dictionaryCountsMoreThanRequirement = wordCounting > dto.getWordCount();

		if (!dictionaryCountsMoreThanRequirement) {
			wordList.addAll(dictionary.getWordList());
			r.setWordList(wordList);
			r.setIsSuccess();
			return r;
		}

		Set<WordDTO> wordSet = new HashSet<>();
		while (wordSet.size() < dto.getWordCount()) {
			randomIndex = random.nextInt(dictionary.getWordList().size());
			WordDTO word = dictionary.getWordList().get(randomIndex);
			wordSet.add(word);
		}
		wordList.addAll(wordSet);
		r.setWordList(wordList);
		r.setIsSuccess();
		return r;
	}

	@Override
	public GetRandomWordResult printNewWords(GetRandomWordDTO dto) {
		GetRandomWordResult r = new GetRandomWordResult();

		CustomerDictionaryV2DTO dictionary = getCustomerDictionaryV2DTO();
		if (dictionary == null || dictionary.getWordList() == null || dictionary.getWordList().isEmpty()) {
			return r;
		}

		List<WordDTO> wordList = new ArrayList<>();

		int wordCounting = 0;
		if (dictionary.getWordList() != null) {
			wordCounting = dictionary.getWordList().size();
		}
		boolean dictionaryCountsMoreThanRequirement = wordCounting > dto.getWordCount();

		if (!dictionaryCountsMoreThanRequirement) {
			wordList.addAll(dictionary.getWordList());
			r.setWordList(wordList);
			r.setIsSuccess();
			return r;
		}

		for (int i = dictionary.getWordList().size() - 1; i >= 0 && wordList.size() < dto.getWordCount(); i--) {
			WordDTO word = dictionary.getWordList().get(i);
			wordList.add(word);
		}

		r.setWordList(wordList);
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult deleteWord(WordDTO dto) {
		CommonResult r = new CommonResult();
		CustomerDictionaryV2DTO dictionary = getCustomerDictionaryV2DTO();

		for (int wordIndex = 0; wordIndex < dictionary.getWordList().size(); wordIndex++) {
			WordDTO word = dictionary.getWordList().get(wordIndex);
			if (word.getEn().equals(dto.getEn())) {
				dictionary.getWordList().remove(wordIndex);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String jsonString = gson.toJson(dictionary);

				save(jsonString);

				r.setMessage("Delete: " + word.toString());
				r.setIsSuccess();
				return r;
			}
		}

		r.setMessage("Can NOT find: " + dto.getEn());
		return r;
	}
}
