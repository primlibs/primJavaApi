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
        for (TableHandler th : tableToCompare) {
            compareTwoTable(th.systable, th.sqlTable);
        }
    }

    private void compareTwoTable(Table systable, Table sqlTable) throws Exception {
        List<Column> sysCol = systable.getColumns();
        List<Column> sqlCol = sqlTable.getColumns();
        Table createColumn = Table.getInstance(systable.name);
        for (Column system : sysCol) {
            boolean existInSql = false;
            for (Column sql : sqlCol) {
                if (system.name.equals(sql.name)) {
                    existInSql = true;
                }
            }
            if (existInSql == false) {
                createColumn.addColumn(Column.getInstance(system.name, system.type, system.isNull, system.isPrimary));
            }
        }
        if (!createColumn.getColumns().isEmpty()) {
            addColumn.add(createColumn);
        }

        Table delColumn = Table.getInstance(sqlTable.name);
        for (Column sql : sqlCol) {
            boolean existInSystem = false;
            for (Column system : sysCol) {
                if (sql.name.equals(system.name)) {
                    existInSystem = true;
                }
            }
            if (existInSystem == false) {
                delColumn.addColumn(Column.getInstance(sql.name, sql.type, sql.isNull, sql.isPrimary));
            }
        }
        if (!delColumn.getColumns().isEmpty()) {
            dropColumn.add(delColumn);
        }
    }

    private class TableHandler {

        Table systable;
        Table sqlTable;

    }

}
