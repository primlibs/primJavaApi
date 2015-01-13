/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support.db;

import support.enums.ColumnTypes;
import sun.security.jca.GetInstance;
import support.StringAdapter;

/**
 *
 * @author kot
 */
public class Column {
    
    final public String name;
    final public ColumnTypes type;
    final public boolean isNull;
    final public boolean isPrimary;
    
    private Column(String name, ColumnTypes type,boolean isNull,boolean isPrimary) throws Exception{
        if(StringAdapter.isNull(name)){
            throw new Exception("ColumnName coudn't be null");
        }
        this.name=name;
        this.type=type;
        this.isNull=isNull;
        this.isPrimary=isPrimary;
    }
    
    public static Column getInstance(String name, ColumnTypes type,boolean isNull,boolean isPrimary) throws Exception{
        return new Column(name, type, isNull, isPrimary);
    }
    
}
