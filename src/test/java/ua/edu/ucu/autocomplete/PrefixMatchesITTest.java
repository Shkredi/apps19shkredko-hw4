
package ua.edu.ucu.autocomplete;

import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import ua.edu.ucu.tries.RWayTrie;
import ua.edu.ucu.tries.Tuple;

/**
 *
 * @author Andrii_Rodionov
 */
public class PrefixMatchesITTest {

    private PrefixMatches pm;

    @Test
    public void RwayTrieTest(){
        RWayTrie t = new RWayTrie();
        System.out.println(t.toString());

        Tuple w1 = new Tuple("abc", 3);
        Tuple w2 = new Tuple("ab", 2);
        Tuple w3 = new Tuple("aba", 3);

        t.add(w1);
        System.out.println(t.toString());
        t.add(w2);
        System.out.println(t.toString());
        t.add(w3);
        System.out.println(t.toString());

        System.out.println(t.contains("abc"));
        System.out.println(t.contains("ab"));
        System.out.println(t.contains("a"));
        System.out.println(t.contains("aba"));
        System.out.println(t.contains("abb"));

        for (String word: t.words()) {
            System.out.println(word);
        }

        System.out.println(t.delete("a"));
        System.out.println(t.toString());
        System.out.println(t.delete("ab"));
        System.out.println(t.toString());
        System.out.println(t.delete("abc"));
        System.out.println(t.toString());
        System.out.println(t.delete("aba"));
        System.out.println(t.toString());

        System.out.println(t.size());


    }

    @Before
    public void init() {
        pm = new PrefixMatches(new RWayTrie());
        pm.load("abc", "abce", "abcd", "abcde", "abcdef");
        System.out.println(pm.toString());
    }

    @Test
    public void testWordsWithPrefix_String() {
        String pref = "ab";

        Iterable<String> result = pm.wordsWithPrefix(pref);

        String[] expResult = {"abc", "abce", "abcd", "abcde", "abcdef"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testWordsWithPrefix_String_and_K() {
        String pref = "abc";
        int k = 3;

        Iterable<String> result = pm.wordsWithPrefix(pref, k);

        String[] expResult = {"abc", "abce", "abcd", "abcde"};

        assertThat(result, containsInAnyOrder(expResult));
    }

}
