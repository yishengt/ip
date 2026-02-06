package tyrone;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TyroneTest {

    @Test
    public void testValidDate(){
        assertTrue(Tyrone.isValidDate("2023-12-21"));
        assertTrue(Tyrone.isValidDate("2026-09-21"));
    }

    @Test
    public void testInvalidDate(){
        assertFalse(Tyrone.isValidDate("2023-1"));
        assertFalse(Tyrone.isValidDate("2026-3"));
        assertFalse(Tyrone.isValidDate("2026-9-21"));

    }

}
