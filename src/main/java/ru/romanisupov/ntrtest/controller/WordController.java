package ru.romanisupov.ntrtest.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.romanisupov.ntrtest.controller.entity.CountWordsRequest;
import ru.romanisupov.ntrtest.controller.entity.WordOccurence;
import ru.romanisupov.ntrtest.mapper.IWordOccurenceServiceToControllerMapper;
import ru.romanisupov.ntrtest.service.IWordService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor

public class WordController {
	@NonNull IWordService wordService;
	@NonNull IWordOccurenceServiceToControllerMapper wordOccurenceMapper;

	@PostMapping(value = "/count")
	public List<WordOccurence> countWords(@Valid @RequestBody CountWordsRequest countWordsRequest) {
		return wordService.count(countWordsRequest.getWords(), countWordsRequest.getText()).stream()
				.map(wordOccurenceMapper::serviceToController)
				.collect(Collectors.toList());
	}
}
