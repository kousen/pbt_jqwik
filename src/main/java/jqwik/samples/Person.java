package jqwik.samples;

// records are immutable
// constructor goes before the braces
// methods inside the record can use the values but not change them
// "getter" methods follow the same name as the property, with parens
// records automatically generate toString, equals, and hashCode
public record Person(String firstName, String lastName) {
    public String fullName() {
        return firstName + " " + lastName;
    }
}
