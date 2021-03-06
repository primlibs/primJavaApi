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
    final private String objectDescription;
    final private String actionDescription;
    
    private Right(String object,String action,String objectDescription,String actionDescription) throws Exception{
        if(!StringAdapter.NotNull(object,action)){
            throw new Exception("Try to add empty object or action");
        }
        this.object=object;
        this.action=action;
        this.objectDescription=StringAdapter.getString(objectDescription);
        this.actionDescription=StringAdapter.getString(actionDescription);
    }
    
    public static Right valueOf(String object,String action) throws Exception{
        return new Right(object, action,"","");
    }
    
    public static Right valueOf(String object,String action,String objectDescription,String actionDescription) throws Exception{
        return new Right(object, action,objectDescription,actionDescription);
    }
    
    public String getObject(){
        return object;
    }
    
    public String getAction(){
        return action;
    }

    public String getObjectDescription() {
        return objectDescription;
    }

    public String getActionDescription() {
        return actionDescription;
    }

}
