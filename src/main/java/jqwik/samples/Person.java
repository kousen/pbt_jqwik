package jqwik.samples;

public record Person(String firstName, String lastName) {
    public String fullName() {
        return firstName + " " + lastName;
    }
}
