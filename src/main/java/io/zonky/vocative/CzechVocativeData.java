package io.zonky.vocative;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Provides data necessary for finding out a nominative name's gender as well as vocative suffixes
 */
class CzechVocativeData {
    private static final String VOCATIVE_SEX_FILE = "czech-vocative-sex.yml";
    private static final String VOCATIVE_SUFFIX_MALE_FILE = "czech-vocative-suffix-male.yml";

    private Yaml yaml;

    CzechVocativeData() {
        yaml = new Yaml();
    }

    /**
     * @return Mapping of name suffixes to a gender
     */
    Map<String, Boolean> getVocativeSex() {
        try (InputStream inputStream = getInputStream(VOCATIVE_SEX_FILE)) {
            return yaml.load(inputStream);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to load the file from classpath: " + VOCATIVE_SEX_FILE, e);
        }
    }

    /**
     * @return Mapping of nominative male name suffixes to vocative ones
     */
    Map<String, String> getVocativeSuffixForMale() {
        try (InputStream inputStream = getInputStream(VOCATIVE_SUFFIX_MALE_FILE)) {
            return yaml.load(inputStream);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to load the file from classpath: " + VOCATIVE_SUFFIX_MALE_FILE, e);
        }
    }

    private InputStream getInputStream(String fileName) {
        return this.getClass().getClassLoader().getResourceAsStream(fileName);
    }
}
