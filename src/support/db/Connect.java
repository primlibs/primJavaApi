/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import support.StringAdapter;
import support.db.executor.ExecutorFabric;
import support.db.executor.QueryExecutor;
import support.settings.SqlSettings;

/**
 *
 * @author kot
 */
public class Connect {    
    
    private Connect(){
        
    }
    
    public static Connection createConnection(SqlSettings sqlSettings) throws Exception {
        Class.forName(sqlSettings.getDbDriverName());
        String url = sqlSettings.getUri() +"/" + sqlSettings.getDbName()+"?autoReconnect=true";
        Properties properties = new Properties();
        properties.setProperty("user", sqlSettings.getUser());
        properties.setProperty("password", sqlSettings.getPassword());
        properties.setProperty("useUnicode", "true");
        properties.setProperty("characterEncoding",sqlSettings.getDbEncoding());
        return DriverManager.getConnection(url, properties);
    }
    
    
    
    
}
