package org.trolo.bencode.impl;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.trolo.bencode.api.Bencode;
import org.trolo.bencode.api.BencodesParser;

import static com.google.common.base.Functions.compose;
import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.ImmutableList.builder;
import static java.util.regex.Pattern.compile;
import static org.trolo.bencode.api.Bencodes.*;
import static org.trolo.bencode.impl.Util.INTEGER_PARSER;

/**
 * @author: Stanislav Kurilin
 */
public class ParserImpl implements BencodesParser {
    public Optional<ImmutableList<Bencode>> parse(String in) {
        final ImmutableList.Builder<Bencode> result = builder();
        final SimpleReader reader = new SimpleReader(in);

        while (reader.hasMore()) {
            final Optional<Bencode> parseResult = parseAtom(reader);
            if (!parseResult.isPresent()) {
                return absent();
            }
            result.add(parseResult.get());
        }
        return of(result.build());
    }


    private Optional<Bencode> parseAtom(SimpleReader scanner) {
        return parseDigit(scanner).or(parseLiteral(scanner)).or(parseDictionary(scanner).or(parseSequence(scanner)));
    }

    private Optional<Bencode> parseSequence(SimpleReader scanner) {
        final Optional<String> listFlag = scanner.next(compile("(l)"));
        if (!listFlag.isPresent()) return absent();
        final ImmutableList.Builder<Bencode> builder = ImmutableList.builder();
        while (!scanner.next(compile("(e)")).isPresent()) {
            final Optional<Bencode> val = parseAtom(scanner);
            if (!val.isPresent()) return absent();
            builder.add(val.get());
        }
        return Optional.of(sequence(builder.build()));
    }

    private Optional<Bencode> parseDictionary(SimpleReader scanner) {
        final Optional<String> dictFlag = scanner.next(compile("(d)"));
        if (!dictFlag.isPresent()) return absent();
        final ImmutableMap.Builder<String, Bencode> builder = ImmutableMap.builder();
        while (!scanner.next(compile("(e)")).isPresent()) {
            final Optional<Bencode> key = parseLiteral(scanner);
            final Optional<Bencode> val = parseAtom(scanner);
            if (!key.isPresent() || !val.isPresent()) return absent();
            final String keyVal = key.get().accept(new Bencode.AbstractVisitor<String>() {
                @Override
                public String visitLiteral(String value) {
                    return value;
                }
            });
            builder.put(keyVal, val.get());
        }
        return Optional.of(dictionary(builder.build()));
    }

    private Optional<Bencode> parseLiteral(SimpleReader reader) {
        final Optional<String> length = reader.next(compile("(\\d+):"));
        if (!length.isPresent()) return absent();
        final Long lengthVal = INTEGER_PARSER.apply(length.get());
        if (lengthVal == 0) return Optional.of(LITERAL.apply(""));
        return Util.<String, Bencode>fmap(reader.next(compile(String.format("(.{%s})", lengthVal)))).apply(LITERAL);
    }


    private Optional<Bencode> parseDigit(SimpleReader reader) {
        final Optional<String> read = checkNotNull(reader.next(compile("i(-?\\d+)e")));
        return Util.<String, Bencode>fmap(
                read)
                .apply(compose(DIGIT, INTEGER_PARSER));
    }


}
