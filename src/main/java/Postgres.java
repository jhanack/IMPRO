import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.sql.*;

public class Postgres implements ExplainInterface{

    @Override
    public JsonNode addExplainToQuery(String query) throws SQLException, IOException {
        query = "EXPLAIN (FORMAT JSON)" + query;
        return getExplainOutput(query);
    }

    @Override
    public JsonNode getExplainOutput(String query) throws SQLException, IOException {
        Connection dbConn1;
        dbConn1 = DriverManager.getConnection("jdbc:postgresql://localhost:8080/impro_database", "impro_user", "impro_password");
        Statement stmt = dbConn1.createStatement();
        stmt.execute(query);
        ResultSet resultSet = stmt.getResultSet();
        resultSet.next();
        String retrievedName = resultSet.getString(1);
        //File file=new File("Q10_explain_PG.json");
        //file.createNewFile();
        //FileWriter fileWriter = new FileWriter(file);
        //fileWriter.write(retrievedName);
        //fileWriter.flush();
        //fileWriter.close();
        stmt.close();
        return preprocessExplainString(retrievedName);
    }

    @Override
    public JsonNode preprocessExplainString(String originalExplainString) throws JsonProcessingException {
        originalExplainString = originalExplainString.replaceFirst("\\[","");
        originalExplainString = originalExplainString.substring(0, originalExplainString. length()-1);
        originalExplainString = originalExplainString.replaceFirst("Plan","tree");
        originalExplainString = originalExplainString.replaceAll("Plans","children");
        originalExplainString = originalExplainString.replaceAll("Node Type","Node_Type");
        originalExplainString = originalExplainString.replaceAll("Parallel Aware","Parallel_Aware");
        originalExplainString = originalExplainString.replaceAll("Startup Cost","Startup_Cost");
        originalExplainString = originalExplainString.replaceAll("Total Cost","Total_Cost");
        originalExplainString = originalExplainString.replaceAll("Plan Rows","Plan_Rows");
        originalExplainString = originalExplainString.replaceAll("Plan Width","Plan_Width");
        originalExplainString = originalExplainString.replaceAll("Parent Relationship","Parent_Relationship");
        originalExplainString = originalExplainString.replaceAll("Partial Mode","Partial_Mode");
        originalExplainString = originalExplainString.replaceAll("Sort Key","Sort_Key");
        originalExplainString = originalExplainString.replaceAll("Group Key","Group_Key");
        originalExplainString = originalExplainString.replaceAll("Workers Planned","Workers_Planned");
        originalExplainString = originalExplainString.replaceAll("Join Type","Join_Type");
        originalExplainString = originalExplainString.replaceAll("Inner Unique","Inner_Unique");
        originalExplainString = originalExplainString.replaceAll("Hash Cond","Hash_Condition");
        originalExplainString = originalExplainString.replaceAll("Join Type","Join_Type");
        originalExplainString = originalExplainString.replaceAll("Relation Name","Relation_Name");
        System.out.println(originalExplainString);
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
        String follow_link_string = "{\n" +
                "\t\t\t\"name\": \"Link node x to q.z\",\n" +
                "\t\t\t\"nodeName\": \"NODE NAME q.z\",\n" +
                "\t\t\t\"direction\": \"SYNC\"\n" +
                "\t\t},";
        ObjectMapper linkMapper = new ObjectMapper();
        if (plan.get("Plans") != null) {
            JsonNode children = plan.get("Plans");
            if (nodecount > 1) {
                follow_link_string = follow_link_string.replaceAll("x", Integer.toString(nodecount) + "." + Integer.toString(childcount));
            }
            else {
                follow_link_string = follow_link_string.replaceAll("x", Integer.toString(nodecount));
            }
            follow_link_string = follow_link_string.replaceAll("q", Integer.toString(nodecount + 1));
            ((ObjectNode) plan).put("nodeName", Integer.toString(nodecount + 1) + "." + Integer.toString(childcount));
            for (int i = 0; i < children.size(); i++) {
                follow_link_string = follow_link_string.replaceAll("z", Integer.toString(i + 1));
                JsonNode node_links = linkMapper.readValue(follow_link_string, JsonNode.class);
                ((ObjectNode) children.get(i)).put("link", node_links);
                addLinks(children.get(i), nodecount + 1, i + 1);
            }
        }
    }

    @Override
    public JsonNode explainToIR(String originalExplainString) {
        return null;
    }
}
