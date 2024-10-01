package com.github.springerris;

import java.util.ArrayList;

public class KafRecordFormatter {

    public static ArrayList<KafRecord> list = new ArrayList<>();

    public static void empty(int cnt) {
        if (cnt > 0) {
            for (int i = 0; i < cnt; i++) {
                System.out.print(" ");
            }
        }
    }

    public static void getRecords() {
        list.clear();
        list = DB.getRecords();
    }

    public static void showRecords() {
    System.out.println("╔════╦══════════════════════════════╦════════════════╗");
    System.out.println("| ID | Название кафедры             | Номер телефона |");
    System.out.println("╚════╩══════════════════════════════╩════════════════╝");
    String str = "";
        for (KafRecord kafRecord : list) {
            String lenst = String.valueOf(kafRecord.id());
            str = "| " + lenst;
            System.out.print(str);
            empty(3 - lenst.length());
            str = "| " + kafRecord.naz();
            System.out.print(str);
            empty(29 - kafRecord.naz().length());
            str = "| " + kafRecord.tel();
            System.out.print(str);
            empty(15 - kafRecord.tel().length());
            System.out.println("|");
            System.out.println("╚════╩══════════════════════════════╩════════════════╝");
        }
    }

}
