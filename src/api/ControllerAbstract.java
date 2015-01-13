/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.util.HashMap;
import java.util.Map;
import support.StringAdapter;
import support.db.Dao;
import support.logic.Controller;

/**
 *
 * @author Кот
 */
public class ControllerAbstract implements Controller{
    private Dao dao;
    private String redirect;
    private boolean isRedirect=false;
    String result="";
    private Map<String, Object> request= new HashMap();
    private Map<String, Object> session= new HashMap();
    private Map<String, Object> response= new HashMap();
    
    
    @Override
    final public void setRequest(Map<String, Object> request) {
        this.request=StringAdapter.cloneHashMap(request);
    }

    @Override
    final public Map<String, Object> getResponce() {
        return StringAdapter.cloneHashMap(response);
    }

    @Override
    final public Map<String, Object> getSession() {
        return StringAdapter.cloneHashMap(session);
    }

    @Override
    final public String  getRedirect() {
       return StringAdapter.getString(redirect);
    }

    @Override
    final public void setDao(Dao dao) {
        this.dao=dao;
    }

    @Override
    final public String getResult() {
        return  StringAdapter.getString(result);
    }

    final public Map<String, Object> getRequest() {
         return StringAdapter.cloneHashMap(request);
    }
       


    final public void setResponce(Map<String, Object> responce) {
        this.response=StringAdapter.cloneHashMap(responce);
    }


    final public void setSession(Map<String, Object> session) {
        this.session=StringAdapter.cloneHashMap(session);
    }


    final public void setRedirect(String redirect) {
        this.redirect=StringAdapter.getString(redirect);
    }


    final public Dao getDao() {
       return this.dao;
    }


    final public void setResult(String result) {
        this.result=StringAdapter.getString(result);
    }
    
    
    final public void addSession(String name,Object value){
        if(StringAdapter.NotNull(name)){
            session.put(name, value);
        }
    }
    
    final public void addResponce(String name,Object value){
        if(StringAdapter.NotNull(name)){
            response.put(name, value);
        }
    }
    
    
    
}
