import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.sql.SQLException;

public interface ExplainInterface {
    public JsonNode addExplainToQuery(String query) throws SQLException, IOException;
    public JsonNode getExplainOutput(String query) throws SQLException, IOException;
    public JsonNode preprocessExplainString(String originalExplainString) throws JsonProcessingException;
    public JsonNode processExplainNodes(String originalExplainString) throws JsonProcessingException;
    public void addLinks(JsonNode plan, int nodecount, int childcount) throws JsonProcessingException;
    public JsonNode explainToIR(String originalExplainString);
}