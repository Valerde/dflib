package com.nhl.dflib.jdbc.unit.dbadapter;

import io.bootique.jdbc.junit5.DbTester;

public class MySQLTestAdapter implements TestDbAdapter {

    private static final String QUOTE = "`";

    private DbTester db;

    public MySQLTestAdapter(DbTester db) {
        this.db = db;
    }

    @Override
    public DbTester getDb() {
        return db;
    }

    @Override
    public String toNativeSql(String command) {
        if (command.contains("SUBSTR")) {
            return command
                    .replaceAll("\"", QUOTE)
                    .replaceAll("SUBSTR", "SUBSTRING");
        }
        return command.replaceAll("\"", QUOTE);
    }
}
