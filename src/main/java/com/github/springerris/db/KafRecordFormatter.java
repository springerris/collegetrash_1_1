package com.github.springerris.db;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KafRecordFormatter {

    private final List<KafRecord> list = new ArrayList<>();

    // Replacement for getRecords
    public void populate(@NotNull DB db) throws SQLException {
        this.list.clear();
        this.list.addAll(db.getRecords());
    }

    // Replacement for showRecords
    public @NotNull String format() {
        final StringBuilder sb = new StringBuilder();
        sb.append("╔════╦══════════════════════════════╦════════════════╗");
        sb.append("| ID | Название кафедры             | Номер телефона |");
        sb.append("╚════╩══════════════════════════════╩════════════════╝");

        String tmp;
        for (KafRecord record : this.list) {
            tmp = String.valueOf(record.id());
            sb.append("| ").append(tmp);
            this.writeEmpty(3 - tmp.length(), sb);

            sb.append("| ").append(record.naz());
            this.writeEmpty(29 - record.naz().length(), sb);

            sb.append("| ").append(record.tel());
            this.writeEmpty(15 - record.tel().length(), sb);

            sb.append("|\n╚════╩══════════════════════════════╩════════════════╝\n");
        }

        return sb.toString();
    }

    private void writeEmpty(int count, @NotNull StringBuilder sb) {
        final int len = sb.length();
        final int newLen = len + count;
        sb.setLength(newLen);
        for (int i=len; i < newLen; i++) sb.setCharAt(i, ' ');
    }

}
