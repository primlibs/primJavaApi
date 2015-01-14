/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support.db;

import java.util.ArrayList;
import java.util.List;
import support.StringAdapter;
import support.enums.ColumnTypes;

/**
 *
 * @author Кот
 */
public class TableComparator {

    private List<Table> systemTable = new ArrayList();
    private List<Table> sqlTable = new ArrayList();
    private List<TableHandler> tableToCompare = new ArrayList();

    private List<Table> create = new ArrayList();
    private List<Table> delete = new ArrayList();

    private List<Table> addColumn = new ArrayList();
    private List<Table> dropColumn = new ArrayList();

    private TableComparator(List<Table> systemTable, List<Table> sqlTable) {
        if (systemTable != null) {
            this.systemTable = systemTable;
        }
        if (sqlTable != null) {
            this.sqlTable = sqlTable;
        }
    }

    public static TableComparator getInstance(List<Table> systemTable, List<Table> sqlTable) {
        return new TableComparator(systemTable, sqlTable);
    }

    public void compare() throws Exception {
        compareTable();
        clearTableHandler();
        compareColumn();
    }

    public List<Table> getTableToCreate() {
        List<Table> result = new ArrayList();
        for (Table table : create) {
            result.add(table);
        }
        return result;
    }

    public List<Table> getTableToDrop() {
        List<Table> result = new ArrayList();
        for (Table table : delete) {
            result.add(table);
        }
        return result;
    }

    public List<Table> getTableAddColumn() {
        List<Table> result = new ArrayList();
        for (Table table : addColumn) {
            result.add(table);
        }
        return result;
    }

    public List<Table> getTableToDropColumn() {
        List<Table> result = new ArrayList();
        for (Table table : dropColumn) {
            result.add(table);
        }
        return result;
    }

    private void compareTable() throws Exception {
        for (Table systable : systemTable) {
            boolean existInSql = false;
            for (Table sqlTabl : sqlTable) {
                if (systable.name.equals(sqlTabl.name)) {
                    existInSql = true;
                    TableHandler th = new TableHandler();
                    th.systable = systable;
                    tableToCompare.add(th);
                }
            }
            if (existInSql == false) {
                create.add(systable);
            }
        }

        for (Table sqlTabl : sqlTable) {
            boolean onlyInSql = true;
            for (Table systable : systemTable) {
                if (sqlTabl.name.equals(systable.name)) {
                    onlyInSql = false;
                    for (TableHandler th : tableToCompare) {
                        if (th.systable.name.equals(sqlTabl.name)) {
                            th.sqlTable = sqlTabl;
                        }
                    }
                }
            }
            if (onlyInSql == true) {
                delete.add(sqlTabl);
            }
        }
    }

    private void clearTableHandler() {
        List<TableHandler> newHandler = new ArrayList();
        for (TableHandler th : tableToCompare) {
            if (th.systable != null && th.sqlTable != null) {
                newHandler.add(th);
            }
        }
        tableToCompare = newHandler;
    }

    private void compareColumn() throws Exception {
        String log="";
        for (TableHandler th : tableToCompare) {
            log+=compareTwoTable(th.systable, th.sqlTable);
        }
        throw new Exception(log);
    }

    private String compareTwoTable(Table systable, Table sqlTable) throws Exception {
        String log="";
        List<Column> sysCol = systable.getColumns();
        List<Column> sqlCol = sqlTable.getColumns();
        Table createColumn = Table.getInstance(systable.name);
        boolean existInSql = false;
        log+=1;
        for (Column system : sysCol) {
            log+=2;
            for (Column sql : sqlCol) {
                if (system.name.equals(sql.name)) {
                    existInSql = true;
                    log+=3+system.name;
                }
            }
            if (existInSql == false) {
                log+=4+system.name;
                createColumn.addColumn(Column.getInstance(system.name, system.type, system.isNull, system.isPrimary));
            }
        }
        if (!createColumn.getColumns().isEmpty()) {
            log+=5+createColumn.name;
            addColumn.add(createColumn);
        }

        Table delColumn = Table.getInstance(sqlTable.name);
        boolean existInSystem = false;
        log+=6;
        for (Column sql : sqlCol) {
            log+=7;
            for (Column system : sysCol) {
                if (sql.name.equals(system.name)) {
                    log+=8+sql.name;
                    existInSystem = true;
                }
            }
            if (existInSystem == false) {
                log+=9+sql.name;
                delColumn.addColumn(Column.getInstance(sql.name, sql.type, sql.isNull, sql.isPrimary));
            }
        }
        if (!delColumn.getColumns().isEmpty()) {
            log+=5+delColumn.name;
            dropColumn.add(delColumn);
        }
        log+="12";
        return log;
    }

    private class TableHandler {

        Table systable;
        Table sqlTable;

    }

}
