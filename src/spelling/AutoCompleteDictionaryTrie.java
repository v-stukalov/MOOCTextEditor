package spelling;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 *
 * @author You
 */
public class AutoCompleteDictionaryTrie implements Dictionary, AutoComplete {

    private TrieNode root;
    private int size;

    public AutoCompleteDictionaryTrie() {
        root = new TrieNode();
    }

    /**
     * Insert a word into the trie.
     * For the basic part of the assignment (part 2), you should ignore the word's case.
     * That is, you should convert the string to all lower case as you addNode it.
     */
    public boolean addWord(String word) {
        if (!isWord(word)) {
            addNode(root, word.toLowerCase().toCharArray(), 0);
        }
        return true;
    }

    /**
     * Return the number of words in the dictionary.  This is NOT necessarily the same
     * as the number of TrieNodes in the trie.
     */
    public int size() {
        //TODO: Implement this method
        return this.size;
    }

    /**
     * Returns whether the string is a word in the trie
     */
    @Override
    public boolean isWord(String str) {
        // TODO: Implement this method
        TrieNode node = findNode(str);
        return node != null && node.endsWord();
    }

    private TrieNode findNode(String str) {
        char[] chars = str.toLowerCase().toCharArray();
        Queue<Character> queue = new LinkedList<>();
        for (Character c : chars) {
            queue.add(c);
        }
        TrieNode node = root;
        while (node != null && !queue.isEmpty()) {
            Character c = queue.remove();
            node = node.getChild(c);
        }
        return node;
    }


    /**
     * * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     *
     * @param prefix         The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */
    @Override
    public List<String> predictCompletions(String prefix, int numCompletions) {
        // TODO: Implement this method
        // This method should implement the following algorithm:
        // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
        //    empty list
        // 2. Once the stem is found, perform a breadth first search to generate completions
        //    using the following algorithm:
        //    Create a queue (LinkedList) and add the node that completes the stem to the back
        //       of the list.
        //    Create a list of completions to return (initially empty)
        //    While the queue is not empty and you don't have enough completions:
        //       remove the first Node from the queue
        //       If it is a word, add it to the completions list
        //       Add all of its child nodes to the back of the queue
        // Return the list of completions

        List completions = Collections.EMPTY_LIST;

        TrieNode stem = findNode(prefix);
        if (stem == null) return completions;

        Queue<TrieNode> queue = new LinkedList<>();
        queue.add(stem);

        completions = new LinkedList<String>();
        while (!queue.isEmpty() && numCompletions > 0) {
            TrieNode node = queue.remove();
            if (node.endsWord()) {
                completions.add(node.getText());
                numCompletions--;
            }
            for (Character c : node.getValidNextCharacters()) {
                queue.add(node.getChild(c));
            }
        }

        return completions;
    }

    // For debugging
    public void printTree() {
        printNode(root);
    }

    /**
     * Do a pre-order traversal from this node down
     */
    public void printNode(TrieNode curr) {
        if (curr == null)
            return;

        System.out.println(curr.getText() + (curr.endsWord() ? "*" : ""));

        TrieNode next;
        for (Character c : curr.getValidNextCharacters()) {
            next = curr.getChild(c);
            printNode(next);
        }
    }

    public void addNode(TrieNode node, char[] chars, int idx) {
        // having node, char[] and idx...
        // if idx>= char[].length then return
        // does node have child for char[idx]?
        // yes: take child, inc idx and go to start
        // no: addNode char[idx] into node, inc idx and go to start
        if (idx >= chars.length) {
            node.setEndsWord(true);
            size++;
            return;
        }
        Character c = chars[idx];
        TrieNode child = node.getChild(c);
        if (child != null) {
            addNode(child, chars, ++idx);
        } else {
            addNode(node.insert(c), chars, ++idx);
        }
    }

    public static void main(String[] args) {
        AutoCompleteDictionaryTrie trie = new AutoCompleteDictionaryTrie();
        trie.addWord("ear");
        trie.addWord("east");
        trie.addWord("easter");
        trie.printTree();
        System.out.println(trie.isWord("ear"));
        System.out.println(trie.isWord("east"));
        System.out.println(trie.isWord("eas"));
        System.out.println(trie.isWord("hi"));
        System.out.println(trie.predictCompletions("ea", 5));
        System.out.println(trie.predictCompletions("h", 15));
    }
}