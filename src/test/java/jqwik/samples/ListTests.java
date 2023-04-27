package jqwik.samples;

import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.Size;
import net.jqwik.api.constraints.UniqueElements;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// from https://blog.johanneslink.net/2018/03/26/from-examples-to-properties/
public class ListTests {
    //@Example
    @Test
    void reverseList() {
        List<Integer> aList = Arrays.asList(1, 2, 3);
        Collections.reverse(aList);
        assertThat(aList).containsExactly(3, 2, 1);
    }

    // @Report(Reporting.GENERATED)
    @Property
    boolean reverseTwiceIsOriginal(@ForAll List<String> original) {
        return reverse(reverse(original)).equals(original);
    }

    private <T> List<T> reverse(List<T> original) {
        List<T> clone = new ArrayList<>(original);
        Collections.reverse(clone);
        return clone;
    }

    // This is ChatGPT 3.5's solution (which is completely wrong):
//    private <T> List<T> reverseUsingStreams(List<T> original) {
//        return original.stream()
//                .sorted(Collections.reverseOrder())
//                .toList();
//    }
//
//    @Property
//    boolean reverseListUsingStreams(@ForAll List<Integer> original) {
//        return reverseUsingStreams(reverseUsingStreams(original)).equals(original);
//    }

    // @Report(Reporting.GENERATED)
    @Property
    boolean reverseWithWildcardType(@ForAll List<?> original) {
        return reverse(reverse(original)).equals(original);
    }

    @Property
    boolean joiningTwoLists(
            @ForAll List<String> list1,
            @ForAll List<String> list2
    ) {
        List<String> joinedList = new ArrayList<>(list1);
        joinedList.addAll(list2);
        return joinedList.size() == list1.size() + list2.size();
    }

    @Property
    void uniqueInList(@ForAll @Size(5) @UniqueElements
                      List<@IntRange(max = 10) Integer> aList) {
        assertThat(aList).doesNotHaveDuplicates();
        assertThat(aList).allMatch(anInt -> anInt >= 0 && anInt <= 10);
    }

    @Property
    void commutativeSortAndFilter(@ForAll List<Integer> list) {
        List<Integer> sortThenFilter = list.stream()
                .sorted()
                .filter(i -> i % 2 == 0)
                .toList();
        List<Integer> filterThenSort = list.stream()
                .filter(i -> i % 2 == 0)
                .sorted()
                .toList();
        assertThat(sortThenFilter).isEqualTo(filterThenSort);
    }

    // from https://blog.johanneslink.net/2018/07/16/patterns-to-find-properties/
    @Property
    boolean sortingAListWorks(@ForAll List<Integer> unsorted) {
        return isSorted(sort(unsorted));
    }

    private boolean isSorted(List<Integer> sorted) {
        if (sorted.size() <= 1) return true;
        return sorted.get(0) <= sorted.get(1) //
               && isSorted(sorted.subList(1, sorted.size()));
    }

    private List<Integer> sort(List<Integer> unsorted) {
        return unsorted.stream()
                .sorted()
                .toList();
    }
}
