package loader;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class SpringTestClassLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringTestClassLoader.class);

    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class<?> loadClass(String className,boolean isInitialized){
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className,isInitialized,getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("loadclass failure"+className,e);
            throw new RuntimeException();
        }
        return clazz;
    }

    public static Class<?> loadClass(String className){
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className,true,getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("loadclass failure"+className,e);
            throw new RuntimeException();
        }
        return clazz;
    }

    public static Set<Class<?>> getClassSet(String packageName){
        Set<Class<?>> clazzSet = new HashSet<Class<?>>();
        URL url = null;
        try {
            Enumeration<URL> urlEnumeration = getClassLoader().getResources(packageName.replace(".", "/"));
            LOGGER.debug("urlEnumeration="+urlEnumeration.hasMoreElements());
            while(urlEnumeration.hasMoreElements()){
                url = urlEnumeration.nextElement();
                if(url != null){
                    String protocol = url.getProtocol();
                    LOGGER.debug("加载类的地址="+url.getPath());
                    if(protocol.equals("file")){
                        String packagePath = url.getPath().replaceAll("%20", " ");
                        addClass(clazzSet,packagePath,packageName);
                    }else if(protocol.equals("jar")){
                        JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();
                        if(jarURLConnection != null){
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if(jarFile != null){
                                Enumeration<JarEntry> entries = jarFile.entries();
                                while(entries.hasMoreElements()){
                                    JarEntry jarEntry = entries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if(jarEntryName.endsWith(".class")){
                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf("."));
                                        doAddClass(clazzSet,className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("load base package failue",e);
            throw new RuntimeException();
        }
        return clazzSet;
    }

    public static void addClass(Set<Class<?>> classSet,String packagePath,String packageName){
        LOGGER.debug("packagePath="+packagePath+",packageName"+packageName);
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return (pathname.isFile() && pathname.getName().endsWith(".class")) || pathname.isDirectory();
            }
        });
        for (File file:files) {
            String name = file.getName();
            if(file.isFile()){
                String className = name.substring(0, name.lastIndexOf("."));
                if(StringUtils.isNotBlank(packageName)){
                    className = packageName + "." + className;
                }
                doAddClass(classSet,className);
            }else{
                String subPackagePath = name;
                if(StringUtils.isNotBlank(packagePath)){
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = name;
                if(StringUtils.isNotBlank(packageName)){
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet,subPackagePath,subPackageName);
            }
        }
    }

    public static void doAddClass(Set<Class<?>> classSet,String className){
        Class<?> clazz = loadClass(className,true);
        classSet.add(clazz);
        LOGGER.debug("添加成功"+className);
    }
}
