package org.trolo.common;

import com.google.common.base.Optional;

/**
 * @author: Stanislav Kurilin
 */
public interface Parser<R> {
    Optional<R> parse(String in);
}
