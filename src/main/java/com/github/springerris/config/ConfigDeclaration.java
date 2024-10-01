package com.github.springerris.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record ConfigDeclaration(@Nullable String section, @NotNull String key, @NotNull String value) { }
