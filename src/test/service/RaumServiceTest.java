package com.example.test3.service;

import com.example.test3.domain.Raum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class RaumServiceTest {

    private RaumService raumService;

    @BeforeEach
    void setup() {
        raumService = new RaumService();
    }

    @Test
    void saveRum() {

    }

    @Test
    void getRoomsBeforeImportIsEmpty() {
        List<Raum> response = raumService.getRooms();
        assertTrue(response.isEmpty());
    }

    @Test
    void getRoomsAfterImport() {
        String content = "1111,Dennis Fischer (dfischer),Dr. Frank von Supper (fsupper),Susanne Moog (smoog),";
        raumService.saveRum(content);
        List<Raum> response = raumService.getRooms();
        assertEquals(1, response.size());
        assertEquals(3, response.get(0).getPeople().size());
    }

    @Test
    void getRoomInvalidRoomNumber() {
        assertThatThrownBy(() -> raumService.getRoom("111"))
                .isInstanceOf(InvalidNumberFormatException.class);
    }


//    @Test
//    void getRoomNotFound() {
//        ResponseEntity response = raumService.getRoom("2771");
//        ErrorDto errorDto = (ErrorDto) response.getBody();
//        assertEquals(5,errorDto.getCode());
//        assertEquals("room number not found",errorDto.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
//    }

}
