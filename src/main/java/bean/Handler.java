package bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

@Data
@AllArgsConstructor
public class Handler {
    private Class<?> controllerClass;
    private Method requestMappingMethod;
}
