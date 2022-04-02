package jqwik.samples;

import java.util.List;

public class Calculator {
    public int sum(List<Integer> addends) {
        return addends.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
