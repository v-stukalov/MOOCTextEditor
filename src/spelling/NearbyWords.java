/**
 *
 */
package spelling;

import java.util.*;


/**
 * @author UC San Diego Intermediate MOOC team
 */
public class NearbyWords implements SpellingSuggest {
    // THRESHOLD to determine how many words to look through when looking
    // for spelling suggestions (stops prohibitively long searching)
    // For use in the Optional Optimization in Part 2.
    private static final int THRESHOLD = 1000;

    Dictionary dict;

    public NearbyWords(Dictionary dict) {
        this.dict = dict;
    }

    /**
     * Return the list of Strings that are one modification away
     * from the input string.
     *
     * @param s         The original String
     * @param wordsOnly controls whether to return only words or any String
     * @return list of Strings which are nearby the original string
     */
    public List<String> distanceOne(String s, boolean wordsOnly) {
        List<String> retList = new ArrayList<String>();
        insertions(s, retList, wordsOnly);
        subsitution(s, retList, wordsOnly);
        deletions(s, retList, wordsOnly);
        return retList;
    }


    /**
     * Add to the currentList Strings that are one character mutation away
     * from the input string.
     *
     * @param original    The original String
     * @param currentList is the list of words to append modified words
     * @param wordsOnly   controls whether to return only words or any String
     * @return
     */
    public void subsitution(String original, List<String> currentList, boolean wordsOnly) {
        // for each letter in the s and for all possible replacement characters
        for (int index = 0; index < original.length(); index++) {
            for (char c = 'a'; c <= 'z'; c++) {
                // use StringBuffer for an easy interface to permuting the
                // letters in the String
                StringBuffer sb = new StringBuffer(original);
                sb.setCharAt(index, c);
                // if the item isn't in the list, isn't the original string, and
                // (if wordsOnly is true) is a real word, add to the list
                register(currentList, sb.toString(), original, wordsOnly);
            }
        }
    }

    /**
     * Add to the currentList Strings that are one character insertion away
     * from the input string.
     *
     * @param original    The original String
     * @param currentList is the list of words to append modified words
     * @param wordsOnly   controls whether to return only words or any String
     * @return
     */
    public void insertions(String original, List<String> currentList, boolean wordsOnly) {
        for (char c = 'a'; c <= 'z'; c++) {
            for (int i = 0; i <= original.length(); i++) {
                StringBuffer sb = new StringBuffer(original);
                sb.insert(i, c);
                register(currentList, sb.toString(), original, wordsOnly);
            }
        }
    }

    private void register(List<String> currentList, String next, String original, boolean wordsOnly) {
        if (!currentList.contains(next)
                && (!wordsOnly || dict.isWord(next))
                && !original.equals(next)) {
            currentList.add(next);
        }
    }

    /**
     * Add to the currentList Strings that are one character deletion away
     * from the input string.
     *
     * @param original    The original String
     * @param currentList is the list of words to append modified words
     * @param wordsOnly   controls whether to return only words or any String
     * @return
     */
    public void deletions(String original, List<String> currentList, boolean wordsOnly) {
        for (int i = 0; i < original.length(); i++) {
            StringBuffer sb = new StringBuffer(original);
            sb.deleteCharAt(i);
            register(currentList, sb.toString(), original, wordsOnly);
        }
    }

    /**
     * Add to the currentList Strings that are one character deletion away
     * from the input string.
     *
     * @param word           The misspelled word
     * @param numSuggestions is the maximum number of suggestions to return
     * @return the list of spelling suggestions
     */
    @Override
    public List<String> suggestions(String word, int numSuggestions) {
        // initial variables
        Queue<String> queue = new LinkedList<String>();     // String to explore
        HashSet<String> visited = new HashSet<String>();   // to avoid exploring the same
        // string multiple times
        List<String> words = new LinkedList<String>();   // words to return
        // addNode first node
        queue.add(word);
        visited.add(word);
        int threshold = THRESHOLD;
        while (!queue.isEmpty() && numSuggestions > 0 && threshold-- > 0) {
            String next = queue.remove();
            for (String n : distanceOne(next, false)) {
                if (!visited.contains(n)) {
                    visited.add(n);
                    queue.add(n);
                    if (dict.isWord(n)) {
                        words.add(n);
                        numSuggestions--;
                    }
                }
            }
        }
        return words;
    }

    public static void main(String[] args) {
       /* basic testing code to get started */
        String word = "i";
        // Pass NearbyWords any Dictionary implementation you prefer
        Dictionary d = new DictionaryHashSet();
        DictionaryLoader.loadDictionary(d, "data/dict.txt");
        NearbyWords w = new NearbyWords(d);
        List<String> l = w.distanceOne(word, true);
        System.out.println("One away word Strings for for \"" + word + "\" are:");
        System.out.println(l + "\n");

        word = "tailo"; //"kangaroo";
        List<String> suggest = w.suggestions(word, 10);
        System.out.println("Spelling Suggestions for \"" + word + "\" are:");
        System.out.println(suggest);
    }

}
