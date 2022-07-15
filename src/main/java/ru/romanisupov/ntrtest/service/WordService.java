package ru.romanisupov.ntrtest.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.romanisupov.ntrtest.service.entity.WordOccurence;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordService implements IWordService {

	@Override
	public List<WordOccurence> count(List<String> words, String text) {
		return words.stream()
				.map(word -> new WordOccurence(word, StringUtils.countOccurrencesOf(text, word)))
				.collect(Collectors.toList());
	}
}
