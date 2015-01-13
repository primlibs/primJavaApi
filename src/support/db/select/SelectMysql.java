/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.db.select;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import support.StringAdapter;
import support.db.Column;
import support.db.Table;
import support.db.executor.ExecutorFabric;
import support.db.executor.QueryExecutor;
import support.db.executor.Row;
import support.enums.DbTypes;

/**
 * объект SQL-запроса
 *
 * @author Кот
 */
public class SelectMysql  {

  private String resultSelect = "";
  List<Row> resultList=new ArrayList();
  private List<String> errors = new ArrayList();
  private SelectMysql(){
  }
  public static SelectMysql getInstance(){
      return new SelectMysql();
  }
  
  public List<String> getError(){
      List<String> result= new ArrayList();
      result.addAll(errors);
      return result;
  }
  public List<Row> getResultList(){
      List<Row> result= new ArrayList();
      result.addAll(resultList);
      return result;
  } 
  
  
  public boolean findAll(Connection connection,Table table){
      boolean result=false;
      try{
        resultSelect=findAllMysqlQuery(table);
        QueryExecutor queryExecutor=ExecutorFabric.getExecutor(connection, resultSelect, DbTypes.MySQL);
        queryExecutor.select();
        resultList=queryExecutor.getResultList();
        if(queryExecutor.getError().isEmpty()){
            result= true;
        }else{
            errors.addAll(queryExecutor.getError());
            result= false;
        }
      }catch(Exception e){
           result= false;
          errors.add(StringAdapter.getStackTraceException(e));
      }
      return result;
  }
    
  private String findAllMysqlQuery(Table table) throws Exception{
      String result="";
      result+="Select ";
      int cnt=0;
      for(Column column:table.getColumns()){
          if(cnt>0){
              result+=" , ";
          }
          result+=" "+ table.name+"."+column.name+" "+column.name+" ";
          cnt++;
      }
      result+=" from "+table.name;
      return result;
  }  
}
