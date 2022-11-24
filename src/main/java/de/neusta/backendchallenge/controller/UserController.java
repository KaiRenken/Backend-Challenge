package de.neusta.backendchallenge.controller;

import de.neusta.backendchallenge.domain.Raum;
import de.neusta.backendchallenge.service.*;
import de.neusta.backendchallenge.service.Exception.InvalidNumberFormatException;
import de.neusta.backendchallenge.service.Exception.PersonAlreadyExistsException;
import de.neusta.backendchallenge.service.Exception.RoomAlreadyExistsException;
import de.neusta.backendchallenge.service.Exception.RoomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
public class UserController {

    private final RoomService roomService;
    public UserController(RoomService raumService) {
        this.roomService = raumService;
    }
    @PostMapping(path = "api/import", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> importDocument(@RequestPart MultipartFile document) throws IOException {
        if (document == null || document.isEmpty()) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(new ErrorDto(4, "no csv file given"));
        }

        ResponseEntity content = new ResponseEntity(document.getBytes());
        try {
            return roomService.saveRoom(content);
        } catch (InvalidNumberFormatException e) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(new ErrorDto(6, "invalid number format"));
        } catch (PersonAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getErrorDto());
        } catch (RoomAlreadyExistsException e) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(new ErrorDto(2, "room already exists"));
        }
    }
        @GetMapping("api/room")
        public List<Raum> getRooms () {
            return roomService.getRooms();
        }

        @GetMapping("api/room/{number}")
        public ResponseEntity<Object> getRoom (@PathVariable String number) {
            try {
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(roomService.getRoom(number));
               // return raumService.getRoom(number);
            } catch (InvalidNumberFormatException e) {
                return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(new ErrorDto(6, "invalid number format"));
            } catch (RoomNotFoundException e) {
                return new ResponseEntity<>(new ErrorDto(5, "room number not found"), HttpStatus.NOT_FOUND);
            }
        }
    }

