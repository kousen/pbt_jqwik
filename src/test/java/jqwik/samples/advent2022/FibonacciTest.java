package jqwik.samples.advent2022;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Report;
import net.jqwik.api.Reporting;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class FibonacciTest {

    @ParameterizedTest(name = "fib({0}) is {1}")
    @CsvSource({"0, 0", "1, 1", "2, 1", "3, 2",
            "4, 3", "5, 5", "6, 8", "7, 13",
            "8, 21", "9, 34", "10, 55"})
    void fib_of_n(long n, long result) {
        assertThat(Fibonacci.fibByBinet(n)).isEqualTo(result);
    }

    @Property
    void fib_using_iteration_equals_fib_by_Binet(
            @ForAll @IntRange(max = 70) int n
    ) {
        long fibIter = Fibonacci.iterativeFib(n);
        long fibBinet = Fibonacci.fibByBinet(n);
        assertThat(fibIter).isEqualTo(fibBinet);
    }

    // fib(p+q) = fib(p) * fib(q+1) + fib(p-1) * fib(q)
    @Property
    void fib_p_plus_q_formula(
            @ForAll @IntRange(max = 35) int p,
            @ForAll @IntRange(max = 35) int q
    ) {
        assertThat(Fibonacci.fibByBinet(p + q)).isEqualTo(
                Fibonacci.fibByBinet(p) * Fibonacci.fibByBinet(q + 1) +
                        Fibonacci.fibByBinet(p - 1) * Fibonacci.fibByBinet(q)
        );
    }
}
