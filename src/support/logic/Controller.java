/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support.logic;

import java.util.Map;
import support.db.Dao;

/**
 *
 * @author Кот
 */
public interface Controller {
        
    public void setRequest(Map<String,Object> request); 
    
    public Map<String,Object> getResponce(); 

    public Map<String,Object> getSession(); 

    public String getRedirect(); 

    public void setDao(Dao dao);

    public String getResult();
    
}
