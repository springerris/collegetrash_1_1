package com.github.springerris;

import com.github.springerris.config.ConfigDeclaration;
import com.github.springerris.config.ConfigLoader;
import com.github.springerris.config.ConfigReader;
import com.github.springerris.db.DB;
import com.github.springerris.db.KafRecordFormatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        // Here we process args, setup state for the application, and launch it.
        // The Application implements Runnable in case we want to ever pass it to a Thread,
        // as Runnable can be an argument to the Thread constructor.
        final String[] dbParams = new String[] {
                // url
                "jdbc:mysql://127.0.0.1/",
                // user
                "",
                // pass
                ""
        };
        readDatabaseConfig(dbParams);

        final DB db = new DB(
                dbParams[0],
                dbParams[1],
                dbParams[2]
        );
        final KafRecordFormatter formatter = new KafRecordFormatter();

        // Launch!
        final Application app = new Application(db, formatter);
        try {
            app.run();
        } finally {
            try {
                db.close();
            } catch (SQLException e) {
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
            }
        }
    }

    private static final List<String> DATABASE_CONFIG_KEYS = List.of("url", "username", "passphrase");
    private static void readDatabaseConfig(String[] params) {
        try (InputStream in = ConfigLoader.loadConfig();
             InputStreamReader rawReader = new InputStreamReader(in, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(rawReader);
             ConfigReader reader = new ConfigReader(bufferedReader)
        ) {
            ConfigDeclaration decl;
            int index;
            while ((decl = reader.readDeclaration()) != null) {
                if (!Objects.equals("database", decl.section())) continue;
                index = DATABASE_CONFIG_KEYS.indexOf(decl.key());
                if (index == -1) continue;
                params[index] = decl.value();
            }
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            System.exit(1);
        }
    }

}
