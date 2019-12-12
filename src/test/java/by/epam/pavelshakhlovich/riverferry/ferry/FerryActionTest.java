package by.epam.pavelshakhlovich.riverferry.ferry;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class FerryActionTest {

    @Test
    public void testSetupFerry() {
        Ferry expected = new Ferry(70.0, 20.0, 15);
        Ferry actual = FerryAction.setupFerry("data/ferry_data.json");
        assertEquals(actual, expected);
    }
}