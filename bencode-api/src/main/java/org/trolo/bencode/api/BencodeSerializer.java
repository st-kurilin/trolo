package org.trolo.bencode.api;

import com.google.common.collect.ImmutableList;

/**
 * @author Stanislav Kurilin
 */
public interface BencodeSerializer {
    ImmutableList<Byte> serialize(Bencode bencode);
}
