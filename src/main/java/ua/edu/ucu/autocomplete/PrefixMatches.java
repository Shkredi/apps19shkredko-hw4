package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.RWayTrie;
import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author andrii
 */
public class PrefixMatches {

    private Trie trie;



    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        int n = 0;
        for (String str :strings) {
            for (String word: str.split(" ")) {
                if (word.length() > 2) {
                    this.trie.add(new Tuple(word, word.length()));
                    n += 1;
                }
            }
        }
        return n;
    }

    public boolean contains(String word) {
        return this.trie.contains(word);
    }

    public boolean delete(String word) {
        return this.trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        if (pref.length() > 1) {
            return this.trie.wordsWithPrefix(pref);
        }
        return null;
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref.length() > 1) {
            RWayTrie trie = (RWayTrie) this.trie;
            return new Iterable<String>() {
                class BFSkGenerator implements Iterator<String> {

                    private int k;
                    private HashSet<Integer> l;
                    private RWayTrie.BFSGenerator gen;
                    private String word;

                    private BFSkGenerator(int k){
                        this.k = k;
                        this.l = new HashSet<>();
                        this.gen = new RWayTrie.BFSGenerator(trie);
                        this.word = this.getNext();
                    }

                    private String getNext(){
                        if (this.gen.hasNext()) {
                            return this.gen.next();
                        }else {
                            return null;
                        }
                    }

                    @Override
                    public boolean hasNext() {
                        return !(this.word == null) &&
                                !(!this.l.contains(this.word.length())
                                        && this.l.size() == this.k);
                    }

                    @Override
                    public String next() {
                        String res = this.word;
                        this.l.add(res.length());
                        this.word = this.getNext();
                        return res;
                    }
                }

                @Override
                public Iterator<String> iterator() {
                    return new BFSkGenerator(k);
                }
            };
        }
        return null;
    }

    public int size() {
        return this.trie.size();
    }

    @Override
    public String toString() {
        return this.trie.toString();
    }
}
