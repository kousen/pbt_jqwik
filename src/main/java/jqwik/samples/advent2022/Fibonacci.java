package jqwik.samples.advent2022;

import java.math.BigInteger;

public class Fibonacci {
    public static long fibByBinet(long n) {
        double sqrt5 = Math.sqrt(5);
        double numerator = Math.pow(1 + sqrt5, n) - Math.pow(1 - sqrt5, n);
        double denominator = Math.pow(2, n) * sqrt5;
        return Math.round(numerator / denominator);
    }

    public static long iterativeFib(long n) {
        if (n <= 1) return n;
        BigInteger fib = BigInteger.ONE;
        BigInteger prevFib = BigInteger.ONE;

        for (int i = 2; i < n; i++) {
            BigInteger temp = fib;
            fib = fib.add(prevFib);
            prevFib = temp;
        }
        return fib.longValue();
    }
}
