/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support.db;

import support.enums.ColumnTypes;
import support.enums.DbTypes;
import java.sql.Connection;
import java.util.List;
import support.JarScan;
import support.StringAdapter;
import support.db.executor.ExecutorFabric;
import support.db.executor.QueryExecutor;

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
    
    
    
    
    
}
