package com.github.springerris;

import org.jetbrains.annotations.NotNull;

public record KafRecord(int id, @NotNull String naz, @NotNull String tel) { }
