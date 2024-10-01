package com.github.springerris.db;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DB {

    protected final String url;
    protected final String usr;
    protected final String pwd;
    protected transient Connection connection = null;

    public DB(@NotNull String url, @NotNull String username, @NotNull String passphrase) {
        this.url = url;
        this.usr = username;
        this.pwd = passphrase;
    }

    // Lazily provides a Connection; these are meant to be reused!
    protected @NotNull Connection getConnection() throws SQLException {
        if (this.connection != null) {
            if (!this.connection.isClosed()) return this.connection;
        }
        return this.connection = DriverManager.getConnection(this.url, this.usr, this.pwd);
    }

    protected @NotNull Statement createStatement() throws SQLException {
        return this.getConnection().createStatement();
    }

    public synchronized void close() throws SQLException {
        if (this.connection != null && !this.connection.isClosed())
            this.connection.close();
    }

    // Note 1
    // ArrayList is not vital to the contract of the method.
    // Callers will only use methods defined on ArrayList that are also defined on List,
    // an interface. Updating the contract to return List allows for more flexibility.

    // Note 2
    // Catching SQLException silently is generally a bad idea. Let it bubble up and halt execution,
    // otherwise we are serving bad data. Imagine we receive an incomplete list, update it, and
    // write that list to the database. That's data loss!

    public @NotNull List<KafRecord> getRecords() throws SQLException {
        List<KafRecord> ret = new ArrayList<>();
        try (Statement statement = this.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT id, naz, tel FROM kaf_records");

            while(rs.next()) {
                int id = rs.getInt("id");
                String tel = rs.getString("tel");
                String naz = rs.getString("naz");
                ret.add(new KafRecord(id,naz,tel));
            }
        }
        return ret;
    }

    // Note 3
    // Handling user interface is out of scope for this class. We should let other classes
    // handle that, and pass exclusively sanitized output to DB.

    public void addRecord(@NotNull String naz, @NotNull String tel) throws SQLException {
        try (Statement statement = this.createStatement()) {
            statement.executeUpdate(
                    "INSERT INTO kaf_records(naz, tel) VALUES('" +
                            naz + "', '" + tel + "')"
            );
        }
    }

    public void addRecord(@NotNull KafRecord record) throws SQLException {
        this.addRecord(record.naz(), record.tel());
    }

    public void updateRecord(int id, @NotNull String naz, @NotNull String tel) throws SQLException {
        try (Statement statement = this.createStatement()) {
            statement.executeUpdate(
                    "UPDATE kaf_records SET naz = '" +
                    naz + "', tel = '" + tel + "' WHERE id = " + id
            );
        }
    }

    public void updateRecord(@NotNull KafRecord record) throws SQLException {
        this.updateRecord(record.id(), record.naz(), record.tel());
    }

    public void deleteRecord(int id) throws SQLException {
        try (Statement statement = this.createStatement()) {
            statement.executeUpdate("DELETE from kaf_records WHERE id = " + id);
        }
    }

    public void deleteRecord(@NotNull KafRecord record) throws SQLException {
        this.deleteRecord(record.id());
    }

}