package org.trolo.bencode.impl;

import com.google.common.base.Optional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author: Stanislav Kurilin
 */
public class SimpleReader {
    final String source;
    int curPos = 0;

    public SimpleReader(String source) {
        this.source = checkNotNull(source);
    }

    public Optional<String> next(Pattern regexp) {
        final String current = source.substring(curPos);
        final Matcher matcher = regexp.matcher(current);
        if(!matcher.lookingAt())return Optional.absent();
        checkState(matcher.groupCount() == 1, "Expected: 1, but was: %s. Parsing %s with %s",
                matcher.groupCount(), current, regexp.pattern());
        curPos += matcher.group(0).length();
        return Optional.of(matcher.group(1));
    }

    public boolean hasMore() {
        return source.length() > curPos;
    }
}
