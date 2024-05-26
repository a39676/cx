package demo.tool.wordHelper.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.service.CommonService;
import demo.tool.wordHelper.pojo.dto.CustomerDictionaryV2DTO;
import demo.tool.wordHelper.pojo.dto.GetWordDTO;
import demo.tool.wordHelper.pojo.dto.UpdateOrAppendWordDTO;
import demo.tool.wordHelper.pojo.dto.WordDTO;
import demo.tool.wordHelper.pojo.result.GetWordResult;
import demo.tool.wordHelper.pojo.type.GetWordsResultType;
import demo.tool.wordHelper.pojo.vo.WordVO;
import demo.tool.wordHelper.service.WordHelperService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class WordHelperServiceImpl extends CommonService implements WordHelperService {

	private String dictionarySavingFolderPathStr = MAIN_FOLDER_PATH + "/wordHelper";

	@Override
	public ModelAndView wordHelper() {
		ModelAndView v = new ModelAndView("toolJSP/wordHelperJSP/wordHelper");
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
	public GetWordResult findWords(WordDTO inputWord) {
		GetWordResult r = new GetWordResult();

		CustomerDictionaryV2DTO dictionary = getCustomerDictionaryV2DTO();

		List<WordDTO> wordList = findWordsByContains(dictionary, inputWord);
		if (wordList.isEmpty()) {
			r.setMessage("Can NOT find words by: " + inputWord.getEn() + ", " + inputWord.getCn());
			r.setIsSuccess();
		} else {
			r.setMessage("Find " + wordList.size() + " words");
			r.setWordList(wordListToVoList(wordList));
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

	private GetWordResult beforeGetWords(GetWordDTO dto, CustomerDictionaryV2DTO dictionary) {
		GetWordResult r = new GetWordResult();

		if (dictionary == null || dictionary.getWordList() == null || dictionary.getWordList().isEmpty()) {
			GetWordsResultType resultType = GetWordsResultType.NO_WORDS;
			r.setCode(String.valueOf(resultType.getCode()));
			r.setMessage(resultType.getName());
			return r;
		}

		int wordCounting = 0;
		if (dictionary.getWordList() != null) {
			wordCounting = dictionary.getWordList().size();
		}
		boolean dictionaryCountsMoreThanRequirement = wordCounting > dto.getWordCount();

		if (!dictionaryCountsMoreThanRequirement) {
			GetWordsResultType resultType = GetWordsResultType.NOT_ENOUGH_WORDS;
			r.setCode(String.valueOf(resultType.getCode()));
			r.setMessage(resultType.getName());
		} else {
			r.setIsSuccess();
		}
		return r;
	}

	@Override
	public GetWordResult printRandomWords(GetWordDTO dto) {
		CustomerDictionaryV2DTO dictionary = getCustomerDictionaryV2DTO();
		GetWordResult r = beforeGetWords(dto, dictionary);
		if (r.isFail()) {
			if (r.getCode().equals(String.valueOf(GetWordsResultType.NO_WORDS.getCode()))) {
				return r;

			} else if (r.getCode().equals(String.valueOf(GetWordsResultType.NOT_ENOUGH_WORDS.getCode()))) {
				List<WordDTO> wordList = new ArrayList<>();
				wordList.addAll(dictionary.getWordList());
				r.setWordList(wordListToVoList(wordList));
				return r;
			}
			return r;
		}

		List<WordDTO> wordList = getRandomWords(dictionary, dto.getWordCount());
		r.setWordList(wordListToVoList(wordList));
		r.setIsSuccess();
		return r;
	}

	@Override
	public GetWordResult printNewWords(GetWordDTO dto) {
		CustomerDictionaryV2DTO dictionary = getCustomerDictionaryV2DTO();
		GetWordResult r = beforeGetWords(dto, dictionary);
		if (r.isFail()) {
			if (r.getCode().equals(String.valueOf(GetWordsResultType.NO_WORDS.getCode()))) {
				return r;

			} else if (r.getCode().equals(String.valueOf(GetWordsResultType.NOT_ENOUGH_WORDS.getCode()))) {
				List<WordDTO> wordList = new ArrayList<>();
				wordList.addAll(dictionary.getWordList());
				r.setWordList(wordListToVoList(wordList));
				return r;
			}
			return r;
		}

		List<WordDTO> wordList = getNewWords(dictionary, dto.getWordCount());
		r.setWordList(wordListToVoList(wordList));
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

	@Override
	public GetWordResult printNewWordsInMarks(GetWordDTO dto) {
		CustomerDictionaryV2DTO dictionary = getCustomerDictionaryV2DTO();
		GetWordResult r = beforeGetWords(dto, dictionary);
		if (r.getCode().equals(String.valueOf(GetWordsResultType.NO_WORDS.getCode()))) {
			return r;

		}

		List<WordDTO> wordList = new ArrayList<>();
		if (r.getCode().equals(String.valueOf(GetWordsResultType.NOT_ENOUGH_WORDS.getCode()))) {
			wordList.addAll(dictionary.getWordList());
		}

		wordList = getNewWords(dictionary, dto.getWordCount());

		r.setWordList(wordListToVoList(wordList, true));
		r.setIsSuccess();
		return r;
	}

	@Override
	public GetWordResult printRandomWordsInMarks(GetWordDTO dto) {
		CustomerDictionaryV2DTO dictionary = getCustomerDictionaryV2DTO();
		GetWordResult r = beforeGetWords(dto, dictionary);
		if (r.getCode().equals(String.valueOf(GetWordsResultType.NO_WORDS.getCode()))) {
			return r;

		}

		List<WordDTO> wordList = new ArrayList<>();
		if (r.getCode().equals(String.valueOf(GetWordsResultType.NOT_ENOUGH_WORDS.getCode()))) {
			wordList.addAll(dictionary.getWordList());
		}

		wordList = getRandomWords(dictionary, dto.getWordCount());

		r.setWordList(wordListToVoList(wordList, true));
		r.setIsSuccess();
		return r;
	}

	private List<WordDTO> getRandomWords(CustomerDictionaryV2DTO dictionary, Integer wordCounting) {
		List<WordDTO> wordList = new ArrayList<>();

		Random random = new Random();
		int randomIndex = 0;

		Set<WordDTO> wordSet = new HashSet<>();
		while (wordSet.size() < wordCounting) {
			randomIndex = random.nextInt(dictionary.getWordList().size());
			WordDTO word = dictionary.getWordList().get(randomIndex);
			wordSet.add(word);
		}
		wordList.addAll(wordSet);
		return wordList;
	}

	private List<WordDTO> getNewWords(CustomerDictionaryV2DTO dictionary, Integer wordCounting) {
		List<WordDTO> wordList = new ArrayList<>();

		for (int i = dictionary.getWordList().size() - 1; i >= 0 && wordList.size() < wordCounting; i--) {
			WordDTO word = dictionary.getWordList().get(i);
			wordList.add(word);
		}

		return wordList;
	}

//	private WordVO wordToVo(WordDTO dto) {
//		return wordToVo(dto, false);
//	}

	private WordVO wordToVo(WordDTO dto, boolean enInMark) {
		WordVO vo = new WordVO();
		BeanUtils.copyProperties(dto, vo);
		if (enInMark) {
			String underLine = "﹎";
			StringBuffer sb = new StringBuffer(dto.getEn());
			int characterLength = 0;
			for(int i = 0; i < sb.length(); i++) {
				char tmpChar = sb.charAt(i);
				if(Character.isLetter(tmpChar)) {
					characterLength++;
				}
			}
			
			if (characterLength <= 3) {
				for (int i = 0; i < sb.length(); i++) {
					sb.setCharAt(i, underLine.charAt(0));
				}
			}
			
			// Keep about 33% characters or at last 3 characters
			int notReplaceCounting = characterLength / 3;
			if (notReplaceCounting < 3) {
				notReplaceCounting = 3;
			}
			int replaceCount = characterLength - notReplaceCounting;
			int randomIndex = 0;
			for (int i = 0; i < replaceCount; i++) {
				randomIndex = ThreadLocalRandom.current().nextInt(0, sb.length());
				char tmpChar = sb.charAt(randomIndex);
				if (Character.isLetter(tmpChar)) {
					sb.replace(randomIndex, randomIndex + 1, underLine);
//				} else if (" ".equals(String.valueOf(tmpChar))) {
//					sb.replace(randomIndex, randomIndex + 1, "  ");
//					i++;
//					replaceCount++;
				} else {
					i--;
					continue;
				}
			}
			vo.setEnInMark(sb.toString());
		}
		return vo;
	}

	private List<WordVO> wordListToVoList(List<WordDTO> wordList) {
		return wordListToVoList(wordList, false);
	}

	private List<WordVO> wordListToVoList(List<WordDTO> wordList, boolean enInMark) {
		List<WordVO> voList = new ArrayList<>();
		for (int i = 0; i < wordList.size(); i++) {
			voList.add(wordToVo(wordList.get(i), enInMark));
		}
		return voList;
	}

}
