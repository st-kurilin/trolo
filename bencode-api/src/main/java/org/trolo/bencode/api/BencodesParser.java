package org.trolo.bencode.api;


import com.google.common.collect.ImmutableList;
import org.trolo.common.Parser;

import javax.annotation.concurrent.Immutable;

/**
 * @author Stanislav Kurilin
 */
@Immutable
public interface BencodesParser extends Parser<ImmutableList<Byte>, ImmutableList<Bencode>> {
}
