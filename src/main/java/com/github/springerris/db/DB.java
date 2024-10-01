package com.github.springerris.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class DB {

    private static String url = "jdbc:mysql://localhost/lab1_jdbc";
    private static String pwd = "";
    private static String usr = "";

    public static ArrayList<KafRecord> getRecords() {
        ArrayList<KafRecord> templist = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url,usr,pwd)) {
            Statement statement = conn.createStatement();
            String sql = "SELECT id, naz, tel FROM kaf_records";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                int id = rs.getInt("id");
                String tel = rs.getString("tel");
                String naz = rs.getString("naz");
                templist.add(new KafRecord(id,naz,tel));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return templist;
    }

    public static void addToRecord() {
        Scanner input = new Scanner(System.in);
        System.out.println("Введите название кафедры");
        String str1 = input.next();
        System.out.println("Введите номер телефона кафедры");
        System.out.println("Телефон должен содержать 11 цифр без пробелов");
        String str2 = input.next();
        while (str2.length() != 11) {
            System.out.println("Телефон должен содержать 11 цифр без пробелов");
            str2 = input.next();
        }

        try (Connection conn = DriverManager.getConnection(url,usr,pwd)) {
            Statement statement = conn.createStatement();
            String sql = String.format("INSERT INTO kaf_records(naz, tel) VALUES('%s', '%s')",str1,str2);
            statement.executeUpdate(sql);
            KafRecordFormatter.getRecords();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void updateRecord(int id) {
        Scanner input = new Scanner(System.in);
        System.out.println("Введите название кафедры");
        String str1 = input.next();
        System.out.println("Введите номер телефона кафедры");
        System.out.println("Телефон должен содержать 11 цифр без пробелов");
        String str2 = input.next();
        while (str2.length() != 11) {
            System.out.println("Телефон должен содержать 11 цифр без пробелов");
            str2 = input.next();
        }

        try (Connection conn = DriverManager.getConnection(url,usr,pwd)) {
            Statement statement = conn.createStatement();
            String sql = String.format("UPDATE kaf_records SET naz = '%s', tel = '%s' WHERE id = %d",str1,str2,id);
            statement.executeUpdate(sql);
            KafRecordFormatter.getRecords();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void deleteRecord(int id) {
        try (Connection conn = DriverManager.getConnection(url,usr,pwd)) {
            Statement statement = conn.createStatement();
            System.out.println(id);
            String sql = String.format("DELETE from kaf_records WHERE id = %d",id);
            statement.executeUpdate(sql);
            KafRecordFormatter.getRecords();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}