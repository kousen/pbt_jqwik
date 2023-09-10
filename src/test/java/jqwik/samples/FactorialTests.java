package jqwik.samples;

import net.jqwik.api.*;

import static org.apache.commons.math3.util.CombinatoricsUtils.factorial;
import static org.assertj.core.api.Assertions.assertThat;

class FactorialTests {
    @Provide("0 to 20")
    Arbitrary<Integer> integersBetween0and20() {
        return Arbitraries.integers().between(0, 20);
    }

    @Property
    void nonNegativeOutput(@ForAll("0 to 20") int a) {
        Assume.that(a >= 0);
        assertThat(factorial(a)).isGreaterThanOrEqualTo(0);
    }

    @Example
    void factorialOfZero() {
        assertThat(factorial(0)).isEqualTo(1);
    }

    @Example
    void factorialOfOne() {
        assertThat(factorial(1)).isEqualTo(1);
    }

    @Property
    void identityRelation(@ForAll("0 to 20") int a) {
        Assume.that(a > 1);
        assertThat(factorial(a)).isEqualTo(a * factorial(a - 1));
    }

    @Property
    void monotonicity(@ForAll("0 to 20") int a, @ForAll("0 to 20") int b) {
        Assume.that(a > b && b >= 0);
        assertThat(factorial(a)).isGreaterThanOrEqualTo(factorial(b));
    }

    @Property
    void divisionProperty(@ForAll("0 to 20") int a) {
        Assume.that(a > 1);
        assertThat(factorial(a) % factorial(a - 1)).isEqualTo(0);
    }

    // You might want to limit this test case to a reasonable range
    // as factorial grows extremely quickly.
    @Property
    void handleLargeValues(@ForAll("0 to 20") int a) {
        Assume.that(a >= 0 && a <= 20); // Limited to 20! due to integer overflow
        assertThat(factorial(a)).isGreaterThanOrEqualTo(0);
    }
}
