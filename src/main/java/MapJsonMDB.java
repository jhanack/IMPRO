import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class MapJsonMDB implements ExplainInterface {

    public static void main(String[] args) throws IOException {
        String jsonStr = "{\n" +
                "  \"query_block\": {\n" +
                "    \"select_id\": 1,\n" +
                "    \"filesort\": {\n" +
                "      \"sort_key\": \"sum(l.l_extendedprice * (1 - l.l_discount)) desc, o.o_orderdate\",\n" +
                "      \"temporary_table\": {\n" +
                "        \"table\": {\n" +
                "          \"table_name\": \"c\",\n" +
                "          \"access_type\": \"ALL\",\n" +
                "          \"rows\": 147614,\n" +
                "          \"filtered\": 100,\n" +
                "          \"attached_condition\": \"c.c_mktsegment = 'BUILDING' and c.c_custkey is not null\"\n" +
                "        },\n" +
                "        \"block-nl-join\": {\n" +
                "          \"table\": {\n" +
                "            \"table_name\": \"o\",\n" +
                "            \"access_type\": \"hash_ALL\",\n" +
                "            \"key\": \"#hash#$hj\",\n" +
                "            \"key_length\": \"5\",\n" +
                "            \"used_key_parts\": [\"o_custkey\"],\n" +
                "            \"ref\": [\"impro_database.c.c_custkey\"],\n" +
                "            \"rows\": 1481253,\n" +
                "            \"filtered\": 100,\n" +
                "            \"attached_condition\": \"o.o_orderdate < DATE'1995-03-15'\"\n" +
                "          },\n" +
                "          \"buffer_type\": \"flat\",\n" +
                "          \"buffer_size\": \"256Kb\",\n" +
                "          \"join_type\": \"BNLH\",\n" +
                "          \"attached_condition\": \"o.o_custkey = c.c_custkey and o.o_orderkey is not null\"\n" +
                "        },\n" +
                "        \"block-nl-join\": {\n" +
                "          \"table\": {\n" +
                "            \"table_name\": \"l\",\n" +
                "            \"access_type\": \"hash_ALL\",\n" +
                "            \"key\": \"#hash#$hj\",\n" +
                "            \"key_length\": \"9\",\n" +
                "            \"used_key_parts\": [\"l_orderkey\"],\n" +
                "            \"ref\": [\"impro_database.o.o_orderkey\"],\n" +
                "            \"rows\": 5784708,\n" +
                "            \"filtered\": 100,\n" +
                "            \"attached_condition\": \"l.l_shipdate > DATE'1995-03-15'\"\n" +
                "          },\n" +
                "          \"buffer_type\": \"incremental\",\n" +
                "          \"buffer_size\": \"256Kb\",\n" +
                "          \"join_type\": \"BNLH\",\n" +
                "          \"attached_condition\": \"l.l_orderkey = o.o_orderkey\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";


        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode tree = objectMapper.readValue(jsonStr, JsonNode.class);
        ((ObjectNode) tree).remove("JIT");
        JsonNode plan = tree.get("tree");

        ((ObjectNode) plan).put("nodeName", 1);
        int nodecount = 1;
        int childcount = 1;

        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File("Test_Output.json"), tree);


    }

    @Override
    public JsonNode addExplainToQuery(String query) throws SQLException, IOException {
        query = "EXPLAIN (FORMAT JSON)" + query;
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

    @Override
    public JsonNode explainToIR(String originalExplainString) {
        return null;
    }
}