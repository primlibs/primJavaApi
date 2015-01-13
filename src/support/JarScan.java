/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

/**
 *
 * @author kot
 */
public class JarScan {
    private JarScan(){
        
    }
    

    public static Collection<String> scanClasses(String cp) throws IOException {
        Collection<String> classes = new ArrayList<String>();
        scan(cp, classes);
        return classes;
    }
    
    public static String getFilePathToClasses(Class cls)throws Exception{
        String result ="";
        String path=cls.getProtectionDomain().getCodeSource().getLocation().getPath();
        String pattern = Pattern.quote(System.getProperty("file.separator"));
        String[] splittedFileName = path.split(pattern);
        boolean exist=false;
        for(String res:splittedFileName){
            result+=System.getProperty("file.separator")+res;
            if(res.equals("classes")){
                exist=true;
                break;
            }
        }
        if(exist==false){
            throw new Exception("classes not found in path "+path+" by class"+cls.getName());
        }
        return result;
    }
    

    /**
     * Просканировать classpath и добавить все найденные классы в коллекцию
     */
    private static void scan(String cp, Collection<String> classes) throws IOException {
        String[] entries = cp.split(File.pathSeparator);

        for (String entryName : entries) {
            File file = new File(entryName);
            if (file.isDirectory()) {
                scanDir("", file, classes);
            } else if (file.getName().endsWith(".jar") || file.getName().endsWith(".zip")) {
                scanJar(file, classes);
            } else {
                throw new IOException("Unknown classpath entry " + file.getName());
            }
        }

    }

    /**
     * Сканировать директорию, представляющую пакет на предмет наличия
     * class-файлов.
     */
    private static void scanDir(String pkg, File dir, Collection<String> classes) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanDir(pkg + file.getName() + File.separator, file, classes);
            } else {
                scanFileName(pkg + file.getName(), classes);
            }
        }
    }

    /**
     * Проверить имя файла и извлечь имя класса
     */
    private static void scanFileName(String name, Collection<String> classes) {
        if (!name.endsWith(".class")) {
            return;
        }
        // Извлекаем имя класса из имени файла
        classes.add(name.substring(0, name.length() - 6).replace(File.separator, "."));
    }

    /**
     * Сканировать Jar-файл на предмет наличия class-файлов
     */
    private static void scanJar(File jarFile, Collection<String> classes) throws IOException {
        JarFile jar = new JarFile(jarFile);

        for (Enumeration<JarEntry> e = jar.entries(); e.hasMoreElements();) {
            JarEntry entry = e.nextElement();

            if (entry.isDirectory()) {
                continue;
            }

            scanFileName(jar.getName()+"."+entry.getName(), classes);
        }

        jar.close();
    }

    
    
}
