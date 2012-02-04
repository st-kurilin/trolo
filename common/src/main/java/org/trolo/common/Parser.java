package org.trolo.common;

import com.google.common.base.Optional;

import javax.annotation.concurrent.Immutable;

/**
 * @author Stanislav Kurilin
 */
@Immutable
public interface Parser<F, R> {
    Optional<R> parse(F in);
}
