import java.util.ArrayList;

public class Kaf_record_storage {
    public static ArrayList<kaf_record> list = new ArrayList<kaf_record>();

    public static void empt(int cnt) {
        if (cnt > 0) {
            for (int i = 0; i < cnt; i++) {
                System.out.print(" ");
            }
        }
    }

    public static void get_records() {
        list.clear();
        list = DB.get_records();
    }

    public static void show_records() {
    System.out.println("╔════╦══════════════════════════════╦════════════════╗");
    System.out.println("| ID | Название кафедры             | Номер телефона |");
    System.out.println("╚════╩══════════════════════════════╩════════════════╝");
    String str = "";
        for (kaf_record kafRecord : list) {
            String lenst = String.valueOf(kafRecord.id);
            str = "| " + lenst;
            System.out.print(str);
            empt(3 - lenst.length());
            str = "| " + kafRecord.naz;
            System.out.print(str);
            empt(29 - kafRecord.naz.length());
            str = "| " + kafRecord.tel;
            System.out.print(str);
            empt(15 - kafRecord.tel.length());
            System.out.println("|");
            System.out.println("╚════╩══════════════════════════════╩════════════════╝");
        }
    }
}
