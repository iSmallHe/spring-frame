package bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@AllArgsConstructor
@Data
public class Param {

    private Map<String,Object> paramMap;

    public Long getLong(String name){
        return Long.valueOf(paramMap.get(name).toString());
    }

    public String getString(String name){
        return String.valueOf(paramMap.get(name));
    }
}
