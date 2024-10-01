package com.github.springerris.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Doesn't implement the full INI specification, just a quick lazy parser for this project.
 */
public class ConfigReader implements Closeable {

    private static final Pattern PATTERN_SECTION = Pattern.compile(
            "^\\[\\s*([a-z]+)\\s*]$",
            Pattern.CASE_INSENSITIVE
    );

    private static final Pattern PATTERN_DECL = Pattern.compile(
            "^([a-z]+)\\s*=\\s*(.*)$",
            Pattern.CASE_INSENSITIVE
    );

    private final BufferedReader in;
    private String section = null;
    public ConfigReader(@NotNull BufferedReader in) {
        this.in = in;
    }

    private @NotNull ReadResult readInternal() throws IOException {
        final String line = this.in.readLine();
        if (line == null) return ReadResult.EOF;
        Matcher matcher;

        matcher = PATTERN_DECL.matcher(line);
        if (matcher.matches()) {
            return new ReadResult(false, new ConfigDeclaration(this.section, matcher.group(1), matcher.group(2)));
        }

        matcher = PATTERN_SECTION.matcher(line);
        if (matcher.matches()) {
            this.section = matcher.group(1);
        }

        return ReadResult.NO_DECL;
    }

    public @Nullable ConfigDeclaration readDeclaration() throws IOException {
        ReadResult rr;
        while (!(rr = this.readInternal()).eof) {
            if (rr.decl != null) return rr.decl;
        }
        return null;
    }

    @Override
    public void close() throws IOException {
        this.in.close();
    }

    //

    private record ReadResult(boolean eof, @Nullable ConfigDeclaration decl) {
        static final ReadResult EOF = new ReadResult(true, null);
        static final ReadResult NO_DECL = new ReadResult(false, null);
    }

}
