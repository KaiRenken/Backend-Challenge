package de.neusta.backendchallenge.service;

import de.neusta.backendchallenge.domain.Raum;
import de.neusta.backendchallenge.service.Exception.InvalidNumberFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RaumServiceTest {

    private RoomService roomService;

    @BeforeEach
    void setup() {
        roomService = new RoomService();
    }

    @Test
    void saveRum() {

    }

    @Test
    void getRoomsBeforeImportIsEmpty() {
        List<Raum> response = roomService.getRooms();
        assertTrue(response.isEmpty());
    }

    @Test
    void getRoomsAfterImport() {
        String content = "1111,Dennis Fischer (dfischer),Dr. Frank von Supper (fsupper),Susanne Moog (smoog),";
        roomService.saveRoom(content);
        List<Raum> response = roomService.getRooms();
        assertEquals(1, response.size());
        assertEquals(3, response.get(0).getPeople().size());
    }

    @Test
    void getRoomInvalidRoomNumber() {
        assertThatThrownBy(() -> roomService.getRoom("111"))
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
