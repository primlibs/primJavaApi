/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support.logic;

import support.StringAdapter;

/**
 *
 * @author Кот
 */
public class Right {
    final private  String object;
    final private String action;
    
    private Right(String object,String action) throws Exception{
        if(!StringAdapter.NotNull(object,action)){
            throw new Exception("Try to add empty object or action");
        }
        this.object=object;
        this.action=action;
    }
    
    public static Right valueOf(String object,String action) throws Exception{
        return new Right(object, action);
    }
    
    public String getObject(){
        return object;
    }
    
    public String getAction(){
        return action;
    }
    
}
