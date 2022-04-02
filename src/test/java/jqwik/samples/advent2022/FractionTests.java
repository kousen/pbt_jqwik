package jqwik.samples.advent2022;

import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;
import org.apache.commons.math3.fraction.Fraction;
import org.assertj.core.data.Percentage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FractionTests {
    @Example
    void fraction_5_10_becomes_1_2() {
        Fraction fraction = new Fraction(5, 10);
        assertAll(
                () -> assertEquals(1, fraction.getNumerator()),
                () -> assertEquals(2, fraction.getDenominator())
        );
    }

    @Example
    void fraction_4_10_becomes_2_5() {
        Fraction fraction = new Fraction(4, 10);
        assertAll(
                () -> assertEquals(2, fraction.getNumerator()),
                () -> assertEquals(5, fraction.getDenominator())
        );
    }

    @Example
    void fraction_4_9_stays_4_9() {
        Fraction fraction = new Fraction(4, 9);
        assertAll(
                () -> assertEquals(4, fraction.getNumerator()),
                () -> assertEquals(9, fraction.getDenominator())
        );
    }

    @Property
    void simplified_fraction_equals_num_div_denom(
            @ForAll @IntRange(max = 1000) int numerator,
            @ForAll("nonZeroIntegers") int denominator) {
        Fraction fraction = new Fraction(numerator, denominator);
        assertThat(numerator / (double) denominator)
                .isCloseTo(fraction.getNumerator() / (double) fraction.getDenominator(),
                        Percentage.withPercentage(0.000_001));
    }

    @Provide
    Arbitrary<Integer> nonZeroIntegers() {
        return Arbitraries.oneOf(
                Arbitraries.integers().between(1, 1000),
                Arbitraries.integers().between(-1000, -1));
    }

    @Property
    void mult_num_and_denom_by_factor_gives_same_fraction(
            @ForAll @IntRange(max = 1000) int numerator,
            @ForAll("nonZeroIntegers") int denominator,
            @ForAll("nonZeroIntegers") int factor) {
        Fraction fraction = new Fraction(numerator, denominator);
        Fraction withFactor =
                new Fraction(factor * numerator, factor * denominator);
        assertThat(withFactor).isEqualTo(fraction);
    }

}
