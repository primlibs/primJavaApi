/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support.db;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import support.JarScan;
import support.StringAdapter;
import support.logic.Right;
import support.logic.RightStack;

/**
 *
 * @author Кот
 */
public class Persistence {



    private Persistence() {

    }

    public static Persistence getInstance() {
        return new Persistence();
    }

    public List<Table> createTableFromJar(Class startClass) throws Exception {
        List<Table> result= new ArrayList();
        Collection string = JarScan.scanClasses(JarScan.getFilePathToClasses(startClass));

        for (Object cls : string) {
            Class cl = Class.forName(cls.toString());
            
            if (cl.isAnnotationPresent(support.commons.db.Table.class)) {
                Object obj = cl.newInstance(); 
                result.add(createTableFromAnnotationObject(obj));
            }
        }
        return result;
    }
    
    public Table createTableFromAnnotationObject(Object ob) throws Exception{
        Table result=null;
        if(ob.getClass().isAnnotationPresent(support.commons.db.Table.class)){
            support.commons.db.Table tabl = (support.commons.db.Table) ob.getClass().getAnnotation(support.commons.db.Table.class);
                result=Table.getInstance(tabl.name());
                Field[] fds = ob.getClass().getDeclaredFields();
                for (Field fd : fds) {
                    if (fd.isAnnotationPresent(support.commons.db.Column.class)) {
                        Column column;
                        support.commons.db.Column col = fd.getAnnotation(support.commons.db.Column.class);
                        if(fd.isAnnotationPresent(support.commons.db.Primary.class)){
                            column=Column.getInstance(col.name(), col.type(), col.isNull(), true);
                        }else{
                            column=Column.getInstance(col.name(), col.type(), col.isNull(), false);
                        }
                        result.addColumn(column);
                    }
                }
        }else{
            throw new Exception("Only @table annotation resolve object "+ob.getClass());
        }
        return result;
    }
       
    public RightStack createRightsFromJar(Class startclass) throws Exception {
        RightStack result= RightStack.getInstance();
        Collection string = JarScan.scanClasses(JarScan.getFilePathToClasses(startclass));

        for (Object cls : string) {
            Class cl = Class.forName(cls.toString());
            
            if (cl.isAnnotationPresent(support.commons.Controller.class)) {
                Object obj = cl.newInstance(); 
                Method[] methods=obj.getClass().getDeclaredMethods();
                for(Method method:methods){
                    if(method.isAnnotationPresent(support.commons.Right.class)){
                        result.add(cl.getName(), method.getName());
                    }
                }
                
            }
        }
        return result;
    }
    
    
    
    
    
    
}
