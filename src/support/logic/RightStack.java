/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support.logic;

import java.util.ArrayList;
import java.util.List;
import support.StringAdapter;

/**
 *
 * @author Кот
 */
public class RightStack {
    private String objectPrefix="";
    private List<Right> rights= new ArrayList();
    
    public RightStack(String prefix){
        if(prefix!=null){
            this.objectPrefix=prefix;
        }
    }
    
    public static RightStack getInstance(){
        return new RightStack(null);
    }
    
    public static RightStack getInstance(String prefix){
        return new RightStack(prefix);
    }
    
    public Right add(String object,String action,String objectDescription,String actionDescription) throws Exception{
            Right rt=Right.valueOf(object, action,objectDescription,actionDescription);
            if(!isRight(object, action)){
                rights.add(rt);
            }
            return rt;
    }
    
    public boolean isRight(String object,String action){
        boolean result =false;
        for(Right right:rights){
            if((right.getObject().equals(object)&&right.getAction().equals(action))
                    ||(right.getObject().equals(objectPrefix+""+object)&&right.getAction().equals(action))){
                result=true;
                break;
            }
        }
        return result;
    }
    
    public List<Right> getRights() throws Exception{
        List<Right> result= new ArrayList();
        for(Right right:rights){
            result.add(Right.valueOf(right.getObject(), right.getAction(),right.getObjectDescription(),right.getActionDescription()));
        }
        return result;
    }
    
}
