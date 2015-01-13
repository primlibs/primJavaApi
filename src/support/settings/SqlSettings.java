/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support.settings;

/**
 *
 * @author Кот
 */
public interface SqlSettings {
    
    public String getUri();
    public String getUser();
    public String getPassword();
    public String getDbName();
    public String getDbEncoding();
    public String getDbDriverName();
    
}
