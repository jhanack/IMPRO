import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.sql.*;

public class MariaDB implements ExplainInterface {

    @Override
    public JsonNode addExplainToQuery(String query) throws SQLException, IOException {
        query = "EXPLAIN FORMAT=JSON" + query;
        return getExplainOutput(query);
    }

    @Override
    public JsonNode getExplainOutput(String query) throws SQLException, IOException {
        Connection dbConn1;
        dbConn1 = DriverManager.getConnection("jdbc:mariadb://localhost:8083/impro_database", "impro_user", "impro_password");
        Statement stmt = dbConn1.createStatement();
        stmt.execute(query);
        ResultSet resultSet = stmt.getResultSet();
        resultSet.next();
        String retrievedName = resultSet.getString(1);
        stmt.close();
        return preprocessExplainString(retrievedName);
    }

    @Override
    public JsonNode preprocessExplainString(String originalExplainString) throws JsonProcessingException {
        originalExplainString = originalExplainString.replaceFirst("query_block", "tree");
        originalExplainString = originalExplainString.replaceAll("block-nl-join ", "link");
        originalExplainString = originalExplainString.replaceAll("sort_key", "Sort Key(s)");
        originalExplainString = originalExplainString.replaceAll("temporary_table" + ":  ", "children"+" : [");
        return processExplainNodes(originalExplainString);
    }

    @Override
    public JsonNode processExplainNodes(String originalExplainString) throws JsonProcessingException {
        String first_link_string = "{\n" +
                "\t\t\t\"name\": \"Link NODE NAME 1\",\n" +
                "\t\t\t\"nodeName\": \"NODE NAME 1\",\n" +
                "\t\t\t\"direction\": \"SYNC\"\n" +
                "\t\t},";
        ObjectMapper firstLinkMapper = new ObjectMapper();
        JsonNode first_node_link = firstLinkMapper.readValue(first_link_string, JsonNode.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode tree = objectMapper.readValue(originalExplainString, JsonNode.class);
        ((ObjectNode) tree).remove("JIT");
        JsonNode plan = tree.get("tree");
        ((ObjectNode) plan).put("link", first_node_link);
        ((ObjectNode) plan).put("nodeName", 1);
        int nodecount = 1;
        int childcount = 1;
        addLinks(plan, nodecount, childcount);
        //ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        //writer.writeValue(new File("Test_Output.json"), tree);
        return tree;

    }

    @Override
    public void addLinks(JsonNode plan, int nodecount, int childcount) throws JsonProcessingException {

    }
}