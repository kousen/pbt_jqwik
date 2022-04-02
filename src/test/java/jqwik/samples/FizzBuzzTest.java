package jqwik.samples;

import net.jqwik.api.*;

// based on example on JQwik home page, https://jqwik.net/
@SuppressWarnings("unused")
class FizzBuzzTest {
    @Property
    boolean every_third_element_starts_with_Fizz(@ForAll("divisibleBy3") int i) {
        return FizzBuzz.fizzBuzz().get(i - 1)
                .startsWith("Fizz");
    }

    @Provide
    Arbitrary<Integer> divisibleBy3() {
        return Arbitraries.integers()
                .between(1, 100)
                .filter(i -> i % 3 == 0);
    }

    @Property
    boolean every_fifth_element_ends_with_Buzz(@ForAll("divisibleBy5") int i) {
        return FizzBuzz.fizzBuzz().get(i - 1)
                .endsWith("Buzz");
    }

    @Provide
    Arbitrary<Integer> divisibleBy5() {
        return Arbitraries.integers()
                .between(1, 100)
                .filter(i -> i % 5 == 0);
    }
}