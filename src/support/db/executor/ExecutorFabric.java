/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.db.executor;

import java.sql.Connection;
import support.enums.DbTypes;

/**
 *
 * @author кот
 */
public class ExecutorFabric {
  private ExecutorFabric(){
    
  }
  
  public static QueryExecutor getExecutor(Connection con, String queryText,DbTypes type) throws Exception{
      if(type.equals(DbTypes.MySQL)){
          return MysqlExecutor.getInstance(con,queryText);
      }else{
          throw new Exception ("Executor type "+type+" not supported yet");
      }
  }  
  
}
