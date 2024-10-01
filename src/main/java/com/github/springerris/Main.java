package com.github.springerris;

import com.github.springerris.db.DB;
import com.github.springerris.db.KafRecordFormatter;

public class Main {

    public static void main(String[] args) {
        // Here we process args, setup state for the application, and launch it.
        // The Application implements Runnable in case we want to ever pass it to a Thread,
        // as Runnable can be an argument to the Thread constructor.
        final DB db = new DB(
                "jdbc:mysql://localhost/lab1_jdbc",
                "admin",
                ""
        );
        final KafRecordFormatter formatter = new KafRecordFormatter();

        // Launch!
        final Application app = new Application(db, formatter);
        app.run();
    }

}
