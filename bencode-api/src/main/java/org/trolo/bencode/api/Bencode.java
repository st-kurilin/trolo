package org.trolo.bencode.api;

import java.util.List;
import java.util.Map;

/**
 * @author: Stanislav Kurilin
 */
public interface Bencode {
    <R> R accept(Visitor<R> visitor);

    interface Visitor<R> {
        R visitDigit(long value);

        R visitLiteral(String value);

        R visitSequence(List<Bencode> value);

        R visitDictionary(Map<String, Bencode> value);

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
        public R visitLiteral(String value) {
            return defaultValue;
        }

        @Override
        public R visitSequence(List<Bencode> value) {
            return defaultValue;
        }

        @Override
        public R visitDictionary(Map<String, Bencode> value) {
            return defaultValue;
        }
    }
}
