package org.trolo.bencode.impl;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Bytes;
import org.testng.annotations.Test;
import org.trolo.bencode.api.Bencode;
import org.trolo.bencode.api.BencodesParser;
import org.trolo.bencode.parser.ParserImpl;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.testng.Assert.assertEquals;


/**
 * @author Stanislav Kurilin
 */
public class ParserTest {
    private final BencodesParser parser = new ParserImpl();

    @Test(dataProvider = "stringBencode", dataProviderClass = BencodeSimpleTestData.class)
    public void test(String toParse, Optional<ImmutableList<Bencode>> result) {
        final Optional<ImmutableList<Bencode>> parse = parser.parse(
                ImmutableList.copyOf(Bytes.asList(toParse.getBytes(Charsets.UTF_8))));
        checkNotNull(parse);
        assertEquals(parse, result);
    }
}
