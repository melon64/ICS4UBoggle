package ai;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.Arrays;

import org.junit.Test;

public class ComputerPlayerTest {
    @Test
    public void runTest() {
        Collection<Character> expectedWord = Arrays.asList ( 'a', 'p', 'p', 'l', 'e' );

        char[][] chars = {
            { 'a', 'p', 'p', 'l', 'e' },
            { 'l', 'v', '*', '*', 's' },
            { 'e', '*', 'o', '*', '*' },
            { 'r', '*', '*', 'i', '*' },
            { 't', '*', '*', '*', 'd' },
        };

        ComputerPlayer a = new ComputerPlayer(chars);
        Collection<Cell> wordCellFound = a.run();

        Collection<Character> word = wordCellFound.stream().map(cell->cell.getLetter()).collect(Collectors.toList());

        assertEquals(expectedWord, word);
    }
}
