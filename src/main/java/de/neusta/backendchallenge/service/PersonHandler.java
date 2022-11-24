package de.neusta.backendchallenge.service;

import de.neusta.backendchallenge.controller.ErrorDto;
import de.neusta.backendchallenge.domain.Person;
import de.neusta.backendchallenge.service.Exception.PersonAlreadyExistsException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonHandler {

    private final Set<String> tempPersonData = new HashSet<>();
    public PersonHandler() {
    }

    public List<Person> extractPersons(String[] roomInfos) {
        List<Person> personList = new ArrayList<>();
        for (int i = 1; i < roomInfos.length; i++) {
            Person person = buildPerson(roomInfos[i].trim());
            personList.add(person);
        }
        //TODO throw new PersonAlreadyExistsException(new ErrorDto(400, "person already exist"));
        //TODO  prüfe ob doppelte Personen vorkommen
        for (Person person: personList) {
            if (!tempPersonData.add(person.firstName() + "" + person.lastName())) {
                throw new PersonAlreadyExistsException(new ErrorDto(3, "person already exist"));
            }
        }
        return personList;
    }

    private Person buildPerson(String userInfo) {
        return new PersonParser().parse(userInfo);
    }
}
