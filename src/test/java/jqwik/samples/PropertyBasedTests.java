package jqwik.samples;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;
import net.jqwik.time.api.constraints.DateRange;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

// from user guide, https://jqwik.net/docs/current/user-guide.html#how-to-use
// @Disabled
public class PropertyBasedTests {
    @Property  // fails!
    boolean absoluteValueOfAllNumbersIsPositive(@ForAll int anInteger) {
        return Math.abs(anInteger) >= 0;
    }

    @Property // fails!
    void lengthOfConcatenatedStringIsGreaterThanLengthOfEach(
            @ForAll String string1, @ForAll String string2
    ) {
        String conc = string1 + string2;
        assertThat(conc.length())
                .isGreaterThan(string1.length());
        assertThat(conc.length())
                .isGreaterThan(string2.length());
    }

    @Property
    void listOfStringsTheFirstCharacterOfWhichMustBeUnique(
            @ForAll @Size(max = 25)
            @UniqueElements(by = FirstChar.class)
                    List<@AlphaChars @StringLength(min = 1, max = 10) String> listOfStrings
    ) {
        Iterable<Character> firstCharacters =
                listOfStrings.stream()
                        .map(s -> s.charAt(0))
                        .collect(Collectors.toList());
        assertThat(firstCharacters)
                .doesNotHaveDuplicates();
    }

    private static class FirstChar implements Function<String, Object> {
        @Override
        public Object apply(String aString) {
            return aString.charAt(0);
        }
    }

    @Property
    boolean concatenatingStringWithInt(
            @ForAll(supplier = ShortStrings.class) String aShortString,
            @ForAll(supplier = TenTo99.class) int aNumber
    ) {
        String concatenated = aShortString + aNumber;
        return concatenated.length() > 2 && concatenated.length() < 11;
    }

    static class ShortStrings implements ArbitrarySupplier<String> {
        @Override
        public Arbitrary<String> get() {
            return Arbitraries.strings().withCharRange('a', 'z')
                    .ofMinLength(1).ofMaxLength(8);
        }
    }

    static class TenTo99 implements ArbitrarySupplier<Integer> {
        @Override
        public Arbitrary<Integer> get() {
            return Arbitraries.integers().between(10, 99);
        }
    }

    @Property
    void generateLocalDatesWithAnnotation(
            @ForAll @DateRange(min = "2019-01-01", max = "2020-12-31") LocalDate localDate) {
        assertThat(localDate).isBetween(
                LocalDate.of(2019, 1, 1),
                LocalDate.of(2020, 12, 31)
        );
    }

    @Provide
    Arbitrary<String> shortStrings() {
        return Arbitraries.strings().withCharRange('a', 'z')
                .ofMinLength(1).ofMaxLength(8);
    }

    @Property
    void letsGenerateGermanZipCodes(@ForAll("germanZipCodes") String zipCode) {
    }

    @Provide
    Arbitrary<String> germanZipCodes() {
        return Arbitraries.strings().withCharRange('0', '9').ofLength(5);
    }

    //@Property(shrinking = ShrinkingMode.OFF)
    @Property(shrinking = ShrinkingMode.FULL)
    // @Report(Reporting.FALSIFIED)
    // 46341 * 46341 = 2_147_488_281, which is > Integer.MAX_VALUE
    boolean rootOfSquareShouldBeOriginalValue(@Positive @ForAll int anInt) {
        int square = anInt * anInt;
        return Math.sqrt(square) == anInt;
    }

    @Property  // NOTE: This fails
    void encodeAndDecodeAreInverse(
            @ForAll @StringLength(min = 1, max = 20) String toEncode,
            @ForAll("charset") String charset
    ) throws UnsupportedEncodingException {
        String encoded = URLEncoder.encode(toEncode, charset);
        assertThat(URLDecoder.decode(encoded, charset)).isEqualTo(toEncode);
    }

    @Property
    void encodeAndDecodeAreInverseForUTF8(
            @ForAll @StringLength(min = 1, max = 20) String toEncode
    ) {
        String encoded = URLEncoder.encode(toEncode, StandardCharsets.UTF_8);
        assertThat(URLDecoder.decode(encoded, StandardCharsets.UTF_8)).isEqualTo(toEncode);
    }

    @Provide
    Arbitrary<String> charset() {
        Set<String> charsets = Charset.availableCharsets().keySet();
        return Arbitraries.of(charsets.toArray(String[]::new));
    }
}
