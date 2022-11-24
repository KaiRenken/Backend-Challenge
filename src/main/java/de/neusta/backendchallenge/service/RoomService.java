package de.neusta.backendchallenge.service;

import de.neusta.backendchallenge.domain.Raum;
import de.neusta.backendchallenge.service.Exception.InvalidNumberFormatException;
import de.neusta.backendchallenge.service.Exception.RoomAlreadyExistsException;
import de.neusta.backendchallenge.service.Exception.RoomNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomService {

    private List<Raum> rooms;

    public String saveRoom(String content) {
        PersonHandler personHandler = new PersonHandler();

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
      //  return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(content);
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
               // return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(room);
                return room;
            }
        }
        throw new RoomNotFoundException();
    }
}





