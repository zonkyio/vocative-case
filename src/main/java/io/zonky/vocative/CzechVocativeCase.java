package io.zonky.vocative;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.Validate.notBlank;

/**
 * <p>
 * A utility for generating Czech vocative cases out of nominative ones.
 * For example, a vocative case for `Tereza` is `Terezo`.
 * </p>
 *
 * <p>For more information, see <a href="https://en.wikipedia.org/wiki/Vocative_case">Vocative case on Wikipedia</a></p>
 */
public final class CzechVocativeCase {

    private static final String VOWEL = "aáeéěiíoóuúyý";

    private static Map<String, Boolean> vocativeSex;
    private static Map<String, String> vocativeSuffixMale;

    /**
     * Lazily-loaded thread-safe initialisation (classloading uses locks)
     */
    private static class StaticHolder {
        static final CzechVocativeCase instance = new CzechVocativeCase();
    }

    /**
     * @return A thread-safe singleton instance
     */
    public static CzechVocativeCase getInstance() {
        return StaticHolder.instance;
    }

    private CzechVocativeCase() {
        CzechVocativeData czechVocativeData = new CzechVocativeData();
        vocativeSex = czechVocativeData.getVocativeSex();
        vocativeSuffixMale = czechVocativeData.getVocativeSuffixForMale();
    }

    /**
     * Gets a name in vocative
     * @param name in Nominative (1.pad). Must not be blank.
     * @return name in vocative (5.pad)
     */
    public String getVocativeCase(final String name) {
        notBlank(name, "name must not be blank");

        return Stream.of(name.toLowerCase().trim().split("\\s+"))
            .map(this::getVocative)
            .collect(Collectors.joining(" "));
    }

    private String getVocative(final String lowerCaseName) {
        if (isFemale(lowerCaseName)) {
            return StringUtils.capitalize(vocativeFemale(lowerCaseName));
        }
        return StringUtils.capitalize(vocativeMale(lowerCaseName));
    }

    /**
     * Gets a vocative case for a female's name
     */
    private String vocativeFemale(final String name) {
        if ('a' == name.charAt(name.length() - 1)) {
            return name.substring(0, name.length() - 1) + "o";
        } else {
            return name;
        }
    }

    /**
     * Gets a vocative case for a male's name
     */
    private String vocativeMale(final String name) {
        final String suffix = findLongestSuffix(name, vocativeSuffixMale.keySet());
        if (suffix != null) {
            // remove the found suffix and append the one from map
            return StringUtils.removeEnd(name, suffix) + vocativeSuffixMale.get(suffix);
        }

        // return when the trailing character is a vowel
        final CharSequence lastChar = String.valueOf(name.charAt(name.length() - 1));
        if (VOWEL.contains(lastChar)) {
            return name;
        }

        // when the suffix is not found, add a default
        return name + vocativeSuffixMale.get("");
    }

    /**
     * Checks whether the name belongs to a female, or a male
     * @param name A name
     * @return True if the name belongs to a female, false otherwise
     */
    private boolean isFemale(final String name) {
        final String suffix = findLongestSuffix(name, vocativeSex.keySet());

        if (suffix != null) {
            return vocativeSex.get(suffix);
        }

        // no entry in the map means the name belongs to a male
        return false;
    }

    /**
     * Finds the longest suffix in the provided set
     * @return The longest suffix if found, null otherwise
     */
    private String findLongestSuffix(final String name, final Set<String> suffixes) {
        // it is important to try suffixes from longest to shortest
        for (int i = 0; i < name.length(); i++) {
            String suffix = name.substring(i);
            if (suffixes.contains(suffix)) {
                return suffix;
            }
        }

        return null;
    }
}
