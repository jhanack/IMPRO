import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.sql.*;

public class MariaDB implements ExplainInterface {

    @Override
    public JsonNode addExplainToQuery(String query) throws SQLException, IOException {
        query = "EXPLAIN FORMAT=JSON " + query;
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
    public JsonNode preprocessExplainString(String originalExplainString) throws IOException {
        originalExplainString = originalExplainString.replaceFirst("query_block", "tree");
        originalExplainString = originalExplainString.replaceFirst("block-nl-join", "block_nl_join1");
        originalExplainString = originalExplainString.replaceFirst("block-nl-join", "block_nl_join2");
        originalExplainString = originalExplainString.replaceFirst("block-nl-join", "block_nl_join3");
        originalExplainString = originalExplainString.replaceFirst("block-nl-join", "block_nl_join4");
        originalExplainString = originalExplainString.replaceFirst("block-nl-join", "block_nl_join5");
        originalExplainString = originalExplainString.replaceFirst("block-nl-join", "block_nl_join6");
        originalExplainString = originalExplainString.replaceFirst("block-nl-join", "block_nl_join7");
        originalExplainString = originalExplainString.replaceFirst("block-nl-join", "block_nl_join8");
        originalExplainString = originalExplainString.replaceFirst("block-nl-join", "block_nl_join9");
        originalExplainString = originalExplainString.replaceFirst("block-nl-join", "block_nl_join10");
        //System.out.println(originalExplainString);
        return processExplainNodes(originalExplainString);
    }

    @Override
    public JsonNode processExplainNodes(String originalExplainString) throws IOException {
        String first_link_string = "{\n" +
                "\t\t\t\"name\": \"Link NODE NAME 1\",\n" +
                "\t\t\t\"nodeName\": \"NODE NAME 1\",\n" +
                "\t\t\t\"direction\": \"SYNC\"\n" +
                "\t\t},";
        String first_node_string = "{\n" +
                "\t\t\t\"sort_key\": \"empty\"\n" +
                "\t\t},";

        String subqueries_node_string = "{\n" +
                "\t\t\t\"Node_Type\": \"subqueries\"\n" +
                "\t\t},";

        ObjectMapper Mapper = new ObjectMapper();
        JsonNode first_node_link = Mapper.readValue(first_link_string, JsonNode.class);
        JsonNode first_node = Mapper.readValue(first_node_string, JsonNode.class);
        JsonNode subqueries_node = Mapper.readValue(subqueries_node_string, JsonNode.class);
        JsonNode tree = Mapper.readValue(originalExplainString, JsonNode.class);
        JsonNode plan = tree.get("tree");
        ((ObjectNode) plan).put("link", first_node_link);
        ((ObjectNode) plan).put("nodeName", "1");
        ((ObjectNode) plan).put("Node_Type", "filesort");
        ((ObjectNode) plan).remove("select_id");

        ((ObjectNode) plan).putArray("children");
        JsonNode children = ((ObjectNode) plan).get("children");
        ((ArrayNode) children).add(first_node);
        ((ObjectNode) children.get(0)).set("sort_key", ((ObjectNode) plan).get("filesort").get("sort_key"));
        ((ObjectNode) children.get(0)).put("Node_Type", "Sort");
        ((ObjectNode) children.get(0)).putArray("children");

        JsonNode children2 = ((ObjectNode) children.get(0)).get("children");
        for (int i = 0; i < plan.get("filesort").get("temporary_table").size(); i++) {
            //System.out.println(plan.get("filesort").get("temporary_table").size());
            if (i == 0) {
                ((ArrayNode) children2).add(plan.get("filesort").get("temporary_table").get("table"));
                ((ObjectNode) children2.get(i)).put("Node_Type", "temporary_table");
            } else {
                if (plan.get("filesort").get("temporary_table").get("block_nl_join" + Integer.toString(i)) == null) {
                    ((ObjectNode) plan.get("filesort").get("temporary_table").get("subqueries").get(0).get("expression_cache").get("query_block")).remove("select_id");
                    ((ArrayNode) children2).add(subqueries_node);
                    ((ObjectNode) children2.get(i)).putArray("children");
                    JsonNode children3 = ((ObjectNode) children2.get(i)).get("children");
                    for (int j = 0; j < plan.get("filesort").get("temporary_table").get("subqueries").get(0).get("expression_cache").get("query_block").size(); j++) {
                        //System.out.println(plan.get("filesort").get("temporary_table").get("subqueries").get(0).get("expression_cache").get("query_block").size());
                        if (j == 0) {
                            ((ArrayNode) children3).add(plan.get("filesort").get("temporary_table").get("subqueries").get(0).get("expression_cache").get("query_block")
                                    .get("table"));
                            ((ObjectNode) children3.get(j)).put("Node_Type", "table");
                        } else {
                            ((ArrayNode) children3).add(plan.get("filesort").get("temporary_table").get("subqueries").get(0).get("expression_cache").get("query_block")
                                    .get("block_nl_join" + Integer.toString(i+j-1)));
                            ((ObjectNode) children3.get(j)).putArray("children");
                            ((ObjectNode) children3.get(j)).put("Node_Type", "block_nl_join" + Integer.toString(i+j-1));
                            JsonNode children4 = ((ObjectNode) children3.get(j)).get("children");
                            ((ArrayNode) children4).add(plan.get("filesort").get("temporary_table").get("subqueries").get(0).get("expression_cache").get("query_block")
                                    .get("block_nl_join" + Integer.toString(i+j-1)).get("table"));
                            ((ObjectNode) children4.get(0)).put("Node_Type", "table");
                            ((ObjectNode) children3.get(j)).remove("table");
                        }
                    }

                    continue;
                }
                ((ArrayNode) children2).add(plan.get("filesort").get("temporary_table").get("block_nl_join" + Integer.toString(i)));
                ((ObjectNode) children2.get(i)).putArray("children");
                ((ObjectNode) children2.get(i)).put("Node_Type", "block_nl_join" + Integer.toString(i));
                JsonNode children5 = ((ObjectNode) children2.get(i)).get("children");
                ((ArrayNode) children5).add(plan.get("filesort").get("temporary_table").get("block_nl_join" + Integer.toString(i)).get("table"));
                ((ObjectNode) children5.get(0)).put("Node_Type", "table");
                ((ObjectNode) children2.get(i)).remove("table");

            }
        }
        ((ObjectNode) plan).remove("filesort");

        int nodecount = 1;
        int childcount = 1;
        addLinks(plan, nodecount, childcount);
        //ObjectWriter writer = Mapper.writer(new DefaultPrettyPrinter());
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
        if (plan.get("children") != null) {
            JsonNode children = plan.get("children");
            if (nodecount > 1) {
                follow_link_string = follow_link_string.replaceAll("x", Integer.toString(nodecount) + "." + Integer.toString(childcount));
                ((ObjectNode) plan).put("nodeName", Integer.toString(nodecount) + "." + Integer.toString(childcount));
            }
            else {
                follow_link_string = follow_link_string.replaceAll("x", Integer.toString(nodecount));
            }
            follow_link_string = follow_link_string.replaceAll("q", Integer.toString(nodecount + 1));
            for (int i = 0; i < children.size(); i++) {
                follow_link_string = follow_link_string.replaceAll("z", Integer.toString(i + 1));
                JsonNode node_links = linkMapper.readValue(follow_link_string, JsonNode.class);
                ((ObjectNode) children.get(i)).put("link", node_links);
                addLinks(children.get(i), nodecount + 1, i + 1);
            }
        }
    }


}