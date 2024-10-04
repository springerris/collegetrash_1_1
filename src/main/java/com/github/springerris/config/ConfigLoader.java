package com.github.springerris.config;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ConfigLoader {

    public static @NotNull InputStream loadConfig() throws IOException {
        File codeSource;
        try {
            codeSource = new File(ConfigLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            throw new IOException("Code source is not a valid URI", e);
        }

        if (codeSource.isFile()) {
            // JAR file
            ZipInputStream zis = new ZipInputStream(new FileInputStream(codeSource));
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                if (ze.getName().equals("config.ini")) return zis;
            }
            zis.close();
            throw new IOException("Config not found");
        } else {
            // Development environment
            final File resourcesDir = new File(
                    codeSource.getParentFile()
                            .getParentFile()
                            .getParentFile(),
                    "resources" + File.separator + "main"
            );
            return new FileInputStream(new File(resourcesDir, "config.ini"));
        }
    }

}
