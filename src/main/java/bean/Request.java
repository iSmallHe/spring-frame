package bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    public String requestPath;
    public String requestType;

    @Override
    public int hashCode(){
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj){
        return EqualsBuilder.reflectionEquals(this,obj);
    }
}
