package ua.edu.ucu.tries;

import ua.edu.ucu.utils.Queue;

import java.util.Hashtable;
import java.util.Iterator;

public class RWayTrie implements Trie {

    public static class BFSGenerator implements Iterator<String> {

        private Queue nodes;

        public BFSGenerator(RWayTrie trie){
            this.nodes = new Queue();
            this.nodes.enqueue(trie);
        }

        @Override
        public boolean hasNext() {
            return !this.nodes.isEmpty();
        }

        private void nextNode(){
            RWayTrie cur = (RWayTrie)this.nodes.peek();
            for (Character c:cur.dict.keySet()) {
                this.nodes.enqueue(cur.dict.get(c));
            }
            this.nodes.dequeue();
        }

        @Override
        public String next() {
            while (!((RWayTrie)this.nodes.peek()).end){
                this.nextNode();
            }
            String word = "";
            RWayTrie cur = (RWayTrie) this.nodes.peek();
            while (cur.parent != null){
                word = cur.let.toString().concat(word);
                cur = cur.parent;
            }
            this.nextNode();
            return word;
        }
    }

    private Hashtable<Character, RWayTrie> dict = new Hashtable<>();
    private RWayTrie parent = null;
    private int deep = 0;
    private int size = 0;
    private boolean end = false;
    private Character let;

    public RWayTrie(){
    }

    private RWayTrie(RWayTrie parent, Character c){
        this.parent = parent;
        this.deep = parent.deep + 1;
        this.let = c;
    }

    @Override
    public void add(Tuple t) {
        String word = t.term;
        if (!this.contains(word)) {
            RWayTrie curTrie = this;
            for (int i = 0; i < word.length(); i++) {
                Character c = word.charAt(i);
                if (!curTrie.dict.containsKey(c)) {
                    curTrie.dict.put(c, new RWayTrie(curTrie, c));
                }
                curTrie = curTrie.dict.get(c);
                curTrie.size += 1;
            }
            curTrie.end = true;
            this.size += 1;
        }
    }

    @Override
    public boolean contains(String word) {
        RWayTrie curTrie = this;
        for (int i = 0; i < word.length(); i++) {
            Character c = word.charAt(i);
            if (!curTrie.dict.containsKey(c)){
                return false;
            }
            curTrie = curTrie.dict.get(c);
        }
        return curTrie.end;
    }

    @Override
    public boolean delete(String word) {
        if (word.isEmpty()){
            if (this.end){
                this.end = false;
                this.size -= 1;
                return true;
            }
            return false;
        }

        Character c = word.charAt(0);
        if (!this.dict.containsKey(c)){
            return false;
        }

        boolean del = this.dict.get(c).delete(word.substring(1));
        if (this.dict.get(c).isEmpty()){
            this.dict.remove(c);
        }

        if (del){
            this.size -= 1;
        }

        return del;
    }

    @Override
    public Iterable<String> words() {
        RWayTrie trie = this;
        return new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return new BFSGenerator(trie);
            }
        };
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        RWayTrie curTrie = this;
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if (!curTrie.dict.containsKey(c)){
                return new RWayTrie().words();
            }
            curTrie = curTrie.dict.get(c);
        }
        return curTrie.words();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public String toString() {
        return this.dict.toString();
    }

    private boolean isEmpty(){
        return !this.end && this.dict.isEmpty();
    }
}
