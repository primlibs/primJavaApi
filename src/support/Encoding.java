/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

/**
 *
 * @author кот
 */
public class Encoding {
    private Type encoding;
    private Encoding (Type encoding) throws Exception{
        this.encoding=encoding;
    }

    public static Encoding getInstance(Type encoding) throws Exception{
        return new Encoding(encoding);
    }
    
    public Type getValue(){
        return encoding;
    }
    
    public static Type getEncodingFromString(String txt){
        String newString=StringAdapter.getString(txt);
        newString.toLowerCase();
        if(newString.equals("utf8")||newString.equals("utf-8")){
            return Type.utf8;
        }
        return Type.utf8;
    }
    
    
    public static enum Type{
        utf8
    }
    
    
}
