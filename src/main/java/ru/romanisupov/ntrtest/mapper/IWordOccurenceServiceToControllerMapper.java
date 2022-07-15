package ru.romanisupov.ntrtest.mapper;

import org.mapstruct.Mapper;
import ru.romanisupov.ntrtest.service.entity.WordOccurence;

@Mapper(componentModel = "spring")
public interface IWordOccurenceServiceToControllerMapper {
	ru.romanisupov.ntrtest.controller.entity.WordOccurence serviceToController(WordOccurence wordOccurence);
	WordOccurence controllerToService(ru.romanisupov.ntrtest.controller.entity.WordOccurence wordOccurence);
}
