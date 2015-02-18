/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import support.enums.ValidatorTypes;
import support.filterValidator.ChainValidator;

/**
 *
 * @author кот
 */
public class StringAdapter {

    private static final Map<Character, String> charMap = new HashMap<Character, String>();

    static {
        charMap.put('А', "A");
        charMap.put('Б', "B");
        charMap.put('В', "V");
        charMap.put('Г', "G");
        charMap.put('Д', "D");
        charMap.put('Е', "E");
        charMap.put('Ё', "E");
        charMap.put('Ж', "Zh");
        charMap.put('З', "Z");
        charMap.put('И', "I");
        charMap.put('Й', "I");
        charMap.put('К', "K");
        charMap.put('Л', "L");
        charMap.put('М', "M");
        charMap.put('Н', "N");
        charMap.put('О', "O");
        charMap.put('П', "P");
        charMap.put('Р', "R");
        charMap.put('С', "S");
        charMap.put('Т', "T");
        charMap.put('У', "U");
        charMap.put('Ф', "F");
        charMap.put('Х', "H");
        charMap.put('Ц', "C");
        charMap.put('Ч', "Ch");
        charMap.put('Ш', "Sh");
        charMap.put('Щ', "Sh");
        charMap.put('Ъ', "'");
        charMap.put('Ы', "Y");
        charMap.put('Ь', "'");
        charMap.put('Э', "E");
        charMap.put('Ю', "U");
        charMap.put('Я', "Ya");
        charMap.put('а', "a");
        charMap.put('б', "b");
        charMap.put('в', "v");
        charMap.put('г', "g");
        charMap.put('д', "d");
        charMap.put('е', "e");
        charMap.put('ё', "e");
        charMap.put('ж', "zh");
        charMap.put('з', "z");
        charMap.put('и', "i");
        charMap.put('й', "i");
        charMap.put('к', "k");
        charMap.put('л', "l");
        charMap.put('м', "m");
        charMap.put('н', "n");
        charMap.put('о', "o");
        charMap.put('п', "p");
        charMap.put('р', "r");
        charMap.put('с', "s");
        charMap.put('т', "t");
        charMap.put('у', "u");
        charMap.put('ф', "f");
        charMap.put('х', "h");
        charMap.put('ц', "c");
        charMap.put('ч', "ch");
        charMap.put('ш', "sh");
        charMap.put('щ', "sh");
        charMap.put('ъ', "'");
        charMap.put('ы', "y");
        charMap.put('ь', "'");
        charMap.put('э', "e");
        charMap.put('ю', "u");
        charMap.put('я', "ya");
        charMap.put(' ', "_");
    }

    /**
     * переводит в верхний регистр первый символ строки
     *
     * @param string
     * @return
     */
    public static String ucFirst(String string) {
        char[] first = {string.charAt(0)};
        String stringFirst = String.valueOf(first);
        stringFirst = stringFirst.toUpperCase();
        String stringSecond = string.substring(1);
        String resultString = stringFirst + stringSecond;
        return resultString;
    }

    private StringAdapter() {
    }

    public static String getString(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return obj.toString();
        }
    }

    public static String getStackTraceException(Exception e) {
        String res = "";
        if (e != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            res = sw.toString();
            pw.close();
            try {
                sw.close();
            } catch (Exception ex) {
                res = "Ошибка при попытке заурыть поток";
            }
        }
        return res;
    }

    /**
     * возвращает true, если каждый из параметров не равен null и не равен
     * пустой строке
     *
     * @param params
     * @return
     */
    public static boolean NotNull(Object... params) {
        Boolean res = true;
        for (Object param : params) {
            if (res == true && param != null && !param.toString().equals("")) {
                res = true;
            } else {
                res = false;
            }
        }
        return res;
    }
    
    /**
     * возвращает true, если параметр равен null либо пустой строке
     *
     * @param param
     * @return
     */
    public static boolean isNull(Object param) {
        return (param == null || param.toString().isEmpty());
    }

    public static String getStringFromList(List<String> strList) {
        String result = "";
        for (String obj : strList) {
            result +=" "+obj;
        }
        return result;
    }
    
    public static String getStringFromObjectList(List<Object> strList) {
        String result = "";
        for (Object obj : strList) {
            result +=" "+StringAdapter.getString(obj);
        }
        return result;
    }

    public static String getStringFromArray(Object[] strArray) {
        String result = "";
        for (Object obj : strArray) {
            result += obj.toString();
        }
        return result;
    }

    public static Map<String, Object> cloneHashMap(Map<String, Object> request) {
        Map<String, Object> result = new HashMap();
        if (request != null) {
            for (String paramName : request.keySet()) {
                if (StringAdapter.NotNull(paramName)) {
                    result.put(paramName, request.get(paramName));
                }
            }
        }
        return result;
    }
    
     public static List<String> cloneList(List<String> request) {
        List<String> result = new ArrayList();
        if (request != null) {
            for (String param : request) {
                if (StringAdapter.NotNull(param)) {
                    result.add(param);
                }
            }
        }
        return result;
    }
     
     
    public static Double toDouble(String value){
        ChainValidator chVal= ChainValidator.getInstance(ValidatorTypes.DECIMALFILTER);
        chVal.execute(value);
        if(chVal.getErrors().isEmpty()){
            return Double.valueOf(getString(chVal.getData()));
        }else{
            return 0.00;
        }
    } 
    
    public static ChainValidator toDoubleChain(String value){
        ChainValidator chVal= ChainValidator.getInstance(ValidatorTypes.DECIMALFILTER);
        chVal.execute(value);
        return chVal;
    } 

}
