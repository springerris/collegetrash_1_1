package com.github.springerris;

import com.github.springerris.db.DB;
import com.github.springerris.db.KafRecordFormatter;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Scanner;

public class Application implements Runnable {

    private final DB db;
    private final KafRecordFormatter formatter;
    private final Scanner scanner;

    public Application(@NotNull DB db, @NotNull KafRecordFormatter formatter) {
        this.db = db;
        this.formatter = formatter;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        try {
            int opt = this.mainMenu();
            while (opt != 0) opt = this.mainMenu();
        } catch (SQLException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    public int mainMenu() throws SQLException {
        System.out.println("Добро пожаловать в систему управления записями БД кафедры.");
        System.out.println("Введите 1 для показывания записей таблиц.");
        System.out.println("Введите 2 для добавления записи в таблицу.");
        System.out.println("Введите 3 для обновления записи.");
        System.out.println("Введите 4 для удаления записи.");
        System.out.println("Введите q для выхода.");

        String option = this.scanner.next();
        return switch (option.length() != 1 ? ' ' : option.charAt(0)) {
            case '1' -> this.option1();
            case '2' -> this.option2();
            case '3' -> this.option3();
            case '4' -> this.option4();
            case 'q' -> 0;
            default -> 1;
        };
    }

    private int option1() throws SQLException {
        this.populateAndPrint();
        return 1;
    }

    private int option2() throws SQLException {
        System.out.println("Введите название кафедры");
        String str1 = this.scanner.next();
        System.out.println("Введите номер телефона кафедры");
        System.out.println("Телефон должен содержать 11 цифр без пробелов");
        String str2 = this.scanner.next();
        while (str2.length() != 11) {
            System.out.println("Телефон должен содержать 11 цифр без пробелов");
            str2 = this.scanner.next();
        }

        this.db.addRecord(str1, str2);
        this.populateAndPrint();
        return 1;
    }

    private int option3() throws SQLException {
        System.out.println("Введите ID для обновления");

        String tmp = this.scanner.next();
        while (stringIsNotNumber(tmp)) {
            System.out.println("ID должно быть числом");
            tmp = this.scanner.next();
        }
        int id = Integer.parseInt(tmp);

        System.out.println("Введите название кафедры");
        String str1 = this.scanner.next();
        System.out.println("Введите номер телефона кафедры");
        System.out.println("Телефон должен содержать 11 цифр без пробелов");
        String str2 = this.scanner.next();
        while (str2.length() != 11) {
            System.out.println("Телефон должен содержать 11 цифр без пробелов");
            str2 = this.scanner.next();
        }

        this.db.updateRecord(id, str1, str2);
        this.populateAndPrint();
        return 1;
    }

    private int option4() throws SQLException {
        System.out.println("Введите ID для удаления");
        String id = this.scanner.next();
        while (stringIsNotNumber(id)) {
            System.out.println("ID должно быть числом");
            id = this.scanner.next();
        }

        this.db.deleteRecord(Integer.parseInt(id));
        this.populateAndPrint();
        return 1;
    }

    private void populateAndPrint() throws SQLException {
        this.formatter.populate(this.db);
        System.out.print(this.formatter.format());
    }

    private boolean stringIsNotNumber(String str) {
        if (str.isEmpty()) return true;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) return true;
        }
        return false;
    }

}
