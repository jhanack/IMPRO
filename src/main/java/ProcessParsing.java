import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public  class ProcessParsing extends StdDeserializer<Date> {

    private static ObjectMapper objecctMappre = getObjectMapper();
public ProcessParsing(){
    this(null);
}
    public ProcessParsing(Class<?> vc) {
        super(vc);
    }

    private static ObjectMapper getObjectMapper() {
        ObjectMapper objecctMappre = new ObjectMapper();
        objecctMappre.registerModule(new SimpleModule());
        objecctMappre.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //in case some attributes are not on the class

        return objecctMappre;


    }

    public static JsonNode parse(String src) throws JsonProcessingException {

        return objecctMappre.readTree(src);
    }

    public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException {
        return objecctMappre.treeToValue(node, clazz);
    }

    public static JsonNode toJson(Object a) {
        return objecctMappre.valueToTree(a);
    }

    public static String stingfy(JsonNode node) throws JsonProcessingException {
        ObjectWriter objectWriter = objecctMappre.writer();
        return objectWriter.writeValueAsString(node);

    }

    public static String prettyPrint(JsonNode node) throws JsonProcessingException {
        ObjectWriter objectWriter = objecctMappre.writer();
        objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        return objectWriter.writeValueAsString(node);

    }

    public static String generateString(JsonNode node, boolean pretty) throws JsonProcessingException {
        if (pretty) {
            return prettyPrint(node);
        } else
            return stingfy(node);
    }
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String date =jsonParser.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }return  null;
    }
}