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

    @Test
    public void testExtractIndex_validMarkCommand(){
        assertEquals(1, Tyrone.extractIndex("mark 1"));
        assertEquals(5, Tyrone.extractIndex("mark 5"));
        assertEquals(10, Tyrone.extractIndex("mark 10"));
    }

    @Test
    public void testExtractIndex_validUnmarkCommand(){
        assertEquals(2, Tyrone.extractIndex("unmark 2"));
        assertEquals(7, Tyrone.extractIndex("unmark 7"));
    }

    @Test
    public void testExtractIndex_invalidCommand(){
        assertEquals(-1, Tyrone.extractIndex("list"));
        assertEquals(-1, Tyrone.extractIndex("mark"));
        assertEquals(-1, Tyrone.extractIndex("delete 1"));
    }

}
