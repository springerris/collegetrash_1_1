package com.github.springerris.db;

import org.jetbrains.annotations.NotNull;

public record KafRecord(int id, @NotNull String naz, @NotNull String tel) { }
