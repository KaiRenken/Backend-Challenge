package de.neusta.backendchallenge.service;

import de.neusta.backendchallenge.domain.Raum;
import de.neusta.backendchallenge.service.Exception.InvalidNumberFormatException;
import de.neusta.backendchallenge.service.Exception.RoomAlreadyExistsException;
import de.neusta.backendchallenge.service.Exception.RoomNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class RoomService {

    // Der PersonHandler wird besser per SpringBoot injected, als direkt in der saveRoom-Methode erzeugt. So lässt er sich auch mocken!
    private final PersonHandler personHandler;

    public RoomService(PersonHandler personHandler) {
        this.personHandler = personHandler;
    }

    private List<Raum> rooms;

    public String saveRoom(String content) {
        String[] rumInfo = content.split("\n");
        List<Raum> tempRumList = new ArrayList<>();
        Set<String> tempRumNumber = new HashSet<>();

        for (String room : rumInfo) {
            String[] roomSplit = room.split(",");

            String rumNr = roomSplit[0];

            if (rumNr.length() != 4) {
                throw new InvalidNumberFormatException();
            }
            if (!tempRumNumber.add(rumNr)) {
                throw new RoomAlreadyExistsException();
            }
            tempRumList.add(new Raum(rumNr, personHandler.extractPersons(roomSplit)));
        }

        rooms = tempRumList;

        return content;
    }

    public List<Raum> getRooms() {
        if (rooms != null) {
            return rooms;
        }
        return Collections.emptyList();
    }

    public Raum getRoom(String number) {
        if (number.length() != 4) {
            throw new InvalidNumberFormatException();
        }

        List<Raum> rooms = getRooms();

        for (Raum room : rooms) {
            if (Objects.equals(room.getRoom(), number)) {
                return room;
            }
        }
        throw new RoomNotFoundException();
    }
}





