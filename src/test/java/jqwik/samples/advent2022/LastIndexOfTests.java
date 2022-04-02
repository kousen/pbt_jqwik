package jqwik.samples.advent2022;

import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.StringLength;

import static org.assertj.core.api.Assertions.assertThat;

public class LastIndexOfTests {
    // Checking the "int lastIndexOf(String)" method on String

    @Example
    public void lastIndexOf_in_String() {
        String str = "abcdef";
        int index = str.lastIndexOf("cde");
        assertThat(index).isEqualTo(2);
    }

    @Example
    public void lastIndexOf_not_in_String() {
        String str = "abcdef";
        int index = str.lastIndexOf("hij");
        assertThat(index).isEqualTo(-1);
    }

    @Example
    public void lastIndexOf_on_String_with_multiples() {
        String str = "abcdefabc";
        int index = str.lastIndexOf("abc");
        assertThat(index).isEqualTo(6);
    }

    @Property
    void should_detect_substrings(@ForAll String a, @ForAll String b, @ForAll String c) {
        String str = a + b + c;
        assertThat(str.lastIndexOf(b)).isNotEqualTo(-1);
    }

    @Property
    void should_return_start_index_of_substring(@ForAll String a, @ForAll String b, @ForAll String c) {
        String str = a + b + c;
        int index = str.lastIndexOf(b);
        assertThat(str.substring(index, index + b.length())).isEqualTo(b);
    }

    @Property
    void substring_after_lastIndex_should_not_contain_str(
            @ForAll String a, @ForAll @StringLength(min = 1) String b, @ForAll String c) {
        String str = a + b + c;
        int index = str.lastIndexOf(b);
        String substr = str.substring(index + 1);
        assertThat(substr.lastIndexOf(b)).isEqualTo(-1);
    }
}
