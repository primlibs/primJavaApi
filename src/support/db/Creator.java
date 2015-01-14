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
    private Creator(){
        
    }
    
    public static void saveNewtables(Connection con,Class startclass) throws Exception{
        saveTables(con,DbTypes.MySQL, Persistence.getInstance().createTableFromJar(startclass));
    }
    
    static boolean saveTables(Connection con,DbTypes type, List<Table>  table) throws Exception{
        for(Table tb:table){
            String query= getSaveTableQuery(type, tb);
            QueryExecutor qe=ExecutorFabric.getExecutor(con, query, type);
            if(qe.update()){
                
            }else{
                throw new Exception(StringAdapter.getStringFromList(qe.getError()));
            }
        }
        return true;
    }
    
    
    private static String getSaveTableQuery(DbTypes type,Table table) throws Exception{
        if(type.equals(DbTypes.MySQL)){
            return getSaveTableQueryMysql(table);
        }else{
            throw new Exception("DbType equals "+type+" not supported yet");
        }
    }
    
    private static String getSaveTableQueryMysql(Table table) throws Exception{
        String query= " Create table if not exists "+table.name+"( ";
        int cnt=0;
        for(Column clmn:table.getColumns()){
            if(cnt>0){
                query+=" , ";
            }
            query+=" "+clmn.name+" ";
            query+=" "+getMysqlColumnType(clmn.type)+" ";
            if(clmn.isNull){
                query+=" NULL ";
            }else{
                query+=" NOT NULL ";
            }
            if(clmn.isPrimary){
                query+=" PRIMARY KEY AUTO_INCREMENT ";
            }
            cnt++;
        }        
        query+=") ENGINE=INNODB CHARSET=UTF8; ";
        return query;
    }
    
        private static String getMysqlColumnType(ColumnTypes columnType) throws Exception{
        String res="";
        if(columnType.equals(ColumnTypes.INTEGER)){
            res="INTEGER(11)";
        }else if(columnType.equals(ColumnTypes.DECIMAL)){
            res="DECIMAL(20)";
        }else if(columnType.equals(ColumnTypes.DATETIME)){
            res="DATETIME";
        }else if(columnType.equals(ColumnTypes.TEXT)){
            res="TEXT";
        }else if(columnType.equals(ColumnTypes.VARCHAR)){
            res="VARCHAR (255)";
        }else{
            throw new Exception("columnType equals "+columnType+" not supported yet");
        }
        return res;
    }
    
    public static List<Table> getTablesFromSql(Connection con) throws Exception{
        List<Table> result=new ArrayList();
        
        QueryExecutor qe=ExecutorFabric.getExecutor(con, "show tables", DbTypes.MySQL);
        qe.select();
        for (Row table:qe.getResultList()){
            Table resTb = Table.getInstance(StringAdapter.getString(table.getFirst()));
            QueryExecutor qe1=ExecutorFabric.getExecutor(con, "describe "+resTb, DbTypes.MySQL);
            qe1.select();
            for (Row column:qe1.getResultList()){
                String name=StringAdapter.getString(column.get("Field"));
                ColumnTypes type=findColumnType(column.get("Type"));
                Boolean isPrimary=isPrimary(column.get("Key"));
                Boolean isNull=isNull(column.get("Null"));
                resTb.addColumn(Column.getInstance(name, type, true, true));
            }
            result.add(resTb);
        }
        return result;
    }
    
     public static boolean isNull(Object type){
         String row=StringAdapter.getString(type);
         if(row.equals("NO")){
             return false;
         }else{
             return true;
         }
     }
    
     public static boolean isPrimary(Object type){
         String row=StringAdapter.getString(type);
         if(row.equals("PRI")){
             return true;
         }else{
             return false;
         }
     }
    
    public static ColumnTypes findColumnType(Object type) throws Exception{
        String row=StringAdapter.getString(type);
        row=row.trim();
        String[] arrayMessage=row.split("\\s+");
        String typeres="";
        if(arrayMessage.length<1){
            throw new Exception("no one word in row");
        }
        if(typeres.equals("int")){
            return ColumnTypes.INTEGER;
        }else if(typeres.equals("text")){
            return ColumnTypes.TEXT;
        }else if(typeres.equals("datetime")||typeres.equals("date")){
            return ColumnTypes.DATETIME;
        }else if(typeres.equals("decimal")){
            return ColumnTypes.DECIMAL;
        }else{
            return ColumnTypes.VARCHAR;
        }
    }
    
}
