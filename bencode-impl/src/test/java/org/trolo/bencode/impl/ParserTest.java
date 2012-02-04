package org.trolo.bencode.impl;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Bytes;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.trolo.bencode.api.Bencode;
import org.trolo.bencode.api.BencodesParser;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.ImmutableList.of;
import static org.testng.Assert.assertEquals;
import static org.trolo.bencode.api.Bencodes.*;
import static org.trolo.common.ByteLists.fromString;


/**
 * @author Stanislav Kurilin
 */
public class ParserTest {
    private final BencodesParser parser = new ParserImpl();

    @Test(dataProvider = "data")
    public void test(String toParse, Optional<ImmutableList<Bencode>> result) {
        final Optional<ImmutableList<Bencode>> parse = parser.parse(ImmutableList.copyOf(Bytes.asList(toParse.getBytes(Charsets.UTF_8))));
        checkNotNull(parse);
        assertEquals(parse, result);
    }


    @DataProvider
    public Object[][] data() {
        return new Object[][]{
                {"i42e", atomic(42)},
                {"i-42e", atomic(-42)},
                {"i0e", atomic(0)},
                {"4:spam", atomic("spam")},
                {"0:", atomic("")},
                {"i677294080e", atomic(677294080)},

                {"9:Hi world!", atomic("Hi world!")},
                {"4:i42e", atomic("i42e")},

                {"l4:spami42ee", atomic(ImmutableList.of(literal(fromString("spam")), digit(42L)))},
                {"d3:bar4:spam3:fooi42ee", atomic(ImmutableMap.of("foo", digit(42L), "bar", literal(fromString("spam"))))},
        };
    }

    private static Optional<ImmutableList<Bencode>> atomic(String value) {
        return Optional.of(of(literal(fromString(value))));
    }

    private static Optional<ImmutableList<Bencode>> atomic(long value) {
        return Optional.of(of(digit(value)));
    }

    private static Optional<ImmutableList<Bencode>> atomic(Map<String, Bencode> value) {
        return Optional.of(of(dictionary(value)));
    }

    private static Optional<ImmutableList<Bencode>> atomic(List<Bencode> value) {
        return Optional.of(of(sequence(value)));
    }

}
