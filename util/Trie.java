package util;
/*
 * Prefix-Trie. Supports linear time find() and insert(). 
 * Supports determining whether a word is a full word in the 
 * Trie or a prefix.
 * @author Kamran Siddiqui
 */

import java.util.Random;

public class Trie {
    
    public static final int R = 255;
    private Node root = new Node();

    /** Checks if the given word or prefix is contained in the Trie
      * @param s - string that you are checking is in the trie
      * @param isFullWord - tells the method if it should check for a prefix or a word
      * @return returns a boolean; true if prefix/word is in the trie or false if not */
    public boolean find(String s, boolean isFullWord) {
        Node n = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!n.links.containsKey(c)) {
                return false;
            }
            n = n.links.get(c);
        }
        if (isFullWord) {
            return n.isWord;
        } else {
            return !n.links.isEmpty();
        }
    }

    /** Inserts a string into the trie. Throws an IllegalArgumentException if a null or empty 
        string is added.
      * @param s - the string you are trying to insert */
    public void insert(String s) {
        if (s == null  || s.equals("")) {
            throw new IllegalArgumentException("Trie cannot hold null or emtpy string.");
        }
        put(root, s, 0);
    }

    /** helps to put in a trie
      * @param n - trie that your are inserting in.
      * @param key - the key you are insering
      * @param ind - the index of char we are looking at currently
      * @return a Node */
    private Node put(Node n, String key, int ind) {
        if (n == null) {
            n = new Node();
        }
        if (ind == key.length()) {
            n.isWord  = true;
            return n;
        }
        char c = key.charAt(ind);
        n.links.put(c, put(n.links.get(c), key, ind + 1));
        n.chars.add(c);
        return n;
    }

    /** Checks if the given word or prefix is contained in the Trie
      * @param s - string that you are traversing in the trie
      * @return returns a Character; picks randomly from the children of last char node of the given string */
    public Character getRandomGuess(String s) {
        Node n = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!n.links.containsKey(c)) {
                return null;
            }
            n = n.links.get(c);
        }
        Random r = new Random();
        return n.chars.get(r.nextInt(n.chars.size()));
    }

    /** Returns the node corresponding to the last character in a given prefix
      * @param s - the given prefix
      * @return returns the Node described above*/
    public Node traversePrefix(String s) {
        Node n = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!n.links.containsKey(c)) {
                return null;
            }
            n = n.links.get(c);
        }
        return n;
    }



    /** returns root node of the trie 
      * @return the root Node*/
    public Node root() {
        return root;
    }
}
