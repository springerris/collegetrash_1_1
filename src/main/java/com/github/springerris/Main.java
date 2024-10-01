package com.github.springerris;

public class Main {

    public static void main(String[] args) {
        // Here we process args, setup state for the application, and launch it.
        // The Application implements Runnable in case we want to ever pass it to a Thread,
        // then the Application can be an argument to the Thread constructor.
        final Application app = new Application(null, null); // TODO
        app.run();
    }

}
