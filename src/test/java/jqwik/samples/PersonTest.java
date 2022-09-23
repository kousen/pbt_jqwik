package jqwik.samples;

import net.jqwik.api.*;

// from https://blog.johanneslink.net/2018/03/29/jqwik-on-junit5/
@SuppressWarnings("unused")
class PersonTest {
    @Property
    boolean anyValidPersonHasAFullName(@ForAll("validPerson") Person aPerson) {
        return aPerson.fullName().length() >= 5;
    }

    @Provide
    Arbitrary<Person> validPerson() {
        Arbitrary<String> firstName = Arbitraries.strings()
                .withCharRange('a', 'z')
                .ofMinLength(2).ofMaxLength(10)
                .map(String::toUpperCase);
        Arbitrary<String> lastName = Arbitraries.strings()
                .withCharRange('a', 'z')
                .ofMinLength(2).ofMaxLength(20);
        return Combinators.combine(firstName, lastName).as(Person::new);
    }
}