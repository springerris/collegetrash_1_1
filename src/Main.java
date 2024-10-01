import java.io.IOException;
import java.rmi.AccessException;
import java.util.Scanner;

public class Main {
    public static boolean strIsNum(String str) {
        if (str.isEmpty()) return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) return false;
        }
        return true;
    }

    public static int mainMenu() {

        System.out.println("Добро пожаловать в систему управления записями БД кафедры.");
        System.out.println("Введите 1 для показывания записей таблиц.");
        System.out.println("Введите 2 для добавления записи в таблицу.");
        System.out.println("Введите 3 для обновления записи.");
        System.out.println("Введите 4 для удаления записи.");
        System.out.println("Введите q для выхода.");
        Scanner input = new Scanner(System.in);
        String option = input.next();
        switch (option) {
            case "1": {
                Kaf_record_storage.get_records();
                Kaf_record_storage.show_records();
                return 1;

            }

            case "2": {
                DB.add_to_record();
                Kaf_record_storage.show_records();
                return 1;

            }

            case "3": {
                System.out.println("Введите ID для обновления");
                String id = input.next();
                while (!strIsNum(id)) {
                    System.out.println("ID должно быть числом");
                    id = input.next();
                }
                DB.update_record(Integer.parseInt(id));
                Kaf_record_storage.show_records();
                return 1;

            }

            case "4": {
                System.out.println("Введите ID для удаления");
                String id = input.next();
                while (!strIsNum(id)) {
                    System.out.println("ID должно быть числом");
                    id = input.next();
                    }
                    DB.delete_record(Integer.parseInt(id));
                    Kaf_record_storage.show_records();
                    return 1;
                }

            case "q": {
                return 0;


            }

            default:
                return 1;




        }
    }

    public static void main(String[] args) throws IOException {
        int opt = mainMenu();
        while (opt != 0) {
            opt = mainMenu();
        }
    }

}
