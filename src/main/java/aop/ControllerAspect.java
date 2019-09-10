package aop;

import annotation.Aspect;
import annotation.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Date;

@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy{

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);

    @Override
    public void before(Class<?> clazz, Method method, Object[] params) throws Throwable {
        LOGGER.debug("开始时间"+new Date());
    }

    @Override
    public void after(Class<?> clazz, Method method, Object[] params) throws Throwable {
        LOGGER.debug("结束时间"+new Date());
    }
}
