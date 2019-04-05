package io.zonky.vocative

import spock.lang.Specification
import spock.lang.Unroll

class CzechVocativeCaseTest extends Specification {

    def czechVocativeCase = CzechVocativeCase.instance

    def "Vocative Case for a null name"() {
        when:
        czechVocativeCase.getVocativeCase(null)

        then:
        def e = thrown(NullPointerException)
        e.message == "name must not be blank"
    }

    @Unroll
    def "Vocative Case for a blank name '#blankName'"() {
        when:
        czechVocativeCase.getVocativeCase(blankName)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "name must not be blank"

        where:
        blankName << ["", " ", "\t", "   "]
    }

    @Unroll
    def "Vocative name for a female name '#nominative'"() {
        expect:
        czechVocativeCase.getVocativeCase(nominative) == vocative

        where:
        nominative       || vocative
        "markéta"        || "Markéto"
        "maRie"          || "Marie"
        "markéta  anna " || "Markéto Anno"
        "aNna"           || "Anno"
        "Tereza"         || "Terezo"
        "Xénie"          || "Xénie"
    }

    @Unroll
    def "Vocative name for a male name '#nominative'"() {
        expect:
        czechVocativeCase.getVocativeCase(nominative) == vocative

        where:
        nominative || vocative
        "Tom"      || "Tome"
        "Pavel"    || "Pavle"
        "Petr "    || "Petře"
        "Petr tom" || "Petře Tome"
        " jiří"    || "Jiří"
        "feLix"    || "Felixi"
    }
}
