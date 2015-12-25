package spelling;

import java.util.List;

public interface SpellingSuggest {
    List<String> suggestions(String word, int numSuggestions);
}
