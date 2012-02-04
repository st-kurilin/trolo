package org.trolo.bencode.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import javax.annotation.concurrent.Immutable;

/**
 * @author Stanislav Kurilin
 */
@Immutable
public interface Bencode {
    <R> R accept(Visitor<R> visitor);

    interface Visitor<R> {
        R visitDigit(long value);

        R visitData(ImmutableList<Byte> value);

        R visitSequence(ImmutableList<Bencode> value);

        R visitDictionary(ImmutableMap<String, Bencode> value);

    }

    class AbstractVisitor<R> implements Visitor<R> {
        private final R defaultValue;

        public AbstractVisitor(R defaultValue) {
            this.defaultValue = defaultValue;
        }

        public AbstractVisitor() {
            this(null);
        }

        @Override
        public R visitDigit(long value) {
            return defaultValue;
        }

        @Override
        public R visitData(ImmutableList<Byte> value) {
            return defaultValue;
        }

        @Override
        public R visitSequence(ImmutableList<Bencode> value) {
            return defaultValue;
        }

        @Override
        public R visitDictionary(ImmutableMap<String, Bencode> value) {
            return defaultValue;
        }
    }
}
