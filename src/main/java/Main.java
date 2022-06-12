import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public class Main {

    public static void main(String[] args) {

        String mySQLexplain="{\n" +
                "  \"query_block\": {\n" +
                "    \"select_id\": 1,\n" +
                "    \"cost_info\": {\n" +
                "      \"query_cost\": \"3.45\"\n" +
                "    },\n" +
                "    \"table\": {\n" +
                "      \"table_name\": \"payment\",\n" +
                "      \"access_type\": \"ALL\",\n" +
                "      \"rows_examined_per_scan\": 32,\n" +
                "      \"rows_produced_per_join\": 32,\n" +
                "      \"filtered\": \"100.00\",\n" +
                "      \"cost_info\": {\n" +
                "        \"read_cost\": \"0.25\",\n" +
                "        \"eval_cost\": \"3.20\",\n" +
                "        \"prefix_cost\": \"3.45\",\n" +
                "        \"data_read_per_join\": \"7K\"\n" +
                "      },\n" +
                "      \"used_columns\": [\n" +
                "        \"p_id\",\n" +
                "        \"p_total_receipt\",\n" +
                "        \"p_cash\",\n" +
                "        \"p_cash_back\",\n" +
                "        \"p_voucher\",\n" +
                "        \"p_paypal\",\n" +
                "        \"p_credit_debit\",\n" +
                "        \"p_ref\"\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}";

        String jsonSrc="{\"title\":\"the book.....\"," + "\"title2\":\"the book2.....\"}";


        try {

            JsonNode jsonNode= ProcessParsing.parse(jsonSrc);
            System.out.println(jsonNode.get("title2" ).asText());}
            catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }


}
