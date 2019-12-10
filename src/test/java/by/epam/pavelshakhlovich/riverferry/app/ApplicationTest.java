package by.epam.pavelshakhlovich.riverferry.app;

import by.epam.pavelshakhlovich.riverferry.ferry.Ferry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.testng.Assert.*;

public class ApplicationTest {

    @Test
    public void testInitFerry() {
        Ferry expected = new Ferry(60.0, 20.0, 15);
        ObjectMapper objectMapper = new ObjectMapper();
        Ferry actual = new Ferry();
        try {
            actual = objectMapper.readValue(new File("data/data.json"), Ferry.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(actual, expected);
    }
}