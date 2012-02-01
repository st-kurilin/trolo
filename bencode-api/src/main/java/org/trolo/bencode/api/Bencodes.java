package org.trolo.bencode.api;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Longs;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author: Stanislav Kurilin
 */
public final class Bencodes {
    private Bencodes() {
    }


    public static Function<Long, Bencode> DIGIT = new Function<Long, Bencode>() {

        @Override
        public Bencode apply(final Long value) {
            checkNotNull(value);
            return new Bencode() {
                public <R> R accept(Visitor<R> visitor) {
                    return visitor.visitDigit(value);
                }

                @Override
                public int hashCode() {
                    return Objects.hashCode(value);
                }

                @Override
                public boolean equals(Object obj) {
                    if (!(obj instanceof Bencode)) return false;
                    return ((Bencode) obj).accept(new AbstractVisitor<Boolean>(false) {
                        @Override
                        public Boolean visitDigit(long otherValue) {
                            return Longs.compare(value, otherValue) == 0;
                        }
                    });
                }

                @Override
                public String toString() {
                    return Objects.toStringHelper(Bencode.class).add("value", value).toString();
                }
            };
        }
    };
    public static Function<String, Bencode> LITERAL = new Function<String, Bencode>() {
        @Override
        public Bencode apply(final String value) {
            return new Bencode() {
                public <R> R accept(Visitor<R> visitor) {
                    return visitor.visitLiteral(value);
                }

                @Override
                public int hashCode() {
                    return Objects.hashCode(value);
                }

                @Override
                public boolean equals(Object obj) {
                    if (!(obj instanceof Bencode)) return false;
                    return ((Bencode) obj).accept(new AbstractVisitor<Boolean>(false) {
                        @Override
                        public Boolean visitLiteral(String otherValue) {
                            return value.equals(otherValue);
                        }
                    });
                }

                @Override
                public String toString() {
                    return Objects.toStringHelper(Bencode.class).add("value", value).toString();
                }
            };
        }
    };

    public static Bencode dictionary(Map<String, Bencode> value) {
        final ImmutableMap<String, Bencode> safeValue = ImmutableMap.copyOf(value);
        return new Bencode() {
            @Override
            public <R> R accept(Visitor<R> visitor) {
                return visitor.visitDictionary(safeValue);
            }

            @Override
            public int hashCode() {
                return Objects.hashCode(safeValue);
            }

            @Override
            public boolean equals(Object obj) {
                if (!(obj instanceof Bencode)) return false;
                return ((Bencode) obj).accept(new AbstractVisitor<Boolean>(false) {
                    @Override
                    public Boolean visitDictionary(ImmutableMap<String, Bencode> otherValue) {
                        return Objects.equal(safeValue, otherValue);
                    }
                });
            }

            @Override
            public String toString() {
                return Objects.toStringHelper(Bencode.class).add("value", safeValue).toString();
            }
        };
    }

    public static Bencode sequence(List<Bencode> value) {
        final ImmutableList<Bencode> safeValue = ImmutableList.copyOf(value);
        return new Bencode() {
            @Override
            public <R> R accept(Visitor<R> visitor) {
                return visitor.visitSequence(safeValue);
            }

            @Override
            public int hashCode() {
                return Objects.hashCode(safeValue);
            }

            @Override
            public boolean equals(Object obj) {
                if (!(obj instanceof Bencode)) return false;
                return ((Bencode) obj).accept(new AbstractVisitor<Boolean>(false) {
                    @Override
                    public Boolean visitSequence(ImmutableList<Bencode> otherValue) {
                        return Objects.equal(safeValue, otherValue);
                    }
                });
            }

            @Override
            public String toString() {
                return Objects.toStringHelper(Bencode.class).add("value", safeValue).toString();
            }
        };
    }

}
