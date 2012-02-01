package org.trolo.bencode.impl;

import com.google.common.base.Function;
import com.google.common.base.Optional;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * @author: Stanislav Kurilin
 */
public class Util {
    public static final Function<String, Long> INTEGER_PARSER = new Function<String, Long>() {
        @Override
        public Long apply(@Nullable String input) {
            try {
                return DecimalFormat.getIntegerInstance().parse(input).longValue();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    };

    public static <F, T> Function<Function<F, T>, Optional<T>> fmap(final Optional<F> in) {
        return new Function<Function<F, T>, Optional<T>>() {
            @Override
            public Optional<T> apply(Function<F, T> mapper) {
                if (in.isPresent()) {
                    return Optional.of(mapper.apply(in.get()));
                } else {
                    return Optional.absent();
                }
            }
        };
    }
}
