import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

public class DiscordCharacters {

    /**
     * generates a map of english characters with discord emoji values
     */
    private Map<Character, String> getAlphabetMap() {
        var ENGLISH_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
        var alphaebetMap = ENGLISH_CHARACTERS
                .chars() // IntStream
                .mapToObj(c -> (char) c) // Intstream -> Stream<Character>
                .collect(Collectors.toMap(c -> c, c -> ":regional_indicator_" + c + ":")); // characters to map
        return alphaebetMap;
    }

    /**
     * adds spacing, punctuation marks to the map
     *
     * @return map of english Charactersm punctuation marks with discord emoji values
     */
    private Map<Character, String> getCharMap() {
        try {
            var charMap = getAlphabetMap();
            var emojiMap = Files.lines(Paths.get("res/emoji.txt"))
                    .collect(Collectors.toMap(s -> s.split(",")[0].toCharArray()[0], s -> ":" + s.split(",")[1].strip() + ":"));
            charMap.putAll(emojiMap); // merge the two maps
            charMap.put(' ', "\t");
            return charMap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param words: there word/sentence/phrase to encode
     * @return encoded word/sentence/phrase
     * <p>
     * examples:
     * getEncodedStatement("") returns :eight:
     * getEncodedStatement("hi manuel") returns :regional_indicator_h: :regional_indicator_i: 	 :regional_indicator_m: :regional_indicator_a: :regional_indicator_n: :regional_indicator_u: :regional_indicator_e: :regional_indicator_l:
     */
    public String getEncodedStatement(String words) {
        var charMap = getCharMap();
        return words.toLowerCase()
                .chars()
                .mapToObj(c -> (char) c)
                .map(c -> charMap.get(c) + " ")
                .collect(Collectors.joining()).replaceAll("null", ""); // just in case the user does something stupid
    }

}