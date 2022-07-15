package ru.romanisupov.ntrtest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import ru.romanisupov.ntrtest.controller.entity.CountWordsRequest;
import ru.romanisupov.ntrtest.controller.entity.WordOccurence;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WordControllerTest {

	private static final String countWordsUrl = "/count";

	@Autowired
	TestRestTemplate template;

	private boolean areEqual(List<WordOccurence> list1, List<WordOccurence> list2) {
		return list1.size() == list2.size() && list1.stream().allMatch(w -> contains(list2, w));
	}

	private boolean areEqual(WordOccurence w1, WordOccurence w2) {
		return w1.word.equals(w2.word) && w1.count.equals(w2.count);
	}

	private boolean contains(List<WordOccurence> list, WordOccurence w) {
		return list.stream().anyMatch(w1 -> areEqual(w, w1));
	}

	@Test
	void countWords() {
		CountWordsRequest requestBody = new CountWordsRequest(
				List.of("word1", "word2", "word3", "word4"),
				"word1 word2 word3 word1 word2 word1 word01 word");

		ResponseEntity<List<WordOccurence>> response = template.exchange(countWordsUrl, HttpMethod.POST,
				new HttpEntity<>(requestBody),
				new ParameterizedTypeReference<>() {});

		List<WordOccurence> expectedResponse = List.of(
				new WordOccurence("word1", 3),
				new WordOccurence("word2", 2),
				new WordOccurence("word3", 1),
				new WordOccurence("word4", 0));

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(areEqual(response.getBody(), expectedResponse)).isTrue();
	}

	@Test
	void countWordsWithNoOccurences() {
		CountWordsRequest requestBody = new CountWordsRequest(
				List.of("word1", "word2", "word3", ""),
				"word4 word5 word6 word7 word8 word9 word01 word");

		ResponseEntity<List<WordOccurence>> response = template.exchange(countWordsUrl, HttpMethod.POST,
				new HttpEntity<>(requestBody),
				new ParameterizedTypeReference<>() {});

		List<WordOccurence> expectedResponse = List.of(
				new WordOccurence("word1", 0),
				new WordOccurence("word2", 0),
				new WordOccurence("word3", 0),
				new WordOccurence("", 0));

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(areEqual(response.getBody(), expectedResponse)).isTrue();
	}

	@Test
	void countWordsWithEmptyText() {
		CountWordsRequest requestBody = new CountWordsRequest(
				List.of("word1", "word2", "word3", "word4"),
				"");

		ResponseEntity<List<WordOccurence>> response = template.exchange(countWordsUrl, HttpMethod.POST,
				new HttpEntity<>(requestBody),
				new ParameterizedTypeReference<>() {});

		List<WordOccurence> expectedResponse = List.of(
				new WordOccurence("word1", 0),
				new WordOccurence("word2", 0),
				new WordOccurence("word3", 0),
				new WordOccurence("word4", 0));

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(areEqual(response.getBody(), expectedResponse)).isTrue();
	}

	@Test
	void countWordsWithEmptyWords() {
		CountWordsRequest requestBody = new CountWordsRequest(
				Collections.emptyList(),
				"word1 word2 word3 word1 word2 word1 word01 word");

		ResponseEntity<List<WordOccurence>> response = template.exchange(countWordsUrl, HttpMethod.POST,
				new HttpEntity<>(requestBody),
				new ParameterizedTypeReference<>() {});

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).isEmpty();
	}

	@Test
	void countWordsWithEmptyTextAndWords() {
		CountWordsRequest requestBody = new CountWordsRequest(
				Collections.emptyList(),
				"");

		ResponseEntity<List<WordOccurence>> response = template.exchange(countWordsUrl, HttpMethod.POST,
				new HttpEntity<>(requestBody),
				new ParameterizedTypeReference<>() {});

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).isEmpty();
	}
}
