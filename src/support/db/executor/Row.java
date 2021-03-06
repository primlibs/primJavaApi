/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support.db.executor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Кот
 */
public class Row {

    final private Map<String, Object> params = new HashMap();

    private Row(Map<String, Object> params) {
        if (params != null) {
            this.params.putAll(params);
        }
    }

    public static Row getInstance(Map<String, Object> params) {
        return new Row(params);
    }

    public Object get(String name) {
        return params.get(name);
    }

    public Object getFirst() throws Exception {
        if (params.isEmpty()) {
            throw new Exception("No one params in result");
        } else {
            for (String name : params.keySet()) {
                return params.get(name);
            }
            return -1;
        }
    }

    public List<String> getNames() {
        List<String> names = new ArrayList();
        for (String name : params.keySet()) {
            names.add(name);
        }
        return names;
    }

    public List<Object> getValues(List<String> names) {
        List<Object> values = new ArrayList();
        for (String name : names) {
            if(params.get(name)!=null){
                values.add(params.get(name));
            }
        }
        return values;
    }

}
