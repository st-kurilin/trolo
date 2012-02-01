package org.trolo.bencode.api;


import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

/**
 * @author: Stanislav Kurilin
 */
public interface Parser {
    Optional<ImmutableList<Bencode>> parse(String in);
}
