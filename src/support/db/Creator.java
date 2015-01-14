/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support.db;

import support.enums.ColumnTypes;
import support.enums.DbTypes;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import support.JarScan;
import support.StringAdapter;
import support.db.executor.ExecutorFabric;
import support.db.executor.QueryExecutor;
import support.db.executor.Row;

/**
 *
 * @author kot
 */
public class Creator {

    private Creator() {

    }

    public static boolean createTables(Connection con, DbTypes type, List<Table> table) throws Exception {
        for (Table tb : table) {
            String query = getSaveTableQuery(type, tb);
            QueryExecutor qe = ExecutorFabric.getExecutor(con, query, type);
            if (qe.update()) {

            } else {
                throw new Exception(StringAdapter.getStringFromList(qe.getError()));
            }
        }
        return true;
    }

    public static boolean dropTables(Connection con, DbTypes type, List<Table> table) throws Exception {
        for (Table tb : table) {
            String query = getDropTableQuery(type, tb);
            QueryExecutor qe = ExecutorFabric.getExecutor(con, query, type);
            if (qe.update()) {

            } else {
                throw new Exception(StringAdapter.getStringFromList(qe.getError()));
            }
        }
        return true;
    }

    public static boolean dropColumnInTables(Connection con, DbTypes type, List<Table> table) throws Exception {
        for (Table tb : table) {
            String query = getDropColumnInTableQuery(type, tb);
            QueryExecutor qe = ExecutorFabric.getExecutor(con, query, type);
            if (qe.update()) {

            } else {
                throw new Exception(StringAdapter.getStringFromList(qe.getError()));
            }
        }
        return true;
    }
    
    
    public static boolean addColumnInTables(Connection con, DbTypes type, List<Table> table) throws Exception {
        for (Table tb : table) {
            String query = getAddColumnInTableQuery(type, tb);
            QueryExecutor qe = ExecutorFabric.getExecutor(con, query, type);
            if (qe.update()) {

            } else {
                throw new Exception(StringAdapter.getStringFromList(qe.getError())+query);
            }
        }
        return true;
    }
    
    
    
    private static String getAddColumnInTableQuery(DbTypes type, Table table) throws Exception {
        if (type.equals(DbTypes.MySQL)) {
            return getAddColumnInTableQueryMysql(table);
        } else {
            throw new Exception("DbType equals " + type + " not supported yet");
        }
    }
    

    private static String getDropColumnInTableQuery(DbTypes type, Table table) throws Exception {
        if (type.equals(DbTypes.MySQL)) {
            return getDropColumnInTableQueryMysql(table);
        } else {
            throw new Exception("DbType equals " + type + " not supported yet");
        }
    }

    private static String getDropTableQueryMysql(Table table) throws Exception {
        String query = " drop table " + table.name + ";";
        return query;
    }

    private static String getSaveTableQuery(DbTypes type, Table table) throws Exception {
        if (type.equals(DbTypes.MySQL)) {
            return getSaveTableQueryMysql(table);
        } else {
            throw new Exception("DbType equals " + type + " not supported yet");
        }
    }

    private static String getDropTableQuery(DbTypes type, Table table) throws Exception {
        if (type.equals(DbTypes.MySQL)) {
            return getDropTableQueryMysql(table);
        } else {
            throw new Exception("DbType equals " + type + " not supported yet");
        }
    }

    private static String getAddColumnInTableQueryMysql(Table table) throws Exception {
        String query = " alter table " + table.name + " ";
        int cnt = 0;
        for (Column col : table.getColumns()) {
            if (cnt > 0) {
                query += ",";
            }
            query += " ADD COLUMN " + col.name + " " + getMysqlColumnType(col.type) + " ";
            if (col.isPrimary == true) {
                query += " PRIMARY KEY AUTO_INCREMENT ";
            } else {
                if (col.isNull != true) {
                    query += " NOT NULL ";
                }
            }
            cnt++;
        }
        query += ";";
        return query;
    }

    private static String getDropColumnInTableQueryMysql(Table table) throws Exception {
        String query = " alter table " + table.name + " drop ";
        int cnt = 0;
        for (Column col : table.getColumns()) {
            if (cnt > 0) {
                query += ",";
            }
            query += " " + col.name + " ";
            cnt++;
        }
        query += ";";
        return query;
    }

    private static String getSaveTableQueryMysql(Table table) throws Exception {
        String query = " Create table if not exists " + table.name + "( ";
        int cnt = 0;
        for (Column clmn : table.getColumns()) {
            if (cnt > 0) {
                query += " , ";
            }
            query += " " + clmn.name + " ";
            query += " " + getMysqlColumnType(clmn.type) + " ";
            if (clmn.isNull) {
                query += " NULL ";
            } else {
                query += " NOT NULL ";
            }
            if (clmn.isPrimary) {
                query += " PRIMARY KEY AUTO_INCREMENT ";
            }
            cnt++;
        }
        query += ") ENGINE=INNODB CHARSET=UTF8; ";
        return query;
    }

    private static String getMysqlColumnType(ColumnTypes columnType) throws Exception {
        String res = "";
        if (columnType.equals(ColumnTypes.INTEGER)) {
            res = "INTEGER(11)";
        } else if (columnType.equals(ColumnTypes.DECIMAL)) {
            res = "DECIMAL(20)";
        } else if (columnType.equals(ColumnTypes.DATETIME)) {
            res = "DATETIME";
        } else if (columnType.equals(ColumnTypes.TEXT)) {
            res = "TEXT";
        } else if (columnType.equals(ColumnTypes.VARCHAR)) {
            res = "VARCHAR (255)";
        } else {
            throw new Exception("columnType equals " + columnType + " not supported yet");
        }
        return res;
    }

    public static List<Table> getTablesFromSql(Connection con) throws Exception {
        List<Table> result = new ArrayList();
        QueryExecutor qe = ExecutorFabric.getExecutor(con, "show tables", DbTypes.MySQL);
        qe.select();
        for (Row table : qe.getResultList()) {
            Table resTb = Table.getInstance(StringAdapter.getString(table.getFirst()));
            QueryExecutor qe1 = ExecutorFabric.getExecutor(con, "describe " + table.getFirst(), DbTypes.MySQL);
            qe1.select();
            for (Row column : qe1.getResultList()) {
                String name = StringAdapter.getString(column.get("Field"));
                ColumnTypes type = findColumnType(column.get("Type"));
                Boolean isPrimary = isPrimary(column.get("Key"));
                Boolean isNull = isNull(column.get("Null"));
                resTb.addColumn(Column.getInstance(name, type, isNull, isPrimary));
            }
            result.add(resTb);
        }
        return result;
    }

    public static boolean isNull(Object type) {
        String row = StringAdapter.getString(type);
        if (row.equals("NO")) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isPrimary(Object type) {
        String row = StringAdapter.getString(type);
        if (row.equals("PRI")) {
            return true;
        } else {
            return false;
        }
    }

    public static ColumnTypes findColumnType(Object type) throws Exception {
        String row = StringAdapter.getString(type);
        row = row.trim();
        String[] arrayMessage = row.split("\\s+");
        String typeres = "";
        if (arrayMessage.length < 1) {
            throw new Exception("no one word in row");
        } else {
            typeres = arrayMessage[0];
        }
        if (typeres.contains("int")) {
            return ColumnTypes.INTEGER;
        } else if (typeres.contains("text")) {
            return ColumnTypes.TEXT;
        } else if (typeres.contains("date")) {
            return ColumnTypes.DATETIME;
        } else if (typeres.contains("decimal")) {
            return ColumnTypes.DECIMAL;
        } else {
            return ColumnTypes.VARCHAR;
        }
    }

}
