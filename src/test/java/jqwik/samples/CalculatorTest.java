package jqwik.samples;

import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.Size;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// from https://github.com/jlink/jqwik-samples
class CalculatorTest {
    @Example
    void summingUpZeros() {
        int result = new Calculator().sum(List.of(0, 0, 0));
        assertThat(result).isEqualTo(0);
    }

    @Example
    void summingUpTwoNumbers() {
        int result = new Calculator().sum(List.of(1, 41));
        assertThat(result).isEqualTo(42);
    }

    @Property
    boolean sumsOfSmallPositivesAreAlwaysPositive(
            @ForAll @Size(min = 1, max = 10)
                    List<@IntRange(min = 1, max = 1000) Integer> addends) {
        int result = new Calculator().sum(addends);
        return result > 0;
    }

    @Property
    void addingZeroToAnyNumberResultsInNumber(@ForAll int aNumber) {
        int result = new Calculator().sum(List.of(aNumber, 0));
        assertThat(result).isEqualTo(aNumber);
    }
}