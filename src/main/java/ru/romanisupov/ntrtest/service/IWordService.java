package ru.romanisupov.ntrtest.service;

import ru.romanisupov.ntrtest.service.entity.WordOccurence;

import java.util.List;

public interface IWordService {

	List<WordOccurence> count(List<String> words, String text);
}
