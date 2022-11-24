package de.neusta.backendchallenge.controller;

import de.neusta.backendchallenge.domain.Raum;
import de.neusta.backendchallenge.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserControllerTest {

    private final UserController userController = new UserController(new RoomService());

    @Test
    void importDocumentNull() throws IOException {
        ResponseEntity<Object> response = userController.importDocument(null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorDto errorDto = (ErrorDto) response.getBody();
        assert errorDto != null;
        assertEquals(4, errorDto.code());
        assertEquals("no csv file given", errorDto.message());
    }

    @Test
    void importDocumentEmpty() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("sitzplanEmpty.csv", new FileInputStream("src/main/resources/sitzplanEmpty.csv"));
        ResponseEntity<Object> response = userController.importDocument(multipartFile);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorDto errorDto = (ErrorDto) response.getBody();
        assertThat(errorDto).isNotNull();
        assertEquals(4, errorDto.code());
        assertEquals("no csv file given", errorDto.message());
    }

    @Test
    void importDocumentWrongRoomNr() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("sitzplanWrongRaumNr.csv", new FileInputStream("src/main/resources/sitzplanWrongRaumNr.csv"));
        ResponseEntity<Object> response = userController.importDocument(multipartFile);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorDto errorDto = (ErrorDto) response.getBody();
        assertThat(errorDto).isNotNull();
        assertEquals(6, errorDto.code());
        assertEquals("invalid number format", errorDto.message());
    }

    @Test
    void importDocumentRoomAlreadyExists() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("sitzplanAlreadyExists.csv", new FileInputStream("src/main/resources/sitzplanAlreadyExists.csv"));
        ResponseEntity<Object> response = userController.importDocument(multipartFile);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorDto errorDto = (ErrorDto) response.getBody();
        assertThat(errorDto).isNotNull();
        assertEquals(2, errorDto.code());
        assertEquals("room already exists", errorDto.message());
    }

    @Test
    void getRoomsBeforeImport() {
        List<Raum> response = userController.getRooms();
        assertTrue(response.isEmpty());
    }

    @Test
    void getRoomsAfterImport() {
        List<Raum> response = userController.getRooms();
        assertEquals(1111, 1111, response.size());
    }

    @Test
    void getRoomDocumentValidNumber() {

    }

    @Test
    void getRoomInvalidNumberFormat() {

    }

    @Test
    void getRoomNotFoundException() {

    }
}

