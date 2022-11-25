package de.neusta.backendchallenge.service;

import de.neusta.backendchallenge.controller.ErrorDto;
import de.neusta.backendchallenge.domain.Person;
import de.neusta.backendchallenge.service.Exception.PersonAlreadyExistsException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Diese Annotation ist nötig, um SpringBoot zu sagen, dass der PersonHandler eine Spring Bean ist und damit injected werden kann (google mal: "Dependency injection")
@Component
public class PersonHandler {

    // Hier genauso wie mit dem PersonHandler im RoomService: Nicht direkt in der Klasse erzeugen, sondern per SpringBoot injecten lassen!
    private final PersonParser personParser;
    private final Set<String> tempPersonData = new HashSet<>();

    public PersonHandler(PersonParser personParser) {
        this.personParser = personParser;
    }

    public List<Person> extractPersons(String[] roomInfos) {
        List<Person> personList = new ArrayList<>();

        for (int i = 1; i < roomInfos.length; i++) {
            Person person = buildPerson(roomInfos[i].trim());
            personList.add(person);
        }

        for (Person person : personList) {
            if (!tempPersonData.add(person.firstName() + "" + person.lastName())) {
                throw new PersonAlreadyExistsException(new ErrorDto(3, "person already exist"));
            }
        }
        return personList;
    }

    private Person buildPerson(String userInfo) {
        return personParser.parse(userInfo);
    }
}
