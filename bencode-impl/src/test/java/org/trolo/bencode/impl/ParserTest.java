package org.trolo.bencode.impl;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.trolo.bencode.api.Bencode;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.ImmutableList.of;
import static org.trolo.bencode.api.Bencodes.*;
import static org.testng.Assert.assertEquals;


/**
 * @author: Stanislav Kurilin
 */
public class ParserTest {


    @Test(dataProvider = "data")
    public void test(String toParse, Optional<ImmutableList<Bencode>> result) {
        final Optional<ImmutableList<Bencode>> parse = new ParserImpl().parse(toParse);
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
                {"9:Hi world!", atomic("Hi world!")},
                {"4:i42e", atomic("i42e")},
                {"l4:spami42ee", atomic(ImmutableList.of(LITERAL.apply("spam"), DIGIT.apply(42L)))},
                {"d3:bar4:spam3:fooi42ee", atomic(ImmutableMap.of("foo", DIGIT.apply(42L), "bar", LITERAL.apply("spam")))},
        };
    }

    private static Optional<ImmutableList<Bencode>> atomic(String value) {
        return Optional.of(of(LITERAL.apply(value)));
    }

    private static Optional<ImmutableList<Bencode>> atomic(long value) {
        return Optional.of(of(DIGIT.apply(value)));
    }

    private static Optional<ImmutableList<Bencode>> atomic(Map<String, Bencode> value) {
        return Optional.of(of(dictionary(value)));
    }
    private static Optional<ImmutableList<Bencode>> atomic(List<Bencode> value) {
            return Optional.of(of(sequence(value)));
        }

}
