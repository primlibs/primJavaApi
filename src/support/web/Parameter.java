/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * параметр в ссылке
 * @author Pavel Rice
 */
public class Parameter {
  
   private String name;
  
  private Object value;
  
  public Parameter(String name, Object value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public Object getValue() {
    return value;
  }  
  
  public static List<Parameter> getArray(Map<String, Object> params){
    List<Parameter> result= new ArrayList();
    for(String name:params.keySet()){
      result.add(new Parameter(name, params.get(name)));
    }
    return result;
  }
  
}
