package jqwik.samples.advent2022;

import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PrimeFactorsTest {

    @Example
    void prime_factors_of_2() {
        List<Integer> factors = PrimeFactors.primeFactors(2);
        assertThat(factors).isEqualTo(List.of(2));
    }

    @Example
    void prime_factors_of_10() {
        List<Integer> factors = PrimeFactors.primeFactors(10);
        assertThat(factors).isEqualTo(List.of(2, 5));
    }

    @Example
    void prime_factors_of_60() {
        List<Integer> factors = PrimeFactors.primeFactors(60);
        assertThat(factors).isEqualTo(List.of(2, 2, 3, 5));
    }

    @Property
    void num_equals_product_of_factors(
            @ForAll @IntRange(min = 2, max = 10_000) int num) {
        List<Integer> factors = PrimeFactors.primeFactors(num);
        int product = factors.stream()
                .reduce(1, (acc, n) -> acc * n);
        assertThat(num).isEqualTo(product);
    }

    @Property
    void factors_of_products_are_factors_of_each(
            @ForAll @IntRange(min = 2, max = 10_000) int a,
            @ForAll @IntRange(min = 2, max = 10_000) int b) {

        List<Integer> factorsA = PrimeFactors.primeFactors(a);
        List<Integer> factorsB = PrimeFactors.primeFactors(b);
        List<Integer> factorsAB = PrimeFactors.primeFactors(a * b);
        List<Integer> factorsAandB =
                Stream.concat(factorsA.stream(), factorsB.stream())
                        .sorted()
                        .toList();
        assertThat(factorsAB).isEqualTo(factorsAandB);
    }
}