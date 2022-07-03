import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Book{
    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
    private String title;
    private boolean inPrint;
    @JsonDeserialize(using = ProcessParsing.class)
    private Date puplishDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isInPrint() {
        return inPrint;
    }

    public void setInPrint(boolean inPrint) {
        this.inPrint = inPrint;
    }

    public Date getPuplishDate() {
        return puplishDate;
    }

    public void setPuplishDate(Date puplishDate) {
        this.puplishDate = puplishDate;
    }
}
