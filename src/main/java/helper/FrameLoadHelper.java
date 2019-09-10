package helper;


import loader.SpringTestClassLoader;

public class FrameLoadHelper {

    public static void init(){
        Class<?>[] classArray = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class};
        for(Class clazz : classArray){
            SpringTestClassLoader.loadClass(clazz.getName());
        }
    }
}
