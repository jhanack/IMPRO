import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;

public class MapJSON {
    public static void main(String[] args) throws IOException {
        String jsonStr = "{\n" +
                "    \"Plan\": {\n" +
                "      \"Node Type\": \"Limit\",\n" +
                "      \"Parallel Aware\": false,\n" +
                "      \"Startup Cost\": 257743.27,\n" +
                "      \"Total Cost\": 257743.30,\n" +
                "      \"Plan Rows\": 10,\n" +
                "      \"Plan Width\": 48,\n" +
                "      \"Plans\": [\n" +
                "        {\n" +
                "          \"Node Type\": \"Sort\",\n" +
                "          \"Parent Relationship\": \"Outer\",\n" +
                "          \"Parallel Aware\": false,\n" +
                "          \"Startup Cost\": 257743.27,\n" +
                "          \"Total Cost\": 258537.04,\n" +
                "          \"Plan Rows\": 317506,\n" +
                "          \"Plan Width\": 48,\n" +
                "          \"Sort Key\": [\"(sum((l.l_extendedprice * ('1'::numeric - l.l_discount)))) DESC\", \"o.o_orderdate\"],\n" +
                "          \"Plans\": [\n" +
                "            {\n" +
                "              \"Node Type\": \"Aggregate\",\n" +
                "              \"Strategy\": \"Sorted\",\n" +
                "              \"Partial Mode\": \"Finalize\",\n" +
                "              \"Parent Relationship\": \"Outer\",\n" +
                "              \"Parallel Aware\": false,\n" +
                "              \"Startup Cost\": 209097.07,\n" +
                "              \"Total Cost\": 250882.08,\n" +
                "              \"Plan Rows\": 317506,\n" +
                "              \"Plan Width\": 48,\n" +
                "              \"Group Key\": [\"l.l_orderkey\", \"o.o_orderdate\", \"o.o_shippriority\"],\n" +
                "              \"Plans\": [\n" +
                "                {\n" +
                "                  \"Node Type\": \"Gather Merge\",\n" +
                "                  \"Parent Relationship\": \"Outer\",\n" +
                "                  \"Parallel Aware\": false,\n" +
                "                  \"Startup Cost\": 209097.07,\n" +
                "                  \"Total Cost\": 243605.91,\n" +
                "                  \"Plan Rows\": 264588,\n" +
                "                  \"Plan Width\": 48,\n" +
                "                  \"Workers Planned\": 2,\n" +
                "                  \"Plans\": [\n" +
                "                    {\n" +
                "                      \"Node Type\": \"Aggregate\",\n" +
                "                      \"Strategy\": \"Sorted\",\n" +
                "                      \"Partial Mode\": \"Partial\",\n" +
                "                      \"Parent Relationship\": \"Outer\",\n" +
                "                      \"Parallel Aware\": false,\n" +
                "                      \"Startup Cost\": 208097.04,\n" +
                "                      \"Total Cost\": 212065.86,\n" +
                "                      \"Plan Rows\": 132294,\n" +
                "                      \"Plan Width\": 48,\n" +
                "                      \"Group Key\": [\"l.l_orderkey\", \"o.o_orderdate\", \"o.o_shippriority\"],\n" +
                "                      \"Plans\": [\n" +
                "                        {\n" +
                "                          \"Node Type\": \"Sort\",\n" +
                "                          \"Parent Relationship\": \"Outer\",\n" +
                "                          \"Parallel Aware\": false,\n" +
                "                          \"Startup Cost\": 208097.04,\n" +
                "                          \"Total Cost\": 208427.78,\n" +
                "                          \"Plan Rows\": 132294,\n" +
                "                          \"Plan Width\": 28,\n" +
                "                          \"Sort Key\": [\"l.l_orderkey\", \"o.o_orderdate\", \"o.o_shippriority\"],\n" +
                "                          \"Plans\": [\n" +
                "                            {\n" +
                "                              \"Node Type\": \"Hash Join\",\n" +
                "                              \"Parent Relationship\": \"Outer\",\n" +
                "                              \"Parallel Aware\": true,\n" +
                "                              \"Join Type\": \"Inner\",\n" +
                "                              \"Startup Cost\": 41319.27,\n" +
                "                              \"Total Cost\": 193675.70,\n" +
                "                              \"Plan Rows\": 132294,\n" +
                "                              \"Plan Width\": 28,\n" +
                "                              \"Inner Unique\": false,\n" +
                "                              \"Hash Cond\": \"(l.l_orderkey = o.o_orderkey)\",\n" +
                "                              \"Plans\": [\n" +
                "                                {\n" +
                "                                  \"Node Type\": \"Seq Scan\",\n" +
                "                                  \"Parent Relationship\": \"Outer\",\n" +
                "                                  \"Parallel Aware\": true,\n" +
                "                                  \"Relation Name\": \"sf1_lineitem\",\n" +
                "                                  \"Alias\": \"l\",\n" +
                "                                  \"Startup Cost\": 0.00,\n" +
                "                                  \"Total Cost\": 146789.13,\n" +
                "                                  \"Plan Rows\": 1337620,\n" +
                "                                  \"Plan Width\": 20,\n" +
                "                                  \"Filter\": \"(l_shipdate > '1995-03-15'::date)\"\n" +
                "                                },\n" +
                "                                {\n" +
                "                                  \"Node Type\": \"Hash\",\n" +
                "                                  \"Parent Relationship\": \"Inner\",\n" +
                "                                  \"Parallel Aware\": true,\n" +
                "                                  \"Startup Cost\": 40546.60,\n" +
                "                                  \"Total Cost\": 40546.60,\n" +
                "                                  \"Plan Rows\": 61814,\n" +
                "                                  \"Plan Width\": 16,\n" +
                "                                  \"Plans\": [\n" +
                "                                    {\n" +
                "                                      \"Node Type\": \"Hash Join\",\n" +
                "                                      \"Parent Relationship\": \"Outer\",\n" +
                "                                      \"Parallel Aware\": true,\n" +
                "                                      \"Join Type\": \"Inner\",\n" +
                "                                      \"Startup Cost\": 4524.71,\n" +
                "                                      \"Total Cost\": 40546.60,\n" +
                "                                      \"Plan Rows\": 61814,\n" +
                "                                      \"Plan Width\": 16,\n" +
                "                                      \"Inner Unique\": false,\n" +
                "                                      \"Hash Cond\": \"(o.o_custkey = c.c_custkey)\",\n" +
                "                                      \"Plans\": [\n" +
                "                                        {\n" +
                "                                          \"Node Type\": \"Seq Scan\",\n" +
                "                                          \"Parent Relationship\": \"Outer\",\n" +
                "                                          \"Parallel Aware\": true,\n" +
                "                                          \"Relation Name\": \"sf1_orders\",\n" +
                "                                          \"Alias\": \"o\",\n" +
                "                                          \"Startup Cost\": 0.00,\n" +
                "                                          \"Total Cost\": 34621.50,\n" +
                "                                          \"Plan Rows\": 304753,\n" +
                "                                          \"Plan Width\": 20,\n" +
                "                                          \"Filter\": \"(o_orderdate < '1995-03-15'::date)\"\n" +
                "                                        },\n" +
                "                                        {\n" +
                "                                          \"Node Type\": \"Hash\",\n" +
                "                                          \"Parent Relationship\": \"Inner\",\n" +
                "                                          \"Parallel Aware\": true,\n" +
                "                                          \"Startup Cost\": 4366.25,\n" +
                "                                          \"Total Cost\": 4366.25,\n" +
                "                                          \"Plan Rows\": 12677,\n" +
                "                                          \"Plan Width\": 4,\n" +
                "                                          \"Plans\": [\n" +
                "                                            {\n" +
                "                                              \"Node Type\": \"Seq Scan\",\n" +
                "                                              \"Parent Relationship\": \"Outer\",\n" +
                "                                              \"Parallel Aware\": true,\n" +
                "                                              \"Relation Name\": \"sf1_customer\",\n" +
                "                                              \"Alias\": \"c\",\n" +
                "                                              \"Startup Cost\": 0.00,\n" +
                "                                              \"Total Cost\": 4366.25,\n" +
                "                                              \"Plan Rows\": 12677,\n" +
                "                                              \"Plan Width\": 4,\n" +
                "                                              \"Filter\": \"(c_mktsegment = 'BUILDING'::bpchar)\"\n" +
                "                                            }\n" +
                "                                          ]\n" +
                "                                        }\n" +
                "                                      ]\n" +
                "                                    }\n" +
                "                                  ]\n" +
                "                                }\n" +
                "                              ]\n" +
                "                            }\n" +
                "                          ]\n" +
                "                        }\n" +
                "                      ]\n" +
                "                    }\n" +
                "                  ]\n" +
                "                }\n" +
                "              ]\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    \"JIT\": {\n" +
                "      \"Worker Number\": -1,\n" +
                "      \"Functions\": 34,\n" +
                "      \"Options\": {\n" +
                "        \"Inlining\": false,\n" +
                "        \"Optimization\": false,\n" +
                "        \"Expressions\": true,\n" +
                "        \"Deforming\": true\n" +
                "      }\n" +
                "    }\n" +
                "  }";

        String first_link_string = "{\n" +
                "\t\t\t\"name\": \"Link NODE NAME 1\",\n" +
                "\t\t\t\"nodeName\": \"NODE NAME 1\",\n" +
                "\t\t\t\"direction\": \"SYNC\"\n" +
                "\t\t},";
        ObjectMapper firstLinkMapper = new ObjectMapper();
        JsonNode first_node_link = firstLinkMapper.readValue(first_link_string, JsonNode.class);

        jsonStr = jsonStr.replaceFirst("Plan","tree");
        jsonStr = jsonStr.replaceAll("Plans","children");
        jsonStr = jsonStr.replaceAll("Node Type","Node-Type");
        jsonStr = jsonStr.replaceAll("Parallel Aware","Parallel-Aware");
        jsonStr = jsonStr.replaceAll("Startup Cost","Startup-Cost");
        jsonStr = jsonStr.replaceAll("Total Cost","Total-Cost");
        jsonStr = jsonStr.replaceAll("Plan Rows","Plan-Rows");
        jsonStr = jsonStr.replaceAll("Plan Width","Plan-Width");
        jsonStr = jsonStr.replaceAll("Parent Relationship","Parent-Relationship");
        jsonStr = jsonStr.replaceAll("Partial Mode","Partial-Mode");
        jsonStr = jsonStr.replaceAll("Sort Key","Sort-Key");
        jsonStr = jsonStr.replaceAll("Group Key","Group-Key");
        jsonStr = jsonStr.replaceAll("Workers Planned","Workers-Planned");
        jsonStr = jsonStr.replaceAll("Join Type","Join-Type");
        jsonStr = jsonStr.replaceAll("Inner Unique","Inner-Unique");
        jsonStr = jsonStr.replaceAll("Hash Cond","Hash-Condition");
        jsonStr = jsonStr.replaceAll("Join Type","Join-Type");
        jsonStr = jsonStr.replaceAll("Relation Name","Relation-Name");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode tree = objectMapper.readValue(jsonStr, JsonNode.class);
        ((ObjectNode) tree).remove("JIT");
        JsonNode plan = tree.get("tree");
        ((ObjectNode) plan).put("link", first_node_link);
        ((ObjectNode) plan).put("nodeName", 1);
        int nodecount = 1;
        int childcount = 1;
        addLinks(plan, nodecount, childcount);
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File("Test_Output.json"), tree);

        //((ObjectNode) array2.get("Plans").get(0)).put("link", node_links);
        //JsonNode name2 = array2.get("link");
        //JsonNode name3 = name2.get("direction");
        //String techStr = name2.asText();
        //System.out.println(node);
        //System.out.println(name3);

    }

    public static void addLinks(JsonNode plan, int nodecount, int childcount) throws JsonProcessingException {
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

}