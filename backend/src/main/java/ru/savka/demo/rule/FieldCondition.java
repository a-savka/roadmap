package ru.savka.demo.rule;

import java.util.Objects;

public class FieldCondition {

    private final String field;
    private final String expected;

    public FieldCondition(String field, String expected) {
        this.field = field;
        this.expected = expected;
    }

    public String getField() {
        return field;
    }

    public String getExpected() {
        return expected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldCondition that = (FieldCondition) o;
        return Objects.equals(field, that.field) && Objects.equals(expected, that.expected);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, expected);
    }
}
