import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.text.SimpleDateFormat;
import java.util.Date;
public class Person  {
    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
    private String name;
    @JsonDeserialize(using = ProcessParsing.class)
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return dateFormat.format(date);
    }

    public void setDate(Date  date) {
        this.date = date;
    }


}
