package util;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class TestTrie {

    @Rule
    public ExpectedException iae = ExpectedException.none();

    @Test
    public void testInsertNull() {
        iae.expect(IllegalArgumentException.class);
        Trie t = new Trie();
        String s = null;
        t.insert(s);
    }

    @Test
    public void testInsertEmpty() {
        iae.expect(IllegalArgumentException.class);
        Trie t = new Trie();
        t.insert("");
    }

    @Test
    public void testRandomGuessSimple() {
        Trie t = new Trie();
        t.insert("same");
        t.insert("sample");
        t.insert("sam");
        t.insert("samsara");
        t.insert("samaritan");
        t.insert("samurai");
        assertTrue(t.find("same", true));
        assertTrue(t.find("sample", true));
        assertTrue(t.find("sam", true));
        assertTrue(t.find("sam", false));
        for(int i = 0; i < 10; i++) {
          char c = t.getRandomGuess("sam");
          boolean isRandom = c  == 'e' || c == 'p' || c  == 's' || c == 'a'
              || c == 'u';
          assertTrue(isRandom);
        }
    }

    @Test
    public void testInsertSimple() {
        Trie t = new Trie();
        t.insert("hello");
        t.insert("h");
        t.insert("hey");
        t.insert("same");
        t.insert("sample");
        t.insert("sam");
        t.insert("Sap");
        t.insert("$311");
        t.insert("josh");
        assertTrue(t.find("hell", false));
        assertTrue(t.find("h", true));
        assertTrue(t.find("hello", true));
        assertTrue(t.find("same", true));
        assertTrue(t.find("sample", true));
        assertTrue(t.find("sam", true));
        assertTrue(t.find("sam", false));
        assertTrue(t.find("$311", true));
        assertTrue(t.find("$31", false));
        assertFalse(t.find("bye", false));
        assertFalse(t.find("Sam", false));
        assertFalse(t.find("heyy", false));
        assertFalse(t.find("josh",false)); //ask about this case
        assertFalse(t.find("joshshrugs",false));
        assertFalse(t.find("hell", true));
        assertFalse(t.find("5$%4", true));
        assertFalse(t.find("awl", true));
    }

    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestTrie.class);
    }
}
