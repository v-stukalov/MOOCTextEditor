package textgen;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An implementation of the MTG interface that uses a list of lists.
 *
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

    // The list of words with their next words
    private List<ListNode> wordList;

    // The starting "word"
    private String starter;

    //private String prevWord;

    // The random number generator
    private Random rnGenerator;

    public MarkovTextGeneratorLoL(Random generator) {
        wordList = new LinkedList<>();
        starter = "";
        rnGenerator = generator;
    }


    /**
     * Train the generator by adding the sourceText
     */
    @Override
    public void train(String sourceText) {
        List<String> words = getTokens(sourceText, "[a-zA-Z,.!?]+");
        if (words.isEmpty()) return;
        starter = words.get(0);
        int i = 1;
        while (i < words.size()) {
            putInPlace(words.get(i - 1), words.get(i++));
        }
        putInPlace(words.get(i - 1), starter);
    }


    private void putInPlace(String prevWord, String nextWord) {
        ListNode node = findListNode(prevWord);
        if (node != null) {
            node.addNextWord(nextWord);
        } else {
            ListNode newNode = new ListNode(prevWord);
            newNode.addNextWord(nextWord);
            wordList.add(newNode);
        }
    }

    private ListNode findListNode(String word) {
        for (ListNode node : wordList) {
            if (word.equals(node.getWord())) {
                return node;
            }
        }
        return null;
    }

    protected List<String> getTokens(String text, String pattern) {
        ArrayList<String> tokens = new ArrayList<String>();
        Pattern tokSplitter = Pattern.compile(pattern);
        Matcher m = tokSplitter.matcher(text);
        while (m.find()) {
            tokens.add(m.group());
        }
        return tokens;
    }

    /**
     * Generate the number of words requested.
     */
    @Override
    public String generateText(int numWords) {
        if (numWords < 1 || wordList.isEmpty()) return "";
        String currWord = starter;
        StringBuilder output = new StringBuilder(currWord);
        while (--numWords > 0) {
            ListNode node = findListNode(currWord);
            currWord = node.getRandomNextWord(rnGenerator);
            output.append(" ").append(currWord);
        }
        return output.toString();
    }


    // Can be helpful for debugging
    @Override
    public String toString() {
        String toReturn = "";
        for (ListNode n : wordList) {
            toReturn += n.toString();
        }
        return toReturn;
    }

    /**
     * Retrain the generator from scratch on the source text
     */
    @Override
    public void retrain(String sourceText) {
        wordList = new LinkedList<>();
        starter = "";
        train(sourceText);
    }

    // TODO: Add any private helper methods you need here.


    /**
     * This is a minimal set of tests.  Note that it can be difficult
     * to test methods/classes with randomized behavior.
     *
     * @param args
     */
    public static void main(String[] args) {
        // feed the generator a fixed random value for repeatable behavior
        MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
        String textString = "hi there! hi. Leo, hi hi hi Leo. Leo hi there.";
        System.out.println(textString);
        gen.train(textString);
        System.out.println(gen);
        System.out.println(gen.generateText(0));

        textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
        System.out.println(textString);
        gen.train(textString);
        System.out.println(gen);
        System.out.println(gen.generateText(20));
        String textString2 = "You say yes, I say no, " +
                "You say stop, and I say go, go, go, " +
                "Oh no. You say goodbye and I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "I say high, you say low, " +
                "You say why, and I say I don't know. " +
                "Oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "Why, why, why, why, why, why, " +
                "Do you say goodbye. " +
                "Oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "You say yes, I say no, " +
                "You say stop and I say go, go, go. " +
                "Oh, oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello,";
        System.out.println(textString2);
        gen.retrain(textString2);
        System.out.println(gen);
        System.out.println(gen.generateText(20));
    }

}

/**
 * Links a word to the next words in the list
 * You should use this class in your implementation.
 */
class ListNode {
    // The word that is linking to the next words
    private String word;

    // The next words that could follow it
    private List<String> nextWords;

    ListNode(String word) {
        this.word = word;
        nextWords = new LinkedList<String>();
    }

    public String getWord() {
        return word;
    }

    public void addNextWord(String nextWord) {
        nextWords.add(nextWord);
    }

    public String getRandomNextWord(Random generator) {
        return nextWords.get(generator.nextInt(nextWords.size()));
    }

    public String toString() {
        String toReturn = word + ": ";
        for (String s : nextWords) {
            toReturn += s + "->";
        }
        toReturn += "\n";
        return toReturn;
    }

}


