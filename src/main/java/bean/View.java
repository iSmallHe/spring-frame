package bean;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class View {
    private String path;
    private Map<String,Object> model;

    public View(){
        model = new HashMap<String,Object>();
    }
    public View(String path){
        this.path = path;
        model = new HashMap<String,Object>();
    }

    public void addModel(String name,Object value){
        model.put(name,value);
    }
}
