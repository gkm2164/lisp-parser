package lengine.types;

import lengine.types.collections.traits.Addable;
import lengine.types.collections.traits.Buildable;
import lengine.types.collections.LengineMapKey;
import lengine.types.collections.traits.LengineIterable;
import lengine.types.collections.traits.LengineIterator;
import lengine.types.collections.traits.LengineObjectType;
import lengine.types.collections.traits.Nillable;

import java.util.Objects;

public class LengineString implements LengineIterable, Nillable<LengineString>, Addable<LengineString>, Buildable<LengineString, LengineStringBuilder>, LengineObjectType {
    private final String value;

    public LengineString(String value) {
        this.value = value;
    }

    @Override
    public LengineIterator iterator() {
        return new LengineStringIterator(value);
    }

    @Override
    public Long len() {
        return (long)value.length();
    }

    @Override
    public Object head() {
        return value.charAt(0);
    }

    @Override
    public LengineIterable tail() {
        return create(value.substring(1));
    }

    public static LengineString create(String value) {
        return new LengineString(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LengineString)) return false;

        LengineString that = (LengineString) o;

        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    public LengineString add(Object ys) {
        return create(value + ys.toString());
    }

    @Override
    public LengineString ADD(Object item) {
        return this.add(item);
    }

    @Override
    public LengineString NIL() {
        return LengineString.create("");
    }

    @Override
    public LengineStringBuilder BUILDER() {
        return new LengineStringBuilder();
    }

    @Override
    public Boolean IS_NIL() {
        return this.value.equals("");
    }

    @Override
    public Object get(LengineMapKey key) {
        switch (key.getKey().value) {
            case "length":
                return len();
            case "lower":
                return LengineString.create(this.value.toLowerCase());
            case "upper":
                return LengineString.create(this.value.toUpperCase());
            case "trim":
                return LengineString.create(this.value.trim());
            default:
                throw new RuntimeException("Unsupported operation");
        }
    }
}
