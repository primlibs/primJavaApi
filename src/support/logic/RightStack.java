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
    private List<Right> rights= new ArrayList();
    
    private RightStack(){
        
    }
    
    public static RightStack getInstance(){
        return new RightStack();
    }
    
    public void add(String object,String action) throws Exception{
            rights.add(Right.valueOf(object, action));
    }
    
    public void add(String object,String action,String objectDescription,String actionDescription) throws Exception{
            rights.add(Right.valueOf(object, action,objectDescription,actionDescription));
    }
    
    public boolean isRight(String object,String action){
        boolean result =false;
        for(Right right:rights){
            if(right.getObject().equals(object)&&right.getAction().equals(action)){
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
