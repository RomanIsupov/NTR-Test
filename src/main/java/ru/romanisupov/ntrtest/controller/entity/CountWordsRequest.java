package ru.romanisupov.ntrtest.controller.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountWordsRequest {
	@NonNull
	private List<String> words;
	@NonNull
	private String text;
}



