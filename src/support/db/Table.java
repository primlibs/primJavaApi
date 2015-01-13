/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import support.StringAdapter;

/**
 * @author kot
 */
public class Table {

    final public String name;
    private List<Column> columns = new ArrayList();

    private Table(String name) throws Exception {
        if (StringAdapter.isNull(name)) {
            throw new Exception("ColumnName coudn't be null");
        }
        this.name = name;
    }

    public static Table getInstance(String name) throws Exception {
        return new Table(name);
    }

    public void addColumn(Column column) throws Exception {
        if (column == null) {
            throw new Exception("Column coudn't be null");
        }
        for (Column col : columns) {
            if (col.name.equals(column.name)) {
                throw new Exception("Tho colums has equal name");
            }
        }

        if (column.isPrimary) {
            for (Column col : columns) {
                if (col.isPrimary) {
                    throw new Exception("Find primary key in column" + col.name + " and new column " + column.name);
                }
            }
        }
        columns.add(column);
    }

    public void addColumn(Collection<Column> columns) throws Exception {
        for (Column cl : columns) {
            addColumn(cl);
        }
    }

    public void addColumn(Column... columns) throws Exception {
        List<Column> clList = Arrays.asList(columns);
        addColumn(clList);
    }

    public List<Column> getColumns() throws Exception {
        List<Column> result = new ArrayList();
        for (Column cl : columns) {
            result.add(Column.getInstance(cl.name, cl.type, cl.isNull, cl.isPrimary));
        }
        return result;
    }

}
